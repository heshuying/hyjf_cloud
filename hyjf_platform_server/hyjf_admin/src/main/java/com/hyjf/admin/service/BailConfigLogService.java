/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogService, v0.1 2018/9/28 11:04
 */
public interface BailConfigLogService extends BaseService {

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    List<DropDownVO> selectHjhInstConfigList();

    /**
     * 获取保证金配置日志总数
     *
     * @param request
     * @return
     */
    Integer selectBailConfigLogCount(BailConfigLogRequest request);

    /**
     * 获取保证金配置日志列表
     *
     * @param request
     * @return
     */
    List<BailConfigLogCustomizeVO> selectBailConfigLogList(BailConfigLogRequest request);
}
