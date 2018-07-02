/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version ActivityListService, v0.1 2018/6/26 17:44
 */
public interface ActivityListService {
    /**
     * 查询活动列表
     * @param activityListRequest
     * @return
     */
    List<ActivityListVO> getRecordList(ActivityListRequest activityListRequest);

    /**
     * 添加活动
     * @param map
     * @return
     */
    int insertRecord(Map<String, String> map);

    /**
     * 根据活动id查询活动信息
     * @param id
     * @return
     */
    ActivityListVO selectActivityById(int id);

    /**
     * 修改活动信息
     * @param map
     * @return
     */
    int updateActivity(Map<String, String> map);

    /**
     * 资料上传
     * @param request
     * @param response
     * @return
     */
    String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 删除活动配置信息
     * @param id
     * @return
     */
    int deleteActivity(int id);
}
