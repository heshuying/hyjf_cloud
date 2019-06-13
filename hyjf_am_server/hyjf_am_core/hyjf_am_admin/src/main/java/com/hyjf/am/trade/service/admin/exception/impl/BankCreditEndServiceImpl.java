/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.admin.StartCreditEndRequest;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.exception.BankCreditEndService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.ref.ReferenceQueue;
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

        Borrow borrow = this.getBorrow(borrowNid);
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
        record.setCreditEndType(2); // 结束债权类型（1:还款，2:散标债转，3:计划债转，5：后台发起）
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
     * 债权结束异常列表
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public List<BankCreditEndVO> getCreditEndList(BankCreditEndListRequest requestBean){
        if(requestBean == null){
            return new ArrayList<>();
        }
        return bankCreditEndCustomizeMapper.selectCreditEndList(requestBean);
    }

    /**
     * 债权结束异常总记录数
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public Integer getCreditEndCount(BankCreditEndListRequest requestBean){
        if(requestBean == null){
            return 0;
        }
        return bankCreditEndCustomizeMapper.selectCreditEndCount(requestBean);
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
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andStatusEqualTo(0); // 初始
        example.setLimitStart(0);
        example.setLimitEnd(500);// 记录数限制
        example.setOrderByClause("id");
        bankCreditEnd.setUpdateTime(GetDate.getDate());
        return this.bankCreditEndMapper.updateByExampleSelective(bankCreditEnd, example);
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
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andBatchNoEqualTo(batchNo);//批次号
        example.createCriteria().andTxDateEqualTo(txDate);//日期

        BankCreditEnd bankCreditEnd = new BankCreditEnd();
        bankCreditEnd.setTxCounts(txCounts);// 添加结束债权交易总笔数 create by wgx 2018/11/16
        bankCreditEnd.setStatus(status);//批次状态
        bankCreditEnd.setUpdateTime(GetDate.getDate());
        return this.bankCreditEndMapper.updateByExampleSelective(bankCreditEnd, example);
    }

    /**
     * 批次结束债权校验（异步回调）
     * 更新批次债权结束，校验
     * @param bean
     * @return
     */
    @Override
    public int updateBatchCreditEndCheck(BankCallBeanVO bean) {

        BankCreditEndExample example = new BankCreditEndExample();
        BankCreditEndExample.Criteria cra = example.createCriteria();

        cra.andBatchNoEqualTo(bean.getBatchNo());
        cra.andTxDateEqualTo(bean.getTxDate());
        cra.andTxTimeEqualTo(bean.getTxTime());
        cra.andSeqNoEqualTo(bean.getSeqNo());
//        cra.andStatusEqualTo(0); // 确定先收到合法性

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
        if(ends != null && ends.size() > 0) {
            BankCreditEnd oneCredit = ends.get(0);
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

        return this.bankCreditEndMapper.updateByExampleSelective(newEnd, example);
    }

    /**
     * 保存结束债权记录
     * @param requestBean
     * @return
     */
    @Override
    public int insertStartCreditEnd(StartCreditEndRequest requestBean) {
        logger.info("【结束债权】保存到db，requestBean：" + JSON.toJSONString(requestBean));

        Borrow borrow = this.getBorrow(requestBean.getBorrowNid());
        if (borrow == null) {
            throw new RuntimeException("结束债券接口：标的"+requestBean.getBorrowNid()+"不存在");
        }
        Account account = this.getAccount(requestBean.getUserId());
        if (account == null) {
            throw new RuntimeException("结束债券接口：借款人"+requestBean.getUserId()+"银行未开户");
        }
        Account accountTender = this.getAccount(requestBean.getTenderUserId());
        if (accountTender == null) {
            throw new RuntimeException("结束债券接口：出借人"+requestBean.getTenderUserId()+"银行未开户");
        }
        String orderId = GetOrderIdUtils.getOrderId2(requestBean.getTenderUserId());

        BankCreditEnd record = new BankCreditEnd();
        record.setUserId(requestBean.getUserId());
        record.setTenderUserId(requestBean.getTenderUserId());
        record.setAccountId(account.getAccountId());
        record.setTenderAccountId(accountTender.getAccountId());
        record.setOrderId(orderId);
        record.setBorrowNid(requestBean.getBorrowNid());
        record.setAuthCode(requestBean.getTenderAuthCode());
        record.setCreditEndType(requestBean.getCreditEndType()); // 结束债权类型（1:还款，2:散标债转，3:计划债转，5：后台发起）
        record.setStatus(0);
        record.setState("A"); // 初始化为A：待处理状态
        record.setOrgOrderId(requestBean.getOrgOrderId());

        Date nowDate = GetDate.getDate();
        record.setCreateUser(1);
        record.setCreateTime(nowDate);
        record.setUpdateUser(1);
        record.setUpdateTime(nowDate);

        return this.bankCreditEndMapper.insertSelective(record);
    }

    /**
     * 结束债权查询必要信息
     * @param requestBean
     * @return
     */
    @Override
    public String queryForCreditEnd(StartCreditEndRequest requestBean){
        BankCreditEnd creditEnd = this.getCreditEndByOrgOrderId(requestBean.getOrgOrderId());
        if(creditEnd!=null){
            logger.error("结束债权已提交过，requestBean:" + JSON.toJSONString(requestBean));
            return "该债权已提交过";
        }
        // 出借明细
        if(requestBean.getStartFrom() == 1){
            BorrowRecover recover = getBorrowRecoverByNid(requestBean.getOrgOrderId());
                if(recover == null){
                logger.error("出借记录不存在，nid:" + requestBean.getOrgOrderId());
                throw new RuntimeException("出借记录不存在，nid:" + requestBean.getOrgOrderId());
            }

            requestBean.setTenderUserId(recover.getUserId());
            requestBean.setUserId(recover.getBorrowUserid());
            requestBean.setBorrowNid(recover.getBorrowNid());
            requestBean.setTenderAuthCode(recover.getAuthCode());
        }

        // 承接明细
        if(requestBean.getStartFrom() == 2){
            CreditTender creditTender = getCreditTender(requestBean.getOrgOrderId());
            if(creditTender == null){
                logger.error("债转记录不存在，assignNid:" + requestBean.getOrgOrderId());
                throw new RuntimeException("债转记录不存在，assignNid:" + requestBean.getOrgOrderId());
            }

            requestBean.setTenderUserId(creditTender.getUserId());
            requestBean.setUserId(creditTender.getBorrowUserId());
            requestBean.setBorrowNid(creditTender.getBidNid());
            requestBean.setTenderAuthCode(creditTender.getAuthCode());
        }

        // 智投承接明细
        if(requestBean.getStartFrom() == 3){
            HjhDebtCreditTender creditTender =getHjhDebtCreditTender(requestBean.getOrgOrderId());
            if(creditTender == null){
                logger.error("债转记录不存在，assignNid:" + requestBean.getOrgOrderId());
                throw new RuntimeException("债转记录不存在，assignNid:" + requestBean.getOrgOrderId());
            }
            Borrow borrow = getBorrow(requestBean.getBorrowNid());

            requestBean.setTenderUserId(creditTender.getUserId());
            requestBean.setUserId(borrow.getUserId());
            requestBean.setBorrowNid(creditTender.getBorrowNid());
            requestBean.setTenderAuthCode(creditTender.getAuthCode());
        }

        return null;
    }

    private BankCreditEnd getCreditEndByOrgOrderId(String orderId){
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrgOrderIdEqualTo(orderId);
        List<BankCreditEnd> creditEnds = bankCreditEndMapper.selectByExample(example);
        if(creditEnds!=null && !creditEnds.isEmpty()){
            return creditEnds.get(0);
        }
        return null;
    }
}
