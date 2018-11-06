package com.hyjf.am.trade.service.admin.pushmanage;

import com.hyjf.am.resquest.admin.AppPushManageRequest;
import com.hyjf.am.trade.dao.model.auto.AppPushManage;

import java.util.List;

/**
 * 移动客户端 App 推送管理
 * @Author : huanghui
 */
public interface AppPushManageService {

    /**
     * 获取总条数
     * @param pushManageRequest
     * @return
     */
    Integer getCountList(AppPushManageRequest pushManageRequest);

    /**
     * 获取所有的数据列表
     * @param pushManageRequest
     * @return
     */
    List<AppPushManage> getAllList(AppPushManageRequest pushManageRequest);

    /**
     * 写入单条记录
     * @param pushManageRequest
     * @return
     */
    int insertPushManage(AppPushManageRequest pushManageRequest);

    /**
     * 更新单条记录
     * @param pushManageRequest
     * @return
     */
    int updatePushManage(AppPushManageRequest pushManageRequest);

    /**
     * 删除单条记录
     * @param id
     * @return
     */
    int deletePushManage(Integer id);
}
