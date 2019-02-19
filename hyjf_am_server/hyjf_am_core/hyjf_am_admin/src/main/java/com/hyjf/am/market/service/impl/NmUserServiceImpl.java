package com.hyjf.am.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.dao.mapper.auto.InviterReturnDetailMapper;
import com.hyjf.am.market.dao.mapper.auto.NmUserMapper;
import com.hyjf.am.market.dao.mapper.auto.PerformanceReturnDetailMapper;
import com.hyjf.am.market.dao.model.auto.InviterReturnDetail;
import com.hyjf.am.market.dao.model.auto.NmUser;
import com.hyjf.am.market.dao.model.auto.NmUserExample;
import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.market.service.NmUserService;
import com.hyjf.am.response.IntegerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @Author: yinhui
 * @Date: 2019/1/22 15:17
 * @Version 1.0
 */
@Service
public class NmUserServiceImpl implements NmUserService {
    private Logger _log = LoggerFactory.getLogger(NmUserServiceImpl.class);

    @Autowired
    private NmUserMapper nmUserMapper;
    @Autowired
    PerformanceReturnDetailMapper performanceReturnDetailMapper;
    @Autowired
    InviterReturnDetailMapper inviterReturnDetailMapper;
    @Override
    public List<NmUser> selectNmUserList(NmUser nmUser) {
        NmUserExample example = new NmUserExample();

        List<NmUser> nmUsers =  nmUserMapper.selectByExample(example);
        return nmUsers;
    }
    @Override
    public IntegerResponse saveReutrnCash(Map<String,Object> map){
        _log.info("返现参数=="+ JSONObject.toJSONString(map));
        IntegerResponse response = new IntegerResponse(1);
        if(map ==null){
            return new IntegerResponse(0) ;
        }
        int level = (int)map.get("level");
        if(level>0) {
            PerformanceReturnDetail performanceReturnDetail = (PerformanceReturnDetail)map.get("performanceReturnDetail");
            this.savePerformanceReturnDetail(performanceReturnDetail);
        }
        if(level == 1){
            InviterReturnDetail inviterReturnDetail = (InviterReturnDetail)map.get("inviterReturnDetail");
            this.saveInviterReturnDetail(inviterReturnDetail);
            return response;
        }
        for(int i =1;i<level;i++){
            this.saveInviterReturnDetail((InviterReturnDetail)map.get("inviterReturnDetail"+i));
        }
        return response;
    }
    private int saveInviterReturnDetail(InviterReturnDetail inviterReturnDetail){
        return inviterReturnDetailMapper.insert(inviterReturnDetail);
    }

    private int savePerformanceReturnDetail(PerformanceReturnDetail performanceReturnDetail){
        return performanceReturnDetailMapper.insert(performanceReturnDetail);
    }

    @Override
    public void updateJoinTime(Integer nowTime,List<InviterReturnDetail> inviterReturnDetailList,List<PerformanceReturnDetail> performanceReturnDetailList){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(InviterReturnDetail inviterReturnDetail:inviterReturnDetailList){
            inviterReturnDetail.setJoinTime(sdf.format(nowTime*1000L));
            inviterReturnDetailMapper.updateByPrimaryKey(inviterReturnDetail);
        }
        for(PerformanceReturnDetail performanceReturnDetail:performanceReturnDetailList){
            performanceReturnDetail.setJoinTime(sdf.format(nowTime*1000L));
            performanceReturnDetailMapper.updateByPrimaryKeySelective(performanceReturnDetail);
        }
    }
}
