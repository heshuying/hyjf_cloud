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

import java.math.BigDecimal;
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
            BigDecimal accedeOrderId = new BigDecimal(repayRequest.getAccedeOrderIdSrch());
            criteria.andAccedeAccountEqualTo(accedeOrderId);
        }

        //清算时间
        if (StringUtils.isNotEmpty(repayRequest.getRepayTimeStart())){
            int repayTimeStart = GetDate.getDayEnd10(repayRequest.getRepayTimeStart() + " 00:00:00");
            int repayTimeEnd;
            if (StringUtils.isNotEmpty(repayRequest.getRepayTimeEnd())){
                repayTimeEnd = GetDate.getDayEnd10(repayRequest.getRepayTimeEnd() + " 23:59:59");
            }else {
                Long logRepayTimeEnd = System.currentTimeMillis()/1000;
                repayTimeEnd = logRepayTimeEnd.intValue();
            }
            criteria.andRepayShouldTimeBetween(repayTimeStart, repayTimeEnd);
        }

        //实际退出时间
        if(StringUtils.isNotEmpty(repayRequest.getActulRepayTimeStart())){
            int actuTimeStart = GetDate.getDayStart10(repayRequest.getActulRepayTimeStart() + " 00:00:00");
            int actuTimeEnd;
            if (StringUtils.isNotEmpty(repayRequest.getActulRepayTimeEnd())){
                actuTimeEnd = GetDate.getDayEnd10(repayRequest.getActulRepayTimeEnd() + "23:59:59");
            }else {
                Long logAutuTimeEnd = System.currentTimeMillis() / 1000;
                actuTimeEnd = logAutuTimeEnd.intValue();
            }
            criteria.andRepayActualTimeBetween(actuTimeStart, actuTimeEnd);
        }

        if (repayRequest.getLimitStart() != null && repayRequest.getLimitStart() != -1){
            example.setLimitStart(repayRequest.getLimitStart());
        }

        if (repayRequest.getLimitEnd() != null && repayRequest.getLimitEnd() != -1){
            example.setLimitEnd(repayRequest.getLimitEnd());
        }
        total = hjhRepayMapper.countByExample(example);
        return total;
    }

    @Override
    public List<HjhRepay> selectByExample(HjhRepayRequest request) {
        HjhRepayExample example = new HjhRepayExample();
        HjhRepayExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(request.getAccedeOrderIdSrch())){
            BigDecimal accedeOrderId = new BigDecimal(request.getAccedeOrderIdSrch());
            criteria.andAccedeAccountEqualTo(accedeOrderId);
        }

        //清算时间
        if (StringUtils.isNotEmpty(request.getRepayTimeStart())){
            int repayTimeStart = GetDate.getDayEnd10(request.getRepayTimeStart() + " 00:00:00");
            int repayTimeEnd;
            if (StringUtils.isNotEmpty(request.getRepayTimeEnd())){
                repayTimeEnd = GetDate.getDayEnd10(request.getRepayTimeEnd() + " 23:59:59");
            }else {
                Long logRepayTimeEnd = System.currentTimeMillis()/1000;
                repayTimeEnd = logRepayTimeEnd.intValue();
            }
            criteria.andRepayShouldTimeBetween(repayTimeStart, repayTimeEnd);
        }

        //实际退出时间
        if(StringUtils.isNotEmpty(request.getActulRepayTimeStart())){
            int actuTimeStart = GetDate.getDayStart10(request.getActulRepayTimeStart() + " 00:00:00");
            int actuTimeEnd;
            if (StringUtils.isNotEmpty(request.getActulRepayTimeEnd())){
                actuTimeEnd = GetDate.getDayEnd10(request.getActulRepayTimeEnd() + "23:59:59");
            }else {
                Long logAutuTimeEnd = System.currentTimeMillis() / 1000;
                actuTimeEnd = logAutuTimeEnd.intValue();
            }
            criteria.andRepayActualTimeBetween(actuTimeStart, actuTimeEnd);
        }

        if (request.getLimitStart() != null && request.getLimitStart() != -1){
            example.setLimitStart(request.getLimitStart());
        }

        if (request.getLimitEnd() != null && request.getLimitEnd() != -1){
            example.setLimitEnd(request.getLimitEnd());
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

    /**
     * 订单退出超过两天邮件预警list
     * @author zhangyk
     * @date 2018/8/15 15:48
     */
    @Override
    public List<HjhRepay> getPlanExitCheck() {
        HjhRepayExample hjhRepayExample = new HjhRepayExample();
        //获取当前的时间减去两天的时间就是前天的时间
        int nowTime1 = GetDate.getNowTime10()-172800;
        HjhRepayExample.Criteria createCriteria = hjhRepayExample.createCriteria();
        createCriteria.andOrderStatusEqualTo(5);
        createCriteria.andRepayShouldTimeLessThanOrEqualTo(nowTime1);
        List<HjhRepay> repayList = hjhRepayMapper.selectByExample(hjhRepayExample);
        return repayList;
    }
}
