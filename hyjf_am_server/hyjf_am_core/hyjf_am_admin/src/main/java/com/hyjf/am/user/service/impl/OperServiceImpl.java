package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.mapper.auto.ScreenConfigMapper;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfigExample;
import com.hyjf.am.user.service.admin.vip.content.OperService;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperServiceImpl implements OperService {

    @Autowired
    private ScreenConfigMapper screenConfigMapper;

    @Override
    public List<ScreenConfig> list(ScreenConfigRequest request) {
        ScreenConfigExample example = new ScreenConfigExample();
        if(StringUtils.isNotBlank(request.getTaskTime())){
            ScreenConfigExample.Criteria cra = example.createCriteria();
            cra.andTaskTimeEqualTo(request.getTaskTime());
        }
        return screenConfigMapper.selectByExample(example);
    }

    @Override
    public int insert(ScreenConfigVO screenConfigVO) {
        ScreenConfig screenConfig = new ScreenConfig();
        BeanUtils.copyProperties(screenConfigVO, screenConfig);
        return screenConfigMapper.insertSelective(screenConfig);
    }

    @Override
    public int update(ScreenConfigVO screenConfigVO) {
        ScreenConfig screenConfig = new ScreenConfig();
        BeanUtils.copyProperties(screenConfigVO, screenConfig);
        return screenConfigMapper.updateByPrimaryKeySelective(screenConfig);
    }


}
