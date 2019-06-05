/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.account.BankCreditEndService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version BankCreditEndServiceImpl, v0.1 2018/7/6 18:37
 */
@Service
public class BankCreditEndServiceImpl extends BaseServiceImpl implements BankCreditEndService {


    @Override
    public int insertBankCreditEndForCreditEnd(HjhDebtCredit hjhDebtCredit, String tenderAccountId, String tenderAuthCode) {

        String borrowNid = hjhDebtCredit.getBorrowNid();
        Integer tenderUserId = hjhDebtCredit.getUserId();

        Borrow borrow = this.getBorrowByNid(borrowNid);
        if (borrow == null) {
            throw new RuntimeException("结束债券接口：标的"+borrowNid+"不存在");
        }
        Account account = this.getAccount(borrow.getUserId());
        if (account == null) {
            throw new RuntimeException("结束债券接口：借款人"+borrow.getUserId()+"银行未开户");
        }

        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        logger.info(borrowNid+" 直投还款结束债权  借款人: "+borrow.getUserId()+"-"+account.getAccountId()+" 出借人: "+tenderUserId+"-"+tenderAccountId+" 授权码: "+tenderAuthCode+" 原始订单号: "+hjhDebtCredit.getSellOrderId());

        BankCreditEnd record = new BankCreditEnd();
        record.setUserId(borrow.getUserId());
        record.setTenderUserId(tenderUserId);
        record.setAccountId(account.getAccountId());
        record.setTenderAccountId(tenderAccountId);
        record.setOrderId(orderId);
        record.setBorrowNid(borrowNid);
        record.setAuthCode(tenderAuthCode);
        record.setCreditEndType(3); // 结束债权类型（1:还款，2:散标债转，3:计划债转，5：后台发起）
        record.setStatus(0);
        record.setState("A"); // 初始化为A：待处理状态
        record.setOrgOrderId(hjhDebtCredit.getSellOrderId());

        Date nowDate = GetDate.getDate();
        record.setCreateUser(tenderUserId);
        record.setCreateTime(nowDate);
        record.setUpdateUser(tenderUserId);
        record.setUpdateTime(nowDate);

        return this.bankCreditEndMapper.insertSelective(record);
    }

    /**
     * 更新
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateBankCreditEnd(BankCreditEnd bankCreditEnd) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrderIdEqualTo(bankCreditEnd.getOrderId());
        bankCreditEnd.setUpdateTime(GetDate.getNowTime());
        return this.bankCreditEndMapper.updateByExampleSelective(bankCreditEnd, example);
    }

    /**
     * 通过orderId检索
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public BankCreditEnd selectByOrderId(String orderId) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrderIdEqualTo(orderId);//订单号
        List<BankCreditEnd> list = this.bankCreditEndMapper.selectByExample(example);
        if (list != null && list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    /**
     * 批次恢复为初始状态
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateCreditEndForInitial(BankCreditEnd bankCreditEnd) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrderIdEqualTo(bankCreditEnd.getOrderId());//订单号
        example.createCriteria().andStatusGreaterThanOrEqualTo(10);//订单状态为失败
        bankCreditEnd.setStatus(0);//批次状态
        bankCreditEnd.setOrderId(GetOrderIdUtils.getOrderId2(Integer.valueOf(bankCreditEnd.getTenderUserId())));//批次状态
        bankCreditEnd.setUpdateTime(GetDate.getNowTime());
        return this.bankCreditEndMapper.updateByExampleSelective(bankCreditEnd, example);
    }


    /**
     * 批次结束债权用更新 结束债权任务表
     * @param bankCreditEnd
     * @return
     */
    @Override
    public int updateBankCreditEndForBatch(BankCreditEnd bankCreditEnd) {
        int result = 0;
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andStatusEqualTo(0); // 初始
        example.setLimitStart(0);
        example.setLimitEnd(500);// 记录数限制
        example.setOrderByClause("id");
        // update by wgx 2019/01/31 结束债权按照主键逐条更新
        List<BankCreditEnd> bankCreditEndList = this.bankCreditEndMapper.selectByExample(example);
        if(bankCreditEndList == null || bankCreditEndList.size() == 0){
            return result;
        }
        for(BankCreditEnd creditEnd : bankCreditEndList){
            bankCreditEnd.setId(creditEnd.getId());
            this.bankCreditEndMapper.updateByPrimaryKeySelective(bankCreditEnd);
            result++;
        }
        return result;
    }

