/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.front.user.UserPortraitBatchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.user.utils.IdCard15To18;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchServiceImpl, v0.1 2018/6/27 16:59
 */
@Service
public class UserPortraitBatchServiceImpl extends BaseServiceImpl implements UserPortraitBatchService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 查询需要更新用户画像的userInfo的list
     * */
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
        criteria.andUpdateTimeBetween(GetDate.stringToDate(yesterdayBegin), GetDate.stringToDate(yesterdayEnd));
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        return userInfoList;
    }
    /**
     * 保存用户画像
     * */
    @Override
    public void saveUserPortrait(BatchUserPortraitQueryRequest request) {
        List<BatchUserPortraitQueryVO> userPortraitQueryVOList = request.getBatchUserPortraitQueryVOList();
        for (BatchUserPortraitQueryVO userPortraitQueryVO : userPortraitQueryVOList) {
            Integer userId = userPortraitQueryVO.getUserId();
            // 根据userId获取到userInfo，然后给userPortrait赋值

            UserPortrait userPortrait = new UserPortrait();
            // bean转换
            convertBean(userPortraitQueryVO, userPortrait);
            // 如果投资进程在trade上未赋值，说明不是投资或者充值
            if (userPortrait.getInvestProcess() == null) {
                BankCardExample bankCardExample = new BankCardExample();
                BankCardExample.Criteria criteria2 = bankCardExample.createCriteria();
                criteria2.andUserIdEqualTo(userId);
                int count = bankCardMapper.countByExample(bankCardExample);
                if (count > 0) {
                    userPortrait.setInvestProcess("开户");
                } else {
                    userPortrait.setInvestProcess("注册");
                }
            }

            // 从userInfo赋值 性别，年龄，身份证号码，城市，最后登录时间
            UserInfoExample userInfoExample = new UserInfoExample();
            userInfoExample.setOrderByClause("update_time desc");
            UserInfoExample.Criteria cra = userInfoExample.createCriteria();
            cra.andUserIdEqualTo(userId);
            List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoExample);
            UserInfo userInfo = null;
            if(userInfoList != null && userInfoList.size() > 0){
                userInfo = userInfoList.get(0);
                if(userInfo != null){
                    // 赋值性别
                    if (userInfo.getSex() != null) {
                        if (userInfo.getSex() == 1) {
                            userPortrait.setSex("男");
                        } else {
                            userPortrait.setSex("女");
                        }
                    }
                    // 赋值身份证号码和城市
                    if (StringUtils.isNotBlank(userInfo.getIdcard())) {
                        try {
                            String idcard = userInfo.getIdcard();
                            SimpleDateFormat sdf = GetDate.date_sdf;

                            Boolean isIdCard = IdCard15To18.isValid(idcard);
                            if (isIdCard) {
                                if (idcard.length() == 15) {
                                    idcard = IdCard15To18.getEighteenIDCard(idcard);
                                }
                                String birthday = idcard.substring(6, 10) + "-" + idcard.substring(10, 12) + "-" + idcard.substring(12, 14);
                                String age = GetDate.getAge(GetDate.str2Date(birthday, sdf));
                                userPortrait.setAge(Integer.valueOf(age));
                            } else {
                                userPortrait.setAge(null);
                            }

                            if (isIdCard) {
                                String area = IdCard15To18.getCityFromCode(idcard.substring(0, 4));
                                if (area != null) {
                                    userPortrait.setCity(area);
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
                    // 最后登录时间
                    String time = GetDate.date2Str(userInfo.getUpdateTime(),GetDate.datetimeFormat);
                    Integer lastLoginTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(time);
                    userPortrait.setLastLoginTime(lastLoginTime);

                }
            }

            // 从user中获取用户名，手机号
            User user = this.findUserByUserId(userId);
            if(user != null){
                // username
                if(user.getUsername() != null){
                    userPortrait.setUserName(user.getUsername());
                }
                if(user.getMobile() != null){
                    userPortrait.setMobile(user.getMobile());
                }
            }

            // 更新用户画像表信息
            int count = 0;
            log.info("userId:{}，保存用户画像", userPortrait.getUserId());
            count = updateInformation(userPortrait);
            if (count <= 0) {
                count = insertInformation(userPortrait);
            }
        }


    }
    /**
     * 将BatchUserPortraitQueryVO值  赋值给  UserPortrait
     * */
    private void convertBean(BatchUserPortraitQueryVO userPortraitQueryVO, UserPortrait userPortrait) {
        // 用户id
        userPortrait.setUserId(userPortraitQueryVO.getUserId());
        // 累计收益
        userPortrait.setInterestSum(userPortraitQueryVO.getInterestSum());
        // 散标累计年化投资金额
        userPortrait.setInvestSum(userPortraitQueryVO.getInvestSum());
        // 累计充值金额
        userPortrait.setRechargeSum(userPortraitQueryVO.getRechargeSum());
        // 累计提现金额
        userPortrait.setWithdrawSum(userPortraitQueryVO.getWithdrawSum());
        // 交易笔数
        userPortrait.setTradeNumber(userPortraitQueryVO.getTradeNumber());
        // 投资进程
        if (userPortraitQueryVO.getInvestProcess() != null && !"".equals(userPortraitQueryVO.getInvestProcess())) {
            userPortrait.setInvestProcess(userPortraitQueryVO.getInvestProcess());
        }
        // 最后提现时间
        if (userPortraitQueryVO.getLastWithdrawTime() != null) {
            userPortrait.setLastWithdrawTime(userPortraitQueryVO.getLastWithdrawTime());
        }
        // 最后充值时间
        if (userPortraitQueryVO.getLastRechargeTime() != null) {
            userPortrait.setLastRechargeTime(userPortraitQueryVO.getLastRechargeTime());
        }
        // 投龄
        if(userPortraitQueryVO.getInvestAge() != null){
            userPortrait.setInvestAge(userPortraitQueryVO.getInvestAge());
        }else{
            userPortrait.setInvestAge(0);
        }
        // 同时投资平台数
        if(userPortraitQueryVO.getInvestPlatform() != null){
            userPortrait.setInvestPlatform(userPortraitQueryVO.getInvestPlatform());
        }else{
            userPortrait.setInvestPlatform(0);
        }
    }
    /**
     * 更新用户画像
     * */
    private int updateInformation(UserPortrait userPortrait) {
        UserPortraitExample example = new UserPortraitExample();
        example.createCriteria().andUserIdEqualTo(userPortrait.getUserId());
        int count = userPortraitMapper.updateByExampleSelective(userPortrait,example);
        return count;
    }
    /**
     * 插入用户画像
     * */
    private int insertInformation(UserPortrait userPortrait) {
        int count = userPortraitMapper.insertSelective(userPortrait);
        return count;
    }
}
