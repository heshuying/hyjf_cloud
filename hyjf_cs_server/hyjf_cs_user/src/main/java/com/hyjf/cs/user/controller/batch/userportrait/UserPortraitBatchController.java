/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.userportrait;

import com.hyjf.cs.user.service.batch.UserPortraitBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchController, v0.1 2018/6/28 18:31
 * 用户画像batch
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-user/batch")
public class UserPortraitBatchController{

    @Autowired
    private UserPortraitBatchService userPortraitBatchService;
    /**
     * 用户画像定时任务
     * 由hyjf-batch调用
     * */
    @GetMapping(value = "/userPortraitBatch", produces = "application/json; charset=utf-8")
    public void userPortraitBatch() {
        userPortraitBatchService.userPortraitBatch(1);
    }

    /**
     * 更新所有时间的用户画像
     * @auth sunpeikai
     * @param
     * @return
     */
    @GetMapping(value = "/update_all_user_portrait")
    public void updateAllUserPortrait(){
        userPortraitBatchService.userPortraitBatch(99);
    }
}
