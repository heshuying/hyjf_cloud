package com.hyjf.am.trade.controller.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RtbIncreaseRepayRequest;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoan;
import com.hyjf.am.trade.mq.MailProducer;
import com.hyjf.am.trade.mq.Producer;
import com.hyjf.am.trade.mq.SmsProducer;
import com.hyjf.am.trade.service.BorrowApicronService;
import com.hyjf.am.trade.service.IncreaseInterestInvestService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version RtbRepayController, v0.1 2018/6/19 14:42
 */

@RestController
@RequestMapping("/am-trade/batch/rtb")
public class RtbRepayController {
	private static final Logger logger = LoggerFactory.getLogger(RtbRepayController.class);

	/**
	 * 任务执行次数
	 */
	public Map<String, TimesBean> runTimes = new HashMap<String, TimesBean>();

	@Autowired
	BorrowApicronService borrowApicronService;
	@Autowired
	IncreaseInterestInvestService increaseInterestService;
	@Autowired
	private MailProducer mailProducer;
	@Autowired
	private SmsProducer smsProducer;

	@Value("${hyjf.env.test}")
	private boolean envTest;

	@RequestMapping("/getRepayAmount/{borrowNid}/{borrowStyle}/{periodNow}")
	public BigDecimal getRtbRepayAmount(@PathVariable String borrowNid, @PathVariable String borrowStyle,
			@PathVariable Integer periodNow) {
		// 取得融通宝还款金额
		return increaseInterestService.selectBorrowAccountWithPeriod(borrowNid, borrowStyle, periodNow);
	}

