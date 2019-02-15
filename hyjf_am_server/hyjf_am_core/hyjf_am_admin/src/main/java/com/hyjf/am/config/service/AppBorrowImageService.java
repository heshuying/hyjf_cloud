package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.AppBorrowImage;

import java.util.List;

/**
 * @author lisheng
 * @version AppBorrowImageService, v0.1 2018/7/12 17:32
 */

public interface AppBorrowImageService {
    /**
     * 查询条数
     * @return
     */
    Integer getCount();
    /**
     * 获取列表
     *
     * @return
     */
    public List<AppBorrowImage> getRecordList( int limitStart, int limitEnd);

    /**
     * 获取单个
     * @return
     */
    public AppBorrowImage getRecord(Integer id);

    /**
     * 插入
     *
     * @param record
     */
    public void insertRecord(AppBorrowImage record) throws Exception;


    /**
     * 更新
     *
     * @param record
     */
    public void updateRecord(AppBorrowImage record) throws Exception;

    /**
     * 删除
     *
     */
    public boolean deleteRecord(Integer id);
}
