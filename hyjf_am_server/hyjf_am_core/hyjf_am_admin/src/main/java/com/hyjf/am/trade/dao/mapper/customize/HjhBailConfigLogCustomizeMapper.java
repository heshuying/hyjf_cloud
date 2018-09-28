/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhBailConfigLogCustomizeMapper, v0.1 2018/9/28 11:43
 */
public interface HjhBailConfigLogCustomizeMapper {
    /**
     * 获取保证金配置日志列表数据
     *
     * @param bailConfigLogRequest
     * @return
     */
    List<BailConfigLogCustomizeVO> selectHjhBailConfigLogList(BailConfigLogRequest bailConfigLogRequest);

    /**
     * 查询总件数
     *
     * @param bailConfigLogRequest
     * @return
     */
    Integer countHjhBailConfigLog(BailConfigLogRequest bailConfigLogRequest);
}
