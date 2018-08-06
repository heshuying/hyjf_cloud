package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhRepayMapper;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.dao.model.auto.HjhRepayExample;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 汇计划订单退出实现
 * @Author : huanghui
 */
@Service
public class HjhRepayServiceImpl implements HjhRepayService {

    @Autowired
    private HjhRepayMapper hjhRepayMapper;

    @Override
    public Integer getRepayCount(HjhRepayRequest repayRequest) {
        int total = 0;
        HjhRepayExample example = new HjhRepayExample();
        HjhRepayExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(repayRequest.getAccedeOrderIdSrch())){
            criteria.andAccedeOrderIdLike("%" + repayRequest.getAccedeOrderIdSrch() + "%");
        }

        if (StringUtils.isNotEmpty(repayRequest.getRepayTimeStart())){
            int repayTimeStart = GetDate.getDayEnd10(repayRequest.getRepayTimeStart());
            int repayTimeEnd;
            if (StringUtils.isNotEmpty(repayRequest.getRepayTimeEnd())){
                repayTimeEnd = GetDate.getDayEnd10(repayRequest.getRepayTimeEnd());
            }else {
                Long logRepayTimeEnd = System.currentTimeMillis()/1000;
                repayTimeEnd = logRepayTimeEnd.intValue();
            }
            criteria.andRepayShouldTimeBetween(repayTimeStart, repayTimeEnd);
        }
        total = hjhRepayMapper.countByExample(example);
        return total;
    }

    @Override
    public List<HjhRepay> selectByExample(HjhRepayRequest request) {
        HjhRepayExample example = new HjhRepayExample();
        HjhRepayExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(request.getAccedeOrderIdSrch())){
            criteria.andAccedeOrderIdLike("%" + request.getAccedeOrderIdSrch() + "%");
        }
        if (StringUtils.isNotEmpty(request.getPlanNidSrch())){
            criteria.andPlanNidLike("%" + request.getPlanNidSrch() + "%");
        }
        if (StringUtils.isNotEmpty(request.getRepayTimeStart())){
            int repayTimeStart = GetDate.getDayEnd10(request.getRepayTimeStart());
            int repayTimeEnd;
            if (StringUtils.isNotEmpty(request.getRepayTimeEnd())){
                repayTimeEnd = GetDate.getDayEnd10(request.getRepayTimeEnd());
            }else {
                Long logRepayTimeEnd = System.currentTimeMillis()/1000;
                repayTimeEnd = logRepayTimeEnd.intValue();
            }
            criteria.andRepayShouldTimeBetween(repayTimeStart, repayTimeEnd);
        }

        List<HjhRepay> repayList = hjhRepayMapper.selectByExample(example);
        return repayList;
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
