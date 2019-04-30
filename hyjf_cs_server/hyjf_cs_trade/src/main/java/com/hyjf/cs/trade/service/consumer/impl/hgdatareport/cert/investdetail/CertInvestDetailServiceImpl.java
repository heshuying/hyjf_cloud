package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.investdetail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.vo.admin.coupon.CertCouponRecoverVO;
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
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.investdetail.CertInvestDetailService;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transact.CertTransactService;
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
public class CertInvestDetailServiceImpl extends BaseHgCertReportServiceImpl implements CertInvestDetailService {
	Logger logger = LoggerFactory.getLogger(CertInvestDetailServiceImpl.class);
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
			logger.error(e.getMessage());
		}

		return JSONArray.parseArray(JSON.toJSONString(list));
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
        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", systemConfig.getCertSourceCode());
        //平台交易流水号
        param.put("transId", accountList.getNid());
        //产品信息编号
        param.put("sourceFinancingCode",borrowAndInfoVO.getPlanNid());
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
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
		//接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", systemConfig.getCertSourceCode());
        //平台交易流水号
        param.put("transId", accountList.getNid());
        //产品信息编号
        param.put("sourceFinancingCode", "-1");
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
        param.put("sourceFinancingCode", "-1");
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
        param1.put("sourceFinancingCode", "-1");
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
		BigDecimal interest=BigDecimal.ZERO;
		BigDecimal capital=BigDecimal.ZERO;
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
					interest=interest.subtract(hjhDebtCreditRepay.getReceiveInterestYes());
					capital=capital.subtract(hjhDebtCreditRepay.getReceiveCapitalYes());
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
					interest=interest.subtract(hjhDebtCreditRepay.getReceiveInterestYes());
					capital=capital.subtract(hjhDebtCreditRepay.getReceiveCapitalYes());
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
		}

		/****************** 发送8赎回本金******************/

        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", systemConfig.getCertSourceCode());
        //平台交易流水号
        param.put("transId", accountList.getNid());
        //产品信息编号
        param.put("sourceFinancingCode", "-1");
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
        param1.put("sourceFinancingCode", "-1");
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
        UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());
        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", systemConfig.getCertSourceCode());
        //平台交易流水号
        param.put("transId", accountList.getNid());
        //产品信息编号
        param.put("sourceFinancingCode", "-1");
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
		UserInfoVO usersInfo=this.amUserClient.findUserInfoById(accountList.getUserId());

        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", systemConfig.getCertSourceCode());
        //平台交易流水号
        param.put("transId", accountList.getNid());
        //产品信息编号
        param.put("sourceFinancingCode", "-1");
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
		Map<String, Object> param = new HashMap<String, Object>();
        CertRequest certRequest=new CertRequest();
        certRequest.setRealTenderId(accountList.getNid());
        List<CouponRealTenderVO> couponRealTenders =amTradeClient.getCouponRealTenderListByCertRequest(certRequest);
        if(couponRealTenders==null||couponRealTenders.size()==0){
			//交易金额
			param.put("transMoney", FORMAT.format(accountList.getAmount()));
        }else{
			certRequest.setCouponTenderId(couponRealTenders.get(0).getRealTenderId());
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
		param.put("sourceFinancingCode", "-1");
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

