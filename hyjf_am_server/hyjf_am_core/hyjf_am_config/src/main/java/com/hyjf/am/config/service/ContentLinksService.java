/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.resquest.admin.ContentLinksRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version ContentLinksService, v0.1 2018/7/14 14:30
 */
public interface ContentLinksService {
    /**
     * 根据条件查询友情链接
     *
     * @param request
     * @return
     */
    List<Link> searchAction(ContentLinksRequest request);

    /**
     * 添加友情链接
     *
     * @param request
     */
    void insertAction(ContentLinksRequest request);

    /**
     * 修改友情链接
     *
     * @param request
     */
    void updateAction(ContentLinksRequest request);

    /**
     * 根据id查询友情链接
     *
     * @param id
     * @return
     */
    Link getRecord(Integer id);

    /**
     * 根据id修改友情链接
     *
     * @param id
     */
    void deleteById(Integer id);
}
