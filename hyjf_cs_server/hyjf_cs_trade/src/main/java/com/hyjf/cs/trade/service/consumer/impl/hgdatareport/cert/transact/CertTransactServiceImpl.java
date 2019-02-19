package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.transact;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.CouponRealTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transact.CertTransactService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.cert.open.CertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hyjf.common.util.GetDate;
/**
 * @author pcc
 */

@Service
public class CertTransactServiceImpl extends BaseHgCertReportServiceImpl implements CertTransactService {
	Logger logger = LoggerFactory.getLogger(CertTransactServiceImpl.class);
	@Autowired
	AmTradeClient amTradeClient;
	@Autowired
	AmUserClient amUserClient;
	@Autowired
	SystemConfig systemConfig;

	private String thisMessName = "交易流水信息";
	private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";


	@Override
	public JSONArray createDate(String minId, String maxId) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		CertRequest certTransactRequest=new CertRequest();
		certTransactRequest.setMaxId(maxId);
		certTransactRequest.setMinId(minId);
		List<CertAccountListCustomizeVO> accountLists=amTradeClient.queryCertAccountList(certTransactRequest);
		try {
			for (CertAccountListCustomizeVO accountList : accountLists) {
				 createParam(accountList,list);
			}
			if(list==null||list.size()==0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JSONArray.parseArray(JSON.toJSONString(list));
	}

	@Override
	public CertAccountListIdCustomizeVO queryCertAccountListId(Map<String, Object> param) {
		CertRequest certRequest=new CertRequest();
		certRequest.setMinId((String) param.get("minId"));
		certRequest.setLimitStart((Integer) param.get("limitStart"));
		certRequest.setLimitEnd((Integer) param.get("limitEnd"));
		logger.info("queryCertAccountListId:" + JSONObject.toJSONString(certRequest));
		return amTradeClient.queryCertAccountListId(certRequest);
	}

	private void createParam(CertAccountListCustomizeVO accountList,List<Map<String, Object>> list) throws Exception {

		switch (accountList.getTrade()) {
		//提现  发送7提现  以及23提现手续费
		case "cash_success":
			cashSuccess(accountList,list);
			break;
		//调减
		case "account_adjustment_down":
			accountAdjustmentDown(accountList,list);
			break;
		//充值        线下充值  发送6充值
		case "recharge":
		case "recharge_offline":
			recharge(accountList,list);
			break;
		case "auto_reverse":
		case "account_adjustment_up":
			accountAdjustmentUp(accountList,list);
			break;
		//借款成功  发送1放款   发送5交易手续费（）
		case "borrow_success":
			borrowSuccess(accountList,list);
			break;
		//还款 发送18还款本金  19还款利息  5交易手续费（还款服务费）
		case "repay_success":
			repaySuccess(accountList,list);
			break;
			//优惠券回款 上送41红包
		case "increase_interest_profit":
			increaseInerestProfit(accountList,list);
				break;
		//优惠券回款 上送41红包
		case "experience_profit":
		case "cash_coupon_profit":
			couponProfit(accountList,list);
			break;
		//投资收到还款  发送8赎回本金 发送9赎回利息
		case "tender_recover_yes":
		case "hjh_repay_balance":
		case "hjh_repay_frost":
			tenderRecoverYes(accountList,list);
			break;
		case "credit_tender_recover_yes":
		case "credit_tender_recover_forst":
			creditTenderRecoverYes(accountList,list);
			break;
		//承接债权 散标债转发送17承接 
		case "creditassign":
			creditAssign(accountList,list);
			break;
		//自动承接债权 计划自动承接债转发送17承接 
		case "accede_assign":
			accedeAssign(accountList,list);
			break;
		//智投清算（转让）  计划 发送11转让  发送40交易手续费（转让服务费）
		case "liquidates_sell":
			liqudatesSell(accountList,list);
			break;
		//出让债权 计划发送11转让  发送5交易手续费（转让服务费）
		case "creditsell":
			creditSell(accountList,list);
			break;
		default:
			break;
		}
	}

	private void creditSell(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		List<CreditTenderVO> creditTenders=amTradeClient.selectCreditTender(accountList.getNid());
		if(creditTenders==null||creditTenders.size()==0){
			return;
		}
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(creditTenders.get(0).getBidNid());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		/****************** 发送11转让 ******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode",  systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "11");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("11"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", creditTenders.get(0).getCreditTenderNid());
		//转让项目编号
		param.put("transferId", creditTenders.get(0).getCreditNid());
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);

		/****************** 发送5交易手续费******************/
		if(creditTenders.get(0).getCreditFee().compareTo(BigDecimal.ZERO)==1){
			//接口版本号
			param1.put("version", CertCallConstant.CERT_CALL_VERSION);
			//交易流水时间
			param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
			//	平台交易流水号
			param1.put("transId", accountList.getNid());
			//平台编号
			param1.put("sourceCode",  systemConfig.getCertSourceCode());
			//原散标编号
			param1.put("sourceProductCode", borrow.getBorrowNid());
			//原散标名称
			param1.put("sourceProductName", borrow.getName());
			//交易类型
			param1.put("transType", "40");
			//交易方式
			param1.put("transPayment", "a");
			//交易类型描述
			param1.put("transTypeDec", CertTradeTypeEnum.getName("40"));
			//交易金额
			param1.put("transMoney", creditTenders.get(0).getCreditFee());
			//	交易日期
			param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
			//交易人员银行（或三方支付名称）
			param1.put("transBank", "江西银行");
			//用户标示哈希
			param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
			//存管银行流水编号
			param1.put("bankTransId", accountList.getSeqNo());
			//原产品信息编号
			param1.put("sourceFinancingCode", "-1");
			//原产品信息名称
			param1.put("sourceFinancingName", "-1");
			//债权编号
			param1.put("finClaimId", creditTenders.get(0).getCreditTenderNid());
			//转让项目编号
			param1.put("transferId", creditTenders.get(0).getCreditNid());
			//	还款计划编号
			param1.put("replanId", "-1");
			list.add(param1);
		}
	}

	private void liqudatesSell(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		List<HjhDebtCreditTenderVO> hjhDebtCreditTenders=amTradeClient.selectHjhCreditTenderListByAssignOrderId(accountList.getNid());
		if(hjhDebtCreditTenders==null||hjhDebtCreditTenders.size()==0){
			return;
		}
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(hjhDebtCreditTenders.get(0).getBorrowNid());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "11");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("11"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//债权编号
		param.put("finClaimId", hjhDebtCreditTenders.get(0).getInvestOrderId());
		//转让项目编号
		param.put("transferId", hjhDebtCreditTenders.get(0).getCreditNid());
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);

		if(hjhDebtCreditTenders.get(0).
				getAssignServiceFee().compareTo(BigDecimal.ZERO)==1){
			//接口版本号
			param1.put("version", CertCallConstant.CERT_CALL_VERSION);
			//交易流水时间
			param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
			//	平台交易流水号
			param1.put("transId", accountList.getNid());
			//平台编号
			param1.put("sourceCode", systemConfig.getCertSourceCode());
			//原散标编号
			param1.put("sourceProductCode", borrow.getBorrowNid());
			//原散标名称
			param1.put("sourceProductName", borrow.getName());
			//交易类型
			param1.put("transType", "40");
			//交易方式
			param1.put("transPayment", "a");
			//交易类型描述
			param1.put("transTypeDec", CertTradeTypeEnum.getName("40"));
			//交易金额
			param1.put("transMoney", hjhDebtCreditTenders.get(0).getAssignServiceFee());
			//	交易日期
			param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
			//交易人员银行（或三方支付名称）
			param1.put("transBank", "江西银行");
			//用户标示哈希
			param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
			//存管银行流水编号
			param1.put("bankTransId", accountList.getSeqNo());
			//原产品信息编号
			param1.put("sourceFinancingCode", "-1");
			//原产品信息名称
			param1.put("sourceFinancingName", "-1");
			//债权编号
			param1.put("finClaimId", hjhDebtCreditTenders.get(0).getInvestOrderId());
			//转让项目编号
			param1.put("transferId", hjhDebtCreditTenders.get(0).getCreditNid());
			//	还款计划编号
			param1.put("replanId", "-1");
			list.add(param1);
		}
	}

