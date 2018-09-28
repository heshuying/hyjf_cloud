package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.trade.dao.model.customize.HjhDayCreditDetailCustomize;
import com.hyjf.am.trade.service.admin.DayCreditDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 ServiceImpl
 * @Author : huanghui
 */
@Service
public class DayCreditDetailServiceImpl extends BaseServiceImpl implements DayCreditDetailService {

    /**
     * 统计条数
     * @param params
     * @return
     */
    @Override
    public Integer countDebtCredit(Map<String, Object> params) {
        return this.hjhDayCreditDetailCustomizeMapper.countDebtCredit(params);
    }

    /**
     * 计划按日转让列表
     * @param params
     * @return
     */
    @Override
    public List<HjhDayCreditDetailCustomize> selectDebtCreditList(Map<String, Object> params) {
        List<HjhDayCreditDetailCustomize> recordList = hjhDayCreditDetailCustomizeMapper.selectDebtCreditList(params);
        return recordList;
    }
}
