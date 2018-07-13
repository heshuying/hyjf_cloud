package com.hyjf.admin.service.impl.exception;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.am.resquest.trade.BankCreditEndRequest;
import com.hyjf.am.resquest.trade.BankCreditEndUpdateRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 结束债权异常
 * @author hesy
 * @version BankCreditEndServiceImpl, v0.1 2018/7/12 16:11
 */
@Service
public class BankCreditEndServiceImpl extends BaseServiceImpl implements com.hyjf.admin.service.exception.BankCreditEndService {
    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public List<BankCreditEndVO> getCreditEndList(BankCreditEndRequest requestBean) {
        return amTradeClient.getCreditEndList(requestBean);
    }

    @Override
    public int getCreditEndCount(BankCreditEndRequest requestBean){
        return amTradeClient.getCreditEndCount(requestBean);
    }

    @Override
    public BankCreditEndVO getCreditEndByOrderId(String orderId){
        return amTradeClient.getCreditEndByOrderId(orderId);
    }

    @Override
    public int updateBankCreditEnd(BankCreditEndVO requestBean) {
        return amTradeClient.updateBankCreditEnd(requestBean);
    }

    @Override
    public boolean updateCreditEndForInitial(BankCreditEndVO requestBean) {
        return amTradeClient.updateCreditEndForInitial(requestBean)>0? true : false;
    }

    /**
     * 请求参数校验
     * @param requestBean
     * @return
     */
    @Override
    public boolean checkForUpdate(BankCreditEndUpdateRequest requestBean){
        if (Validator.isNull(requestBean.getTxCounts())) {
            return false;
        }
        if (Validator.isNull(requestBean.getTxDate())) {
            return false;
        }
        if (Validator.isNull(requestBean.getBatchNo())) {
            return false;
        }
        if (Validator.isNull(requestBean.getStatus())) {
            return false;
        }
        return true;
    }

    /**
     * 请求参数校验
     * @param requestBean
     * @return
     */
    @Override
    public boolean checkForUpdateInitial(BankCreditEndUpdateRequest requestBean){
        if (Validator.isNull(requestBean.getOrderId())) {
            return false;
        }
        if (Validator.isNull(requestBean.getTxDate())) {
            return false;
        }
        if (Validator.isNull(requestBean.getBatchNo())) {
            return false;
        }
        if (Validator.isNull(requestBean.getStatus())) {
            return false;
        }
        return true;
    }

    /**
     * 查询银行接口
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public List<BankCallBean> queryBatchDetails(BankCreditEndUpdateRequest requestBean) {
        // 总交易笔数
        int txCounts = Integer.parseInt(requestBean.getTxCounts());
        String batchTxDate = String.valueOf(requestBean.getTxDate());
        String batchNo = requestBean.getBatchNo();// 批次号
        int pageSize = 50;// 每页笔数
        int pageTotal = txCounts / pageSize + 1;// 总页数
        String type = null;
        if (Integer.parseInt(requestBean.getStatus()) == 10){
            //合法性校验失败交易
            type = BankCallConstant.DEBT_BATCH_TYPE_VERIFYFAIL;
        }else {
            //所有交易
            type = BankCallConstant.DEBT_BATCH_TYPE_ALL;
        }
        List<BankCallBean> results = new ArrayList<>();
        for (int i = 1; i <= pageTotal; i++) {
            String logOrderId = GetOrderIdUtils.getOrderId2(requestBean.getUserId());
            String orderDate = GetOrderIdUtils.getOrderDate();
            BankCallBean bean = new BankCallBean();
            // 消息类型(批次结束债权)
            bean.setTxCode(BankCallConstant.TXCODE_BATCH_DETAILS_QUERY);
            bean.setBatchTxDate(batchTxDate);
            bean.setBatchNo(batchNo);
            bean.setType(type);
            bean.setPageNum(String.valueOf(i));
            bean.setPageSize(String.valueOf(pageSize));
//            bean.setLogUserId(String.valueOf(bankCreditEnd.getTenderUserId()));
            bean.setLogOrderId(logOrderId);
            bean.setLogOrderDate(orderDate);
            bean.setLogRemark("查询批次交易明细(批次结束债权)");
            bean.setLogClient(0);
            // 调用放款接口
            BankCallBean result = BankCallUtils.callApiBg(bean);
            if (Validator.isNotNull(result)) {
                String retCode = StringUtils.isNotBlank(result.getRetCode()) ? result.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    results.add(result);
                    break;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return results;
    }

    /**
     * 更新数据
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public boolean updateBankCreditEndFromBankQuery(List<BankCallBean> resultBeans) {
        boolean result = false;
        for (BankCallBean bankCallBean : resultBeans) {
            String subPacks = bankCallBean.getSubPacks();
            JSONArray array = JSONObject.parseArray(subPacks);
            if (array != null && array.size() != 0) {
                for (int j = 0; j < array.size(); j++) {
                    JSONObject obj = array.getJSONObject(j);
                    BankCreditEndVO bean = new BankCreditEndVO();
                    bean.setOrderId(obj.getString("orderId"));//订单号
                    bean.setState(Integer.parseInt(obj.getString("txState")));//交易状态
                    bean.setFailmsg(obj.getString("failMsg"));//失败描述
                    //更新对应记录
                    result = amTradeClient.updateBankCreditEnd(bean)>0 ? true : false;
                }
            }
        }
        return result;
    }
}
