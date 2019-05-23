package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.investdetail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.trade.CertReportEntityResponse;
import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.vo.admin.coupon.CertCouponRecoverVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRealTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.investdetail.CertOldInvestDetailService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.cert.open.CertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pcc
 */

@Service
public class CertOldInvestDetailServiceImpl extends BaseHgCertReportServiceImpl implements CertOldInvestDetailService {
	Logger logger = LoggerFactory.getLogger(CertOldInvestDetailServiceImpl.class);
	@Autowired
	AmTradeClient amTradeClient;
	@Autowired
	AmUserClient amUserClient;
	@Autowired
	SystemConfig systemConfig;

	private String thisMessName = "投资明细信息";
	private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

	public static final DecimalFormat FORMAT = new DecimalFormat("#0.00");
	@Override
	public JSONArray createDate(List<CertAccountListCustomizeVO> accountLists) {
		

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (CertAccountListCustomizeVO accountList : accountLists) {
				 createParam(accountList,list);
			}
			if(list==null||list.size()==0){
				return null;
			}
		} catch (Exception e) {
			logger.error("报错了",e);
		}

		return JSONArray.parseArray(JSON.toJSONString(list));
	}
	@Autowired
	private BaseClient baseClient;

	private String baseUrl = "http://CS-MESSAGE/cs-message/certStatistical/";
	@Override
	public void insertOldMessage(CertReportEntityVO bean) {
		try {
			// 设置共通参数
			bean = setCommonParam(bean);
		}catch (Exception e) {
			logger.error("设置参数失败",e);
		}
		Map<String, String> params = getBankParam(bean);
		// 插入mongo
		String url = baseUrl + "insertOldMessage";
		BooleanResponse response = this.baseClient.postExe(url, bean, BooleanResponse.class);

	}

	@Override
	public List<CertAccountListCustomizeVO> getCertAccountListCustomizeVO(Integer page, Integer size, List<String> selectBorrowNidList, String trader) {
		CertRequest certTransactRequest=new CertRequest();
		certTransactRequest.setLimitStart((page-1) * size);
		certTransactRequest.setLimitEnd(size);
		certTransactRequest.setTrade(trader);
		certTransactRequest.setMaxId(RedisUtils.get("CERT_OLD_INVEST_DETAIL_MAX_ID"));
		if(selectBorrowNidList!=null){
			certTransactRequest.setBorrowNidList(selectBorrowNidList);
		}
		List<CertAccountListCustomizeVO> accountLists=amTradeClient.getCertAccountListCustomizeVO(certTransactRequest);
		return accountLists;
	}

	@Override
	public List<CertReportEntityVO> getNotSendAccountList() {
		String url = baseUrl + "getNotSendAccountList";
		CertReportEntityResponse response = this.baseClient.postExe(url,null,CertReportEntityResponse.class);
		List<CertReportEntityVO> certReportEntityVOList=response.getResultList();
		return certReportEntityVOList;
	}

	@Override
	public void updateAccountSuccess(CertReportEntityVO bean) {
		String url = baseUrl + "updateAccountSuccess";
		BooleanResponse response = this.baseClient.postExe(url, bean, BooleanResponse.class);
	}

    @Override
    public List<String> getBorrowNidList() {
        return amTradeClient.getBorrowNidList();
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
		//优惠券回款 上送41红包
		case "experience_profit":
		case "cash_coupon_profit":
			couponProfit(accountList,list);
			break;

         //优惠券回款 上送41红包
         case "increase_interest_profit":
             increaseInerestProfit(accountList,list);
             break;
		//投资收到还款  发送8赎回本金 发送9赎回利息
		case "tender_recover_yes":
		case "hjh_repay_balance":
		case "hjh_repay_frost":
			tenderRecoverYes(accountList,list);
			break;
		//投资收到还款  发送8赎回本金 发送9赎回利息
		case "credit_tender_recover_yes":
		case "credit_tender_recover_forst":
			creditTenderRecoverYes(accountList,list);
			break;
		//承接债权 散标债转发送2出借金额
		case "creditassign":
			creditAssign(accountList,list);
			break;
		//自动承接债权 计划自动承接债转发送2出借金额
		case "accede_assign":
			accedeAssign(accountList,list);
			break;
		// 计划自动投资2出借金额
         case "hjh_tender_success":
             hjhTenderSuccess(accountList,list);
             break;
         // 散标投资2出借金额
         case "tender_success":
             tenderSuccess(accountList,list);
             break;
		default:
			break;
		}
	}
	//已改
	private void accedeAssign(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		List<HjhDebtCreditTenderVO> hjhDebtCreditTenders=amTradeClient.selectHjhCreditTenderListByAssignOrderId(accountList.getNid());
		if(hjhDebtCreditTenders==null||hjhDebtCreditTenders.size()==0){
			return;
		}
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(hjhDebtCreditTenders.get(0).getBorrowNid());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode",borrowAndInfoVO.getPlanNid()==null?borrowAndInfoVO.getBorrowNid():borrowAndInfoVO.getPlanNid());
		//交易类型
		param.put("transType", "2");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}
	//已改
	private void creditAssign(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		List<CreditTenderVO> creditTenders=amTradeClient.selectCreditTender(accountList.getNid());
		if(creditTenders==null||creditTenders.size()==0){
			return;
		}
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", creditTenders.get(0).getBidNid());
		//交易类型
		param.put("transType", "2");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));

		list.add(param);
	}
	//已改
	private void creditTenderRecoverYes(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		BigDecimal creditInterest=BigDecimal.ZERO;
		BigDecimal creditCapital=BigDecimal.ZERO;
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
		}

		/****************** 发送8赎回本金******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", borrowAndInfoVO.getPlanNid()==null?borrowAndInfoVO.getBorrowNid():borrowAndInfoVO.getPlanNid());
		//交易类型
		param.put("transType", "8");
		//交易金额
		param.put("transMoney", FORMAT.format(creditCapital));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
		/****************** 发送9赎回利息******************/
		//接口版本号
		param1.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param1.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param1.put("transId", accountList.getNid());
		//产品信息编号
		param1.put("sourceFinancingCode",borrowAndInfoVO.getPlanNid()==null?borrowAndInfoVO.getBorrowNid():borrowAndInfoVO.getPlanNid());
		//交易类型
		param1.put("transType", "9");
		//交易金额
		param1.put("transMoney", FORMAT.format(creditInterest));
		//用户标示哈希
		param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param1);
	}
	//已改
	private void tenderRecoverYes(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		BigDecimal interest=BigDecimal.ZERO;
		BigDecimal capital=BigDecimal.ZERO;
		if("end".equals(borrowAndInfoVO.getBorrowStyle())||"endday".equals(borrowAndInfoVO.getBorrowStyle())){
			CertRequest certRequest=new CertRequest();
			certRequest.setRepayOrdid(accountList.getNid());
			certRequest.setBorrowNid(accountList.getRemark());
			List<BorrowRecoverVO> borrowRecovers=amTradeClient.selectBorrowRecoverListByRequest(certRequest);
			if(borrowRecovers==null||borrowRecovers.size()==0){
				return;
			}
			interest=borrowRecovers.get(0).getRecoverInterestYes();
			capital=borrowRecovers.get(0).getRecoverCapitalYes();
			logger.info(logHeader + "interest:"+interest);
			logger.info(logHeader + "capital:"+capital);
			BorrowRecoverVO borrowRecover=borrowRecovers.get(0);
			if(borrowAndInfoVO.getPlanNid()!=null&&borrowAndInfoVO.getPlanNid().length()>0){
				//智投
                /*CertRequest certRequest1=new CertRequest();
                certRequest1.setInvestOrderId(borrowRecover.getNid());
                certRequest1.setBorrowNid(borrowRecover.getBorrowNid());
                certRequest1.setPeriod(borrowRecover.getRecoverPeriod());
                List<HjhDebtCreditRepayVO> hjhDebtCreditRepays=amTradeClient.getHjhDebtCreditRepayListByRequest(certRequest1);
                for (HjhDebtCreditRepayVO hjhDebtCreditRepay : hjhDebtCreditRepays) {
                    interest=interest.subtract(hjhDebtCreditRepay.getReceiveInterestYes());
                    capital=capital.subtract(hjhDebtCreditRepay.getReceiveCapitalYes());
                }*/
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
			logger.info(logHeader + "interest:"+interest);
			logger.info(logHeader + "capital:"+capital);
			if(borrowAndInfoVO.getPlanNid()!=null&&borrowAndInfoVO.getPlanNid().length()>0){
				//智投
/*
                CertRequest certRequest1=new CertRequest();
                certRequest1.setInvestOrderId(borrowRecoverPlan.getNid());
                certRequest1.setBorrowNid(borrowRecoverPlan.getBorrowNid());
                certRequest1.setPeriod(borrowRecoverPlan.getRecoverPeriod());
                List<HjhDebtCreditRepayVO> hjhDebtCreditRepays=amTradeClient.getHjhDebtCreditRepayListByRequest(certRequest1);
                logger.info(logHeader + "hjhDebtCreditRepays.size（）:"+hjhDebtCreditRepays.size());
                for (HjhDebtCreditRepayVO hjhDebtCreditRepay : hjhDebtCreditRepays) {
                    logger.info(logHeader + "hjhDebtCreditRepay.getReceiveInterestYes():"+hjhDebtCreditRepay.getReceiveInterestYes());
                    logger.info(logHeader + "hjhDebtCreditRepay.getReceiveCapitalYes():"+hjhDebtCreditRepay.getReceiveCapitalYes());
                    interest=interest.subtract(hjhDebtCreditRepay.getReceiveInterestYes());
                    capital=capital.subtract(hjhDebtCreditRepay.getReceiveCapitalYes());
                    logger.info(logHeader + "interest:"+interest);
                    logger.info(logHeader + "capital:"+capital);

                }
                logger.info(logHeader + "hjhDebtCreditRepays.size（）:"+hjhDebtCreditRepays.size());*/
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
		}
		logger.info(logHeader + "interest00:"+interest);
		logger.info(logHeader + "capital00:"+capital);
		/****************** 发送8赎回本金******************/

		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", borrowAndInfoVO.getPlanNid()==null?borrowAndInfoVO.getBorrowNid():borrowAndInfoVO.getPlanNid());
		//交易类型
		param.put("transType", "8");
		//交易金额
		param.put("transMoney", FORMAT.format(capital));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
		/****************** 发送9赎回利息******************/
		//接口版本号
		param1.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param1.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param1.put("transId", accountList.getNid());
		//产品信息编号
		param1.put("sourceFinancingCode", borrowAndInfoVO.getPlanNid()==null?borrowAndInfoVO.getBorrowNid():borrowAndInfoVO.getPlanNid());
		//交易类型
		param1.put("transType", "9");
		//交易金额
		param1.put("transMoney", FORMAT.format(interest));
		//用户标示哈希
		param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param1);
	}


	private void increaseInerestProfit(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		CertRequest certRequest=new CertRequest();
		certRequest.setTransferId(accountList.getNid());
		List<CertCouponRecoverVO> certCouponRecoverVOList=amTradeClient.getCouponRecoverListByCertRequest(certRequest);
		if(certCouponRecoverVOList==null||certCouponRecoverVOList.size()==0){
			//交易金额
			return;
		}
		certRequest.setCouponTenderId(certCouponRecoverVOList.get(0).getTenderId());
		List<CouponRealTenderVO> couponRealTenders =amTradeClient.getCouponRealTenderListByCertRequest(certRequest);
		if(couponRealTenders==null||couponRealTenders.get(0).getRealTenderId()==null){
			//交易金额
			return;
		}
		List<BorrowTenderCpnVO> borrowTenderCpnList=amTradeClient.getBorrowTenderCpnListByCertRequest(certRequest);
		if(borrowTenderCpnList==null||borrowTenderCpnList.size()==0){
			//交易金额
			return;
		}
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(borrowTenderCpnList.get(0).getBorrowNid());
		if(borrowAndInfoVO==null){
			return;
		}
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", borrowTenderCpnList.get(0).getBorrowNid());
		//交易类型
		param.put("transType", "41");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}




	//已改
	private void couponProfit(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		CertRequest certRequest=new CertRequest();
		certRequest.setTransferId(accountList.getNid());
		List<CertCouponRecoverVO> certCouponRecoverVOList=amTradeClient.getCouponRecoverListByCertRequest(certRequest);
		if(certCouponRecoverVOList==null||certCouponRecoverVOList.size()==0){
			//交易金额
			return;
		}
		certRequest.setCouponTenderId(certCouponRecoverVOList.get(0).getTenderId());
		List<CouponRealTenderVO> couponRealTenders =amTradeClient.getCouponRealTenderListByCertRequest(certRequest);
		if(couponRealTenders==null||couponRealTenders.get(0).getRealTenderId()==null||couponRealTenders.get(0).getRealTenderId().length()==0){
			//交易金额
			return;
		}
		List<BorrowTenderCpnVO> borrowTenderCpnList=amTradeClient.getBorrowTenderCpnListByCertRequest(certRequest);
		if(borrowTenderCpnList==null||borrowTenderCpnList.size()==0){
			//交易金额
			return;
		}
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(borrowTenderCpnList.get(0).getBorrowNid());
		if(borrowAndInfoVO==null){
			return;
		}
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", borrowTenderCpnList.get(0).getBorrowNid());
		//交易类型
		param.put("transType", "10");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}

	//已改
	private void tenderSuccess(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws Exception {
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		CertRequest certRequest=new CertRequest();
		certRequest.setRealTenderId(accountList.getNid());
		List<CouponRealTenderVO> couponRealTenders =amTradeClient.getCouponRealTenderListByCertRequest(certRequest);
		if(couponRealTenders==null||couponRealTenders.size()==0){
			//交易金额
			param.put("transMoney", FORMAT.format(accountList.getAmount()));
		}else{
			certRequest.setCouponTenderId(couponRealTenders.get(0).getCouponTenderId());
			List<BorrowTenderCpnVO> borrowTenderCpnList=amTradeClient.getBorrowTenderCpnListByCertRequest(certRequest);
			if(borrowTenderCpnList==null||borrowTenderCpnList.size()==0){
				//交易金额
				param.put("transMoney", FORMAT.format(accountList.getAmount()));
			}else{
				//交易金额
				param.put("transMoney", FORMAT.format(accountList.getAmount().add(borrowTenderCpnList.get(0).getRecoverAccountAll())));
			}
		}

		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", accountList.getRemark());
		//交易类型
		param.put("transType", "2");
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}

	//已改
	private void hjhTenderSuccess(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws Exception {
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(accountList.getRemark());
		Map<String, Object> param = new HashMap<String, Object>();
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", borrowAndInfoVO.getPlanNid());
		//交易类型
		param.put("transType", "2");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}

	//已改
	private void accountAdjustmentUp(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		if(accountList.getRoleId()!=1){
			return ;
		}

		/******************发送6充值******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		if(accountList.getNid()==null||accountList.getNid().length()==0){
			//平台交易流水号
			param.put("transId", accountList.getId()+"");
		}else{
			//平台交易流水号
			param.put("transId", accountList.getNid());
		}
		//产品信息编号
		param.put("sourceFinancingCode", "-1");
		//交易类型
		param.put("transType", "6");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}
	//已改
	private void recharge(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		if(accountList.getRoleId()!=1){
			return ;
		}
		/******************发送6充值******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", "-1");
		//交易类型
		param.put("transType", "6");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}
	//已改
	private void accountAdjustmentDown(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
		if(accountList.getRoleId()!=1){
			return ;
		}
		/******************发送7提现******************/
		//接口版本号
		param.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		if(accountList.getNid()==null||accountList.getNid().length()==0){
			//平台交易流水号
			param.put("transId", accountList.getId()+"");
		}else{
			//平台交易流水号
			param.put("transId", accountList.getNid());
		}
		//产品信息编号
		param.put("sourceFinancingCode", "-1");
		//交易类型
		param.put("transType", "7");
		//交易金额
		param.put("transMoney", FORMAT.format(accountList.getAmount()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
	}
	//已改
	private void cashSuccess(CertAccountListCustomizeVO accountList, List<Map<String,Object>> list) throws CertException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		UserInfoVO usersInfo=amUserClient.findUsersInfoById(accountList.getUserId());
		if(usersInfo==null||usersInfo.getIdcard()==null){
			return;
		}
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
		//平台编号
		param.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param.put("transId", accountList.getNid());
		//产品信息编号
		param.put("sourceFinancingCode", "-1");
		//交易类型
		param.put("transType", "7");
		//交易金额
		param.put("transMoney", FORMAT.format(accountwithdraws.get(0).getCredited()));
		//用户标示哈希
		param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param);
		/******************发送23提现手续费******************/

		param1.put("version", CertCallConstant.CERT_CALL_VERSION);
		//平台编号
		param1.put("sourceCode", systemConfig.getCertSourceCode());
		//平台交易流水号
		param1.put("transId", accountList.getNid());
		//产品信息编号
		param1.put("sourceFinancingCode", "-1");
		//交易类型
		param1.put("transType", "23");
		//交易金额
		param1.put("transMoney", accountwithdraws.get(0).getFee());
		//用户标示哈希
		param1.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
		//交易流水时间
		param1.put("transTime", GetDate.dateToString(accountList.getCreateTime()));
		list.add(param1);
	}
}

