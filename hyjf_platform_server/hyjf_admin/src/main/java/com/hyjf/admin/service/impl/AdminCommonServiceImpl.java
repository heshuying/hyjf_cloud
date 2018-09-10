/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version AdminCommonServiceImpl, v0.1 2018/7/3 10:25
 */
@Service
public class AdminCommonServiceImpl implements AdminCommonService {
    @Autowired
    AmTradeClient AmTradeClient;

    /**
     * 获取相应paramname数据
     *
     * @param param
     * @return
     */
    @Override
    public List<DropDownVO> getParamNameList(String param) {
        Map<String, String> resultMap = CacheUtil.getParamNameMap(param);
        return ConvertUtils.convertParamMapToDropDown(resultMap);
    }

    /**
     * 还款方式下拉列表
     *
     * @return
     */
    @Override
    public List<DropDownVO> selectBorrowStyleList() {
        List<BorrowStyleVO> borrowStyleVOList = AmTradeClient.selectCommonBorrowStyleList();
        return ConvertUtils.convertListToDropDown(borrowStyleVOList,"nid","name");
    }

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    @Override
    public List<DropDownVO> selectHjhInstConfigList() {
        List<HjhInstConfigVO> hjhInstConfigVOList = AmTradeClient.selectCommonHjhInstConfigList();
        return ConvertUtils.convertListToDropDown(hjhInstConfigVOList,"instCode","instName");
    }

    /**
     * 项目类型下拉列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<DropDownVO> selectProjectType() {
        List<BorrowProjectTypeVO> borrowProjectTypeVOList = AmTradeClient.selectBorrowProjectList();
        return ConvertUtils.convertListToDropDown(borrowProjectTypeVOList,"borrowCd","borrowName");
    }
}
