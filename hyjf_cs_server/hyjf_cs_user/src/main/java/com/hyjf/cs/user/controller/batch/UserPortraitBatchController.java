/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.trade.BatchUserPortraitQueryResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.user.service.batch.UserPortraitBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchController, v0.1 2018/6/28 18:31
 * 用户画像batch
 */
@RestController
@RequestMapping("/cs-user/batch")
@Api(value = "用户画像定时任务")
public class UserPortraitBatchController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserPortraitBatchService userPortraitBatchService;

    @ApiOperation(value = "用户画像定时任务", notes = "用户画像定时任务")
    @RequestMapping(value = "/userPortraitBatch", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public void userPortraitBatch() {
        userPortraitBatchService.userPortraitBatch();
    }
}
