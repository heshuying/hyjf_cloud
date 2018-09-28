/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;

import java.util.List;

/**
 * @author fuqiang
 * @version PlatformCountService, v0.1 2018/7/18 17:58
 */
public interface PlatformCountService {
    /**
     * 获取列表
     * @param requestBean
     * @return
     */
    List<PlatformCountCustomizeVO>  searchAction(PlatformCountRequestBean requestBean);
}
