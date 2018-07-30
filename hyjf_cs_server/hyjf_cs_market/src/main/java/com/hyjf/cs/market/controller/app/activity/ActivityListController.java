package com.hyjf.cs.market.controller.app.activity;

import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.ActivityListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 10:01
 * @Description: ActivityListController
 */
@Api(value = "app活动", description = "app活动")
@RestController
@RequestMapping("/hyjf-app/activity")
public class ActivityListController extends BaseMarketController {

    @Resource
    private ActivityListService activityListService;

    @ApiOperation(value = "获取活动专区", notes = "app活动-获取活动专区")
    @PostMapping("/activityList")
    public ActivityListResponse init(HttpServletRequest request, @RequestBody ActivityListRequest activityListRequest){
        ActivityListResponse response = new ActivityListResponse();
// 检查参数正确性
        if (Validator.isNull(activityListRequest.getVersion()) || Validator.isNull(activityListRequest.getNetStatus()) || Validator.isNull(activityListRequest.getPlatform()) || Validator.isNull(activityListRequest.getSign())) {
            response.setStatus("1");
            response.setStatusDesc("请求参数非法");
            return response;
        }

//        // 判断sign是否存在
//        boolean isSignExists = SecretUtil.isExists(sign);
//        if (!isSignExists) {
//            ret.put("status", "1");
//            ret.put("statusDesc", "请求参数非法");
//            return ret;
//        }

        // 取得加密用的Key
        String key = SecretUtil.getKey(activityListRequest.getSign());
        if (Validator.isNull(key)) {
            response.setStatus("1");
            response.setStatusDesc("请求参数非法");
            return response;
        }

        int count= activityListService.queryActivityCount(activityListRequest);
        response.setStatus("10");
        response.setStatusDesc("成功");
        response.setCount(count);

        List<ActivityListBeanVO> activityListBeans = activityListService.queryActivityList(activityListRequest);
        response.setActivityList(activityListBeans);
        return response;
    }
}
