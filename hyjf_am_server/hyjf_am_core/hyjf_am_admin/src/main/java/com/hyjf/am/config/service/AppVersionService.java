package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.dao.model.customize.VersionConfigBean;

/**
 * @author lisheng
 * @version AppVersionService, v0.1 2018/7/14 11:46
 */

public interface AppVersionService {
    /**
     * 获得记录条数
     */
    public Integer countRecord(VersionConfigBean record);
    /**
     * 获取列表
     *
     * @return
     */
    public List<Version> getRecordList(VersionConfigBean device, int limitStart, int limitEnd);

    /**
     * 获取单个
     *
     * @return
     */
    public Version getRecord(Integer id);


    /**
     * 插入
     **/
    public void insertRecord(VersionConfigBean form);

    /**
     * 更新
     *
     */
    public void updateRecord(VersionConfigBean form);

    /**
     * 删除
     *
     * @param ids
     */
    public void deleteRecord(List<Integer> ids);


    /**
     * 根据type     * @param type
     * @return
     */
    Version getNewVersionByType(Integer type);

    /**
     * 强制更新版本号
     * @param type
     * @param isupdate
     * @param versionStr
     * @return
     */
    Version getUpdateversion(Integer type, Integer isupdate, String versionStr);
}
