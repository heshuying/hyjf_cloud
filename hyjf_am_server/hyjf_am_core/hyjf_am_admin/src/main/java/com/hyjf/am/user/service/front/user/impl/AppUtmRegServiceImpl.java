package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.user.dao.mapper.auto.AppUtmRegMapper;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.AppUtmRegExample;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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

    /***
     * 开户更新开户渠道统计开户时间_test
     * @author Zha Daojian
     * @date 2019/3/11 11:19
     * @param entity
     * @return void
     **/
    @Override
    public int updateByPrimaryKeySelective(AppUtmReg entity) {
        return  appUtmRegMapper.updateByPrimaryKeySelective(entity);
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

    @Override
    public Integer countAppUtmReg(AppChannelStatisticsDetailRequest request) {
        AppUtmRegExample appUtmRegExample = new AppUtmRegExample();
        AppUtmRegExample.Criteria criteria = appUtmRegExample.createCriteria();
        if (StringUtils.isNotBlank(request.getUserNameSrch())) {
            criteria.andUserNameEqualTo(request.getUserNameSrch());
        }
        if (request.getSourceIdSrch()!=null) {
            criteria.andSourceIdEqualTo(request.getSourceIdSrch());
        }
        return appUtmRegMapper.countByExample(appUtmRegExample);
    }

    @Override
    public List<AppUtmReg> findAppUtmReg(AppChannelStatisticsDetailRequest request, Paginator paginator) {
        AppUtmRegExample appUtmRegExample = new AppUtmRegExample();
        AppUtmRegExample.Criteria criteria = appUtmRegExample.createCriteria();
        if (StringUtils.isNotBlank(request.getUserNameSrch())) {
            criteria.andUserNameEqualTo(request.getUserNameSrch());
        }
        if (request.getSourceIdSrch()!=null) {
            criteria.andSourceIdEqualTo(request.getSourceIdSrch());
        }
        if (paginator.getLimit() >= 0 && paginator.getOffset() >= 0) {
            appUtmRegExample.setLimitStart(paginator.getOffset());
            appUtmRegExample.setLimitEnd(paginator.getLimit());
        }
        appUtmRegExample.setOrderByClause("register_time desc");
        return appUtmRegMapper.selectByExample(appUtmRegExample);

    }
}
