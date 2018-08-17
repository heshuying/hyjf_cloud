/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.userportrait;

import com.hyjf.cs.user.service.batch.UserPortraitBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/user_portrait_batch", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public void userPortraitBatch() {
        userPortraitBatchService.userPortraitBatch();
    }
}
