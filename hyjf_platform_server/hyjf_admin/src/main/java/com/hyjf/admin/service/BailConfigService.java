/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigService, v0.1 2018/9/26 16:36
 */
public interface BailConfigService extends BaseService {

    /**
     * 获取保证金配置总数
     *
     * @param request
     * @return
     */
    Integer selectBailConfigCount(BailConfigRequest request);

    /**
     * 获取保证金配置列表
     *
     * @param request
     * @return
     */
    List<BailConfigCustomizeVO> selectRecordList(BailConfigRequest request);
}
