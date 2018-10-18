/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BailConfigLogService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogServiceImpl, v0.1 2018/9/28 11:04
 */
@Service
public class BailConfigLogServiceImpl extends BaseServiceImpl implements BailConfigLogService {

    @Autowired
    AmAdminClient amAdminClient;

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    @Override
    public List<DropDownVO> selectHjhInstConfigList() {
        List<HjhInstConfigVO> hjhInstConfigVOList = amAdminClient.selectHjhInstConfigList();
        return ConvertUtils.convertListToDropDown(hjhInstConfigVOList,"instCode","instName");
    }

    /**
     * 获取保证金配置日志总数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectBailConfigLogCount(BailConfigLogRequest request) {
        return amAdminClient.selectBailConfigLogCount(request);
    }

    /**
     * 获取保证金配置日志列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BailConfigLogCustomizeVO> selectBailConfigLogList(BailConfigLogRequest request) {
        return amAdminClient.selectBailConfigLogList(request);
    }
}
