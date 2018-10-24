package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.DebtConfigLogMapper;
import com.hyjf.am.config.dao.mapper.auto.DebtConfigMapper;
import com.hyjf.am.config.dao.model.auto.DebtConfig;
import com.hyjf.am.config.dao.model.auto.DebtConfigExample;
import com.hyjf.am.config.dao.model.auto.DebtConfigLog;
import com.hyjf.am.config.dao.model.auto.DebtConfigLogExample;
import com.hyjf.am.config.service.DebtConfigService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.DebtConfigLogResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigLogVO;
import com.hyjf.am.vo.config.DebtConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DebtConfigServiceImpl implements DebtConfigService {


    @Autowired
    DebtConfigMapper debtConfigMapper;
    @Autowired
    DebtConfigLogMapper debtConfigLogMapper;


    /**
     * 查询债转配置表配置
     *
     * @param
     */
    @Override
    public List<DebtConfigVO> getDebtConfig() {
        DebtConfigExample example = new DebtConfigExample();
        List<DebtConfig> configList = debtConfigMapper.selectByExample(example);
        List<DebtConfigVO> voList = new ArrayList<DebtConfigVO>();
        for (DebtConfig debtConfig:configList){
            DebtConfigVO vo = new DebtConfigVO();
            BeanUtils.copyProperties(debtConfig,vo);
            voList.add(vo);
        }
        return voList;
    }


    /**
     * 修改债转配置表配置
     *
     * @param record
     */
    @Override
    public void updateDebtConfig(DebtConfigRequest record){
        DebtConfigLog debtConfigLog = new DebtConfigLog();
        DebtConfig debtConfig = new DebtConfig();
        debtConfigLog.setAttornRate(record.getAttornRate());
        debtConfigLog.setCloseDes(record.getCloseDes());
        debtConfigLog.setConcessionRateDown(record.getConcessionRateDown());
        debtConfigLog.setConcessionRateUp(record.getConcessionRateUp());
        debtConfigLog.setHyjfDebtConfigId(record.getId());
        debtConfigLog.setToggle(record.getToggle());
        debtConfigLog.setUpdateTime(new Date());
        debtConfigLog.setUpdateUser(record.getUpdateUser());
        debtConfigLog.setUpdateUsername(record.getUpdateUsername());
        debtConfigLog.setIpAddress(record.getIpAddress());
        BeanUtils.copyProperties(record,debtConfig);
        debtConfigLogMapper.insertSelective(debtConfigLog);
        debtConfigMapper.updateByPrimaryKeySelective(debtConfig);
    }

    @Override
    public IntegerResponse countDebtConfigLogTotal(){
        IntegerResponse response = new IntegerResponse();
        DebtConfigLogExample example = new DebtConfigLogExample();
        int count = debtConfigLogMapper.countByExample(example);
        response.setResultInt(count);
        return response;
    }

    @Override
    public DebtConfigLogResponse getDebtConfigLogList(DebtConfigRequest form){
        DebtConfigLogResponse response = new DebtConfigLogResponse();
        DebtConfigLogExample example = new DebtConfigLogExample();
        example.setLimitStart(form.getLimitStart());
        example.setLimitEnd(form.getLimitEnd());
        example.setOrderByClause("`update_time` desc");
        List<DebtConfigLog> list =  debtConfigLogMapper.selectByExample(example);
        List<DebtConfigLogVO> voList = new ArrayList<>();
        for (DebtConfigLog log:list){
            DebtConfigLogVO vo = new DebtConfigLogVO();
            BeanUtils.copyProperties(log,vo);
            voList.add(vo);
        }
        response.setResultList(voList);
        return  response;
    }
}
