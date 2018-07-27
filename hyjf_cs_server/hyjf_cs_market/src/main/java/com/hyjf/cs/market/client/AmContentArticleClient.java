/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AmContentArticleClient, v0.1 2018/7/26 15:05
 */
public interface AmContentArticleClient {

    /**
     * 获取网贷知识列表总条数
     * @author pcc
     * @param params
     * @return
     */
    Integer countContentArticleByType(Map<String, Object> params);

    /**
     * 获取网贷知识列表
     * @author pcc
     * @param params
     * @param request
     * @return
     */
    List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params, HttpServletRequest request);
}
