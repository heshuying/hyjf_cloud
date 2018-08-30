package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.vo.trade.InvestListCustomizeVO;
import com.hyjf.am.trade.service.front.borrow.BorrowListService;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.hyjf.am.trade.dao.mapper.customize.ApiProjectListCustomizeMapper;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class BorrowListServiceImpl extends BaseServiceImpl implements BorrowListService {

    @Autowired
    ApiProjectListCustomizeMapper apiProjectListCustomizeMapper;

    @Override
    public List<InvestListCustomizeVO> InvestRepaysList(Map<String, Object> params) {
        List<InvestListCustomizeVO> repayList = apiProjectListCustomizeMapper.searchInvestListNew(params);
        //获取redis数据
        Map<String, String> INVEST_TYPE =  CacheUtil.getParamNameMap("INVEST_TYPE");
        Map<String, String> USER_PROPERTY = CacheUtil.getParamNameMap("USER_PROPERTY");
        //插入redis数据
        for(InvestListCustomizeVO vo: repayList){
            if (!CollectionUtils.isEmpty(INVEST_TYPE)) {
                vo.setInvestType(INVEST_TYPE.get(vo.getInvest_type_rds()));
            }
            if (!CollectionUtils.isEmpty(USER_PROPERTY)) {
                vo.setTenderUserAttribute(USER_PROPERTY.get(vo.getTender_user_attribute_rds()));
                vo.setInviteUserAttribute(USER_PROPERTY.get(vo.getInvite_user_attribute_rds()));
            }
        }
        return repayList;
    }
}
