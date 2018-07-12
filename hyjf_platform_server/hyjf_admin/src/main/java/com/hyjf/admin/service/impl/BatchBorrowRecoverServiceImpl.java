package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.response.admin.BatchBorrowRecoverBankInfoReponse;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BorrowRecoverBankInfoVo;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.common.service.BaseClientImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchBorrowRecoverServiceImpl  extends BaseServiceImpl implements BatchBorrowRecoverService{


    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private BaseClient baseClient;

    /**
     * 获取批次放款的显示列表
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverReponse queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {

        BatchBorrowRecoverReponse reponse = amTradeClient.getBatchBorrowRecoverList(request);
        return reponse;
    }

    /**
     * 获取批次内容的显示状态描述
     * @param listAccountDetail
     */
    @Override
    public void queryBatchCenterStatusName(List<BatchBorrowRecoverVo> listAccountDetail,String nameClass) {
        //获取放款相关状态描述
        List<ParamNameVO> paramNameList = this.getParamNameList(nameClass);

        for (BatchBorrowRecoverVo vo:
             listAccountDetail) {
            String paramName = this.getParamName(vo.getStatus(), paramNameList);
            vo.setStatusStr(paramName);
        }
    }

    /**
     * 获得批次列表的求和
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverVo queryBatchCenterListSum(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse reponse = amTradeClient.getBatchBorrowCenterListSum(request);
        if(reponse != null){
            return reponse.getResult();
        }
        return null;
    }

    /**
     * 查询实时放款银行交易明细
     * @param apicronID
     * @return
     */
    @Override
    public BatchBorrowRecoverBankInfoReponse queryBatchBorrowRecoverBankInfoList(String apicronID) {
        BorrowApicronResponse reponse = amTradeClient.getBorrowApicronByID(apicronID);
        if(reponse != null){
            BorrowApicronVO apicron = reponse.getResult();
            // 借款人用户ID
            Integer borrowUserId = apicron.getUserId();
            // 根据借款人用户ID查询借款人用户电子账户号
            String url = "http://AM-USER/am-user/bankopen/selectById/" + borrowUserId;
            BankOpenAccountResponse bankOpenAccountResponse = baseClient.getExe(url, BankOpenAccountResponse.class);
            BankOpenAccountVO borrowUserAccount = bankOpenAccountResponse.getResult();

            // 借款人用户ID
            String borrowUserAccountId = borrowUserAccount.getAccount();
            // 放款订单号
            String orderId = apicron.getOrdid();
            String channel = BankCallConstant.CHANNEL_PC;
            String txDate = GetOrderIdUtils.getTxDate();
            String txTime = GetOrderIdUtils.getTxTime();
            String seqNo = GetOrderIdUtils.getSeqNo(6);
            // 调用满标自动放款查询
            BankCallBean bean = new BankCallBean();
            // 版本号
            bean.setVersion(BankCallConstant.VERSION_10);
            // 交易代码
            bean.setTxCode(BankCallConstant.TXCODE_AUTOLEND_PAY_QUERY);
            // 渠道
            bean.setChannel(channel);
            // 交易日期
            bean.setTxDate(txDate);
            // 交易时间
            bean.setTxTime(txTime);
            // 流水号
            bean.setSeqNo(seqNo);
            // 借款人电子账号
            bean.setAccountId(borrowUserAccountId);
            // 申请订单号(满标放款交易订单号)
            bean.setLendPayOrderId(orderId);
            // 标的编号
            bean.setProductId(apicron.getBorrowNid());
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
            bean.setLogUserId(String.valueOf(borrowUserId));
            bean.setLogOrderId(GetOrderIdUtils.getOrderId2(borrowUserId));
            bean.setLogRemark("满标自动放款查询");
            BankCallBean bankCallBean = BankCallUtils.callApiBg(bean);
            List<BorrowRecoverBankInfoVo> detailList = getDetailList(bankCallBean);
            BatchBorrowRecoverBankInfoReponse resultReponse = new BatchBorrowRecoverBankInfoReponse();
            resultReponse.setResultList(detailList);
            return resultReponse;
        }
        return null;
    }

    /**
     * 获得页面需要的展示列表
     * @param resultBean
     * @return
     */
    private List<BorrowRecoverBankInfoVo> getDetailList(BankCallBean resultBean) {
        List<BorrowRecoverBankInfoVo> detailList = new ArrayList<>();
        String subPacks = resultBean.getSubPacks();
        if (StringUtils.isNotBlank(subPacks)) {
            JSONArray loanDetails = JSONObject.parseArray(subPacks);
            for (int j = 0; j < loanDetails.size(); j++) {
                JSONObject loanDetail = loanDetails.getJSONObject(j);
                BorrowRecoverBankInfoVo info = new BorrowRecoverBankInfoVo();
                info.setAuthCode(loanDetail.getString(BankCallConstant.PARAM_AUTHCODE));// 授权码
                info.setTxState(loanDetail.getString(BankCallConstant.PARAM_TXSTATE));// 交易状态
                info.setOrderId(loanDetail.getString(BankCallConstant.PARAM_ORDERID));// 订单号
                info.setTxAmount(loanDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT).toString());// 操作金额
                info.setForAccountId(loanDetail.getString(BankCallConstant.PARAM_FORACCOUNTID));// 借款人银行账户
                info.setProductId(loanDetail.getString(BankCallConstant.PARAM_PRODUCTID));// 标的号
                info.setFileMsg(loanDetail.getString(BankCallConstant.PARAM_FAILMSG));//错误提示
                detailList.add(info);
            }
        }
        return detailList;
    }
}
