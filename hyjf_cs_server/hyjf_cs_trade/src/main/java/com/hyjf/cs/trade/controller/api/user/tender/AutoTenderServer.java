/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.user.tender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.api.AutoTenderRequestBean;
import com.hyjf.cs.trade.bean.api.AutoTenderResultBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author libin
 * @version AutoTenderServer.java, v0.1 2018年8月24日 上午10:36:59
 */
@Api(value = "api端-手动出借散标(第三方出借散标)",tags = "api端-手动出借散标(第三方出借散标)")
@Controller
@RequestMapping("/hyjf-api/server/tender")
public class AutoTenderServer extends BaseTradeController{
	
	@Autowired
	private TenderService tenderService;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmTradeClient amTradeClient;
    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "手动出借散标", notes = "手动出借散标")
    @PostMapping(value = "/tender.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AutoTenderResultBean autoTender(@RequestBody AutoTenderRequestBean autoTenderRequestBean, HttpServletRequest request, HttpServletResponse response) {
    	
    	logger.info("出借输入参数.... autoTenderRequestBean is :{}", JSONObject.toJSONString(autoTenderRequestBean));
    	
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
		
		// ————> 老验签
/*		if (!this.verifyRequestSign(autoTenderRequestBean, AutoTenderDefine.REQUEST_MAPPING + AutoTenderDefine.AUTOTENDER_ACTION)) {
			resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
			logger.info("验签失败！---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: " + autoTenderRequestBean.getBorrowNid());
			resultBean.setStatusDesc("验签失败！");
			return resultBean;
		}*/
		
        //新验签
        if(!SignUtil.verifyRequestSign(autoTenderRequestBean, "/tender")){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("验签失败！---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: " + autoTenderRequestBean.getBorrowNid());
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }
		
        logger.info("开始自动出借，请求参数 " + JSON.toJSONString(autoTenderRequestBean));
		
		String couponGrantId = "";
		String bizAccount = autoTenderRequestBean.getAccountId();
		// 借款borrowNid
		String borrowNid = autoTenderRequestBean.getBorrowNid();
		// 出借金额
		String accountStr = autoTenderRequestBean.getMoney();
		if (accountStr == null || "".equals(accountStr)) {
			accountStr = "0";
		}
        
		// ————> 出借校验
		JSONObject result = tenderService.checkAutoTenderParam(borrowNid, accountStr, bizAccount, "0", couponGrantId);
		
		if (result == null) {
			logger.info("出借校验失败---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid());
			resultBean.setStatusForResponse("TZ000099");
			resultBean.setStatusDesc("出借失败,系统错误");
			return resultBean;
		} else if (result.get("error") != null && result.get("error").equals("1")) {
			logger.info("出借校验失败，原因: "+result.get("data").toString()+"  "+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid());
			resultBean.setStatusForResponse("TZ000096");
			resultBean.setStatusDesc("出借失败，原因: "+result.get("data").toString());
			return resultBean;
		}
		
		
		logger.info("1.到此说明出借校验成功！！");
		
		
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
		// ————> 写日志 调用出借接口
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
				resultBean.setStatusDesc("出借失败,系统错误");
				return resultBean;
			}
			
			logger.info("2.到此说明tenderService.updateTenderLog成功   .... borrowTenderTmpMapper和 borrowTenderTmpInfo更新成功！！");
			
			
			logger.info("出借调用接口前---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid()+" ordId: "+orderId);
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
			bean.setLogUserId(String.valueOf(userId));// 出借用户
			bean.setLogRemark("自动投标申请");
			bean.setLogClient(0);
			try {
				registResult = BankCallUtils.callApiBg(bean);
			} catch (Exception e) {
				logger.info("出借调用接口异常---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid()+" ordId: "+orderId);
				removeTenderTmp(borrowNid, userId, orderId);
				logger.error(e.getMessage());
				resultBean.setStatusForResponse("TZ000098");
				resultBean.setStatusDesc("调用出借银行接口异常");
				return resultBean;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			resultBean.setStatusForResponse("TZ000099");
			resultBean.setStatusDesc("出借失败,系统错误");
			return resultBean;
		}
		
		if(registResult == null || StringUtils.isBlank(registResult.getRetCode())){
			logger.info("出借调用接口返回为空---------"+autoTenderRequestBean.getAccountId()+ " borrowNid: "+autoTenderRequestBean.getBorrowNid()+" ordId: "+orderId);
//			removeTenderTmp(borrowNid, userId, orderId);
			resultBean.setStatusForResponse("TZ000098");
			resultBean.setStatusDesc("调用出借银行接口异常");
			return resultBean;
		}
		
		if (!BankCallConstant.RESPCODE_SUCCESS.equals(registResult.getRetCode())) {
			// 返回码提示余额不足，不结冻
			logger.info("用户:" + userId + " 出借接口调用失败，错误码：" + registResult.getRetCode());
			removeTenderTmp(borrowNid, userId, orderId);
			if (BankCallConstant.RETCODE_BIDAPPLY_YUE_FAIL.equals(registResult.getRetCode())) {
				resultBean.setStatusForResponse("TZ000097");
				resultBean.setStatusDesc("出借失败，可用余额不足！请联系客服.");
				return resultBean;
			} else {
				resultBean.setStatusForResponse("TZ000096");
				resultBean.setStatusDesc("出借失败，原因: "+registResult.getRetMsg());
				return resultBean;
			}
		}
		if(BankCallConstant.RESPCODE_SUCCESS.equals(registResult.getRetCode())){
			logger.info("3.到此说明调用银行成功！！");
		}
		registResult.convert();
		String message ="";
		//用户测评逻辑开始++++++
		UserVO user = tenderService.getUsers(userId);
		Integer isAnswer = -1;
		if (user != null) {
			isAnswer = user.getIsEvaluationFlag();
		}
		if (isAnswer == 0) {
			//返回错误码
			resultBean.setStatusForResponse("CP000000");
			resultBean.setStatusDesc("用户未测评");
			return resultBean;
		} else {
			if(isAnswer==1 && null != user.getEvaluationExpiredTime()){
				//测评到期日
				Long lCreate = user.getEvaluationExpiredTime().getTime();
				//当前日期
				Long lNow = System.currentTimeMillis();
				if (lCreate <= lNow) {
					//返回错误码
					resultBean.setStatusForResponse("CP000001");
					resultBean.setStatusDesc("用户评测已过期");
					return resultBean;
				}
			} else {
				//返回错误码
				resultBean.setStatusForResponse("CP000000");
				resultBean.setStatusDesc("用户未测评");
			}
		}
		//限额判断：从user中获取客户类型，ht_user_evalation_result（用户测评总结表）
		UserEvalationResultVO userEvalationResultCustomize = amUserClient.selectUserEvalationResultByUserId(userId);
		if(userEvalationResultCustomize != null){
			EvaluationConfigVO evalConfig = new EvaluationConfigVO();
			//1.散标／债转出借者测评类型校验
			String debtEvaluationTypeCheck = "0";
			//2.散标／债转单笔出借金额校验
			String deptEvaluationMoneyCheck = "0";
			//3.散标／债转待收本金校验
			String deptCollectionEvaluationCheck = "0";
			//获取开关信息
			List<EvaluationConfigVO> evalConfigList = amTradeClient.selectEvaluationConfig(evalConfig);
			if(evalConfigList != null && evalConfigList.size() > 0){
				evalConfig = evalConfigList.get(0);
				//1.散标／债转出借者测评类型校验
				debtEvaluationTypeCheck = evalConfig.getDebtEvaluationTypeCheck() == null ? "0" : String.valueOf(evalConfig.getDebtEvaluationTypeCheck());
				//2.散标／债转单笔出借金额校验
				deptEvaluationMoneyCheck = evalConfig.getDeptEvaluationMoneyCheck() == null ? "0" : String.valueOf(evalConfig.getDeptEvaluationMoneyCheck());
				//3.散标／债转待收本金校验
				deptCollectionEvaluationCheck = evalConfig.getDeptCollectionEvaluationCheck() == null ? "0" : String.valueOf(evalConfig.getDeptCollectionEvaluationCheck());
				//7.投标时校验（二期）(预留二期开发)
			}
			//初始化金额返回值
			String revaluation_money,revaluation_money_principal;
			//根据类型从redis或数据库中获取测评类型和上限金额
			String eval_type = userEvalationResultCustomize.getEvalType();
			switch (eval_type){
				case "保守型":
					//从redis获取金额（单笔）
					revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE);
					//如果reids不存在或者为零尝试获取数据库（单笔）
					if("0".equals(revaluation_money)){
						revaluation_money = evalConfig.getConservativeEvaluationSingleMoney() == null ? "0": String.valueOf(evalConfig.getConservativeEvaluationSingleMoney());
					}
					//从redis获取金额（代收本金）
					revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL);
					//如果reids不存在或者为零尝试获取数据库（代收本金）
					if("0".equals(revaluation_money_principal)){
						revaluation_money_principal = evalConfig.getConservativeEvaluationPrincipalMoney() == null ? "0": String.valueOf(evalConfig.getConservativeEvaluationPrincipalMoney());
					}
					break;
				case "稳健型":
					//从redis获取金额（单笔）
					revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS);
					//如果reids不存在或者为零尝试获取数据库（单笔）
					if("0".equals(revaluation_money)){
						revaluation_money = evalConfig.getSteadyEvaluationSingleMoney() == null ? "0": String.valueOf(evalConfig.getSteadyEvaluationSingleMoney());
					}
					//从redis获取金额（代收本金）
					revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL);
					//如果reids不存在或者为零尝试获取数据库（代收本金）
					if("0".equals(revaluation_money_principal)){
						revaluation_money_principal = evalConfig.getSteadyEvaluationPrincipalMoney() == null ? "0": String.valueOf(evalConfig.getSteadyEvaluationPrincipalMoney());
					}
					break;
				case "成长型":
					//从redis获取金额（单笔）
					revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_GROWTH) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_GROWTH);
					//如果reids不存在或者为零尝试获取数据库（单笔）
					if("0".equals(revaluation_money)){
						revaluation_money = evalConfig.getGrowupEvaluationSingleMoney() == null ? "0": String.valueOf(evalConfig.getGrowupEvaluationSingleMoney());
					}
					//从redis获取金额（代收本金）
					revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_GROWTH_PRINCIPAL) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_GROWTH_PRINCIPAL);
					//如果reids不存在或者为零尝试获取数据库（代收本金）
					if("0".equals(revaluation_money_principal)){
						revaluation_money_principal = evalConfig.getGrowupEvaluationPrincipalMoney() == null ? "0": String.valueOf(evalConfig.getGrowupEvaluationPrincipalMoney());
					}
					break;
				case "进取型":
					//从redis获取金额（单笔）
					revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE);
					//如果reids不存在或者为零尝试获取数据库（单笔）
					if("0".equals(revaluation_money)){
						revaluation_money = evalConfig.getEnterprisingEvaluationSinglMoney() == null ? "0": String.valueOf(evalConfig.getEnterprisingEvaluationSinglMoney());
					}
					//从redis获取金额（代收本金）
					revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL);
					//如果reids不存在或者为零尝试获取数据库（代收本金）
					if("0".equals(revaluation_money_principal)){
						revaluation_money_principal = evalConfig.getEnterprisingEvaluationPrincipalMoney() == null ? "0": String.valueOf(evalConfig.getEnterprisingEvaluationPrincipalMoney());
					}
					break;
				default:
					revaluation_money = null;
					revaluation_money_principal = null;
			}
			//测评到期日
			Long lCreate = user.getEvaluationExpiredTime().getTime();
			//当前日期
			Long lNow = System.currentTimeMillis();
			if (lCreate <= lNow) {
				//返回错误码
				resultBean.setStatusForResponse("CP000001");
				resultBean.setStatusDesc("用户评测已过期");
				return resultBean;
			}
			if(CustomConstants.EVALUATION_CHECK.equals(debtEvaluationTypeCheck)){
				//计划类判断用户类型为稳健型以上才可以出借
				if(!CommonUtils.checkStandardInvestment(eval_type,"BORROW_SB",borrow.getInvestLevel())){
					//返回错误码
					resultBean.setStatusForResponse("CP000002");
					resultBean.setStatusDesc("当前用户测评等级为：“"+ eval_type +"”达到“"+borrow.getInvestLevel()+"”及以上才可以出借此项目");
					return resultBean;
				}
			}
			if(revaluation_money == null){
				logger.info("=============从redis中获取测评类型和上限金额异常!(没有获取到对应类型的限额数据) eval_type="+eval_type);
			}else {
				if(CustomConstants.EVALUATION_CHECK.equals(deptEvaluationMoneyCheck)){
					//金额对比判断（校验金额 大于 设置测评金额）
					if (new BigDecimal(accountStr).compareTo(new BigDecimal(revaluation_money)) > 0) {
						//返回错误码
						resultBean.setStatusForResponse("CP000003");
						resultBean.setStatusDesc("单笔测评限额超额！当前用户测评等级为：“"+ eval_type +"”单笔测评限额为：“"+ StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()) +"”");
						return resultBean;
					}
				}
			}
			if(revaluation_money_principal == null){
				logger.info("=============从redis中获取测评类型和上限金额异常!(没有获取到对应类型的限额数据) eval_type="+eval_type);
			}else {
				//代收本金限额校验
				if(CustomConstants.EVALUATION_CHECK.equals(deptCollectionEvaluationCheck)){
					//获取冻结金额和代收本金
					CallCenterAccountDetailVO accountDetail = amTradeClient.queryAccountEvalDetail(userId);
					if (accountDetail != null) {
						BigDecimal planFrost = accountDetail.getPlanFrost();// plan_frost 汇添金计划真实冻结金额
						BigDecimal bankFrost = accountDetail.getBankFrost();// bank_frost 银行冻结金额
						BigDecimal bankAwaitCapital = accountDetail.getBankAwaitCapital();// bank_await_capital 银行待收本金
						BigDecimal accountFrost = BigDecimal.ZERO;
						//加法运算
						accountFrost = accountFrost.add(planFrost).add(bankFrost).add(bankAwaitCapital).add(new BigDecimal(accountStr));
						//金额对比判断（校验金额 大于 设置测评金额）（代收本金）
						if (accountFrost.compareTo(new BigDecimal(revaluation_money_principal)) > 0) {
							//返回错误码
							resultBean.setStatusForResponse("CP000004");
							resultBean.setStatusDesc("待收本金测评限额超额！当前用户测评等级为：“"+ eval_type +"”待收本金测评限额为：“"+ StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money_principal).intValue()) +"”");
							return resultBean;
						}
					}
				}
			}
		}else{
			logger.info("=============该用户测评总结数据为空! userId="+userId);
		}
		//出借逻辑开始+++++++++
		try {
			// 进行出借, tendertmp锁住
			JSONObject tenderResult = this.tenderService.userAutoTender(borrow, registResult,couponGrantId);

			// 投标成功
			if (tenderResult.getString("status").equals("1")) {
				logger.info("用户:" + userId + "  投标成功：" + account);
				message = "恭喜您投标成功!";
			}
			// 出借失败 回滚redis
			else {
				logger.info("用户:" + userId + "   出借失败：" + account);
				message = "出借失败";
				// 出借失败,出借撤销
				try {
					tenderService.bidCancel(userId, borrowNid, orderId, account);
					tenderService.deleteBorrowTenderTmpByParam(userId, borrowNid, orderId);
				} catch (Exception e) {
					logger.error(e.getMessage());
					resultBean.setStatusForResponse("TZ000099");
					resultBean.setStatusDesc("出借失败,系统错误");
					return resultBean;
				}
				if(tenderResult != null && tenderResult.getString("message") != null){
					message = "出借失败, 原因："+tenderResult.getString("message");
				}
				resultBean.setStatusForResponse("TZ000096");
				resultBean.setStatusDesc(message);
				return resultBean;
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultBean.setStatusForResponse("TZ000099");
			resultBean.setStatusDesc("出借失败,系统错误");
			return resultBean;
		}
		logger.info(autoTenderRequestBean.getInstCode()+" userid: "+userId+" 结束出借");
		resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
		resultBean.setStatusDesc(message);
		resultBean.setOrderId(orderId);
		return resultBean;
    }
    
	/**
	 * 出借失败,删除出借临时表
	 * @param borrowNid
	 * @param userId
	 * @param orderId
	 */
	private void removeTenderTmp(String borrowNid, Integer userId,
			String orderId) {
		// 出借失败,出借撤销
		try {
			@SuppressWarnings("unused")
			boolean updateFlag = tenderService.deleteBorrowTenderTmpByParam(userId, borrowNid, orderId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
    
}
