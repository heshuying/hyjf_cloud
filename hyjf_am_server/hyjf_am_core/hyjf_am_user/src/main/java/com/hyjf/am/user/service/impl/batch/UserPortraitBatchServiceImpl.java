/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.batch;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.BatchBorrowTenderCustomizeRequest;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.auto.UserPortraitMapper;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.service.batch.UserPortraitBatchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchServiceImpl, v0.1 2018/6/27 16:59
 */
@Service
public class UserPortraitBatchServiceImpl implements UserPortraitBatchService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static boolean isRun = false;

    @Autowired
    private UserPortraitMapper userPortraitMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserInfo> searchUserInfoList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = GetDate.date_sdf.format(cal.getTime());
        // 获取到昨天的开始和结束时间，格式:yyyy-MM-dd HH:mm:ss
        String yesterdayBegin = yesterday + " 00:00:00";
        String yesterdayEnd = yesterday + " 23:59:59";

        // 从UserInfo中获得所有昨天登录过的userId
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUpdateTimeBetween(GetDate.stringToDate(yesterdayBegin),GetDate.stringToDate(yesterdayEnd));
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        return userInfoList;
    }

    @Override
    public void updateUserPortraitInfo() {
/*        if (!isRun) {
            log.info("定时 用户画像 OntimeUserPortraitTask.run 开始...");
            isRun = true;
            try {
                // 查询登录用户信息
                List<UserPortrait> userPortraitList = ontimeUserPortraitService.selcetUserList();
                for (UserPortrait userPortrait:userPortraitList) {
                    // 更新用户画像表信息
                    log.info("更新用户id ：{}", userPortrait.getUserId());
                    int count = ontimeUserPortraitService.updateImformation(userPortrait);
                    log.info("更新条数 ：{}", count);
                    if (count <= 0) {
                       int count1 = ontimeUserPortraitService.insertImformation(userPortrait);
                        log.info("插入条数 ：{}", count1);
                    }
                }
            } catch (Exception e) {
                log.error("错误：{}",e);
            } finally {
                isRun = false;
            }
            log.info("定时 用户画像 OntimeUserPortraitTask.run 结束...");
        }*/
    }
    private List<UserPortrait> getUserPortraitList(){
        return null;
 /*       Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = GetDate.date_sdf.format(cal.getTime());
        // 获取到昨天的开始和结束时间，格式:yyyy-MM-dd HH:mm:ss
        String yesterdayBegin = yesterday + " 00:00:00";
        String yesterdayEnd = yesterday + " 23:59:59";

        // 从UserInfo中获得所有昨天登录过的userId
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUpdateTimeBetween(GetDate.stringToDate(yesterdayBegin),GetDate.stringToDate(yesterdayEnd));
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        List<User> userList = new ArrayList<>();
        for(UserInfo userInfo:userInfoList){ // 遍历所有userInfo，挨个处理
            // 根据获取到的userInfo中的userId 去获取user
            Integer userId = userInfo.getUserId();
            if(null != userId){
                User user = userMapper.selectByPrimaryKey(userInfo.getUserId()); // 通过userId查询user
                if(user !=null){
                    // 将查询出来的user 放到list中
                    userList.add(user);
                }
            }
        }
        // 赋值处理
        if (!CollectionUtils.isEmpty(userList)) {
            try {
                for (User user : userList) {
                    UserPortrait userPortrait = new UserPortrait();
                    Integer userId = user.getUserId();
                    String userName = user.getUsername();
                    String mobile = user.getMobile();
                    //累计收益
                    BigDecimal interestSum =  borrowCustomizeMapper.getInterestSum(userId);

                    //散标累计年化投资金额
                    BigDecimal investSum = borrowCustomizeMapper.getInvestSum(userId);
                    if (investSum == null) {
                        investSum = new BigDecimal("0.00");
                    }
                    //计划累计年化投资金额
                    BigDecimal planSum = borrowCustomizeMapper.getPlanSum(userId);
                    //累计充值金额
                    BigDecimal rechargeSum = borrowCustomizeMapper.getRechargeSum(userId);
                    if (rechargeSum == null) {
                        rechargeSum = new BigDecimal("0.00");
                    }
                    //累计提现金额
                    BigDecimal withdrawSum = borrowCustomizeMapper.getWithdrawSum(userId);
                    //交易笔数
                    int tradeNumber = borrowCustomizeMapper.getTradeNumber(userId);

                    //投资进程
                    int tenderRecord = borrowCustomizeMapper.countInvest(userId);
                    AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
                    AccountRechargeExample.Criteria criteria1 = accountRechargeExample.createCriteria();
                    criteria1.andUserIdEqualTo(userId).andStatusEqualTo(2);
                    int count1 = accountRechargeMapper.countByExample(accountRechargeExample);
                    BankCardExample bankCardExample = new BankCardExample();
                    BankCardExample.Criteria criteria2 = bankCardExample.createCriteria();
                    criteria2.andUserIdEqualTo(userId);
                    int count2 = bankCardMapper.countByExample(bankCardExample);
                    if (tenderRecord > 0) {
                        userPortrait.setInvestProcess("投资");
                    } else if (count1 > 0) {
                        userPortrait.setInvestProcess("充值");
                    } else if (count2 > 0) {
                        userPortrait.setInvestProcess("开户");
                    } else {
                        userPortrait.setInvestProcess("注册");
                    }

                    UsersInfoExample usersInfoExample = new UsersInfoExample();
                    usersInfoExample.createCriteria().andUserIdEqualTo(userId);
                    List<UsersInfo> usersInfoList = this.usersInfoMapper.selectByExample(usersInfoExample);
                    if (usersInfoList != null && usersInfoList.size() > 0) {
                        UsersInfo usersInfo = usersInfoList.get(0);

                        if (usersInfo != null && usersInfo.getSex() != null) {
                            if (usersInfo.getSex() == 1) {
                                userPortrait.setSex("男");
                            } else {
                                userPortrait.setSex("女");
                            }
                        }

                        if (usersInfo != null && StringUtils.isNotBlank(usersInfo.getIdcard())) {
                            try {
                                String idcard = usersInfo.getIdcard();
                                SimpleDateFormat sdf = GetDate.date_sdf;
                                Boolean isIdCard = IdCardUtil.isValid(idcard);
                                if (isIdCard) {
                                    if (idcard.length() == 15) {
                                        idcard = IdCardUtil.id15To18(idcard);
                                    }
                                    String birthday = idcard.substring(6, 10) + "-" + idcard.substring(10, 12) + "-" + idcard.substring(12, 14);
                                    String age = GetDate.getAge(GetDate.str2Date(birthday, sdf));
                                    userPortrait.setAge(Integer.valueOf(age));
                                } else {
                                    userPortrait.setAge(null);
                                }

                                if (isIdCard) {
                                    Object area = map.get(idcard.substring(0, 4));
                                    if (area != null) {
                                        userPortrait.setCity(String.valueOf(area));
                                    } else {
                                        userPortrait.setCity("");
                                    }
                                } else {
                                    userPortrait.setCity("");
                                }

                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    UsersExample usersExample = new UsersExample();
                    usersExample.createCriteria().andUserIdEqualTo(userId);
                    List<Users> usersList = this.usersMapper.selectByExample(usersExample);
                    if (usersList != null && usersList.size() > 0) {
                        Users users1 = usersList.get(0);
                        if (users1 != null) {
                            int lastLoginTime = users1.getLoginTime();
                            userPortrait.setLastLoginTime(lastLoginTime);
                        }
                    }

                    AccountwithdrawExample accountwithdrawExample = new AccountwithdrawExample();
                    accountwithdrawExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(2);
                    accountwithdrawExample.setOrderByClause("addtime desc");
                    List<Accountwithdraw> accountwithdraws = accountwithdrawMapper.selectByExample(accountwithdrawExample);
                    if (!CollectionUtils.isEmpty(accountwithdraws)) {
                        int addTime = Integer.parseInt(accountwithdraws.get(0).getAddtime());
                        userPortrait.setLastWithdrawTime(addTime);
                    }

                    AccountRechargeExample accountRechargeExample1 = new AccountRechargeExample();
                    accountRechargeExample1.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(2);
                    accountRechargeExample1.setOrderByClause("addtime desc");
                    List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(accountRechargeExample1);
                    if (!CollectionUtils.isEmpty(accountRecharges)) {
                        int createTime = accountRecharges.get(0).getCreateTime();
                        userPortrait.setLastRechargeTime(createTime);
                    }

                    userPortrait.setUserId(userId);
                    userPortrait.setUserName(userName);
                    userPortrait.setMobile(mobile);
                    userPortrait.setInterestSum(interestSum);
                    userPortrait.setInvestSum(investSum.add(planSum));
                    userPortrait.setRechargeSum(rechargeSum);
                    userPortrait.setWithdrawSum(withdrawSum);
                    userPortrait.setTradeNumber(tradeNumber);
                    list.add(userPortrait);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        log.info(JSON.toJSONString(userInfoList));
*/
    }

    private int updateImformation(UserPortrait userPortrait) {
        int count = userPortraitMapper.updateByPrimaryKeySelective(userPortrait);
        return count;
    }


    private int insertImformation(UserPortrait userPortrait) {
        //int count = borrowCustomizeMapper.insertUserPortrait(userPortrait);
        return 0;
    }
}
