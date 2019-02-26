package com.hyjf.admin.service.mobileclient;

import com.hyjf.admin.beans.request.AppPushManageRequestBean;
import com.hyjf.am.response.AppPushManageResponse;

/**
 * App 推送管理
 * @Author : huanghui
 */
public interface AppPushManageService {

    /**
     * 获取推送列表
     * @param requestBean
     * @return
     */
    AppPushManageResponse getAppPushManageList(AppPushManageRequestBean requestBean);

    /**
     * 单条写入
     * @param requestBean
     * @return
     */
    AppPushManageResponse insertPushManage(AppPushManageRequestBean requestBean);

    /**
     * 更新
     * @param requestBean
     * @return
     */
    boolean updatePushManage(AppPushManageRequestBean requestBean);

    /**
     * 删除操作
     * @param id
     * @return
     */
    boolean deletePushManage(Integer id);

    /**
     * 根据ID获取单条记录内容
     * @param id
     * @return
     */
    AppPushManageResponse getAppPushManageInfoById(Integer id);

    /**
     * 根据ID 更新记录状态
     * @param requestBean
     * @return
     */
    boolean updatePushManageStatusById(AppPushManageRequestBean requestBean);
}
