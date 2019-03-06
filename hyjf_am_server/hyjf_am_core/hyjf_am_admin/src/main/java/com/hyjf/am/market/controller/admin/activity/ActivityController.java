package com.hyjf.am.market.controller.admin.activity;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.customize.app.ActivityListCustomize;
import com.hyjf.am.market.service.ActivityService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.ActivityListCustomizeResponse;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/getActivityList")
    public ActivityListResponse getActivityList(){
        ActivityListResponse response = new ActivityListResponse();
        List<ActivityList> list=activityService.getActivityList();
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,ActivityListVO.class));
        }
        return response;
    }



    @PostMapping("/selectActivityList")
    public ActivityListResponse selectActivityList(@RequestBody ActivityListRequest activityListRequest){
        ActivityListResponse response = new ActivityListResponse();
        ActivityList activityList = activityService.selectActivityList(activityListRequest.getId());
        if(activityList != null){
            ActivityListVO activityListVO = new ActivityListVO();
            BeanUtils.copyProperties(activityList,activityListVO);
            response.setResult(activityListVO);
        }else {
            response.setRtn(Response.FAIL);
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
    public ActivityListResponse getRecordList(@RequestBody ActivityListRequest request) {
        logger.info("---getRecordList by param---  " + JSONObject.toJSON(request));
        ActivityListResponse response = new ActivityListResponse();
        int recordCount = activityService.countActivityList(request);
        Paginator paginator = new Paginator(request.getCurrPage(), recordCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), recordCount);
        }
        if(recordCount>0){
            List<ActivityList> activityLists = activityService.getRecordList(request,paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(activityLists)) {
                List<ActivityListVO> activityListVOS = CommonUtils.convertBeanList(activityLists, ActivityListVO.class);
                response.setResultList(activityListVOS);
                response.setCount(recordCount);
                response.setRtn(Response.SUCCESS);
            }else {
                response.setRtn(Response.FAIL);
            }
        }
        return response;
    }



    @PostMapping("/insertRecord")
    public ActivityListResponse insertRecord(@RequestBody ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        try {
            ActivityList activityList = new ActivityList();
            BeanUtils.copyProperties(request, activityList);
            activityList.setTimeStart(GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartTime()));
            activityList.setTimeEnd(GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndTime()));
            int result = activityService.insertRecord(activityList);
            if (result > 0){
                response.setRtn(Response.SUCCESS);
            }else {
                response.setRtn(Response.FAIL);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }


    @PostMapping("/updateActivity")
    public ActivityListResponse updateActivity(@RequestBody ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        try {
        ActivityList activityList = new ActivityList();
        BeanUtils.copyProperties(request,activityList);
        activityList.setTimeStart(GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartTime()));
        activityList.setTimeEnd(GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndTime()));
        int result = activityService.updateActivity(activityList);
            if (result > 0){
                response.setRtn(Response.SUCCESS);
            }else {
                response.setRtn(Response.FAIL);
            }
        }catch (Exception e) {
            response.setRtn(Response.FAIL);
            logger.error(e.getMessage());
        }
        return response;
    }


    @PostMapping("/deleteActivity")
    public ActivityListResponse deleteActivity(@RequestBody ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        int id = request.getId();
        int result = activityService.deleteActivity(id);
        if (result > 0) {
            response.setRtn(Response.SUCCESS);
        }else {
            response.setRtn(Response.FAIL);
        }
        return response;
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
    public ActivityListCustomizeResponse selectRecordListValid(@RequestBody ActivityListCustomizeVO request) {
        ActivityListCustomizeResponse response = new ActivityListCustomizeResponse();
        List<ActivityListCustomize> recordList = activityService.selectRecordListValid(new ActivityListCustomize(),-1,-1);
        if (!CollectionUtils.isEmpty(recordList)) {
            List<ActivityListCustomizeVO> activityListCustomizeVOS = CommonUtils.convertBeanList(recordList,ActivityListCustomizeVO.class);
            response.setResultList(activityListCustomizeVOS);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  APP根据条件查询活动列表总数
     * @Date 11:53 2018/7/26
     * @Param activityListRequest
     * @return ActivityListResponse
     */
    @RequestMapping("/queryactivitycount")
    public ActivityListResponse queryActivityCount(@RequestBody ActivityListRequest activityListRequest){
        ActivityListResponse response = new ActivityListResponse();
        Integer count = activityService.queryactivitycount(activityListRequest);
        response.setCount(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  APP根据条件分页查询数据
     * @Date 11:54 2018/7/26
     * @Param activityListRequest
     * @return
     */
    @RequestMapping("/queryactivitylist")
    public ActivityListResponse queryActivityList(@RequestBody ActivityListRequest activityListRequest) {
        ActivityListResponse response = new ActivityListResponse();
        List<ActivityListBeanVO> list = activityService.queryActivityList(activityListRequest);
        response.setActivityList(list);
        return response;
    }

    @GetMapping("/getActivity/{day}")
    public ActivityListResponse getActivity(@PathVariable int day){
        ActivityListResponse activityListResponse = new ActivityListResponse();
        List<ActivityList> activity = activityService.getActivity(day);
        List<ActivityListVO> activityListVOS = CommonUtils.convertBeanList(activity, ActivityListVO.class);
        activityListResponse.setResultList(activityListVOS);
        return activityListResponse;
    }

    @GetMapping("/getInfoById/{id}")
    public ActivityListResponse getInfoById(@PathVariable Integer id) {
        ActivityListResponse response = new ActivityListResponse();
        ActivityList activityList = activityService.getActivityInfo(id);
        ActivityListVO activityListVO = new ActivityListVO();
        if (activityList != null) {
            BeanUtils.copyProperties(activityList, activityListVO);
            activityListVO.setAcStartTime(GetDate.getDateTimeMyTime(activityList.getTimeStart()));
            activityListVO.setAcEndTime(GetDate.getDateTimeMyTime(activityList.getTimeEnd()));
            response.setResult(activityListVO);
        }
        return response;
    }
    @GetMapping("/checkActivityIfAvailable/{id}")
    public StringResponse checkActivityIfAvailable(@PathVariable Integer id){
        StringResponse response = new StringResponse();
        ActivityList activityList = activityService.getActivityInfo(id);
        if (activityList == null) {
            response.setResultStr("104");
            return response;
        }
        if (activityList.getTimeStart() > GetDate.getNowTime10()) {
            response.setResultStr("101");
            return response;
        }
        if (activityList.getTimeEnd() < GetDate.getNowTime10()) {
            response.setResultStr("102");
            return response;
        }
        response.setResultStr("000");
        return response;
    }
}
