package com.hyjf.am.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.customize.app.ActivityListCustomize;
import com.hyjf.am.market.service.ActivityService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ActivityListCustomizeResponse;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version ActivityController, v0.1 2018/4/19 15:38
 */

@RestController
@RequestMapping("/am-market/activity")
public class ActivityController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @PostMapping("/selectActivityList")
    public ActivityListResponse selectActivityList(@RequestBody @Valid ActivityListRequest activityListRequest){
        ActivityListResponse response = new ActivityListResponse();
        ActivityList activityList = activityService.selectActivityList(activityListRequest.getId());
        if(activityList != null){
            ActivityListVO activityListVO = new ActivityListVO();
            BeanUtils.copyProperties(activityList,activityListVO);
            response.setResult(activityListVO);
        }
        return response;
    }

    @RequestMapping("/selectActivity/{activityId}")
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

    @PostMapping("/getRecordList")
    public ActivityListResponse getRecordList(@RequestBody @Valid ActivityListRequest request) {
        logger.info("---getRecordList by param---  " + JSONObject.toJSON(request));
        ActivityListResponse response = new ActivityListResponse();
        String returnCode = Response.FAIL;
//        Map<String,Object> mapParam = paramSet(request);
        int recordCount = activityService.countActivityList(request);
        Paginator paginator = new Paginator(request.getPaginatorPage(), recordCount,request.getLimit());
        if(request.getLimit()==0){
            paginator = new Paginator(request.getPaginatorPage(), recordCount);
        }
        List<ActivityList> activityLists = activityService.getRecordList(request,paginator.getOffset(), paginator.getLimit());
        if(recordCount>0){
            if (!CollectionUtils.isEmpty(activityLists)) {
                List<ActivityListVO> activityListVOS = CommonUtils.convertBeanList(activityLists, ActivityListVO.class);
                response.setResultList(activityListVOS);
                response.setCount(recordCount);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);
        return response;
    }



    @PostMapping("/insertRecord")
    public ActivityListResponse insertRecord(@RequestBody @Valid ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        try {
            ActivityList activityList = new ActivityList();
            BeanUtils.copyProperties(request, activityList);
            activityList.setTimeStart(request.getStartTime());
            activityList.setTimeEnd(request.getEndTime());
            Map<String, Object> resultMap = activityService.insertRecord(activityList);
            if ((Boolean) resultMap.get("success")) {
                return response;
            } else {
                response.setRtn("failed");
                response.setMessage((String) resultMap.get("msg"));
                return response;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        response.setMessage("添加失败");
        response.setRtn("1");
        return response;
    }


    @PostMapping("/updateActivity")
    public ActivityListResponse updateActivity(@RequestBody @Valid ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        try {
        ActivityList activityList = new ActivityList();
        BeanUtils.copyProperties(request,activityList);
        activityList.setTimeStart(request.getStartTime());
        activityList.setTimeEnd(request.getEndTime());
        Map<String, Object> resultMap = activityService.updateActivity(activityList);
        if ((Boolean) resultMap.get("success")) {
            return response;
        } else {
            response.setRtn("failed");
            response.setMessage((String) resultMap.get("msg"));
            return response;
        }
    }catch (Exception e) {
        e.printStackTrace();
    }
        response.setMessage("修改失败");
        response.setRtn("1");
        return response;
    }


    @PostMapping("/deleteActivity")
    public ActivityListResponse deleteActivity(@RequestBody @Valid ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        int id = request.getId();
        Map<String, Object> resultMap = activityService.deleteActivity(id);
        if ((Boolean) resultMap.get("success")) {
            return response;
        } else {
            response.setRtn("failed");
            response.setMessage((String) resultMap.get("msg"));
            return response;
        }
    }




    /**
     * 查询条件设置
     *
     * @param activityListRequest
     * @return
     */
    private Map<String, Object> paramSet(ActivityListRequest activityListRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("title", activityListRequest.getTitle());
        mapParam.put("startTime", activityListRequest.getStartTime());
        mapParam.put("endTime", activityListRequest.getEndTime());
        mapParam.put("startCreate",activityListRequest.getStartCreate());
        mapParam.put("endCreate", activityListRequest.getEndCreate());
        return mapParam;
    }

    /**
     * @auth walter.limeng
     * 根据活动ID获取活动title
     * @param activityId 活动ID
     * @return CouponTenderResponse
     */
    @RequestMapping("/hztgetactivitytitle/{activityId}")
    public CouponTenderResponse getActivityTitle(@PathVariable int activityId){
        ActivityList activityList = activityService.selectActivityList(activityId);
        CouponTenderResponse response = new CouponTenderResponse();
        if(null != activityList){
            response.setAttrbute(activityList.getTitle());
        }
        return response;
    }


    /**
     * 获取有效活动列表
     * @param request
     * @return
     */
    @PostMapping("/selectRecordListValid")
    public ActivityListCustomizeResponse selectRecordListValid(@RequestBody ActivityListRequest request) {
        ActivityListCustomizeResponse response = new ActivityListCustomizeResponse();
        List<ActivityListCustomize> recordList = activityService.selectRecordListValid(request,-1,-1);
        if (!CollectionUtils.isEmpty(recordList)) {
            List<ActivityListCustomizeVO> activityListCustomizeVOS = CommonUtils.convertBeanList(recordList,ActivityListCustomizeVO.class);
            response.setResultList(activityListCustomizeVOS);
        }
        return response;
    }
}
