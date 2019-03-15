/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.front.user.UserPortraitBatchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.user.utils.IdCard15To18;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.am.vo.user.UserAndSpreadsUserVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public List<UserAndSpreadsUserVO> searchUserIdForUserPortrait(int flag) {

        Calendar cal = Calendar.getInstance();
        String startDay = "";
        if(flag == 99){
            //更新三个月内用户画像，数据缺失或数据有误等极其特殊的情况下才能调用，因为数据量特别大
            cal.add(Calendar.MONTH, -3);
            startDay = GetDate.date_sdf.format(cal.getTime());
        }else{
            //更新昨日的用户画像，正常情况下调用
            cal.add(Calendar.DATE, -1);
            startDay = GetDate.date_sdf.format(cal.getTime());
        }
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        String endDay = GetDate.date_sdf.format(cal.getTime());
        // 获取到昨天的开始和结束时间，格式:yyyy-MM-dd HH:mm:ss
        String yesterdayBegin = startDay + " 00:00:00";
        String yesterdayEnd = endDay + " 23:59:59";

        // 从UserInfo中获得所有昨天登录过的userId
//        UserLoginLogExample example = new UserLoginLogExample();
//        UserLoginLogExample.Criteria criteria = example.createCriteria();
//        criteria.andLoginTimeBetween(GetDate.stringToDate(yesterdayBegin), GetDate.stringToDate(yesterdayEnd));
        Map<String, Object> map = new HashMap<>();
        map.put("yesterdayBegin", GetDate.stringToDate(yesterdayBegin));
        map.put("yesterdayEnd", GetDate.stringToDate(yesterdayEnd));
        List<UserLoginLog> userLoginLogList = userInfoCustomizeMapper.selectLoginUserByTime(map);


        List<UserAndSpreadsUserVO> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userLoginLogList)){
            for(UserLoginLog userLoginLog:userLoginLogList){
                UserAndSpreadsUserVO userAndSpreadsUserVO = new UserAndSpreadsUserVO();
                Integer userId = userLoginLog.getUserId();
                userAndSpreadsUserVO.setUserId(userId);
                List<Integer> spreadsUserId = selectSpreadsUserId(userId);
                userAndSpreadsUserVO.setSpreadsUserId(spreadsUserId);
                result.add(userAndSpreadsUserVO);
            }
        }
        return result;
    }

    /**
     * 根据userId查询他的邀约客户id
     * @auth sunpeikai
     * @param
     * @return
     */
    private List<Integer> selectSpreadsUserId(Integer userId){
        List<Integer> result = new ArrayList<>();
        SpreadsUserExample example = new SpreadsUserExample();
        example.createCriteria().andSpreadsUserIdEqualTo(userId);
        List<SpreadsUser> spreadsUserList = spreadsUserMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(spreadsUserList)){
            for(SpreadsUser spreadsUser:spreadsUserList){
                result.add(spreadsUser.getUserId());
            }
        }
        return result;
    }

    /**
     * 保存用户画像
     * */
    @Override
    public void saveUserPortrait(BatchUserPortraitQueryRequest request) {
        List<BatchUserPortraitQueryVO> userPortraitQueryVOList = request.getBatchUserPortraitQueryVOList();
        for (BatchUserPortraitQueryVO userPortraitQueryVO : userPortraitQueryVOList) {
            Integer userId = userPortraitQueryVO.getUserId();

            // bean转换
            UserPortrait userPortrait = CommonUtils.convertBean(userPortraitQueryVO,UserPortrait.class);
            // 如果出借进程在trade上未赋值，说明不是出借或者充值
            if (userPortrait.getInvestProcess() == null) {
                UserExample userExample = new UserExample();
                userExample.createCriteria().andUserIdEqualTo(userId);
                List<User> users = userMapper.selectByExample(userExample);
                User user = null;
                if (!CollectionUtils.isEmpty(users)) {
                    user = users.get(0);
                }
                if (user.getBankOpenAccount() == 1) {
                    userPortrait.setInvestProcess("开户");
                } else {
                    userPortrait.setInvestProcess("注册");
                }
            }

            // 从userInfo赋值 性别，年龄，身份证号码，城市，最后登录时间
            UserInfoExample userInfoExample = new UserInfoExample();
            userInfoExample.createCriteria().andUserIdEqualTo(userId);
            List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoExample);
            if(userInfoList != null && userInfoList.size() > 0){
                UserInfo userInfo = userInfoList.get(0);
                if(userInfo != null){
                    // 赋值性别
                    if (userInfo.getSex() != null) {
                        if (userInfo.getSex() == 1) {
                            userPortrait.setSex("男");
                        } else if (userInfo.getSex() == 2){
                            userPortrait.setSex("女");
                        } else {
                            userPortrait.setSex("未知");
                        }
                    }
                    //有无主单
                    if(null != userInfo.getAttribute()){
                        userPortrait.setAttribute(userInfo.getAttribute());
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
                                String area = IdCard15To18.getCityFromCode(idcard.substring(0, 6));
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
                    UserLoginLogExample userLoginLogExample = new UserLoginLogExample();
                    userLoginLogExample.createCriteria().andUserIdEqualTo(userId);
                    List<UserLoginLog> userLoginLogList = userLoginLogMapper.selectByExample(userLoginLogExample);
                    if(!CollectionUtils.isEmpty(userLoginLogList)){
                        UserLoginLog userLoginLog = userLoginLogList.get(0);
                        String time = GetDate.date2Str(userLoginLog.getLoginTime(),GetDate.datetimeFormat);
                        Integer lastLoginTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(time);
                        userPortrait.setLastLoginTime(lastLoginTime);
                    }


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
            log.info("保存用户画像:【{}】", JSON.toJSONString(userPortrait));
            count = updateInformation(userPortrait);
            if (count <= 0) {
                count = insertInformation(userPortrait);
            }
        }


    }

    /**
     * 更新用户画像
     * */
    private int updateInformation(UserPortrait userPortrait) {
        UserPortraitExample select = new UserPortraitExample();
        select.createCriteria().andUserIdEqualTo(userPortrait.getUserId());
        List<UserPortrait> userPortraits = userPortraitMapper.selectByExample(select);
        if(CollectionUtils.isEmpty(userPortraits)){
           // 如果为空，说明没有查询出已存在数据
           return 0;
        }
        userPortrait.setId(userPortraits.get(0).getId());
        return userPortraitMapper.updateByPrimaryKeySelective(userPortrait);
    }
    /**
     * 插入用户画像
     * */
    private int insertInformation(UserPortrait userPortrait) {
        return userPortraitMapper.insertSelective(userPortrait);
    }
}
