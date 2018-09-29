/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.dao.mapper.customize.AdminPoundageCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.PoundageDetail;
import com.hyjf.am.trade.dao.model.auto.PoundageDetailExample;
import com.hyjf.am.trade.dao.model.auto.PoundageLedger;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;
import com.hyjf.am.trade.service.admin.TradePoundageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TradePoundageServiceImpl, v0.1 2018/9/3 15:20
 */
@Service(value = "tradeTradePoundageServiceImpl")
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

    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageLedger getPoundageLedgerById(int id) {
        return poundageLedgerMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询详情的count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageDetailCount(AdminPoundageDetailRequest request) {
        PoundageDetailExample example = convertExample(request);
        return poundageDetailMapper.countByExample(example);
    }

    /**
     * 查询详情的list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageDetail> searchPoundageDetailList(AdminPoundageDetailRequest request) {
        PoundageDetailExample example = convertExample(request);
        return poundageDetailMapper.selectByExample(example);
    }

    /**
     * 转example
     * @auth sunpeikai
     * @param
     * @return
     */
    private PoundageDetailExample convertExample(AdminPoundageDetailRequest request){
        PoundageDetailExample example = new PoundageDetailExample();
        PoundageDetailExample.Criteria criteria = example.createCriteria();
        //实际分账时间段
        if(StringUtils.isNotBlank(String.valueOf(request.getLedgerTimeSer()))){
            criteria.andLedgerTimeEqualTo(request.getLedgerTimeSer());
        }
        //手续费分账配置id
        if(StringUtils.isNotBlank(String.valueOf(request.getLedgerIdSer()))){
            criteria.andLedgerIdEqualTo(request.getLedgerIdSer());
        }
        if(StringUtils.isNotBlank(request.getBorrowNidSer())){
            criteria.andBorrowNidLike("%" + request.getBorrowNidSer() + "%");
        }
        if(StringUtils.isNotBlank(request.getBorrowTypeSer())){
            criteria.andBorrowTypeEqualTo(request.getBorrowTypeSer());
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getAddTimeStart())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getAddTimeStart())));
        }
        //添加时间结束
        if (StringUtils.isNotEmpty(request.getAddTimeEnd())) {
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayEnd(request.getAddTimeEnd())));
        }

        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return example;
    }
}
