package com.hyjf.am.user.service.front.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.user.dao.mapper.auto.AppUtmRegMapper;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.AppUtmRegExample;
import com.hyjf.am.user.service.front.user.AppUtmRegService;

/**
 * @author fuqiang
 * @version AppUtmRegServiceImpl, v0.1 2018/11/8 17:18
 */
@Service
public class AppUtmRegServiceImpl implements AppUtmRegService {
    @Resource
    private AppUtmRegMapper appUtmRegMapper;

    @Override
    public AppUtmReg findByUserId(Integer userId) {
        AppUtmRegExample example = new AppUtmRegExample();
        AppUtmRegExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<AppUtmReg> list = appUtmRegMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void update(AppUtmReg entity) {
        appUtmRegMapper.updateByPrimaryKey(entity);
    }

    @Override
    public void insert(AppUtmReg entity) {
        appUtmRegMapper.insertSelective(entity);
    }

    @Override
    public List<AppUtmReg> exportStatisticsList(AppChannelStatisticsDetailRequest request) {
        AppUtmRegExample example = new AppUtmRegExample();
        AppUtmRegExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getUserNameSrch())) {
            criteria.andUserNameEqualTo(request.getUserNameSrch());
        }
        if (request.getSourceIdSrch() != null) {
            criteria.andSourceIdEqualTo(request.getSourceIdSrch());
        }
        return appUtmRegMapper.selectByExample(example);
    }
}
