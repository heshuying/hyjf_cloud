package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhRepayMapper;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.dao.model.auto.HjhRepayExample;
import com.hyjf.am.trade.dao.model.customize.HjhRepayCustomize;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 汇计划订单退出实现
 * @Author : huanghui
 */
@Service
public class HjhRepayServiceImpl extends BaseServiceImpl implements HjhRepayService {

    @Autowired
    private HjhRepayMapper hjhRepayMapper;

    /**
     * 统计条数
     * @param params
     * @return
     */
    @Override
    public Integer getRepayCount(Map<String, Object> params) {
        return hjhPlanRepayCustomizeMapper.getListTotal(params);
    }

    /**
     * 查询数据列表
     * @param params
     * @return
     */
    @Override
    public List<HjhRepayVO> selectByExample(Map<String, Object> params) {

        List<HjhRepayCustomize> repayList = hjhPlanRepayCustomizeMapper.exportPlanRepayList(params);
        List<HjhRepayVO> repayVOList = CommonUtils.convertBeanList(repayList, HjhRepayVO.class);
        return repayVOList;
    }

    @Override
    public List<HjhRepayVO> selectByAccedeOrderId(String accedeOrderId) {
        HjhRepayExample example = new HjhRepayExample();
        HjhRepayExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(accedeOrderId)){
            criteria.andAccedeOrderIdEqualTo(accedeOrderId);
        }

        List<HjhRepay> repayMentList = hjhRepayMapper.selectByExample(example);
        List<HjhRepayVO> repayMentVoList = CommonUtils.convertBeanList(repayMentList, HjhRepayVO.class);
        return repayMentVoList;
    }
}
