/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.tender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.api.AutoTenderRequestBean;
import com.hyjf.cs.trade.bean.api.AutoTenderResultBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.tender.TenderService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author libin
 * @version AutoTenderServer.java, v0.1 2018年8月24日 上午10:36:59
 */
@Api(value = "AEMS系统-手动投资散标",tags = "AEMS系统-手动投资散标")
@Controller
@RequestMapping("/hyjf-api/aems/tender")
public class AemsAutoTenderServer extends BaseTradeController{
	
	@Autowired
	private TenderService tenderService;
	
    @Autowired
    SystemConfig systemConfig;

	@ResponseBody
    @ApiOperation(value = "手动投资散标", notes = "手动投资散标")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    public AutoTenderResultBean autoTender(@RequestBody AutoTenderRequestBean autoTenderRequestBean, HttpServletRequest request, HttpServletResponse response) {
    	
    	logger.info("投资输入参数.... autoTenderRequestBean is :{}", JSONObject.toJSONString(autoTenderRequestBean));
    	
    	//初始化请求组合体
    	AutoTenderComboRequest autoTenderComboRequest = new AutoTenderComboRequest();
    	
		AutoTenderResultBean resultBean = new AutoTenderResultBean();
		resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
		resultBean.setStatusDesc("请求参数非法");
    	
		// ————> 验证请求参数
		if (Validator.isNull(autoTenderRequestBean) || Validator.isNull(autoTenderRequestBean.getInstCode())
				|| Validator.isNull(autoTenderRequestBean.getMoney()) 
				|| autoTenderRequestBean.getAccountId() == null 
				|| Validator.isNull(autoTenderRequestBean.getBorrowNid())
				|| Validator.isNull(autoTenderRequestBean.getChkValue())
				|| Validator.isNull(autoTenderRequestBean.getTimestamp())) {
			logger.info("请求参数非法-------" + autoTenderRequestBean);
			return resultBean;
		}

        //新验签
        if(!SignUtil.AEMSVerifyRequestSign(autoTenderRequestBean, "/aems/tender/tender")){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("验签失败！---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: " + autoTenderRequestBean.getBorrowNid());
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }
		
        logger.info("开始自动投资，请求参数 " + JSON.toJSONString(autoTenderRequestBean));
		
		String couponGrantId = "";
		String bizAccount = autoTenderRequestBean.getAccountId();
		// 借款borrowNid
		String borrowNid = autoTenderRequestBean.getBorrowNid();
		// 投资金额
		String accountStr = autoTenderRequestBean.getMoney();
		if (accountStr == null || "".equals(accountStr)) {
			accountStr = "0";
		}
        
		// ————> 投资校验
		JSONObject result = tenderService.checkAutoTenderParam(borrowNid, accountStr, bizAccount, "0", couponGrantId);
		
		if (result == null) {
			logger.info("投资校验失败---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid());
			resultBean.setStatusForResponse("TZ000099");
			resultBean.setStatusDesc("投资失败,系统错误");
			return resultBean;
		} else if (result.get("error") != null && result.get("error").equals("1")) {
			logger.info("投资校验失败，原因: "+result.get("data").toString()+"  "+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid());
			resultBean.setStatusForResponse("TZ000096");
			resultBean.setStatusDesc("投资失败，原因: "+result.get("data").toString());
			return resultBean;
		}
		
		
		logger.info("1.到此说明投资校验成功！！");
		
		
		// 根据借款Id检索标的信息
		/*原BorrowWithBLOBs borrow = this.tenderService.getBorrowByNid(borrowNid);*/
		BorrowAndInfoVO borrow = this.tenderService.getBorrowByNid(borrowNid);
		if (borrow == null || borrow.getBorrowNid() == null) {
			resultBean.setStatusForResponse("TZ000001");
			resultBean.setStatusDesc("标的编号不存在！");
			return resultBean;
		}
		
		String account = accountStr;
		String tenderUsrcustid = result.getString("tenderUsrcustid");
		String tenderUserName = result.getString("tenderUserName");
		String autoOrderId = result.getString("tenderAutoOrderId");
		Integer userId = Integer.parseInt(result.getString("userId"));
		
		// 生成订单
		String orderId = GetOrderIdUtils.getOrderId2(userId);
		// ————> 写日志 调用投资接口
		Boolean flag = false;
		BankCallBean registResult = null;
		
		// 将必要参数传入 autoTenderComboRequest(类变量)
		autoTenderComboRequest.setBorrowNid(borrowNid);
		// 注意：生成订单号换名了
		autoTenderComboRequest.setGenerateOrderId(orderId);
		autoTenderComboRequest.setUserId(userId);
		autoTenderComboRequest.setAccount(account);
		autoTenderComboRequest.setIpAddress(GetCilentIP.getIpAddr(request));
		autoTenderComboRequest.setCouponGrantId(couponGrantId);
		autoTenderComboRequest.setTenderUserName(tenderUserName);
		try {
			/*原flag = tenderService.updateTenderLog(borrowNid, orderId, userId, account, GetCilentIP.getIpAddr(request), couponGrantId, tenderUserName);*/
			flag = tenderService.updateTenderLog(autoTenderComboRequest);
			if (!flag) {
				resultBean.setStatusForResponse("TZ000099");
				resultBean.setStatusDesc("投资失败,系统错误");
				return resultBean;
			}
			
			logger.info("2.到此说明tenderService.updateTenderLog成功   .... borrowTenderTmpMapper和 borrowTenderTmpInfo更新成功！！");
			
			
			logger.info("投资调用接口前---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid()+" ordId: "+orderId);
			BankCallBean bean = new BankCallBean();
			// 获取共同参数
			/*原String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);*/
			String bankCode = systemConfig.getBankBankcode();
			/*原String instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);*/
			String instCode = systemConfig.getBankInstcode();
			// 生成订单
			bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
			bean.setTxCode(BankCallConstant.TXCODE_BIDAUTO_APPLY);// 交易代码
			bean.setInstCode(instCode);
			bean.setBankCode(bankCode);
			bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
			bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
			bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
			bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
			bean.setAccountId(tenderUsrcustid);// 电子账号
			bean.setOrderId(orderId);// 订单号
			bean.setTxAmount(CustomUtil.formatAmount(account.toString()));// 交易金额
			bean.setProductId(borrowNid);// 标的号
//			bean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_FREEZE);// 是否冻结金额
			bean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_UNFREEZE);// 是否冻结金额
			bean.setContOrderId(autoOrderId);
			bean.setLogOrderId(orderId);// 订单号
			bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单日期
			bean.setLogUserId(String.valueOf(userId));// 投资用户
			bean.setLogRemark("自动投标申请");
			bean.setLogClient(0);
			try {
				registResult = BankCallUtils.callApiBg(bean);
			} catch (Exception e) {
				logger.info("投资调用接口异常---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid()+" ordId: "+orderId);
				removeTenderTmp(borrowNid, userId, orderId);
				logger.error(e.getMessage());
				resultBean.setStatusForResponse("TZ000098");
				resultBean.setStatusDesc("调用投资银行接口异常");
				return resultBean;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			resultBean.setStatusForResponse("TZ000099");
			resultBean.setStatusDesc("投资失败,系统错误");
			return resultBean;
		}
		
		if(registResult == null || StringUtils.isBlank(registResult.getRetCode())){
			logger.info("投资调用接口返回为空---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid()+" ordId: "+orderId);
//			removeTenderTmp(borrowNid, userId, orderId);
			resultBean.setStatusForResponse("TZ000098");
			resultBean.setStatusDesc("调用投资银行接口异常");
			return resultBean;
		}
		
		if (!BankCallConstant.RESPCODE_SUCCESS.equals(registResult.getRetCode())) {
			// 返回码提示余额不足，不结冻
			logger.info("用户:" + userId + " 投资接口调用失败，错误码：" + registResult.getRetCode());
			removeTenderTmp(borrowNid, userId, orderId);
			if (BankCallConstant.RETCODE_BIDAPPLY_YUE_FAIL.equals(registResult.getRetCode())) {
				resultBean.setStatusForResponse("TZ000097");
				resultBean.setStatusDesc("投资失败，可用余额不足！请联系客服.");
				return resultBean;
			} else {
				resultBean.setStatusForResponse("TZ000096");
				resultBean.setStatusDesc("投资失败，原因: "+registResult.getRetMsg());
				return resultBean;
			}
		}
		

		if(BankCallConstant.RESPCODE_SUCCESS.equals(registResult.getRetCode())){
			logger.info("3.到此说明调用银行成功！！");
		}
		
		
		registResult.convert();
		String message ="";
		try {
			// 进行投资, tendertmp锁住
			JSONObject tenderResult = this.tenderService.userAutoTender(borrow, registResult,couponGrantId);

			// 投资成功
			if (tenderResult.getString("status").equals("1")) {
				logger.info("用户:" + userId + "  投资成功：" + account);
				message = "恭喜您投资成功!";
			}
			// 投资失败 回滚redis
			else {
				logger.info("用户:" + userId + "   投资失败：" + account);
				message = "投资失败";
				// 投资失败,投资撤销
				try {
					tenderService.bidCancel(userId, borrowNid, orderId, account);
					tenderService.deleteBorrowTenderTmpByParam(userId, borrowNid, orderId);
				} catch (Exception e) {
					logger.error(e.getMessage());
					resultBean.setStatusForResponse("TZ000099");
					resultBean.setStatusDesc("投资失败,系统错误");
					return resultBean;
				}
				if(tenderResult != null && tenderResult.getString("message") != null){
					message = "投资失败, 原因："+tenderResult.getString("message");
				}
				resultBean.setStatusForResponse("TZ000096");
				resultBean.setStatusDesc(message);
				return resultBean;
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultBean.setStatusForResponse("TZ000099");
			resultBean.setStatusDesc("投资失败,系统错误");
			return resultBean;
		}
		logger.info(autoTenderRequestBean.getInstCode()+" userid: "+userId+" 结束投资");
		resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
		resultBean.setStatusDesc(message);
		resultBean.setOrderId(orderId);
		return resultBean;
    }
    
	/**
	 * 投资失败,删除投资临时表
	 * @param borrowNid
	 * @param userId
	 * @param orderId
	 */
	private void removeTenderTmp(String borrowNid, Integer userId,
			String orderId) {
		// 投资失败,投资撤销
		try {
			@SuppressWarnings("unused")
			boolean updateFlag = tenderService.deleteBorrowTenderTmpByParam(userId, borrowNid, orderId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
    
}
