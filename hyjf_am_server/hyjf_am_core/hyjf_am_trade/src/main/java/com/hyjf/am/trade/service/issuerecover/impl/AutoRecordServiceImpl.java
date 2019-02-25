package com.hyjf.am.trade.service.issuerecover.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.issuerecover.AutoRecordService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 汇计划备案
 * 
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 16:30
 */
@Service
public class AutoRecordServiceImpl extends BaseServiceImpl implements AutoRecordService {
	private static final Logger logger = LoggerFactory.getLogger(AutoRecordServiceImpl.class);

	@Autowired
	private CommonProducer commonProducer;

	@Override
	public boolean updateRecordBorrow(Borrow borrow, BorrowInfo borrowInfo) {
		// 备案，需要更新资产表
		JSONObject jsonObject = this.debtRegist(borrow, borrowInfo);

		// 更新资产表为初审中
		if ("0".equals(jsonObject.get("success"))) {
			return true;
			// 重复失败的情况
		} else if ("2".equals(jsonObject.get("success"))) {
			logger.warn("备案失败 " + borrow.getBorrowNid() + " 原因：" + jsonObject.get("msg"));
		} else {
			logger.warn("备案失败 " + borrow.getBorrowNid() + " 原因：" + jsonObject.get("msg"));
			this.sendWarnSms(borrow.getBorrowNid());
		}
		return false;
	}

