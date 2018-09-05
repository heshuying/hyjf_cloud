/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.dao.mapper.customize.AdminPoundageCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;
import com.hyjf.am.trade.service.admin.TradePoundageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TradePoundageServiceImpl, v0.1 2018/9/3 15:20
 */
@Service
public class TradePoundageServiceImpl extends BaseServiceImpl implements TradePoundageService {

    @Autowired
    private AdminPoundageCustomizeMapper adminPoundageCustomizeMapper;

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageCount(PoundageListRequest request) {
        return adminPoundageCustomizeMapper.getPoundageCount(request);
    }

    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminPoundageCustomize> searchPoundageList(PoundageListRequest request) {
        return adminPoundageCustomizeMapper.getPoundageList(request);
    }

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public AdminPoundageCustomize getPoundageSum(PoundageListRequest request) {
        return adminPoundageCustomizeMapper.getPoundageSum(request);
    }

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public AdminPoundageCustomize getPoundageById(Integer id) {
        return adminPoundageCustomizeMapper.getPoundageById(id);
    }

    /**
     * 审核-更新poundage
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer updatePoundage(PoundageCustomizeVO poundageCustomizeVO) {
        return adminPoundageCustomizeMapper.updatePoundage(poundageCustomizeVO);
    }
}
