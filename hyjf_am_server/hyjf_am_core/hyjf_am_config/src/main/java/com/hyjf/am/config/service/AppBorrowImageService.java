package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.AppBorrowImage;

import java.util.List;

/**
 * @author lisheng
 * @version AppBorrowImageService, v0.1 2018/7/12 17:32
 */

public interface AppBorrowImageService {

    /**
     * 获取列表
     *
     * @return
     */
    List<AppBorrowImage> getRecordList(AppBorrowImage config, int limitStart, int limitEnd);

    /**
     * 获取单个
     * @return
     */
    AppBorrowImage getRecord(Integer id);

    /**
     * 插入
     *
     * @param record
     */
    void insertRecord(AppBorrowImage record) throws Exception;


    /**
     * 更新
     *
     * @param record
     */
    void updateRecord(AppBorrowImage record) throws Exception;

    /**
     * 删除
     *
     */
    boolean deleteRecord(Integer id);
}