	@Override
	public boolean updateRecordBorrow(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType) {
		boolean result = false;
		// 备案，需要更新资产表
		JSONObject jsonObject = this.debtRegist(hjhPlanAsset.getBorrowNid());

		// 更新资产表为初审中
		if ("0".equals(jsonObject.get("success"))) {
			HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
			hjhPlanAssetnew.setId(hjhPlanAsset.getId());

			// 受托支付，更新为待授权
			if (hjhPlanAsset.getEntrustedFlg() != null && hjhPlanAsset.getEntrustedFlg().intValue() == 1) {
				hjhPlanAssetnew.setStatus(4);// 4 待授权
			} else {
				hjhPlanAssetnew.setStatus(5);// 初审中
			}
			hjhPlanAssetnew.setUpdateTime(new Date());
			hjhPlanAssetnew.setUpdateUserId(1);
			result = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew) > 0 ? true
					: false;
			// 重复失败的情况
		} else if ("2".equals(jsonObject.get("success"))) {
			logger.warn("备案失败 " + hjhPlanAsset.getBorrowNid() + " 原因：" + jsonObject.get("msg"));
		} else {
			logger.warn("备案失败 " + hjhPlanAsset.getBorrowNid() + " 原因：" + jsonObject.get("msg"));
			this.sendWarnSms(hjhPlanAsset.getBorrowNid());
		}
		return result;
	}

	/**
	 * 备案
	 * 
	 * @param borrowNid
	 * @return
	 */
	private JSONObject debtRegist(String borrowNid) {
		// 返回结果
		JSONObject result = new JSONObject();
		// 获取相应的标的信息
		Borrow borrow = this.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		if (borrow == null || borrowInfo == null) {
			result.put("success", "1");
			result.put("msg", "标的编号不存在！");
			logger.warn("标的：" + borrowNid + " 标的编号不存在!");
			return result;
		}
		return debtRegist(borrow, borrowInfo);
	}

	/**
	 * 备案
	 * 
	 * @param borrow
	 * @return
	 */
	private JSONObject debtRegist(Borrow borrow, BorrowInfo borrowInfo) {
		// 返回结果
		JSONObject result = new JSONObject();

		// 检查是否备案失败，如果是，跳过
		if (borrow.getStatus() == 0 && borrow.getRegistStatus() == 4) {
			logger.warn("标的：" + borrow.getBorrowNid() + " 自动备案失败过");
			result.put("success", "2");
			result.put("msg", "自动备案失败过！");
			return result;
		}

		Account account = this.getAccount(borrow.getUserId());
		if (null == account || StringUtils.isBlank(account.getAccountId())) {
			logger.warn("标的：" + borrow.getBorrowNid() + " 借款人信息错误");
			result.put("success", "1");
			result.put("msg", "借款人信息错误！");
			return result;
		}

		// 更新相应的标的状态为备案中
		boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1);
		if (!debtRegistingFlag) {
			logger.warn("标的：" + borrow.getBorrowNid() + " 更新相应的标的信息失败,请稍后再试");
			result.put("success", "1");
			result.put("msg", "更新相应的标的信息失败,请稍后再试！");
			return result;
		}

		String entrustFlag = "";
		String stAccountId = "";
		String repayOrgAccountId = "";
		// 受托支付
		if (borrowInfo.getEntrustedFlg() != null && borrowInfo.getEntrustedFlg().intValue() == 1) {
			StzhWhiteList sTZHWhiteList = getStzhWhiteList(borrowInfo.getEntrustedUserId(), borrowInfo.getInstCode());
			if (sTZHWhiteList != null) {
				entrustFlag = borrowInfo.getEntrustedFlg().toString();
				stAccountId = sTZHWhiteList.getStAccountId();
				/*----------------upd by liushouyi HJH3 Start-----------------------*/
			} else {
				// 原逻辑三方资产此处未处理、推送资产时已校验过白名单、手动录标的时候白名单依然做校验处理
				this.updateBorrowRegist(borrow, 0, 4);
				result.put("error", "1");
				result.put("msg", "受托白名单查询为空！");
				return result;
			}
			/*----------------upd by liushouyi HJH3 End-----------------------*/

		}

		if (borrowInfo.getRepayOrgUserId() != null) {
			Account repayOrgAccount = this.getAccount(borrowInfo.getRepayOrgUserId());
			if (repayOrgAccount != null && StringUtils.isNotBlank(repayOrgAccount.getAccountId())) {
				repayOrgAccountId = repayOrgAccount.getAccountId();
			}
		}

		BankCallBean debtRegistBean = this.buildBankCallBean(borrow, borrowInfo, account.getAccountId(), entrustFlag,
				stAccountId, repayOrgAccountId);
		try {
			BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
			String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
			if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {

				// 如果是受托支付 备案成功时更改状态
				int status = 1;
				if (borrowInfo.getEntrustedFlg() != null && borrowInfo.getEntrustedFlg().intValue() == 1) {
					status = 7;
				}

				boolean debtRegistedFlag = this.updateBorrowRegist(borrow, status, 2);
				if (debtRegistedFlag) {
					result.put("success", "0");
					result.put("msg", "备案成功！");
				} else {
					result.put("success", "1");
					result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
				}
			} else {
				this.updateBorrowRegist(borrow, 0, 4);
				String message = registResult.getRetMsg();
				result.put("success", "1");
				result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.updateBorrowRegist(borrow, 0, 4);
			result.put("success", "1");
			result.put("msg", "银行备案接口调用失败！");
		}
		return result;
	}

	private void sendWarnSms(String borrowNid) {
		// 备案失败发送短信 TODO: 没有配合模板
		// 您好，有一笔标的备案失败，请及时关注！
		Map<String, String> replaceStrs = new HashMap<String, String>();
		replaceStrs.put("val_title", borrowNid);
		SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER,
				null, CustomConstants.PARAM_TPL_NOTICE_BORROW_RECORD_FAIL, CustomConstants.CHANNEL_TYPE_NORMAL);
		try {
			commonProducer.messageSend(
					new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
		} catch (MQException e2) {
			logger.error("发送备案失败短信失败..", e2);
		}
	}

	private BankCallBean buildBankCallBean(Borrow borrow, BorrowInfo borrowInfo, String accountId, String entrustFlag,
			String stAccountId, String repayOrgAccountId) {
		// 获取共同参数
		String channel = BankCallConstant.CHANNEL_PC;
		String orderId = GetOrderIdUtils.getOrderId2(borrow.getUserId());
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);

		// 调用开户接口
		BankCallBean debtRegistBean = new BankCallBean();
		// 接口版本号
		debtRegistBean.setVersion(BankCallConstant.VERSION_10);
		// 消息类型(用户开户)
		debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);
		debtRegistBean.setTxDate(txDate);
		debtRegistBean.setTxTime(txTime);
		debtRegistBean.setSeqNo(seqNo);
		debtRegistBean.setChannel(channel);
		// 借款人电子账号
		debtRegistBean.setAccountId(accountId);
		// 标的表id
		debtRegistBean.setProductId(borrow.getBorrowNid());
		// 标的名称
		debtRegistBean.setProductDesc(borrowInfo.getName());
		// 募集日,标的保存时间
		debtRegistBean.setRaiseDate(borrowInfo.getBankRaiseStartDate());
		// 募集结束日期
		debtRegistBean.setRaiseEndDate(borrowInfo.getBankRaiseEndDate());
		// 項目还款方式
		String borrowStyle = borrow.getBorrowStyle();
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {
			// 付息方式没有不确定日期
			debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);
		} else {
			debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
		}
		// (借款期限,天数）
		debtRegistBean.setDuration(String.valueOf(borrowInfo.getBankBorrowDays()));
		// 交易金额
		debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));
		// 年华利率
		debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));

		if (StringUtils.isNotBlank(entrustFlag)) {
			debtRegistBean.setEntrustFlag(entrustFlag);
		}
		if (StringUtils.isNotBlank(stAccountId)) {
			debtRegistBean.setReceiptAccountId(stAccountId);
		}
		if (StringUtils.isNotBlank(repayOrgAccountId)) {
			debtRegistBean.setBailAccountId(repayOrgAccountId);
		}

		debtRegistBean.setLogOrderId(orderId);
		debtRegistBean.setLogOrderDate(orderDate);
		debtRegistBean.setLogUserId(String.valueOf(borrow.getUserId()));
		debtRegistBean.setLogRemark("借款人标的登记");
		debtRegistBean.setLogClient(0);
		return debtRegistBean;
	}

	private StzhWhiteList getStzhWhiteList(Integer StUserId, String instCode) {
		StzhWhiteListExample sTZHWhiteListExample = new StzhWhiteListExample();
		StzhWhiteListExample.Criteria sTZHCra = sTZHWhiteListExample.createCriteria();
		sTZHCra.andStUserIdEqualTo(StUserId);
		sTZHCra.andInstCodeEqualTo(instCode);
		/*----------------upd by liushouyi HJH3 Start-----------------------*/
		sTZHCra.andDelFlagEqualTo(0);
		sTZHCra.andStateEqualTo(1);
		/*----------------upd by liushouyi HJH3 End-----------------------*/
		List<StzhWhiteList> sTZHWhiteList = this.stzhWhiteListMapper.selectByExample(sTZHWhiteListExample);
		return CollectionUtils.isEmpty(sTZHWhiteList) ? null : sTZHWhiteList.get(0);
	}

	private boolean updateBorrowRegist(Borrow borrow, int status, int registStatus) {
		Date nowDate = new Date();
		BorrowExample example = new BorrowExample();
		example.createCriteria().andIdEqualTo(borrow.getId()).andStatusEqualTo(borrow.getStatus())
				.andRegistStatusEqualTo(borrow.getRegistStatus());
		borrow.setRegistStatus(registStatus);
		borrow.setStatus(status);
		// add by liuyang 合规自查 20181119 start
		borrow.setVerifyStatus(1);//跳过已交保证金状态
		// add by liuyang 合规自查 20181119 end
		borrow.setRegistUserId(1);// id写死1
		borrow.setRegistUserName("AutoRecord");
		borrow.setRegistTime(nowDate);
		return this.borrowMapper.updateByExampleSelective(borrow, example) > 0 ? true : false;
	}
}
