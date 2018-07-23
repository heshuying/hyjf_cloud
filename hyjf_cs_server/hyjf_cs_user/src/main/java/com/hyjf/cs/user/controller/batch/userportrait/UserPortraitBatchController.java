/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.userportrait;

import com.hyjf.cs.user.service.batch.UserPortraitBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchController, v0.1 2018/6/28 18:31
 * 用户画像batch
 */
@RestController
@RequestMapping("/cs-user/batch")
@Api(value = "用户画像定时任务",description = "batch-用户画像定时任务")
public class UserPortraitBatchController{

    @Autowired
    private UserPortraitBatchService userPortraitBatchService;
    /**
     * 用户画像定时任务
     * 由hyjf-batch调用
     * */
    @ApiOperation(value = "用户画像定时任务", notes = "用户画像定时任务")
    @RequestMapping(value = "/user_portrait_batch", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public void userPortraitBatch() {
        userPortraitBatchService.userPortraitBatch();
    }
}
