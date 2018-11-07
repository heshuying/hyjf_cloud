package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.beans.request.AppPushManageRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.mobileclient.AppPushManageService;
import com.hyjf.am.response.AppPushManageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * App 推送管理
 * @Author : huanghui
 */
@Service
public class AppPushManageServiceImpl  implements AppPushManageService {

    @Autowired
    AmAdminClient amAdminClient;

    /**
     * 获取列表
     * @param requestBean
     * @return
     */
    @Override
    public AppPushManageResponse getAppPushManageList(AppPushManageRequestBean requestBean) {
        return amAdminClient.getPushManageList(requestBean);
    }

    /**
     * 添加
     * @param requestBean
     * @return
     */
    @Override
    public AppPushManageResponse insertPushManage(AppPushManageRequestBean requestBean) {
        return amAdminClient.insterPushManage(requestBean);
    }

    /**
     * 更新操作
     * @param requestBean
     * @return
     */
    @Override
    public boolean updatePushManage(AppPushManageRequestBean requestBean) {
        return amAdminClient.updatePushManage(requestBean);
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @Override
    public boolean deletePushManage(Integer id) {
        return amAdminClient.deletePushManage(id);
    }
}
