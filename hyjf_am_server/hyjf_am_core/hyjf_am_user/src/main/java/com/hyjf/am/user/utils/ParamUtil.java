package com.hyjf.am.user.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.pay.lib.anrong.bean.AnRongBean;


public class ParamUtil {

    /**
     * 
     * 安融查询接口参数
     * @author sss
     * @param form
     * @return anRongBean
     */
    public static AnRongBean getQueryUserParm(MspApplytRequest form) {
        String customerName = form.getName();
        String paperNumber = form.getIdentityCard();
        String loanType = form.getLoanType();
        String creditAddress = form.getCreditAddress();
        BigDecimal loanMoney = form.getLoanMoney();
        Integer loanTimeLimit = form.getLoanTimeLimit();
        String applyDate = form.getApplyDate();
        String logUserId = form.getAdminId();
        AnRongBean ab=new AnRongBean();
        ab.setCustomerName(customerName);
        ab.setPaperNumber(paperNumber);
        ab.setLoanType(loanType);
        ab.setLoanCreditCity(creditAddress);
        ab.setCreditAddress(creditAddress);
        ab.setLoanMoney(loanMoney.toString());
        ab.setLoanTimeLimit(loanTimeLimit.toString());
        ab.setApplyDate(applyDate);
        ab.setLogUserId(logUserId);
        return ab;
    }

    /**
     * 
     * 安融共享 接口参数
     * @author sss
     * @param form
     * @return
     */
    public static Map<String, String> getSendParm(MspApplytRequest form) {
        String logUserId = form.getAdminId();
        
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("customerName", form.getName());
        parm.put("paperNumber", form.getIdentityCard());
        parm.put("loanType", form.getLoanType());
     //   parm.put("creditAddress", form.getCreditAddress());
      
//        parm.put("applyLoanMoney", form.getLoanMoney()==null?"":form.getLoanMoney().toString());
   //     parm.put("loanTimeLimit", form.getLoanTimeLimit()+"");
 //       parm.put("applyDate", form.getApplyDate());
        parm.put("logUserId", logUserId);
        parm.put("bizType", form.getServiceType());
        
        parm.put("loanId", form.getApplyId());
        parm.put("checkResultTime", form.getApprovalDate());
        parm.put("checkResult", form.getApprovalResult());
        
        if("01".equals(form.getApprovalResult())) {
        	parm.put("loanMoney", form.getLoanMoney()==null?"":form.getLoanMoney().toString());
            parm.put("loanStartDate", form.getContractBegin());
            parm.put("loanEndDate", form.getContractEnd());
            parm.put("loanCreditCity", form.getCreditAddress());
            parm.put("loanAssureType", form.getGuaranteeType());
            parm.put("loanPeriods", form.getLoanTimeLimit()+"");
            // 债权信息
            parm.put("nbsMoney", form.getUnredeemedMoney()==null?"":form.getUnredeemedMoney().toString());
            parm.put("state", form.getRepaymentStatus());
            parm.put("nbMoney", form.getOverdueAmount()==null?"":form.getOverdueAmount().toString());
            parm.put("overdueStartDate", form.getOverdueDate());
            parm.put("overdueDays", form.getOverdueLength());
            parm.put("overdueReason", form.getOverdueReason());
        }

        
        return parm;
    }
}
