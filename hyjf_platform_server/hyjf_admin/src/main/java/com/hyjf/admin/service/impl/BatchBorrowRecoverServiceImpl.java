package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BatchBorrowRepayBankInfoVO;
import com.hyjf.am.vo.admin.BorrowRecoverBankInfoVo;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.hyjf.admin.controller.BaseController.*;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchBorrowRecoverServiceImpl  extends BaseServiceImpl implements BatchBorrowRecoverService{


    @Autowired
    private AmAdminClient amAdminClient;

    private SystemConfig systemConfig = new SystemConfig();

    /**
     * 批次中心-批次放款导出记录总数
     * @param request
     * @return
     */
    @Override
    public int getBatchBorrowRecoverCount(BatchBorrowRecoverRequest request) {
        return amAdminClient.getBatchBorrowRecoverCount(request);
    }

    /**
     * 获取批次放款的显示列表
     * @param request
     * @return
     */
    @Override
    public JSONObject queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {

        JSONObject jsonObject = new JSONObject();
        BatchBorrowRecoverReponse batchBorrowRepayReponse = amAdminClient.getBatchBorrowRecoverList(request);
        if (null != batchBorrowRepayReponse) {
            List<BatchBorrowRecoverVo> listAccountDetail = batchBorrowRepayReponse.getResultList();
            Integer recordCount = batchBorrowRepayReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                this.queryBatchCenterStatusName(listAccountDetail,request.getNameClass());
            }
            if (null != listAccountDetail) {
                BatchBorrowRecoverVo sumVo = this.queryBatchCenterListSum(request);
                jsonObject.put(STATUS, SUCCESS);
                jsonObject.put(MSG, "成功");
                jsonObject.put(TRCORD, recordCount);
                jsonObject.put(LIST, listAccountDetail);
                jsonObject.put("sumObject",sumVo);
            } else {
                JSONObject info = new JSONObject();
                info.put(MSG, "暂无符合条件数据");
                info.put(STATUS, FAIL);
                return info;
            }
        }else{
            JSONObject info = new JSONObject();
            info.put(MSG, "暂无符合条件数据");
            info.put(STATUS, FAIL);
            return info;
        }
        return jsonObject;
    }


    /**
     * 获取批次内容的显示状态描述
     * @param listAccountDetail
     */
    @Override
    public void queryBatchCenterStatusName(List<BatchBorrowRecoverVo> listAccountDetail,String nameClass) {
        //获取放款相关状态描述
        Map<String, String> paramNameMap = CacheUtil.getParamNameMap(nameClass);
        for (BatchBorrowRecoverVo vo:
             listAccountDetail) {
            vo.setStatusStr(paramNameMap.get(vo.getStatus()));
            if("0".equals(vo.getIncreaseInterestFlag())){//不加息
                vo.setIncreaseInterestFlag("不加息");
                vo.setExtraYieldStatus("");
                vo.setExtraYieldRepayStatus("");
            }else if("1".equals(vo.getIncreaseInterestFlag())){
                vo.setIncreaseInterestFlag("加息");
                if("0".equals(vo.getExtraYieldStatus())){
                    vo.setExtraYieldStatus("待放款");
                }else if("1".equals(vo.getExtraYieldStatus())){
                    vo.setExtraYieldStatus("放款完成");
                }
                if("0".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("待还款");
                }else if("1".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("还款完成");
                }else if("2".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("还款执行中");
                }else if("9".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("还款失败");
                }
            }
        }
    }

    /**
     * 获得批次列表的求和
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverVo queryBatchCenterListSum(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse reponse = amAdminClient.getBatchBorrowCenterListSum(request);
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
    public List<BorrowRecoverBankInfoVo> queryBatchBorrowRecoverBankInfoList(String apicronID) {
        BorrowApicronResponse reponse = amAdminClient.getBorrowApicronByID(apicronID);
        if(reponse != null){
            BorrowApicronVO apicron = reponse.getResult();
            // 借款人用户ID
            Integer borrowUserId = apicron.getUserId();
            // 根据借款人用户ID查询借款人用户电子账户号

            BankOpenAccountVO borrowUserAccount = this.getBankOpenAccount(borrowUserId);

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
            List<BorrowRecoverBankInfoVo> detailList = getRecoverDetailList(bankCallBean);
            return detailList;
        }
        return null;
    }

    /**
     * 获取批次还款的批次交易明细
     * @param apicronID
     * @return
     */
    @Override
    public List<BatchBorrowRepayBankInfoVO> queryBatchBorrowRepayBankInfoList(String apicronID) {
        BorrowApicronResponse reponse = amAdminClient.getBorrowApicronByID(apicronID);
        List<BatchBorrowRepayBankInfoVO> bankInfoVOList = new ArrayList<>();
        if(reponse != null){
            BorrowApicronVO apicron = reponse.getResult();
            int txCounts = apicron.getTxCounts();// 总交易笔数
            String batchTxDate = String.valueOf(apicron.getTxDate());
            int status  = apicron.getStatus();
            int pageSize = 50;// 每页笔数
            int pageTotal = txCounts / pageSize + 1;// 总页数
            List<BankCallBean> results = new ArrayList<>();
            // 获取共同参数
            String bankCode = systemConfig.getBANK_BANKCODE();
            String instCode = systemConfig.getBANK_INSTCODE();
            String channel = BankCallConstant.CHANNEL_PC;
            for (int i = 1; i <= pageTotal; i++) {
                String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
                String orderDate = GetOrderIdUtils.getOrderDate();
                String txDate = GetOrderIdUtils.getTxDate();
                String txTime = GetOrderIdUtils.getTxTime();
                String seqNo = GetOrderIdUtils.getSeqNo(6);
                BankCallBean loanBean = new BankCallBean();
                loanBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                loanBean.setTxCode(BankCallConstant.TXCODE_BATCH_DETAILS_QUERY);// 消息类型(批量放款)
                loanBean.setInstCode(instCode);// 机构代码
                loanBean.setBankCode(bankCode);
                loanBean.setTxDate(txDate);
                loanBean.setTxTime(txTime);
                loanBean.setSeqNo(seqNo);
                loanBean.setChannel(channel);
                loanBean.setBatchTxDate(batchTxDate);
                loanBean.setBatchNo(apicron.getBatchNo());
                if (4 == status) {//校验失败
                    loanBean.setType(BankCallConstant.DEBT_BATCH_TYPE_VERIFYFAIL);
                }else{
                    loanBean.setType(BankCallConstant.DEBT_BATCH_TYPE_ALL);
                }
                loanBean.setPageNum(String.valueOf(i));
                loanBean.setPageSize(String.valueOf(pageSize));
                loanBean.setLogUserId(String.valueOf(apicron.getUserId()));
                loanBean.setLogOrderId(logOrderId);
                loanBean.setLogOrderDate(orderDate);
                loanBean.setLogRemark("查询批次交易明细！");
                loanBean.setLogClient(0);
                // 调用放款接口
                BankCallBean loanResult = BankCallUtils.callApiBg(loanBean);
                if (Validator.isNotNull(loanResult)) {
                    String retCode = StringUtils.isNotBlank(loanResult.getRetCode()) ? loanResult.getRetCode() : "";
                    if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                        results.add(loanResult);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            logger.info("查询银行result.size():【{}】", results.size());
            if(results.size() > 0){
                bankInfoVOList = getRepayDetailList(results);
            }
        }
        return bankInfoVOList;
    }

    /**
     * 获取资金来源
     * @return
     */
    @Override
    public List<HjhInstConfigVO> findHjhInstConfigList() {

        List<HjhInstConfigVO> hjhInstConfigList = amAdminClient.selectHjhInstConfigList();
        if(hjhInstConfigList != null && hjhInstConfigList.size() > 0){
            return  hjhInstConfigList;
        }
        return null;
    }

    @Override
    public JSONObject initPage(String nameClass) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",SUCCESS);
        //批次状态
        Map<String, String> paramNameMap = CacheUtil.getParamNameMap(nameClass);
        List<DropDownVO> paramMapList = ConvertUtils.convertParamMapToDropDown(paramNameMap);
        if(paramNameMap != null && paramNameMap.size() > 0){
            jsonObject.put("批次状态列表","recoverStatusList");
            jsonObject.put("recoverStatusList",paramMapList);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取转让状态列表失败！");
        }
        //资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.findHjhInstConfigList();
        List<DropDownVO> dropDownVOS = ConvertUtils.convertListToDropDown(hjhInstConfigList, "instCode", "instName");
        if(hjhInstConfigList != null && hjhInstConfigList.size() > 0){
            jsonObject.put("资金来源列表","hjhInstConfigList");
            jsonObject.put("hjhInstConfigList",dropDownVOS);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取资金来源列表失败！");
        }
        return jsonObject;
    }

    /**
     * 组装批次还款交易明细查询列表
     * @param resultBeans
     * @return
     */
    private List<BatchBorrowRepayBankInfoVO> getRepayDetailList(List<BankCallBean> resultBeans) {

        List<BatchBorrowRepayBankInfoVO> detailList = new ArrayList<>();
        for (int i = 0; i < resultBeans.size(); i++) {
            BankCallBean resultBean = resultBeans.get(i);
            String subPacks = resultBean.getSubPacks();
            if (StringUtils.isNotBlank(subPacks)) {
                JSONArray loanDetails = JSONObject.parseArray(subPacks);
                for (int j = 0; j < loanDetails.size(); j++) {
                    JSONObject loanDetail = loanDetails.getJSONObject(j);
                    BatchBorrowRepayBankInfoVO info = new BatchBorrowRepayBankInfoVO();
                    //交易状态S-成功 F-失败 N-交易不存在 Z-未知 D-待处理
                    if(loanDetail.getString(BankCallConstant.PARAM_TXSTATE).equals("S")) {
                    	info.setTxMsg("成功");
                    }else if(loanDetail.getString(BankCallConstant.PARAM_TXSTATE).equals("F")) {
                    	info.setTxMsg("失败");
                    }else if(loanDetail.getString(BankCallConstant.PARAM_TXSTATE).equals("N")) {
                    	info.setTxMsg("交易不存在");
                    }else if(loanDetail.getString(BankCallConstant.PARAM_TXSTATE).equals("Z")) {
                    	info.setTxMsg("未知");
                    }else if(loanDetail.getString(BankCallConstant.PARAM_TXSTATE).equals("D")) {
                    	info.setTxMsg("待处理");
                    }
                    info.setAuthCode(loanDetail.getString(BankCallConstant.PARAM_AUTHCODE));// 授权码
                    info.setTxState(loanDetail.getString(BankCallConstant.PARAM_TXSTATE));// 交易状态
                    info.setOrderId(loanDetail.getString(BankCallConstant.PARAM_ORDERID));// 订单号
                    info.setTxAmount(loanDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT));// 操作金额
                    info.setForAccountId(loanDetail.getString(BankCallConstant.PARAM_FORACCOUNTID));// 借款人银行账户
                    info.setProductId(loanDetail.getString(BankCallConstant.PARAM_PRODUCTID));// 标的号
                    info.setFileMsg(loanDetail.getString(BankCallConstant.PARAM_FAILMSG));//错误提示
                    detailList.add(info);
                }
            }
        }
        return detailList;
    }

    /**
     * 获得批次放款页面需要的展示列表
     * @param resultBeans
     * @return
     */
    private List<BorrowRecoverBankInfoVo> getRecoverDetailList(BankCallBean resultBeans) {
        List<BorrowRecoverBankInfoVo> detailList = new ArrayList<>();

        BorrowRecoverBankInfoVo detailLists = new BorrowRecoverBankInfoVo();
        if (Validator.isNotNull(resultBeans)) {
            // 借款人电子账户号
            detailLists.setForAccountId(resultBeans.getAccountId());
            // 借款人姓名
            detailLists.setName(resultBeans.getName());
            // 响应代码
            detailLists.setRetCode(resultBeans.getRetCode());
            // 错误描述
            detailLists.setFileMsg(resultBeans.getRetMsg());
            // 标的编号
            detailLists.setProductId(resultBeans.getProductId());
            // 借款人入账金额
            detailLists.setTxAmount(resultBeans.getTxAmount());
            // 手续费金额
            detailLists.setFeeAmount(resultBeans.getFeeAmount());
            // 风险准备金
            detailLists.setRiskAmount(resultBeans.getRiskAmount());
            // 交易状态
            detailLists.setTxState(BankCallConstant.RESPCODE_SUCCESS.equals(resultBeans.getRetCode()) ? "成功" : "失败");
        }
        detailList.add(detailLists);
        return detailList;
    }
}