    /**
     * 据批次号和日期，取得结束债权任务列表
     * @param batchNo
     * @param txDate
     * @return
     */
    @Override
    public List<BankCreditEnd> getBankCreditEndListByBatchnoTxdate(String batchNo, String txDate) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andBatchNoEqualTo(batchNo);//批次号
        example.createCriteria().andTxDateEqualTo(txDate);//日期
        return this.bankCreditEndMapper.selectByExample(example);
    }

    @Override
    public int updateBankCreditEndForStatus(String batchNo, String txDate, Integer txCounts, int status) {
        int result = 0;
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andBatchNoEqualTo(batchNo);//批次号
        example.createCriteria().andTxDateEqualTo(txDate);//日期
        // update by wgx 2019/01/31 结束债权按照主键逐条更新
        List<BankCreditEnd> bankCreditEndList = bankCreditEndMapper.selectByExample(example);
        if(bankCreditEndList == null || bankCreditEndList.size() == 0){
            return result;
        }
        BankCreditEnd bankCreditEnd = new BankCreditEnd();
        bankCreditEnd.setTxCounts(txCounts);// 添加结束债权交易总笔数 create by wgx 2018/11/16
        bankCreditEnd.setStatus(status);//批次状态
        for(BankCreditEnd creditEnd: bankCreditEndList){
            bankCreditEnd.setId(creditEnd.getId());
            this.bankCreditEndMapper.updateByPrimaryKeySelective(bankCreditEnd);
            result++;
        }
        return result;
    }

    /**
     * 批次结束债权校验（异步回调）
     * 更新批次债权结束，校验
     * @param bean
     * @return
     */
    @Override
    public int updateBatchCreditEndCheck(BankCallBeanVO bean) {
        logger.info("updateBatchCreditEndCheck, bean: " + JSON.toJSONString(bean));

        BankCreditEndExample example = new BankCreditEndExample();
        BankCreditEndExample.Criteria cra = example.createCriteria();

        cra.andBatchNoEqualTo(bean.getBatchNo());
        cra.andTxDateEqualTo(bean.getTxDate());
        cra.andTxTimeEqualTo(bean.getTxTime());
        cra.andSeqNoEqualTo(bean.getSeqNo());

        BankCreditEndExample exampleLimit = example;
        exampleLimit.setLimitStart(0);
        exampleLimit.setLimitEnd(1);
        exampleLimit.setOrderByClause("id desc");
        List<BankCreditEnd> ends = this.bankCreditEndMapper.selectByExample(exampleLimit);
        logger.info(bean.getBatchNo()+"  "+ends.size());
        if(ends != null && ends.size() > 0) {
            BankCreditEnd oneCredit = ends.get(0);
            if(oneCredit.getStatus() == 4) {
                logger.info(bean.getBatchNo()+" 合法性已经成功 ");
                return 0;
            }
        }else {
            return 0;
        }

        BankCreditEnd newEnd = new BankCreditEnd();
        newEnd.setCheckRetcode(bean.getRetCode());
        newEnd.setCheckRetmsg(bean.getRetMsg());

        if (BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            newEnd.setStatus(4); // 校验成功
        }else {
            newEnd.setStatus(10); // 校验失败
        }
        newEnd.setUpdateUser(1);
        newEnd.setUpdateTime(GetDate.getDate());

        return this.bankCreditEndMapper.updateByExampleSelective(newEnd, example);
    }

    @Override
    public int updateBatchCreditEndFinish(BankCallBeanVO bean) {

        BankCreditEndExample example = new BankCreditEndExample();
        BankCreditEndExample.Criteria cra = example.createCriteria();

        cra.andBatchNoEqualTo(bean.getBatchNo());
        cra.andTxDateEqualTo(bean.getTxDate());
        cra.andTxTimeEqualTo(bean.getTxTime());
        cra.andSeqNoEqualTo(bean.getSeqNo());
//        cra.andStatusEqualTo(2);// 确定先收到

        BankCreditEndExample exampleLimit = example;
        exampleLimit.setLimitStart(0);
        exampleLimit.setLimitEnd(1);
        exampleLimit.setOrderByClause("id desc");
        List<BankCreditEnd> ends = this.bankCreditEndMapper.selectByExample(exampleLimit);
        logger.info(bean.getBatchNo()+"  "+ends.size());
        Integer txCounts = 1;
        if(ends != null && ends.size() > 0) {
            BankCreditEnd oneCredit = ends.get(0);
            txCounts = oneCredit.getTxCounts();
            if(oneCredit.getStatus() == 11 || oneCredit.getStatus() == 5) {
                logger.info(bean.getBatchNo()+" 业务处理已经成功 ");
                return 0;
            }
        }else {
            return 0;
        }

        BankCreditEnd newEnd = new BankCreditEnd();
        newEnd.setRetcode(bean.getRetCode());
        newEnd.setRetmsg(bean.getRetMsg());
        newEnd.setSucCounts(Integer.valueOf(bean.getSucCounts()));
        int failCnt = 0;
        if(bean.getFailCounts() != null) {
            failCnt = Integer.valueOf(bean.getFailCounts());
        }
        newEnd.setFailCounts(failCnt);

        if (BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            if(failCnt > 0) {
                newEnd.setStatus(11); // 业务部分成功
            }else {
                newEnd.setStatus(5); // 业务全部成功
            }
        }else {
            newEnd.setStatus(12); // 业务全部失败
        }
        newEnd.setUpdateUser(1);
        newEnd.setUpdateTime(GetDate.getDate());

        int result = this.bankCreditEndMapper.updateByExampleSelective(newEnd, example);

        // 如果批次部分成功，请求批次明细接口更新失败原因及状态
        if(result > 0 && failCnt >0){
            logger.info("结束债权批次号batchNO:{},部分成功，开始请求批次交易明细接口", bean.getBatchNo());
            List<BankCallBean> resultBeans = queryBatchDetails(bean.getBatchNo(), txCounts, bean.getTxDate(), bean.getBatchNo());
            logger.info("结束债权批次号batchNO:{},部分成功，请求批次交易明细接口resultBeans:{}", bean.getBatchNo(), JSON.toJSONString(resultBeans));
            if(resultBeans != null){
                for (BankCallBean bankCallBean : resultBeans) {
                    String subPacks = bankCallBean.getSubPacks();
                    JSONArray array = JSONObject.parseArray(subPacks);
                    if (array != null && array.size() != 0) {
                        for (int j = 0; j < array.size(); j++) {
                            JSONObject obj = array.getJSONObject(j);
                            String txState = obj.getString("txState");
                            String failMsg = obj.getString("failMsg");
                            logger.info("结束债权批次号batchNO:{}，txState:{} failMsg:" + failMsg, bean.getBatchNo(), txState);
                            // 更新对应债权状态及失败描述
                            example = new BankCreditEndExample();
                            example.createCriteria().andBatchNoEqualTo(bean.getBatchNo()).andOrderIdEqualTo(obj.getString("orderId"));

                            newEnd = new BankCreditEnd();
                            newEnd.setState(txState);
                            newEnd.setFailmsg(failMsg);
                            result = bankCreditEndMapper.updateByExampleSelective(newEnd, example);
                        }
                    }
                }
            }else{
                logger.error("调用批次交易明细接口返回结果为null");
            }
        }

        return result;
    }

    /**
     * 查询批次交易明细
     * @param batchNo
     * @param txCounts
     * @param batchTxDate
     * @param userId
     * @return
     */
    public List<BankCallBean> queryBatchDetails(String batchNo, Integer txCounts, String batchTxDate, String userId) {

        int pageSize = 50;// 每页笔数
        int pageTotal = txCounts / pageSize + 1;// 总页数
        String type = BankCallConstant.DEBT_BATCH_TYPE_FAIL; //只查失败类型
        List<BankCallBean> results = new ArrayList<>();
        for (int i = 1; i <= pageTotal; i++) {
            String logOrderId = GetOrderIdUtils.getOrderId2(Integer.parseInt(userId));
            String orderDate = GetOrderIdUtils.getOrderDate();
            BankCallBean bean = new BankCallBean();
            // 消息类型(批次结束债权)
            bean.setTxCode(BankCallConstant.TXCODE_BATCH_DETAILS_QUERY);
            bean.setBatchTxDate(batchTxDate);
            bean.setBatchNo(batchNo);
            bean.setType(type);
            bean.setPageNum(String.valueOf(i));
            bean.setPageSize(String.valueOf(pageSize));
            bean.setLogOrderId(logOrderId);
            bean.setLogOrderDate(orderDate);
            bean.setLogRemark("查询批次交易明细(批次结束债权)");
            bean.setLogClient(0);
            // 调用放款接口
            BankCallBean result = BankCallUtils.callApiBg(bean);
            logger.info("调用银行接口返回：" + JSON.toJSONString(result));
            if (Validator.isNotNull(result)) {
                String retCode = StringUtils.isNotBlank(result.getRetCode()) ? result.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    results.add(result);
                    break;
                } else {
                    logger.error("批次交易明细接口查询失败retCode：" + retCode);
                    continue;
                }
            } else {
                logger.error("批次交易明细接口查询失败, 返回null");
                continue;
            }
        }
        return results;
    }
}
