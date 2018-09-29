/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.exception.AccountMobileSynchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountMobileSynchServiceImpl, v0.1 2018/8/15 14:14
 */
@Service(value = "userAccountMobileSynchServiceImpl")
public class AccountMobileSynchServiceImpl extends BaseServiceImpl implements AccountMobileSynchService {

    /**
     * 线下修改信息同步查询列表count
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public int getModifyInfoCount(AccountMobileSynchRequest request) {
        AccountMobileSynchExample example = convertExample(request);
        return accountMobileSynchMapper.countByExample(example);
    }

    /**
     * 线下修改信息同步查询列表list
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountMobileSynch> searchModifyInfoList(AccountMobileSynchRequest request) {
        AccountMobileSynchExample example = convertExample(request);
        return accountMobileSynchMapper.selectByExample(example);
    }

    /**
     * 添加信息
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public int insertAccountMobileSynch(AccountMobileSynchRequest request) {
        String username = request.getAccountMobileSynchVO().getUsername();
        String flag = String.valueOf(request.getAccountMobileSynchVO().getFlag());

        AccountMobileSynchExample accountMobileAynchExample = new AccountMobileSynchExample();
        AccountMobileSynchExample.Criteria criteria = accountMobileAynchExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStatusEqualTo(0);
        if ("1".equals(flag)) {
            // 插入手机号同步信息
            criteria.andFlagEqualTo(1);
            List<AccountMobileSynch> accountMobileAynches = accountMobileSynchMapper.selectByExample(accountMobileAynchExample);
            //只要当前用户有一个未同步的数据，就不允许新增
            if (accountMobileAynches != null && !accountMobileAynches.isEmpty()) {
                return 1;
            }
            // 查询user表
            UserExample userExample = new UserExample();
            UserExample.Criteria userExampleCriteria = userExample.createCriteria();
            userExampleCriteria.andUsernameEqualTo(username);
            List<User> userList = userMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(userList)) {
                return 2;
            }
            User user = userList.get(0);
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user.getUserId());
            if (userInfo == null) {
                return 2;
            }
            BankOpenAccountExample bankOpenAccountExample = new BankOpenAccountExample();
            BankOpenAccountExample.Criteria bankOpenAccountExampleCriteria = bankOpenAccountExample.createCriteria();
            bankOpenAccountExampleCriteria.andUserIdEqualTo(user.getUserId());
            List<BankOpenAccount> bankOpenAccountList = bankOpenAccountMapper.selectByExample(bankOpenAccountExample);
            if (CollectionUtils.isEmpty(bankOpenAccountList)) {
                return 3;
            }
            // 构建数据并插入
            BankOpenAccount bankOpenAccount = bankOpenAccountList.get(0);
            AccountMobileSynch accountMobileSynch = new AccountMobileSynch();
            accountMobileSynch.setUserId(user.getUserId());
            accountMobileSynch.setUsername(user.getUsername());
            accountMobileSynch.setName(userInfo.getTruename());
            accountMobileSynch.setAccountid(bankOpenAccount.getAccount());
            accountMobileSynch.setMobile(user.getMobile());
            accountMobileSynch.setFlag(1);
            accountMobileSynch.setSearchtime(0);
            accountMobileSynch.setCreateTime(GetDate.getNowTime());
            accountMobileSynch.setStatus(0);
            accountMobileSynch.setUpdateTime(GetDate.getNowTime());

            int i = accountMobileSynchMapper.insertSelective(accountMobileSynch);
            if (i > 0) {
                return 0;
            } else {
                return 5;
            }
        }else {
            // 插入银行卡同步信息
            criteria.andFlagEqualTo(2);
            List<AccountMobileSynch> accountMobileAynches = accountMobileSynchMapper.selectByExample(accountMobileAynchExample);
            //只要当前用户有一个未同步的数据，就不允许新增
            if (accountMobileAynches != null && !accountMobileAynches.isEmpty()) {
                return 1;
            }

            // 查询user表
            UserExample userExample = new UserExample();
            UserExample.Criteria userExampleCriteria = userExample.createCriteria();
            userExampleCriteria.andUsernameEqualTo(username);
            List<User> userList = userMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(userList)) {
                return 2;
            }
            User user = userList.get(0);
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user.getUserId());
            if (userInfo == null) {
                return 2;
            }
            BankOpenAccountExample bankOpenAccountExample = new BankOpenAccountExample();
            BankOpenAccountExample.Criteria bankOpenAccountExampleCriteria = bankOpenAccountExample.createCriteria();
            bankOpenAccountExampleCriteria.andUserIdEqualTo(user.getUserId());
            List<BankOpenAccount> bankOpenAccountList = bankOpenAccountMapper.selectByExample(bankOpenAccountExample);
            if (CollectionUtils.isEmpty(bankOpenAccountList)) {
                return 3;
            }

            BankCardExample bankCardExample = new BankCardExample();
            BankCardExample.Criteria bankCardExampleCriteria = bankCardExample.createCriteria();
            bankCardExampleCriteria.andUserIdEqualTo(user.getUserId());
            List<BankCard> bankCardList = bankCardMapper.selectByExample(bankCardExample);
            if(CollectionUtils.isEmpty(bankCardList)){
                return 4;
            }
            BankCard bankCard = bankCardList.get(0);

            // 构建数据并插入
            BankOpenAccount bankOpenAccount = bankOpenAccountList.get(0);
            AccountMobileSynch accountMobileSynch = new AccountMobileSynch();
            accountMobileSynch.setUserId(user.getUserId());
            accountMobileSynch.setUsername(user.getUsername());
            accountMobileSynch.setName(userInfo.getTruename());
            accountMobileSynch.setAccountid(bankOpenAccount.getAccount());
            accountMobileSynch.setAccount(bankCard.getCardNo());
            accountMobileSynch.setMobile(user.getMobile());
            accountMobileSynch.setFlag(2);
            accountMobileSynch.setSearchtime(0);
            accountMobileSynch.setCreateTime(GetDate.getNowTime());
            accountMobileSynch.setStatus(0);
            accountMobileSynch.setUpdateTime(GetDate.getNowTime());

            int i = accountMobileSynchMapper.insertSelective(accountMobileSynch);
            if (i > 0) {
                return 0;
            } else {
                return 5;
            }
        }

    }

    /**
     * 根据主键id删除一条信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int deleteAccountMobileSynch(AccountMobileSynchRequest request) {
        return accountMobileSynchMapper.deleteByPrimaryKey(request.getAccountMobileSynchVO().getId());
    }

    private AccountMobileSynchExample convertExample(AccountMobileSynchRequest request) {
        AccountMobileSynchExample example = new AccountMobileSynchExample();
        AccountMobileSynchExample.Criteria criteria = example.createCriteria();
        AccountMobileSynchVO accountMobileSynchVO = request.getAccountMobileSynchVO();
        String account = accountMobileSynchVO.getAccount();
        String newAccount = accountMobileSynchVO.getNewAccount();
        String username = accountMobileSynchVO.getUsername();
        String mobile = accountMobileSynchVO.getMobile();
        String newMobile = accountMobileSynchVO.getNewMobile();
        Integer status = accountMobileSynchVO.getStatus();
        String accountId = accountMobileSynchVO.getAccountid();
        Integer flag = accountMobileSynchVO.getFlag();
        flag = (flag == null || flag == 0) ? 1 : 2;
        criteria.andFlagEqualTo(flag);
        //电子账号
        if (StringUtils.isNotBlank(accountId)) {
            criteria.andAccountidEqualTo(accountId);
        }
        // 银行卡号
        if (StringUtils.isNotEmpty(account)) {
            criteria.andAccountEqualTo(account);
        }
        //新银行卡号
        if (StringUtils.isNotEmpty(newAccount)) {
            criteria.andNewAccountEqualTo(newAccount);
        }
        // 用户名
        if (StringUtils.isNotEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }
        // 原手机号
        if (StringUtils.isNotEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        //新手机号
        if (StringUtils.isNotEmpty(newMobile)) {
            criteria.andNewMobileEqualTo(newMobile);
        }
        //同步状态
        if (!StringUtils.equals("null", status + "")) {
            criteria.andStatusEqualTo(status);
        }
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        example.setOrderByClause("create_time desc");
        return example;
    }
}
