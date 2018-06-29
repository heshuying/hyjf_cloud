/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.batch;

import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.service.batch.UserPortraitBatchService;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchController, v0.1 2018/6/27 16:55
 * 用户画像
 */
@RestController
@RequestMapping("/user_batch/portrait")
public class UserPortraitBatchController {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveBatchController.class);

    @Autowired
    private UserPortraitBatchService userPortraitBatchService;

    /**
     * 查询需要更新用户画像的userInfo的list
     * */
    @RequestMapping("/search_user_info_list")
    public UserInfoResponse searchUserInfoList(){
        logger.info("员工画像.....searchUserInfoList");
        UserInfoResponse response = new UserInfoResponse();
        List<UserInfo> userInfoList = userPortraitBatchService.searchUserInfoList();
        if(!CollectionUtils.isEmpty(userInfoList)){
            List<UserInfoVO> list = CommonUtils.convertBeanList(userInfoList,UserInfoVO.class);
            response.setResultList(list);
        }
        return response;
    }
    /**
     * 保存用户画像
     * */
    @PostMapping("/save_user_portrait")
    public void saveUserPortrait(@RequestBody @Valid BatchUserPortraitQueryRequest request){
        logger.info("员工画像.....saveUserPortrait::::::::::request======={}",request);
        userPortraitBatchService.saveUserPortrait(request);
    }
}
