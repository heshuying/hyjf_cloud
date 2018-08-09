/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.trade.dao.mapper.auto.SubCommissionListConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.SubCommissionMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.producer.AccountWebListProducer;
import com.hyjf.am.trade.service.admin.finance.SubCommissionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.EmployeeCustomizeVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: sunpeikai
 * @version: SubCommissionServiceImpl, v0.1 2018/7/10 10:15
 */
@Service
public class SubCommissionServiceImpl extends BaseServiceImpl implements SubCommissionService {

    @Autowired
    private SubCommissionListConfigMapper subCommissionListConfigMapper;

    @Autowired
    private SubCommissionMapper subCommissionMapper;

    @Autowired
    private AccountWebListProducer accountWebListProducer;

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<SubCommissionListConfig> searchSubCommissionListConfig() {
        SubCommissionListConfigExample example = new SubCommissionListConfigExample();
        SubCommissionListConfigExample.Criteria criteria = example.createCriteria();
        return subCommissionListConfigMapper.selectByExample(example);
    }

    /**
     * 插入发起账户分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSubCommission(SubCommissionVO subCommissionVO) {
        SubCommission subCommission = CommonUtils.convertBean(subCommissionVO,SubCommission.class);
        return subCommissionMapper.insertSelective(subCommission)>0;
    }

    /**
     * 根据订单号查询分佣信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @Override
    public SubCommission searchSubCommissionByOrderId(String orderId) {
        SubCommissionExample example = new SubCommissionExample();
        SubCommissionExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<SubCommission> subCommissionList = subCommissionMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(subCommissionList)){
            return subCommissionList.get(0);
        }
        return null;
    }

    /**
     * 根据筛选条件查询分佣数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getSubCommissionCount(SubCommissionRequest request) {
        SubCommissionExample example = convertExample(request);
        Integer count = subCommissionMapper.countByExample(example);
        return count;
    }

    /**
     * 根据筛选条件查询分佣数据list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<SubCommission> searchSubCommissionList(SubCommissionRequest request) {
        SubCommissionExample example = convertExample(request);
        List<SubCommission> subCommissionList = subCommissionMapper.selectByExample(example);
        return subCommissionList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject subCommission(SubCommissionRequest request) {
        JSONObject jsonObject = new JSONObject();
        if(request.isCallBankSuccess()){
            // 调用银行接口成功
            boolean isUpdate = updateSubCommissionSuccess(request);
            jsonObject.put("isUpdate",isUpdate);
        }else{
            // 调用银行接口失败
            updateSubCommission(request);
        }
        return jsonObject;
    }

    /**
     * 更新失败订单状态
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSubCommission(SubCommissionRequest request) {
        BankCallBeanVO bean = request.getResultBean();
        AdminSystemVO adminSystemVO = request.getAdminSystemVO();

        Date nowTime = GetDate.getNowTime();
        SubCommission subCommission = searchSubCommissionByOrderId(bean.getLogOrderId());
        if (subCommission != null) {
            // 银行返回错误信息
            subCommission.setTradeStatus(2);// 失败
            subCommission.setErrorMsg(request.getErrorMsg());
            subCommission.setUpdateTime(nowTime);
            subCommission.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));
            subCommission.setUpdateUserName(adminSystemVO.getUsername());
            subCommissionMapper.updateByPrimaryKeySelective(subCommission);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateSubCommissionSuccess(SubCommissionRequest request) {

        BankCallBeanVO resultBean = request.getResultBean();
        AdminSystemVO adminSystemVO = request.getAdminSystemVO();

        Integer nowTime = GetDate.getNowTime10();
        Date date = new Date();
        // 转账订单号
        String orderId = resultBean.getLogOrderId();
        // 转入用户ID
        Integer receiveUserId = request.getReceiveUserId();
        // 交易金额
        String txAmount = resultBean.getTxAmount();
        // 交易日期
        String txDate = resultBean.getTxDate();
        // 交易时间
        String txTime = resultBean.getTxTime();
        // 交易流水号
        String seqNo = resultBean.getSeqNo();
        // 更新订单状态
        SubCommission subCommission = searchSubCommissionByOrderId(orderId);
        subCommission.setTradeStatus(1);// 成功
        subCommission.setUpdateTime(date);
        subCommission.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));
        subCommission.setUpdateUserName(adminSystemVO.getUsername());

        if (request.getReceiveAccountId()!=null) {
            subCommission.setReceiveAccountId(request.getReceiveAccountId());
        }

        boolean updateFlag = subCommissionMapper.updateByPrimaryKeySelective(subCommission)>0;
        if (!updateFlag) {
            logger.info("更新分账记录表失败,订单号:[" + resultBean.getLogOrderId() + "].");
            throw new RuntimeException("更新分账记录表失败,订单号:[" + resultBean.getLogOrderId() + "].");
        }

        // 更新转入用户账户信息
        AccountVO receiveUserAccount = new AccountVO();
        receiveUserAccount.setUserId(receiveUserId);
        receiveUserAccount.setBankTotal(new BigDecimal(txAmount));
        receiveUserAccount.setBankBalance(new BigDecimal(txAmount));
        boolean isUpdateFlag = accountMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(receiveUserAccount,Account.class)) > 0;
        if (!isUpdateFlag) {
            logger.info("更新转入用户的账户信息失败,用户ID:[" + receiveUserId + "].订单号:[" + orderId + "].");
            throw new RuntimeException("更新转入用户的账户信息失败,用户ID:[" + receiveUserId + "].订单号:[" + orderId + "].");
        }
        // 插入交易明细
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(receiveUserId);
        List<Account> accountList = accountMapper.selectByExample(example);
        List<AccountVO> accountVOList = CommonUtils.convertBeanList(accountList,AccountVO.class);
        if(null != accountVOList && accountVOList.size() == 1){
            receiveUserAccount = accountVOList.get(0);
        }

        AccountListVO receiveUserList = new AccountListVO();
        receiveUserList.setNid(orderId); // 订单号
        receiveUserList.setUserId(receiveUserId); // 转入人用户ID
        receiveUserList.setAmount(new BigDecimal(txAmount)); // 操作金额
        /** 银行相关 */
        receiveUserList.setAccountId(resultBean.getForAccountId());
        receiveUserList.setBankAwait(receiveUserAccount.getBankAwait());
        receiveUserList.setBankAwaitCapital(receiveUserAccount.getBankAwaitCapital());
        receiveUserList.setBankAwaitInterest(receiveUserAccount.getBankAwaitInterest());
        receiveUserList.setBankBalance(receiveUserAccount.getBankBalance());
        receiveUserList.setBankFrost(receiveUserAccount.getBankFrost());
        receiveUserList.setBankInterestSum(receiveUserAccount.getBankInterestSum());
        receiveUserList.setBankInvestSum(receiveUserAccount.getBankInvestSum());
        receiveUserList.setBankTotal(receiveUserAccount.getBankTotal());
        receiveUserList.setBankWaitCapital(receiveUserAccount.getBankWaitCapital());
        receiveUserList.setBankWaitInterest(receiveUserAccount.getBankWaitInterest());
        receiveUserList.setBankWaitRepay(receiveUserAccount.getBankWaitRepay());
        receiveUserList.setCheckStatus(0);
        receiveUserList.setTradeStatus(1);// 交易状态 0:失败 1:成功
        receiveUserList.setIsBank(1);
        receiveUserList.setTxDate(Integer.parseInt(txDate));
        receiveUserList.setTxTime(Integer.parseInt(txTime));
        receiveUserList.setSeqNo(seqNo);
        receiveUserList.setBankSeqNo(txDate + txTime + seqNo);
        /** 非银行相关 */
        receiveUserList.setType(1); // 1收入
        receiveUserList.setTrade("fee_share_in"); // 手续费分账转入
        receiveUserList.setTradeCode("balance"); // 余额操作
        receiveUserList.setTotal(receiveUserAccount.getTotal()); // 投资人资金总额
        receiveUserList.setBalance(receiveUserAccount.getBalance()); // 投资人可用金额
        receiveUserList.setPlanFrost(receiveUserAccount.getPlanFrost());// 汇添金冻结金额
        receiveUserList.setPlanBalance(receiveUserAccount.getPlanBalance());// 汇添金可用金额
        receiveUserList.setFrost(receiveUserAccount.getFrost()); // 投资人冻结金额
        receiveUserList.setAwait(receiveUserAccount.getAwait()); // 投资人待收金额
        receiveUserList.setCreateTime(nowTime); // 创建时间
        receiveUserList.setBaseUpdate(nowTime); // 更新时间
        receiveUserList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
        receiveUserList.setRemark("账户分佣");
        receiveUserList.setIp(""); // 操作IP
        receiveUserList.setIsUpdate(0);
        receiveUserList.setBaseUpdate(0);
        receiveUserList.setInterest(BigDecimal.ZERO); // 利息
        receiveUserList.setWeb(0); // PC
        boolean receiveUserListFlag = accountListMapper.insertSelective(CommonUtils.convertBean(receiveUserList,AccountList.class)) > 0;
        if (!receiveUserListFlag) {
            logger.info("插入转入用户交易记录失败,用户ID:[" + receiveUserId + "],订单号:[" + orderId + "].");
            throw new RuntimeException("插入转出用户交易记录失败,用户ID:[" + receiveUserId + "],订单号:[" + orderId + "].");
        }
        return true;
    }

    private SubCommissionExample convertExample(SubCommissionRequest request){
        SubCommissionExample example = new SubCommissionExample();
        SubCommissionExample.Criteria criteria = example.createCriteria();
        // 订单号
        if(StringUtils.isNotEmpty(request.getOrderIdSrch())){
            criteria.andOrderIdLike("%"+request.getOrderIdSrch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getReceiveUserNameSrch())){
            criteria.andReceiveUserNameLike("%"+request.getReceiveUserNameSrch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getTradeStatusSrch())){
            criteria.andTradeStatusEqualTo(Integer.parseInt(request.getTradeStatusSrch()));
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getTimeStartSrch())));
        }
        // 添加时间结束
        if (StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayStart(request.getTimeEndSrch())));
        }
        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }
}
