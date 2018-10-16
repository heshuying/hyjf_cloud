/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.user.UserAndSpreadsUserResponse;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.UserPortraitBatchService;
import com.hyjf.am.vo.user.UserAndSpreadsUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/search_user_id_for_user_portrait/{flag}")
    public UserAndSpreadsUserResponse searchUserIdForUserPortrait(@PathVariable int flag){
        logger.info("员工画像.....searchUserInfoList");
        UserAndSpreadsUserResponse response = new UserAndSpreadsUserResponse();
        List<UserAndSpreadsUserVO> userAndSpreadsUserVOList = userPortraitBatchService.searchUserIdForUserPortrait(flag);
        if(!CollectionUtils.isEmpty(userAndSpreadsUserVOList)){
            response.setResultList(userAndSpreadsUserVOList);
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
