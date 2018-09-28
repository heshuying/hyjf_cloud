/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config.impl;

import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.trade.service.admin.config.BailConfigLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogServiceImpl, v0.1 2018/9/28 11:29
 */
@Service
public class BailConfigLogServiceImpl extends BaseServiceImpl implements BailConfigLogService {

    /**
     * 获取保证金配置日志总数
     *
     * @param bailConfigLogRequest
     * @return
     */
    @Override
    public Integer selectBailConfigLogCount(BailConfigLogRequest bailConfigLogRequest) {
        return hjhBailConfigLogCustomizeMapper.countHjhBailConfigLog(bailConfigLogRequest);
    }

    /**
     * 获取保证金配置日志列表
     *
     * @param bailConfigLogRequest
     * @return
     */
    @Override
    public List<BailConfigLogCustomizeVO> selectBailConfigLogList(BailConfigLogRequest bailConfigLogRequest) {
        return hjhBailConfigLogCustomizeMapper.selectHjhBailConfigLogList(bailConfigLogRequest);
    }
}
