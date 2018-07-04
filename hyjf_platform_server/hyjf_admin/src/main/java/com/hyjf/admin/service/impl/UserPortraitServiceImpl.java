/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.LoanCoverClient;
import com.hyjf.admin.client.UserPortraitClient;
import com.hyjf.admin.service.LoanCoverService;
import com.hyjf.admin.service.UserPortraitService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version LoanCoverServiceImpl, v0.1 2018/6/26 17:11
 */
@Service
public class UserPortraitServiceImpl implements UserPortraitService {
    @Autowired
    private UserPortraitClient userPortraitClient;
    /**
     * 根据参数查询用户画像信息
     * @return
     */
    @Override
    public UserPortraitResponse selectRecordList(UserPortraitRequest userPortraitRequest){
        UserPortraitResponse response =  userPortraitClient.selectRecordList(userPortraitRequest);
        return response;
    }

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @return
     */
    @Override
    public UserPortraitVO selectUsersPortraitByUserId(Integer userId){
        UserPortraitVO userPortraitVO = userPortraitClient.selectUsersPortraitByUserId(userId);
        return userPortraitVO;
    }

    /**
     * 修改用户画像
     */
    @Override
    public int updateUserPortrait(Map<String,Object>mapParam){
        UserPortraitRequest userPortraitRequest =  setParamRequest(mapParam);
        int updFlg = userPortraitClient.updateUserPortrait(userPortraitRequest);
        return updFlg;
    }

    public UserPortraitRequest setParamRequest(Map<String, Object> mapParam) {
        UserPortraitRequest request = new UserPortraitRequest();
        if (null != mapParam && mapParam.size() > 0) {
            if (mapParam.containsKey("userId")) {
                request.setUserId(Integer.parseInt(mapParam.get("userId").toString()));
            }
            if (mapParam.containsKey("userName")) {
                request.setUserName(mapParam.get("userName").toString());
            }
            if (mapParam.containsKey("education")) {
                request.setEducation(mapParam.get("education").toString());
            }
            if (mapParam.containsKey("occupation")) {
                request.setOccupation(mapParam.get("occupation").toString());
            }
            if (mapParam.containsKey("interest")) {
                request.setInterest(mapParam.get("interest").toString());
            }
            if (mapParam.containsKey("loginActive")) {
                request.setLoginActive(mapParam.get("loginActive").toString());
            }
            if (mapParam.containsKey("customerSource")) {
                request.setCustomerSource(mapParam.get("customerSource").toString());
            }
            if (mapParam.containsKey("investPlatform")) {
                request.setInvestPlatform(Integer.parseInt(mapParam.get("investPlatform").toString()));
            }
            if (mapParam.containsKey("investAge")) {
                request.setInvestAge(Integer.parseInt(mapParam.get("investAge").toString()));
            }
            if (mapParam.containsKey("currentOwner")) {
                request.setCurrentOwner(mapParam.get("currentOwner").toString());
            }
            if (mapParam.containsKey("addWechat")) {
                request.setAddWechat(mapParam.get("addWechat").toString());
            }
            if (mapParam.containsKey("customerComplaint")) {
                request.setCustomerComplaint(mapParam.get("customerComplaint").toString());
            }
            if (mapParam.containsKey("inviteCustomer")) {
                request.setInviteCustomer(Integer.parseInt(mapParam.get("inviteCustomer").toString()));
            }
            if (mapParam.containsKey("remark")) {
                request.setRemark(mapParam.get("remark").toString());
            }
            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                request.setPageSize((Integer) mapParam.get("limit"));
            }
            //查询用
            if (mapParam.containsKey("yesterdayBeginTime") && StringUtils.isNotBlank(mapParam.get("yesterdayBeginTime").toString())) {
                request.setYesterdayBeginTime(mapParam.get("yesterdayBeginTime").toString());
            }
            if (mapParam.containsKey("yesterdayEnd") && StringUtils.isNotBlank(mapParam.get("yesterdayEnd").toString())) {
                request.setYesterdayEndTime(mapParam.get("yesterdayBeginTime").toString());
            }
            //导出用
            if (mapParam.containsKey("limitFlg") && StringUtils.isNotBlank(mapParam.get("limitFlg").toString())) {
                request.setLimitFlg((Integer) mapParam.get("limitFlg"));
            }
        }
        return request;
    }

}
