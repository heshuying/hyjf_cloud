package com.hyjf.cs.user.service;

/**
 * @author xiasq
 * @version ActivityService, v0.1 2018/4/12 11:36
 */
public interface ActivityService {
    /**
     * 检查活动是否有效
     * @param activityId
     * @return
     */
    boolean checkActivityIfAvailable(String activityId);
}
