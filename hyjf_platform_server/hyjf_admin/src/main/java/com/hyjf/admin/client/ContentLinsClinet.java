/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.ContentLinksRequestBean;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;

/**
 * @author fuqiang
 * @version ContentLinsClinet, v0.1 2018/7/14 14:05
 */
public interface ContentLinsClinet {
    /**
     * 根据条件查询友情链接列表
     *
     * @param requestBean
     * @return
     */
    LinkResponse searchAction(ContentLinksRequestBean requestBean);

    /**
     * 添加根据条件查询友情链接列表
     *
     * @param requestBean
     * @return
     */
    LinkResponse insertAction(ContentLinksRequestBean requestBean);

    /**
     * 修改根据条件查询友情链接列表
     *
     * @param requestBean
     * @return
     */
    LinkResponse updateAction(ContentLinksRequestBean requestBean);

    /**
     * 根据id查询根据条件查询友情链接列表
     *
     * @param id
     * @return
     */
    LinkVO getRecord(Integer id);

    /**
     * 根据id删除根据条件查询友情链接列表
     *
     * @param id
     * @return
     */
    LinkResponse deleteById(Integer id);
}
