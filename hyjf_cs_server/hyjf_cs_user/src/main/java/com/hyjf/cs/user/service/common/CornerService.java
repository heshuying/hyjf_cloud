/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.common;

import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author zhangqingqing
 * @version CornerService, v0.1 2018/7/18 11:27
 */
public interface CornerService extends BaseUserService{
    /**
     * 获取版本信息
     * @param type
     * @return
     */
    VersionVO getNewVersionByType(Integer type);

    /**
     * 强制更新版本
     * @param type
     * @param isUpdate
     * @param version
     * @return
     */
    VersionVO getVersionByType(Integer type, Integer isUpdate, String version);
}