	@RequestMapping("/increaseInterestRepay")
	public void rtbInterestRepay(@RequestBody @Valid RtbIncreaseRepayRequest request) {
		BorrowApicronVO borrowApicron = request.getBorrowApicronVO();

		Long startTime = GetDate.getMillis();
		// 错误信息
		StringBuffer sbError = new StringBuffer();
		int errorCnt = 0;
		try {
			logger.info("融通宝加息自动还款任务开始。[借款编号:" + borrowApicron.getBorrowNid() + "]");
			// 更新任务API状态为进行中
			Integer updateFlag = borrowApicronService.updateBorrowApicron(borrowApicron.getId(),
					CommonConstant.APICRON_EXTRA_YIELD_REPAY_STATUS_RUNNING);
			if (updateFlag < 1) {
				throw new RuntimeException("更新apicron失败...apicron is :{}" + JSONObject.toJSON(borrowApicron));
			}
			// 借款编号
			String borrowNid = borrowApicron.getBorrowNid();
			// 借款人ID
			Integer borrowUserId = borrowApicron.getUserId();
			// 取得还款明细列表
			List<IncreaseInterestLoan> increaseInterestLoans = increaseInterestService
					.selectIncreaseInterestLoanList(borrowNid);
			if (!CollectionUtils.isEmpty(increaseInterestLoans)) {

				// 循环还款列表
				for (IncreaseInterestLoan increaseInterestLoan : increaseInterestLoans) {
					try {
						// 自动还款
						increaseInterestService.increaseInterestRepay(borrowApicron, increaseInterestLoan,
								request.getAccount(), request.getCompanyAccount());
					} catch (Exception e) {
						sbError.append(e.getMessage()).append("<br/>");
						logger.info("exception is :" + e);
						errorCnt++;
					}
				}

				// 还款有错误时
				if (errorCnt > 0) {
					throw new Exception("融通宝加息还款时发生错误。" + "[借款编号：" + borrowNid + "]，" + "[错误件数：" + errorCnt + "]");
				} else {
					// 更新最后还款状态
					this.increaseInterestService.updateRepay(borrowNid, borrowApicron.getPeriodNow(), borrowUserId);
					// 发送成功短信
					Map<String, String> replaceStrs = new HashMap<String, String>();
					replaceStrs.put("val_title", borrowApicron.getBorrowNid());
					replaceStrs.put("val_time", GetDate.formatTime());
					SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
							MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_HUANKUAN_SUCCESS,
							CustomConstants.CHANNEL_TYPE_NORMAL);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
					} catch (MQException e1) {
						logger.error("发送短信失败..", e1);
					}
				}
			} else {
				logger.error("还款明细件数为0件。[标号：" + borrowNid + "]");
			}
			// 更新任务API状态为完成
			this.borrowApicronService.updateBorrowApicron(borrowApicron.getId(),
					CommonConstant.APICRON_EXTRA_YIELD_REPAY_STATUS_FINISH);
		} catch (Exception e) {
			int runCnt = 1;
			if (runTimes.containsKey(borrowApicron.getBorrowNid())) {
				TimesBean bean = runTimes.get(borrowApicron.getBorrowNid());
				bean.setCnt(bean.getCnt() + 1);
				bean.setTime(GetDate.getMyTimeInMillis());
				runCnt = bean.getCnt();
				runTimes.put(borrowApicron.getBorrowNid(), bean);
			} else {
				TimesBean bean = new TimesBean();
				bean.setCnt(runCnt);
				bean.setTime(GetDate.getMyTimeInMillis());
				bean.setStatus(1);
				runTimes.put(borrowApicron.getBorrowNid(), bean);
			}
			if (runCnt >= 3) {
				// 清除重新还款任务
				runTimes.remove(borrowApicron.getBorrowNid());
				if (sbError.length() == 0) {
					sbError.append(e.getMessage());
				}
				// 更新任务API状态为错误
				borrowApicronService.updateBorrowApicron(borrowApicron.getId(),
						CommonConstant.APICRON_EXTRA_YIELD_REPAY_STATUS_ERROR, sbError.toString());
				// 发送失败短信
				Borrow borrow = increaseInterestService.selectBorrowByNid(borrowApicron.getBorrowNid());
				// 是否分期(true:分期, false:单期)
				boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle())
						|| CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
						|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle());
				Map<String, String> replaceStrs = new HashMap<String, String>();
				replaceStrs.put("val_title", borrow.getBorrowNid());
				replaceStrs.put("val_period", isMonth ? "第" + borrowApicron.getPeriodNow() + "期" : "");
				replaceStrs.put("val_package_error", String.valueOf(errorCnt));

				SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMSSENDFORMANAGER,
						null, CustomConstants.PARAM_TPL_HUANKUAN_FAILD, CustomConstants.CHANNEL_TYPE_NORMAL);
				try {
					smsProducer.messageSend(
							new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
				} catch (MQException e1) {
					logger.error("发送短信失败..", e1);
				}
			} else {
				// 更新任务API状态为重新执行
				borrowApicronService.updateBorrowApicron(borrowApicron.getId(),
						CommonConstant.APICRON_EXTRA_YIELD_REPAY_STATUS_INIT);
			}

			this.sendFailMail(borrowApicron.getBorrowNid(), borrowApicron.getPeriodNow(), runCnt, e.getMessage(),
					sbError.toString());
			logger.error("融通宝加息自动还款任务发生错误。[订单号：" + borrowApicron.getBorrowNid() + "]");
		} finally {
			Long endTime = GetDate.getMillis();
			logger.info("融通宝加息自动还款任务结束。[订单号：" + borrowApicron.getBorrowNid() + "]， 耗时：" + (endTime - startTime) / 1000
					+ "s");
		}

	}

	/**
	 * 融通宝加息自动还款任务失败发送邮件通知相关人员
	 * 
	 * @param borrowNid
	 * @param periodNow
	 * @param runCnt
	 * @param errorMessage
	 * @param errorDetail
	 */
	private void sendFailMail(String borrowNid, Integer periodNow, int runCnt, String errorMessage,
			String errorDetail) {
		// 发送错误邮件
		StringBuffer msg = new StringBuffer();
		msg.append("借款标号：").append(borrowNid).append("<br/>");
		msg.append("当前期数：").append(periodNow).append("<br/>");
		msg.append("还款时间：").append(GetDate.formatTime()).append("<br/>");
		msg.append("执行次数：").append("第" + runCnt + "次").append("<br/>");
		msg.append("错误信息：").append(errorMessage).append("<br/>");
		msg.append("详细错误信息：<br/>").append(errorDetail);

		String[] toMail;

		if (envTest) {
			toMail = new String[] { "jiangying@hyjf.com", "liudandan@hyjf.com" };
		} else {
			toMail = new String[] { "zhangjinpeng@hyjf.com", "gaohonggang@hyjf.com" };
		}
		MailMessage mailMessage = new MailMessage(null, null,
				borrowNid + "-" + periodNow + "融通宝加息还款,第" + runCnt + "次还款失败", msg.toString(), null, toMail, null,
				MessageConstant.MAILSENDFORMAILINGADDRESSMSG);
		try {
			mailProducer.messageSend(new Producer.MassageContent(MQConstant.MAIL_TOPIC, JSON.toJSONBytes(mailMessage)));
		} catch (MQException e) {
			logger.error("发送邮件失败..", e);
		}
	}

	class TimesBean {
		private Integer cnt;
		private Integer time;
		private Integer status;

		public Integer getCnt() {
			return cnt;
		}

		public void setCnt(Integer cnt) {
			this.cnt = cnt;
		}

		public Integer getTime() {
			return time;
		}

		public void setTime(Integer time) {
			this.time = time;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}
	}

}
