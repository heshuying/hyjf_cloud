/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserLoginLogResponse;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserLoginLog;
import com.hyjf.am.user.service.front.user.UserPortraitBatchService;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserLoginLogVO;
import com.hyjf.common.util.CommonUtils;
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
public class UserPortraitBatchController extends BaseController {

    @Autowired
    private UserPortraitBatchService userPortraitBatchService;

    /**
     * 查询需要更新用户画像的userInfo的list
     * */
    @RequestMapping("/search_user_id_for_user_portrait")
    public UserLoginLogResponse searchUserIdForUserPortrait(){
        logger.info("员工画像.....searchUserInfoList");
        UserLoginLogResponse response = new UserLoginLogResponse();
        List<UserLoginLog> userLoginLogList = userPortraitBatchService.searchUserIdForUserPortrait();
        if(!CollectionUtils.isEmpty(userLoginLogList)){
            List<UserLoginLogVO> list = CommonUtils.convertBeanList(userLoginLogList,UserLoginLogVO.class);
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
