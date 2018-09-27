package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.DebtConfigMapper;
import com.hyjf.am.config.dao.model.auto.DebtConfig;
import com.hyjf.am.config.dao.model.auto.DebtConfigExample;
import com.hyjf.am.config.service.DebtConfigService;
import com.hyjf.am.vo.config.DebtConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DebtConfigServiceImpl implements DebtConfigService {


    @Autowired
    DebtConfigMapper debtConfigMapper;


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

}
