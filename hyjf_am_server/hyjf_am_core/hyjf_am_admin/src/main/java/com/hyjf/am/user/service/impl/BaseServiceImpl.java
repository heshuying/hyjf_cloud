/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.customize.CustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

/**
 * 用户服务:BaseService实现类
 *
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2018/6/27 9:46
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {

    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public User findUserByUserId(int userId) {
        UserExample usersExample = new UserExample();
        usersExample.createCriteria().andUserIdEqualTo(userId);
        List<User> usersList = userMapper.selectByExample(usersExample);
        if (!CollectionUtils.isEmpty(usersList)) {
            return usersList.get(0);
        }
        return null;
    }


    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo findUsersInfo(int userId) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取所有用户
     * @return
     * @throws ParseException
     */
    @Override
    public List<User> findAllUser() throws ParseException {
        UserExample usersExample = new UserExample();
        UserExample.Criteria cra = usersExample.createCriteria();
        cra.andBankOpenAccountEqualTo(1);
        cra.andIsCaFlagEqualTo(0);
        //法大大上线时间2018-03-14 00:00:00
        cra.andRegTimeGreaterThanOrEqualTo(DateFormat.getDateTimeInstance().parse("2018/3/14"));
        return userMapper.selectByExample(usersExample);
    }

    /**
     * 根据用户ID查询企业用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId) {
        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        // 江西银行
        cra.andIsBankEqualTo(1);
        List<CorpOpenAccountRecord> list = this.corpOpenAccountRecordMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据用户Id查询用户CA认证相关信息
     *
     * @param userId
     * @return
     */
    @Override
    public CertificateAuthority selectCAInfoByUserId(Integer userId) {
        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CertificateAuthority> list = this.certificateAuthorityMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 获取userId查找推荐人
     * @param userId
     * @return
     */
    @Override
    public SpreadsUser selectSpreadsUsersByUserId(int userId){
        SpreadsUserExample example = new SpreadsUserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SpreadsUser> spreadUsersList = spreadsUserMapper.selectByExample(example);
        if (spreadUsersList != null && spreadUsersList.size() > 0) {
            return spreadUsersList.get(0);
        }
        return null;
    }

    @Override
    public BankOpenAccount selectByExample(BankOpenAccountExample example) {
        List<BankOpenAccount> bankOpenAccountList = bankOpenAccountMapper.selectByExample(example);
        if (bankOpenAccountList != null && bankOpenAccountList.size() == 1) {
            return bankOpenAccountList.get(0);
        }
        return null;
    }

    /**
     * 根据电子账号获取用户
     * @auther: hesy
     * @date: 2018/7/14
     */
    @Override
    public User getUserByAccountId(String accountId){
        BankOpenAccountExample example = new BankOpenAccountExample();
        example.createCriteria().andAccountEqualTo(accountId);
        List<BankOpenAccount> accountList = bankOpenAccountMapper.selectByExample(example);

        if(accountList != null && !accountList.isEmpty()){
            BankOpenAccount account = accountList.get(0);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(account.getUserId());
            List<User> userList = userMapper.selectByExample(userExample);
            if(userList != null && !userList.isEmpty()){
                return userList.get(0);
            }
        }

        return null;
    }
}
