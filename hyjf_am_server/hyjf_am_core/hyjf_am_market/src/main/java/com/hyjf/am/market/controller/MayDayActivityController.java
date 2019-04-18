/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.auto.ActivityUserGuess;
import com.hyjf.am.market.service.ActivityUserGuessService;
import com.hyjf.am.response.admin.ActivityUserGuessResponse;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import com.hyjf.am.vo.admin.ActivityUserGuessVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoyong
 * @version MayDayActivityController, v0.1 2019/4/18 15:53
 */
@RestController
@RequestMapping("/am-market/mayDay")
public class MayDayActivityController {

    @Autowired
    private ActivityUserGuessService activityUserGuessService;

    @RequestMapping("/guessUserList")
    public ActivityUserGuessResponse getGuessList(@RequestBody ActivityUserGuessRequest request) {
        ActivityUserGuessResponse response = new ActivityUserGuessResponse();
        int recordCount = activityUserGuessService.getGuessListCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), recordCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), recordCount);
        }
        if (recordCount > 0) {
            List<ActivityUserGuessVO> userGuessVOList = activityUserGuessService.getGuessList(request, paginator.getOffset(), paginator.getLimit());
            response.setResultList(userGuessVOList);
            response.setCount(recordCount);
            return response;
        }
        return response;
    }
}
