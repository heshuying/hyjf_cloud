package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.service.ActivityService;
import com.hyjf.am.response.user.ActivityListResponse;
import com.hyjf.am.vo.user.ActivityListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version ActivityController, v0.1 2018/4/19 15:38
 */

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/selectActivityList/{activityId}")
    public ActivityListResponse selectActivityList(@PathVariable int activityId){
        ActivityList activityList = activityService.selectActivityList(activityId);
        ActivityListResponse response = new ActivityListResponse();
        if(null != activityList){
            ActivityListVO activityListVO = new ActivityListVO();
            BeanUtils.copyProperties(activityList,activityListVO);
            response.setResult(activityListVO);
        }
        return response;
    }
}