	private void accedeAssign(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		List<HjhDebtCreditTenderVO> hjhDebtCreditTenders=amTradeClient.selectHjhCreditTenderListByAssignOrderId(accountList.getNid());
		if(hjhDebtCreditTenders==null||hjhDebtCreditTenders.size()==0){
			return;
		}
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(hjhDebtCreditTenders.get(0).getBorrowNid());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "17");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("17"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", hjhDebtCreditTenders.get(0).getInvestOrderId());
		//转让项目编号
		param.put("transferId", hjhDebtCreditTenders.get(0).getCreditNid());
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);
	}

	private void creditAssign(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		List<CreditTenderVO> creditTenders=amTradeClient.selectCreditTender(accountList.getNid());
		if(creditTenders==null||creditTenders.size()==0){
			return;
		}
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(creditTenders.get(0).getBidNid());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "17");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("17"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", creditTenders.get(0).getCreditTenderNid());
		//转让项目编号
		param.put("transferId", creditTenders.get(0).getCreditNid());
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);
	}

	private void creditTenderRecoverYes(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		String period="";
		String nid="";
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(accountList.getRemark());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());

		BigDecimal creditInterest=BigDecimal.ZERO;
		BigDecimal creditCapital=BigDecimal.ZERO;
		period=borrow.getBorrowNid();
		nid="";
		String transferId="";
		if(borrowAndInfoVO.getPlanNid()!=null&&borrowAndInfoVO.getPlanNid().length()>0){
			//智投
			CertRequest certRequest=new CertRequest();
			certRequest.setRepayOrdid(accountList.getNid());
			List<HjhDebtCreditRepayVO> hjhDebtCreditRepays=amTradeClient.getHjhDebtCreditRepayListByRepayOrdId(certRequest);
			if(hjhDebtCreditRepays==null||hjhDebtCreditRepays.size()==0){
				return;
			}
			creditInterest=hjhDebtCreditRepays.get(0).getReceiveInterestYes();
			creditCapital=hjhDebtCreditRepays.get(0).getReceiveCapitalYes();
			period=period+"-"+hjhDebtCreditRepays.get(0).getRepayPeriod();
			nid=hjhDebtCreditRepays.get(0).getInvestOrderId();
			transferId=hjhDebtCreditRepays.get(0).getCreditNid()+"";
		}else{
			//散标
			CertRequest certRequest=new CertRequest();
			certRequest.setRepayOrdid(accountList.getNid());
			List<CreditRepayVO> creditRepays=amTradeClient.getCreditRepayListByRepayOrdId(certRequest);
			if(creditRepays==null||creditRepays.size()==0){
				return;
			}
			creditInterest=creditRepays.get(0).getAssignRepayInterest();
			creditCapital=creditRepays.get(0).getAssignRepayCapital();
			period=period+"-"+creditRepays.get(0).getRecoverPeriod();
			nid=creditRepays.get(0).getCreditTenderNid();
			transferId=creditRepays.get(0).getCreditNid()+"";
		}

		/****************** 发送8赎回本金******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "8");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("8"));
		//交易金额
		param.put("transMoney", creditCapital);
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", nid);
		//转让项目编号
		param.put("transferId", transferId);
		//	还款计划编号
		param.put("replanId", period);
		list.add(param);
		/****************** 发送9赎回利息******************/
		//接口版本号
		param1.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param1.put("transId", accountList.getNid());
		//平台编号
		param1.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param1.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param1.put("sourceProductName", borrow.getName());
		//交易类型
		param1.put("transType", "9");
		//交易方式
		param1.put("transPayment", "a");
		//交易类型描述
		param1.put("transTypeDec", CertTradeTypeEnum.getName("9"));
		//交易金额
		param1.put("transMoney", creditInterest);
		//	交易日期
		param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param1.put("transBank", "江西银行");
		//用户标示哈希
		param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param1.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param1.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param1.put("sourceFinancingName", "-1");
		//债权编号
		param1.put("finClaimId", nid);
		//转让项目编号
		param1.put("transferId", transferId);
		//	还款计划编号
		param1.put("replanId", period);
		list.add(param1);
	}

	private void tenderRecoverYes(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(accountList.getRemark());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		String period="";
		String nid="";
		BigDecimal interest=BigDecimal.ZERO;
		BigDecimal capital=BigDecimal.ZERO;
		period=borrow.getBorrowNid();
		if("end".equals(borrowAndInfoVO.getBorrowStyle())||"endday".equals(borrowAndInfoVO.getBorrowStyle())){
			CertRequest certRequest=new CertRequest();
			certRequest.setRepayOrdid(accountList.getNid());
			List<BorrowRecoverVO> borrowRecovers=amTradeClient.selectBorrowRecoverListByRequest(certRequest);
			if(borrowRecovers==null||borrowRecovers.size()==0){
				return;
			}

			interest=borrowRecovers.get(0).getRecoverInterestYes();
			capital=borrowRecovers.get(0).getRecoverCapitalYes();
			BorrowRecoverVO borrowRecover=borrowRecovers.get(0);
			if(borrowAndInfoVO.getPlanNid()!=null&&borrowAndInfoVO.getPlanNid().length()>0){
				//智投
				CertRequest certRequest1=new CertRequest();
				certRequest1.setInvestOrderId(borrowRecover.getNid());
				certRequest1.setBorrowNid(borrowRecover.getBorrowNid());
				certRequest1.setPeriod(borrowRecover.getRecoverPeriod());
				List<HjhDebtCreditRepayVO> hjhDebtCreditRepays=amTradeClient.getHjhDebtCreditRepayListByRequest(certRequest1);
				for (HjhDebtCreditRepayVO hjhDebtCreditRepay : hjhDebtCreditRepays) {
					interest=interest.subtract(hjhDebtCreditRepay.getRepayInterestYes());
					capital=capital.subtract(hjhDebtCreditRepay.getRepayCapitalYes());
				}
			}else{
				//散标
				CertRequest certRequest1=new CertRequest();
				certRequest1.setInvestOrderId(borrowRecover.getNid());
				certRequest1.setBorrowNid(borrowRecover.getBorrowNid());
				certRequest1.setPeriod(borrowRecover.getRecoverPeriod());
				List<CreditRepayVO> creditRepays=amTradeClient.getCreditRepayListByRequest(certRequest1);
				for (CreditRepayVO creditRepay : creditRepays) {
					interest=interest.subtract(creditRepay.getAssignRepayInterest());
					capital=capital.subtract(creditRepay.getAssignRepayCapital());
				}
			}
			period=period+"-"+borrowRecover.getRecoverPeriod();
			nid=borrowRecover.getNid();
		}else{
			CertRequest certRequest=new CertRequest();
			certRequest.setRepayOrdid(accountList.getNid());
			List<BorrowRecoverPlanVO> borrowRecoverPlans=amTradeClient.selectBorrowRecoverPlanListByRequest(certRequest);
			if(borrowRecoverPlans==null||borrowRecoverPlans.size()==0){
				return;
			}
			BorrowRecoverPlanVO borrowRecoverPlan=borrowRecoverPlans.get(0);
			interest=borrowRecoverPlan.getRecoverInterestYes();
			capital=borrowRecoverPlan.getRecoverCapitalYes();
			if(borrowAndInfoVO.getPlanNid()!=null&&borrowAndInfoVO.getPlanNid().length()>0){
				//智投

				CertRequest certRequest1=new CertRequest();
				certRequest1.setInvestOrderId(borrowRecoverPlan.getNid());
				certRequest1.setBorrowNid(borrowRecoverPlan.getBorrowNid());
				certRequest1.setPeriod(borrowRecoverPlan.getRecoverPeriod());
				List<HjhDebtCreditRepayVO> hjhDebtCreditRepays=amTradeClient.getHjhDebtCreditRepayListByRequest(certRequest1);

				for (HjhDebtCreditRepayVO hjhDebtCreditRepay : hjhDebtCreditRepays) {
					interest=interest.subtract(hjhDebtCreditRepay.getRepayInterestYes());
					capital=capital.subtract(hjhDebtCreditRepay.getRepayCapitalYes());
				}
			}else{
				//散标
				CertRequest certRequest1=new CertRequest();
				certRequest1.setInvestOrderId(borrowRecoverPlan.getNid());
				certRequest1.setBorrowNid(borrowRecoverPlan.getBorrowNid());
				certRequest1.setPeriod(borrowRecoverPlan.getRecoverPeriod());
				List<CreditRepayVO> creditRepays=amTradeClient.getCreditRepayListByRequest(certRequest1);

				for (CreditRepayVO creditRepay : creditRepays) {
					interest=interest.subtract(creditRepay.getAssignRepayInterest());
					capital=capital.subtract(creditRepay.getAssignRepayCapital());
				}
			}
			period=period+"-"+borrowRecoverPlan.getRecoverPeriod();
			nid=borrowRecoverPlan.getNid();
		}

		/****************** 发送8赎回本金******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "8");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("8"));
		//交易金额
		param.put("transMoney", capital);
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", nid);
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", period);
		list.add(param);


		/****************** 发送9赎回利息******************/
		//接口版本号
		param1.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param1.put("transId", accountList.getNid());
		//平台编号
		param1.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param1.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param1.put("sourceProductName", borrow.getName());
		//交易类型
		param1.put("transType", "9");
		//交易方式
		param1.put("transPayment", "a");
		//交易类型描述
		param1.put("transTypeDec", CertTradeTypeEnum.getName("9"));
		//交易金额
		param1.put("transMoney", interest);
		//	交易日期
		param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param1.put("transBank", "江西银行");
		//用户标示哈希
		param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param1.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param1.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param1.put("sourceFinancingName", "-1");
		//债权编号
		param1.put("finClaimId", nid);
		//转让项目编号
		param1.put("transferId", "-1");
		//	还款计划编号
		param1.put("replanId", period);
		list.add(param1);
	}

	private void couponProfit(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		CertRequest certRequest=new CertRequest();
		certRequest.setTransferId(accountList.getNid());
		List<CouponRecoverVO> couponRecovers=amTradeClient.getCouponRecoverListByCertRequest(certRequest);
		if(couponRecovers==null||couponRecovers.size()==0){
			return;
		}
		CouponRecoverVO couponRecover=couponRecovers.get(0);
		certRequest.setCouponTenderId(couponRecover.getTenderId());
		List<BorrowTenderCpnVO> borrowTenderCpnList=amTradeClient.getBorrowTenderCpnListByCertRequest(certRequest);
		if(borrowTenderCpnList==null||borrowTenderCpnList.size()==0){
			return;
		}
		BorrowTenderCpnVO borrowTenderCpn=borrowTenderCpnList.get(0);
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(borrowTenderCpn.getBorrowNid());
		List<CouponRealTenderVO> couponRealTenders =amTradeClient.getCouponRealTenderListByCertRequest(certRequest);
		if(couponRealTenders==null||couponRealTenders.size()==0){
			return;
		}
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());

		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow==null?"-1":borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow==null?"-1":borrow.getName());
		//交易类型
		param.put("transType", "10");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("10"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		if(couponRealTenders.get(0).getRealTenderId()!=null&&couponRealTenders.get(0).getRealTenderId().length()>0){
			//债权编号
			param.put("finClaimId", couponRealTenders.get(0).getRealTenderId());
		}else{
			//债权编号
			param.put("finClaimId", "-1");

		}
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", borrowTenderCpn.getBorrowNid()+"-"+couponRecover.getRecoverPeriod());
		list.add(param);
	}

	private void increaseInerestProfit(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		CertRequest certRequest=new CertRequest();
		certRequest.setTransferId(accountList.getNid());
		List<CouponRecoverVO> couponRecovers=amTradeClient.getCouponRecoverListByCertRequest(certRequest);
		if(couponRecovers==null||couponRecovers.size()==0){
			return;
		}
		CouponRecoverVO couponRecover=couponRecovers.get(0);
		certRequest.setCouponTenderId(couponRecover.getTenderId());
		List<BorrowTenderCpnVO> borrowTenderCpnList=amTradeClient.getBorrowTenderCpnListByCertRequest(certRequest);
		if(borrowTenderCpnList==null||borrowTenderCpnList.size()==0){
			return;
		}
		BorrowTenderCpnVO borrowTenderCpn=borrowTenderCpnList.get(0);
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(borrowTenderCpn.getBorrowNid());
		List<CouponRealTenderVO> couponRealTenders =amTradeClient.getCouponRealTenderListByCertRequest(certRequest);
		if(couponRealTenders==null||couponRealTenders.size()==0){
			return;
		}
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow==null?"-1":borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow==null?"-1":borrow.getName());
		//交易类型
		param.put("transType", "41");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("41"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		if(couponRealTenders.get(0).getRealTenderId()!=null&&couponRealTenders.get(0).getRealTenderId().length()>0){
			//债权编号
			param.put("finClaimId", couponRealTenders.get(0).getRealTenderId());
		}else{
			//债权编号
			param.put("finClaimId", "-1");

		}

		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", borrowTenderCpn.getBorrowNid()+"-"+couponRecover.getRecoverPeriod());
		list.add(param);
	}

	private void repaySuccess(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws Exception {
		//还款 发送18还款本金  19还款利息  5交易手续费（还款服务费）
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(accountList.getRemark());
		String period="";
		String nid="";
		period = borrow.getBorrowNid();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		Map<String, Object> param2 = new HashMap<String, Object>();

		CertUserVO certUser=this.getCertUserByUserIdBorrowNid(borrow.getUserId(),accountList.getRemark());
		if(certUser==null){
			certUser=new CertUserVO();
			certUser.setUserIdCardHash(getUserHashValue(borrowAndInfoVO));
		}

		if("end".equals(borrowAndInfoVO.getBorrowStyle())||"endday".equals(borrowAndInfoVO.getBorrowStyle())){
			BigDecimal repayCapitalYes=BigDecimal.ZERO;
			BigDecimal repayInterestYes=BigDecimal.ZERO;
			BigDecimal repayFee=BigDecimal.ZERO;
			CertRequest certRequest=new CertRequest();
			certRequest.setBorrowNid(accountList.getRemark());
			certRequest.setNid(accountList.getNid());
			List<BorrowRepayVO> borrowRepays =this.amTradeClient.getBorrowRepayListByRequest(certRequest) ;
			repayCapitalYes=borrowRepays.get(0).getRepayCapitalYes();
			repayInterestYes=borrowRepays.get(0).getRepayInterestYes();
			repayFee=borrowRepays.get(0).getRepayFee();

			period=period+"-"+borrowRepays.get(0).getRepayPeriod();
			/****************** 发18还款本金5交易手续费******************/
			//接口版本号
			param.put("version", CertCallConstant.CERT_CALL_VERSION);
			//交易流水时间
			param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
			//	平台交易流水号
			param.put("transId", accountList.getNid());
			//平台编号
			param.put("sourceCode", systemConfig.getCertSourceCode());
			//原散标编号
			param.put("sourceProductCode", borrow.getBorrowNid());
			//原散标名称
			param.put("sourceProductName", borrow.getName());
			//交易类型
			param.put("transType", "18");
			//交易方式
			param.put("transPayment", "a");
			//交易类型描述
			param.put("transTypeDec", CertTradeTypeEnum.getName("18"));
			//交易金额
			param.put("transMoney", repayCapitalYes);
			//	交易日期
			param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
			//交易人员银行（或三方支付名称）
			param.put("transBank", "江西银行");
			//用户标示哈希
			param.put("userIdcardHash", certUser.getUserIdCardHash());
			//存管银行流水编号
			param.put("bankTransId", accountList.getSeqNo());
			//原产品信息编号
			param.put("sourceFinancingCode", "-1");
			//原产品信息名称
			param.put("sourceFinancingName", "-1");
			//债权编号
			param.put("finClaimId", "-1");
			//转让项目编号
			param.put("transferId", "-1");
			//	还款计划编号
			param.put("replanId", period);
			list.add(param);

			/****************** 发送19还款利息******************/

			//接口版本号
			param1.put("version", CertCallConstant.CERT_CALL_VERSION);
			//交易流水时间
			param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
			//	平台交易流水号
			param1.put("transId", accountList.getNid());
			//平台编号
			param1.put("sourceCode", systemConfig.getCertSourceCode());
			//原散标编号
			param1.put("sourceProductCode", borrow.getBorrowNid());
			//原散标名称
			param1.put("sourceProductName", borrow.getName());
			//交易类型
			param1.put("transType", "19");
			//交易方式
			param1.put("transPayment", "a");
			//交易类型描述
			param1.put("transTypeDec", CertTradeTypeEnum.getName("19"));
			//交易金额
			param1.put("transMoney", repayInterestYes);
			//	交易日期
			param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
			//交易人员银行（或三方支付名称）
			param1.put("transBank", "江西银行");
			//用户标示哈希
			param1.put("userIdcardHash", certUser.getUserIdCardHash());
			//存管银行流水编号
			param1.put("bankTransId", accountList.getSeqNo());
			//原产品信息编号
			param1.put("sourceFinancingCode", "-1");
			//原产品信息名称
			param1.put("sourceFinancingName", "-1");
			//债权编号
			param1.put("finClaimId", "-1");
			//转让项目编号
			param1.put("transferId", "-1");
			//	还款计划编号
			param1.put("replanId", period);
			list.add(param1);

			/****************** 发送5交易手续费******************/
			if(repayFee.compareTo(BigDecimal.ZERO)==1){
				//接口版本号
				param2.put("version", CertCallConstant.CERT_CALL_VERSION);
				//交易流水时间
				param2.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
				//	平台交易流水号
				param2.put("transId", accountList.getNid());
				//平台编号
				param2.put("sourceCode", systemConfig.getCertSourceCode());
				//原散标编号
				param2.put("sourceProductCode", borrow.getBorrowNid());
				//原散标名称
				param2.put("sourceProductName", borrow.getName());
				//交易类型
				param2.put("transType", "42");
				//交易方式
				param2.put("transPayment", "a");
				//交易类型描述
				param2.put("transTypeDec", CertTradeTypeEnum.getName("42"));
				//交易金额
				param2.put("transMoney", repayFee);
				//	交易日期
				param2.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
				//交易人员银行（或三方支付名称）
				param2.put("transBank", "江西银行");
				//用户标示哈希
				param2.put("userIdcardHash", certUser.getUserIdCardHash());
				//存管银行流水编号
				param2.put("bankTransId", accountList.getSeqNo());
				//原产品信息编号
				param2.put("sourceFinancingCode", "-1");
				//原产品信息名称
				param2.put("sourceFinancingName", "-1");
				//债权编号
				param2.put("finClaimId", "-1");
				//转让项目编号
				param2.put("transferId", "-1");
				//	还款计划编号
				param2.put("replanId", period);
				list.add(param2);
			}
		}else{

			CertRequest certRequest=new CertRequest();
			certRequest.setBorrowNid(accountList.getRemark());
			certRequest.setRepayYestime(accountList.getCreateTime());
			certRequest.setNid(accountList.getNid());
			List<BorrowRepayPlanVO> borrowRepayPlans =this.amTradeClient.getBorrowRepayPlanListByRequest(certRequest) ;

			for (BorrowRepayPlanVO borrowRepayPlan : borrowRepayPlans) {
				BigDecimal repayCapitalYes=BigDecimal.ZERO;
				BigDecimal repayInterestYes=BigDecimal.ZERO;
				BigDecimal repayFee=BigDecimal.ZERO;
				repayCapitalYes=repayCapitalYes.add(borrowRepayPlan.getRepayCapitalYes());
				repayInterestYes=repayInterestYes.add(borrowRepayPlan.getRepayInterestYes());
				repayFee=repayFee.add(borrowRepayPlan.getRepayFee());
				String replanId=period+"-"+borrowRepayPlan.getRepayPeriod();

				Map<String, Object> param18 = new HashMap<String, Object>();
				Map<String, Object> param19 = new HashMap<String, Object>();
				Map<String, Object> param5 = new HashMap<String, Object>();
				/****************** 发18还款本金5交易手续费******************/
				//接口版本号
				param18.put("version", CertCallConstant.CERT_CALL_VERSION);
				//交易流水时间
				param18.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
				//	平台交易流水号
				param18.put("transId", accountList.getNid());
				//平台编号
				param18.put("sourceCode", systemConfig.getCertSourceCode());
				//原散标编号
				param18.put("sourceProductCode", borrow.getBorrowNid());
				//原散标名称
				param18.put("sourceProductName", borrow.getName());
				//交易类型
				param18.put("transType", "18");
				//交易方式
				param18.put("transPayment", "a");
				//交易类型描述
				param18.put("transTypeDec", CertTradeTypeEnum.getName("18"));
				//交易金额
				param18.put("transMoney", repayCapitalYes);
				//	交易日期
				param18.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
				//交易人员银行（或三方支付名称）
				param18.put("transBank", "江西银行");
				//用户标示哈希
				param18.put("userIdcardHash", certUser.getUserIdCardHash());
				//存管银行流水编号
				param18.put("bankTransId", accountList.getSeqNo());
				//原产品信息编号
				param18.put("sourceFinancingCode", "-1");
				//原产品信息名称
				param18.put("sourceFinancingName", "-1");
				//债权编号
				param18.put("finClaimId", "-1");
				//转让项目编号
				param18.put("transferId", "-1");
				//	还款计划编号
				param18.put("replanId", replanId);
				list.add(param18);

				/****************** 发送19还款利息******************/

				//接口版本号
				param19.put("version", CertCallConstant.CERT_CALL_VERSION);
				//交易流水时间
				param19.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
				//	平台交易流水号
				param19.put("transId", accountList.getNid());
				//平台编号
				param19.put("sourceCode", systemConfig.getCertSourceCode());
				//原散标编号
				param19.put("sourceProductCode", borrow.getBorrowNid());
				//原散标名称
				param19.put("sourceProductName", borrow.getName());
				//交易类型
				param19.put("transType", "19");
				//交易方式
				param19.put("transPayment", "a");
				//交易类型描述
				param19.put("transTypeDec", CertTradeTypeEnum.getName("19"));
				//交易金额
				param19.put("transMoney", repayInterestYes);
				//	交易日期
				param19.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
				//交易人员银行（或三方支付名称）
				param19.put("transBank", "江西银行");
				//用户标示哈希
				param19.put("userIdcardHash", certUser.getUserIdCardHash());
				//存管银行流水编号
				param19.put("bankTransId", accountList.getSeqNo());
				//原产品信息编号
				param19.put("sourceFinancingCode", "-1");
				//原产品信息名称
				param19.put("sourceFinancingName", "-1");
				//债权编号
				param19.put("finClaimId", "-1");
				//转让项目编号
				param19.put("transferId", "-1");
				//	还款计划编号
				param19.put("replanId", replanId);
				list.add(param19);

				/****************** 发送5交易手续费******************/
				if(repayFee.compareTo(BigDecimal.ZERO)==1){
					//接口版本号
					param5.put("version", CertCallConstant.CERT_CALL_VERSION);
					//交易流水时间
					param5.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
					//	平台交易流水号
					param5.put("transId", accountList.getNid());
					//平台编号
					param5.put("sourceCode", systemConfig.getCertSourceCode());
					//原散标编号
					param5.put("sourceProductCode", borrow.getBorrowNid());
					//原散标名称
					param5.put("sourceProductName", borrow.getName());
					//交易类型
					param5.put("transType", "42");
					//交易方式
					param5.put("transPayment", "a");
					//交易类型描述
					param5.put("transTypeDec", CertTradeTypeEnum.getName("42"));
					//交易金额
					param5.put("transMoney", repayFee);
					//	交易日期
					param5.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
					//交易人员银行（或三方支付名称）
					param5.put("transBank", "江西银行");
					//用户标示哈希
					param5.put("userIdcardHash", certUser.getUserIdCardHash());
					//存管银行流水编号
					param5.put("bankTransId", accountList.getSeqNo());
					//原产品信息编号
					param5.put("sourceFinancingCode", "-1");
					//原产品信息名称
					param5.put("sourceFinancingName", "-1");
					//债权编号
					param5.put("finClaimId", "-1");
					//转让项目编号
					param5.put("transferId", "-1");
					//	还款计划编号
					param5.put("replanId", replanId);
					list.add(param5);
				}
			}
		}
	}

	private void borrowSuccess(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws Exception {
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		BorrowInfoVO borrow = amTradeClient.getBorrowInfoByNid(accountList.getRemark());
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		CertUserVO certUser=this.getCertUserByUserIdBorrowNid(borrow.getUserId(),accountList.getRemark());
		if(certUser==null){
			certUser=new CertUserVO();
			certUser.setUserIdCardHash(getUserHashValue(borrowAndInfoVO));
		}
		/******************发送 发送1放款******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", borrow.getBorrowNid());
		//原散标名称
		param.put("sourceProductName", borrow.getName());
		//交易类型
		param.put("transType", "1");
		//交易方式
		param.put("transPayment", "a");

		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("1"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", certUser.getUserIdCardHash());
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", "-1");
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);


		/****************** 发送5交易手续费******************/
		List<BorrowRecoverVO> borrowRecovers=new ArrayList<BorrowRecoverVO>();
		borrowRecovers=this.amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
		BigDecimal recoverFee=BigDecimal.ZERO;
		if(borrowRecovers==null||borrowRecovers.size()==0){
			return;
		}
		for (BorrowRecoverVO borrowRecover : borrowRecovers) {
			recoverFee=recoverFee.add(borrowRecover.getRecoverServiceFee());
		}
		if(recoverFee.compareTo(BigDecimal.ZERO)==1){
			//接口版本号
			param1.put("version", CertCallConstant.CERT_CALL_VERSION);
			//交易流水时间
			param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
			//	平台交易流水号
			param1.put("transId", accountList.getNid());
			//平台编号
			param1.put("sourceCode", systemConfig.getCertSourceCode());
			//原散标编号
			param1.put("sourceProductCode", borrow.getBorrowNid());
			//原散标名称
			param1.put("sourceProductName", borrow.getName());
			//交易类型
			param1.put("transType", "42");
			//交易方式
			param1.put("transPayment", "a");

			//交易类型描述
			param1.put("transTypeDec", CertTradeTypeEnum.getName("42"));
			//交易金额
			param1.put("transMoney", recoverFee);
			//	交易日期
			param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
			//交易人员银行（或三方支付名称）
			param1.put("transBank", "江西银行");
			//用户标示哈希
			param1.put("userIdcardHash", certUser.getUserIdCardHash());
			//存管银行流水编号
			param1.put("bankTransId", accountList.getSeqNo());
			//原产品信息编号
			param1.put("sourceFinancingCode", "-1");
			//原产品信息名称
			param1.put("sourceFinancingName", "-1");
			//债权编号
			param1.put("finClaimId", "-1");
			//转让项目编号
			param1.put("transferId", "-1");
			//	还款计划编号
			param1.put("replanId", "-1");
			list.add(param1);
		}

		List<String> tradeList=new ArrayList<String>();
		tradeList.add("hjh_tender_success");
		tradeList.add("tender_success");
		CertRequest certTransactRequest=new CertRequest();
		certTransactRequest.setTradeList(tradeList);
		certTransactRequest.setBorrowNid(borrow.getBorrowNid());
		List<AccountListVO> tenderList=this.amTradeClient.getAccountListVOListByRequest(certTransactRequest);

		for (AccountListVO accountList2 : tenderList) {
			UserInfoVO tenderUsersInfo=this.amUserClient.findUserInfoById(accountList2.getUserId());
			Map<String, Object> param3 = new HashMap<String, Object>();
			//接口版本号
			param3.put("version", CertCallConstant.CERT_CALL_VERSION);
			//交易流水时间
			param3.put("transTime", GetDate.dateToString(accountList2.getCreateTime()));
			//	平台交易流水号
			param3.put("transId", accountList2.getNid());
			//平台编号
			param3.put("sourceCode", systemConfig.getCertSourceCode());
			//原散标编号
			param3.put("sourceProductCode", borrow.getBorrowNid());
			//原散标名称
			param3.put("sourceProductName", borrow.getName());
			//交易类型
			param3.put("transType", "2");
			//交易方式
			param3.put("transPayment", "a");
			//交易类型描述
			param3.put("transTypeDec", CertTradeTypeEnum.getName("2"));
			//交易金额
			param3.put("transMoney", accountList2.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			//	交易日期
			param3.put("transDate", GetDate.formatDate(accountList2.getCreateTime()));
			//交易人员银行（或三方支付名称）
			param3.put("transBank", "江西银行");
			//用户标示哈希
			param3.put("userIdcardHash", tool.idCardHash(tenderUsersInfo.getIdcard()));
			//存管银行流水编号
			param3.put("bankTransId", accountList2.getSeqNo());
			//原产品信息编号
			param3.put("sourceFinancingCode", "-1");
			//原产品信息名称
			param3.put("sourceFinancingName", "-1");
			//债权编号
			param3.put("finClaimId", accountList2.getNid());
			//转让项目编号
			param3.put("transferId", "-1");
			//还款计划编号
			param3.put("replanId", "-1");
			list.add(param3);
		}
	}

	private void accountAdjustmentUp(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(accountList.getRoleId()!=1){
			return ;
		}

		/******************发送6充值******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		if(accountList.getNid()==null||accountList.getNid().length()==0){
			//平台交易流水号
			param.put("transId", accountList.getId()+"");
		}else{
			//平台交易流水号
			param.put("transId", accountList.getNid());
		}
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", "-1");
		//原散标名称
		param.put("sourceProductName", "-1");
		//交易类型
		param.put("transType", "6");
		//交易方式
		param.put("transPayment", "a");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("6"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", "-1");
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);
	}

	private void recharge(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(accountList.getRoleId()!=1){
			return ;
		}
		/******************发送6充值******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", "-1");
		//原散标名称
		param.put("sourceProductName", "-1");
		//交易类型
		param.put("transType", "6");
		if("recharge".equals(accountList.getTrade())){
			//交易方式
			param.put("transPayment", "f");
		}else{
			//交易方式
			param.put("transPayment", "d");
		}

		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("6"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//	交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", "-1");
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);
	}

	private void accountAdjustmentDown(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(accountList.getRoleId()!=1){
			return ;
		}
		/******************发送7提现******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//平台交易流水号
		if(accountList.getNid()==null||accountList.getNid().length()==0){
			//平台交易流水号
			param.put("transId", accountList.getId()+"");
		}else{
			//平台交易流水号
			param.put("transId", accountList.getNid());
		}
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", "-1");
		//原散标名称
		param.put("sourceProductName", "-1");
		//交易类型
		param.put("transType", "7");
		//交易方式
		param.put("transPayment", "g");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("7"));
		//交易金额
		param.put("transMoney", accountList.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		//交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", "-1");
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);
	}

	private void cashSuccess(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(accountList.getRoleId()!=1){
			return ;
		}
		List<AccountWithdrawVO> accountwithdraws=amTradeClient.selectAccountWithdrawByOrdId(accountList.getNid());
		if(accountwithdraws==null||accountwithdraws.size()==0){
			return;
		}

		/******************发送7提现******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param.put("sourceProductCode", "-1");
		//原散标名称
		param.put("sourceProductName", "-1");
		//交易类型
		param.put("transType", "7");
		//交易方式
		param.put("transPayment", "g");
		//交易类型描述
		param.put("transTypeDec", CertTradeTypeEnum.getName("7"));
		//交易金额
		param.put("transMoney", accountwithdraws.get(0).getCredited());
		//交易日期
		param.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param.put("transBank", "江西银行");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param.put("sourceFinancingName", "-1");
		//债权编号
		param.put("finClaimId", "-1");
		//转让项目编号
		param.put("transferId", "-1");
		//	还款计划编号
		param.put("replanId", "-1");
		list.add(param);
		/******************发送23提现手续费******************/
		//接口版本号
		param1.put("version", CertCallConstant.CERT_CALL_VERSION);
		//交易流水时间
		param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		//	平台交易流水号
		param1.put("transId", accountList.getNid());
		//平台编号
		param1.put("sourceCode", systemConfig.getCertSourceCode());
		//原散标编号
		param1.put("sourceProductCode", "-1");
		//原散标名称
		param1.put("sourceProductName", "-1");
		//交易类型
		param1.put("transType", "23");
		//交易方式
		param1.put("transPayment", "g");
		//交易类型描述
		param1.put("transTypeDec", CertTradeTypeEnum.getName("23"));
		//交易金额
		param1.put("transMoney", accountwithdraws.get(0).getFee());
		//	交易日期
		param1.put("transDate", GetDate.formatDate(accountList.getCreateTime()));
		//交易人员银行（或三方支付名称）
		param1.put("transBank", "江西银行");
		//用户标示哈希
		param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//存管银行流水编号
		param1.put("bankTransId", accountList.getSeqNo());
		//原产品信息编号
		param1.put("sourceFinancingCode", "-1");
		//原产品信息名称
		param1.put("sourceFinancingName", "-1");
		//债权编号
		param1.put("finClaimId", "-1");
		//转让项目编号
		param1.put("transferId", "-1");
		//	还款计划编号
		param1.put("replanId", "-1");
		list.add(param1);
	}
}

