/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AutoTenderExceptionServiceImpl, v0.1 2018/7/12 10:30
 */
@Service
public class AutoTenderExceptionServiceImpl implements AutoTenderExceptionService {
    @Autowired
    private AmTradeClient amTradeClient;

    private Logger logger = LoggerFactory.getLogger(AutoTenderExceptionServiceImpl.class);

    /**
     * 检索汇计划加入明细列表
     * @param request
     * @return
     */
    @Override
    public AutoTenderExceptionResponse selectAccedeRecordList(AutoTenderExceptionRequest request){
        return amTradeClient.selectAccedeRecordList(request);
    }

    /**
     * 查找HjhAccedeVO实体
     * @param tenderExceptionSolveRequest
     * @return
     */
    @Override
    public HjhAccedeVO getHjhAccedeVO(TenderExceptionSolveRequest tenderExceptionSolveRequest){
        HjhAccedeResponse hjhAccedeResponse =amTradeClient.selectHjhAccedeByParam(tenderExceptionSolveRequest);
        HjhAccedeVO hjhAccedeVO = new HjhAccedeVO();
        if(null!=hjhAccedeResponse){
            hjhAccedeVO =  hjhAccedeResponse.getResult();
        }
        return hjhAccedeVO;
    }

    /**
     * 查找HjhPlanBorrowTmpVO实体
     * @param tenderExceptionSolveRequest
     * @return
     */
    @Override
    public HjhPlanBorrowTmpVO getHjhPlanBorrowTmpVO(TenderExceptionSolveRequest tenderExceptionSolveRequest){
        HjhPlanBorrowTmpResponse hjhAccedeResponse =amTradeClient.selectBorrowJoinList(tenderExceptionSolveRequest);
        HjhPlanBorrowTmpVO  hjhAccedeVO = new HjhPlanBorrowTmpVO ();
        if(null!=hjhAccedeResponse){
            hjhAccedeVO =  hjhAccedeResponse.getResult();
        }
        return hjhAccedeVO;
    }
    /**
     * 查询相应的债权的状态
     *
     * @param userId
     * @param accountId
     * @param orderId
     * @return
     */
    @Override
    public BankCallBean debtStatusQuery(int userId, String accountId, String orderId) {
        // 获取共同参数
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_BID_APPLY_QUERY);// 消息类型
        bean.setLogUserId(String.valueOf(userId));
        /*bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        //银行代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));*/
        //暂定
        bean.setBankCode("30050000");
        bean.setInstCode("00810001");// 机构代码
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(accountId);// 电子账号
        bean.setOrgOrderId(orderId);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));

        BankCallBean statusResult = BankCallUtils.callApiBg(bean);

        return statusResult;
    }
    /**
     * 查询相应的债权的状态
     *
     * @param userId
     * @param accountId
     * @param orderId
     * @return
     */
    @Override
    public BankCallBean creditStatusQuery(int userId, String accountId, String orderId) {
        // 获取共同参数
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_CREDIT_INVEST_QUERY);// 消息类型
        bean.setLogUserId(String.valueOf(userId));
        /*
        // 机构代码
        bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));
        //银行代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));*/
        //暂定
        bean.setBankCode("30050000");
        bean.setInstCode("00810001");
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(accountId);// 电子账号
        bean.setOrgOrderId(orderId);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));

        BankCallBean statusResult = BankCallUtils.callApiBg(bean);

        return statusResult;
    }

    @Override
    public HjhPlanVO getFirstHjhPlanVO(String planNid){
        List<HjhPlanVO> hjhPlanVOList= amTradeClient.getHjhPlanByPlanNid(planNid);
        if(null!=hjhPlanVOList&&hjhPlanVOList.size()>0){
            return hjhPlanVOList.get(0);
        }
        return null;
    }
    /**
     * 根据编号获取borrow
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowVO selectBorrowByNid(String borrowNid){
        return amTradeClient.selectBorrowByNid(borrowNid);
    }
    /**
     * 更新投资数据
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateBorrowForAutoTender(BorrowVO borrow, HjhAccedeVO hjhAccede, BankCallBean bean){
        return amTradeClient.updateBorrowForAutoTender(borrow,hjhAccede,bean);
    }
    /**
     * 更新
     * @param status
     * @param accedeId
     * @return
     */
    @Override
    public boolean updateTenderByParam(int status,int accedeId){
        return amTradeClient.updateTenderByParam(status,accedeId);
    }
    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author nxl
     */
    @Override
    public Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        return amTradeClient.saveCreditTenderLogNoSave(credit,hjhAccede,orderId,orderDate,yujiAmoust,isLast);
    }
    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean,String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap){
        return amTradeClient.updateCreditForAutoTender(credit,hjhAccede,hjhPlan,bean,tenderUsrcustid,sellerUsrcustid,resultMap);
    }
}
