/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.resquest.admin.ContentLinksRequest;

/**
 * @author fuqiang
 * @version ContentLinksService, v0.1 2018/7/13 10:51
 */
public interface ContentLinksService {
    /**
     * 根据条件查询友情链接列表
     *
     * @param requestBean
     * @return
     */
    LinkResponse searchAction(ContentLinksRequest requestBean);

    /**
     * 添加友情链接列表查询
     *
     * @param requestBean
     * @return
     */
    LinkResponse insertAction(ContentLinksRequest requestBean);

    /**
     * 根据id查询数据
     *
     * @param requestBean
     * @return
     */
    LinkResponse infoInfoAction(ContentLinksRequest requestBean);

    /**
     * 修改友情链接列表查询
     *
     * @param requestBean
     * @return
     */
    LinkResponse updateAction(ContentLinksRequest requestBean);

    /**
     * 修改友情链接列表查询状态
     *
     * @param requestBean
     * @return
     */
    LinkResponse updateStatus(ContentLinksRequest requestBean);

    /**
     * 根据id删除友情链接列表查询
     *
     * @param id
     * @return
     */
    LinkResponse deleteById(Integer id);
}
