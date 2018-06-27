/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.ActivityListClient;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version ActivityListServiceImpl, v0.1 2018/6/26 17:45
 */
@Service
public class ActivityListServiceImpl implements ActivityListService {

    @Autowired
    ActivityListClient activityListClient;
    @Override
    public List<ActivityListVO> getRecordList(ActivityListRequest activityListRequest) {
        List<ActivityListVO> activityListVOList = activityListClient.getRecordList(activityListRequest);
        return activityListVOList;
    }

    @Override
    public int insertRecord(Map<String, String> map) {
        ActivityListRequest request = null;
        if (null != map && map.size() > 0) {
            if (map.containsKey("title") && null != map.get("title")) {
                request.setTitle(map.get("title"));
            }
            if (map.containsKey("imgPc") && null != map.get("imgPc")) {
                request.setImgPc(map.get("imgPc"));
            }
            if (map.containsKey("imgApp") && null != map.get("imgApp")) {
                request.setImgApp(map.get("imgApp"));
            }
            if (map.containsKey("imgWei") && null != map.get("imgWei")) {
                request.setImgWei(map.get("imgWei"));
            }
            if (map.containsKey("qr") && null != map.get("qr")) {
                request.setQr(map.get("qr"));
            }
            if (map.containsKey("platform") && null != map.get("platform")) {
                request.setPlatform(map.get("platform"));
            }
            if (map.containsKey("activityPcUrl") && null != map.get("activityPcUrl")) {
                request.setActivityPcUrl(map.get("activityPcUrl"));
            }
            if (map.containsKey("activityAppUrl") && null != map.get("activityAppUrl")) {
                request.setActivityAppUrl(map.get("activityAppUrl"));
            }
            if (map.containsKey("activityWeiUrl") && null != map.get("activityWeiUrl")) {
                request.setActivityWeiUrl(map.get("activityWeiUrl"));
            }
            if (map.containsKey("urlBackground") && null != map.get("urlBackground")) {
                request.setUrlBackground(map.get("urlBackground"));
            }
            if (map.containsKey("description") && null != map.get("description")) {
                request.setDescription(map.get("description"));
            }
            if (map.containsKey("timeStart") && null != map.get("timeStart")) {
                request.setStartTime(Integer.parseInt(map.get("timeStart")));
            }
            if (map.containsKey("timeEnd") && null != map.get("timeEnd")) {
                request.setEndTime(Integer.parseInt(map.get("timeEnd")));
            }
        }
        int insertFlag = activityListClient.insertRecord(request);
        return insertFlag;
    }

    @Override
    public ActivityListVO selectActivityById(int id) {
        ActivityListVO activityListVO = activityListClient.selectActivityById(id);
        return activityListVO;
    }

    @Override
    public int updateActivity(Map<String, String> map) {
        ActivityListRequest request = null;
        if (null != map && map.size() > 0) {
            if (map.containsKey("id") && null != map.get("id")) {
                request.setId(Integer.parseInt(map.get("id")));
            }
            if (map.containsKey("title") && null != map.get("title")) {
                request.setTitle(map.get("title"));
            }
            if (map.containsKey("imgPc") && null != map.get("imgPc")) {
                request.setImgPc(map.get("imgPc"));
            }
            if (map.containsKey("imgApp") && null != map.get("imgApp")) {
                request.setImgApp(map.get("imgApp"));
            }
            if (map.containsKey("imgWei") && null != map.get("imgWei")) {
                request.setImgWei(map.get("imgWei"));
            }
            if (map.containsKey("qr") && null != map.get("qr")) {
                request.setQr(map.get("qr"));
            }
            if (map.containsKey("platform") && null != map.get("platform")) {
                request.setPlatform(map.get("platform"));
            }
            if (map.containsKey("activityPcUrl") && null != map.get("activityPcUrl")) {
                request.setActivityPcUrl(map.get("activityPcUrl"));
            }
            if (map.containsKey("activityAppUrl") && null != map.get("activityAppUrl")) {
                request.setActivityAppUrl(map.get("activityAppUrl"));
            }
            if (map.containsKey("activityWeiUrl") && null != map.get("activityWeiUrl")) {
                request.setActivityWeiUrl(map.get("activityWeiUrl"));
            }
            if (map.containsKey("urlBackground") && null != map.get("urlBackground")) {
                request.setUrlBackground(map.get("urlBackground"));
            }
            if (map.containsKey("description") && null != map.get("description")) {
                request.setDescription(map.get("description"));
            }
            if (map.containsKey("timeStart") && null != map.get("timeStart")) {
                request.setStartTime(Integer.parseInt(map.get("timeStart")));
            }
            if (map.containsKey("timeEnd") && null != map.get("timeEnd")) {
                request.setEndTime(Integer.parseInt(map.get("timeEnd")));
            }
        }
        int updateFlag = activityListClient.updateActivity(request);
        return updateFlag;
    }
}
