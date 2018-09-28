/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config;

import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogService, v0.1 2018/9/28 11:29
 */
public interface BailConfigLogService extends BaseService {

    /**
     * 获取保证金配置日志总数
     *
     * @param bailConfigLogRequest
     * @return
     */
    Integer selectBailConfigLogCount(BailConfigLogRequest bailConfigLogRequest);

    /**
     * 获取保证金配置日志列表
     *
     * @param bailConfigLogRequest
     * @return
     */
    List<BailConfigLogCustomizeVO> selectBailConfigLogList(BailConfigLogRequest bailConfigLogRequest);
}
