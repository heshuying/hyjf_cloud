/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.user.dao.mapper.auto.UserPortraitMapper;
import com.hyjf.am.user.dao.mapper.customize.UserPortraitManagerMapper;
import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.service.front.user.UserPortraitManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->用户画像 接口实现
 */
@Service
public class UserPortraitManagerServiceImpl implements UserPortraitManagerService {

    @Autowired
    private UserPortraitManagerMapper userPortraitManagerMapper;
    @Autowired
    private UserPortraitMapper userPortraitMapper;

    private static Logger logger = LoggerFactory.getLogger(UserPortraitManagerServiceImpl.class);


    /**
     * 根据参数查询用户画像信息
     * @param userPortrait
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<UserPortrait> selectRecordList(Map<String, Object> userPortrait, int limitStart, int limitEnd) {

        if (limitStart == 0 || limitStart > 0) {
            userPortrait.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            userPortrait.put("limitEnd", limitEnd);
        }

        List<UserPortrait> usersPortraits = userPortraitManagerMapper.selectUserPortraitList(userPortrait);
        return usersPortraits;
    }

    /**
     * 根据条件获取记录数
     * @param userPortrait
     * @return
     */
    @Override
    public int countLoanSubjectCertificateAuthority (Map<String, Object> userPortrait){
        int countTotal = userPortraitManagerMapper.countRecordTotal(userPortrait);
        return countTotal;
    }

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @return
     */
    @Override
    public UserPortrait selectUsersPortraitByUserId(Integer userId){
        UserPortrait userPortrait = userPortraitMapper.selectByPrimaryKey(userId);
        return userPortrait;
    }

    /**
     * 修改用户画像
     */
    @Override
    public int updateUserPortrait(UserPortraitRequest request){
        UserPortrait usersPortrait = new UserPortrait();
        BeanUtils.copyProperties(request,usersPortrait);
        int intInsertFlg = userPortraitMapper.updateByPrimaryKeySelective(usersPortrait);
        if(intInsertFlg> 0){
            logger.info("==================用户画像变更保存成功!======");
        }else{
            throw new RuntimeException("============用户画像变更失败!========");
        }
        return intInsertFlg;
    }
}
