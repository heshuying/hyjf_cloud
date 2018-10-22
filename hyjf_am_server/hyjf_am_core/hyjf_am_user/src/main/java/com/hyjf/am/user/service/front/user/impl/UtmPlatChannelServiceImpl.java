/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.UtmExample;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.dao.model.auto.UtmPlatExample;
import com.hyjf.am.user.service.front.user.UtmPlatChannelService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.cs.message.bean.ic.AppChannelStatisticsDetail;
import com.hyjf.cs.message.mongo.ic.AppChannelStatisticsDetailDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户渠道相关
 *
 * @author liuyang
 * @version UtmPlatChannelServiceImpl, v0.1 2018/10/18 16:41
 */
@Service
public class UtmPlatChannelServiceImpl extends BaseServiceImpl implements UtmPlatChannelService {

    @Autowired
    private AppChannelStatisticsDetailDao appChannelStatisticsDetailDao;
    /**
     * 根据utmId查询渠道信息
     *
     * @param utmId
     * @return
     */
    @Override
    public UtmVO selectUtmByUtmId(Integer utmId) {
        UtmVO utmVO = new UtmVO();
        UtmExample example = new UtmExample();
        UtmExample.Criteria cra = example.createCriteria();
        cra.andUtmIdEqualTo(utmId);
        List<Utm> list = this.utmMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            Utm utm = list.get(0);
            BeanUtils.copyProperties(utm, utmVO);
            return utmVO;
        }
        return null;
    }

    /**
     * 根据source_id查询UtmPlat
     *
     * @param sourceId
     * @return
     */
    @Override
    public UtmPlatVO selectUtmPlatBySourceId(Integer sourceId) {
        UtmPlatVO utmPlatVO = new UtmPlatVO();
        UtmPlatExample example = new UtmPlatExample();
        UtmPlatExample.Criteria cra = example.createCriteria();
        cra.andSourceIdEqualTo(sourceId);
        List<UtmPlat> list = this.utmPlatMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            UtmPlat utmPlat = list.get(0);
            BeanUtils.copyProperties(utmPlat, utmPlatVO);
            return utmPlatVO;
        }
        return null;
    }


    /**
     * 根据用户ID查询是否App渠道过来的用户
     *
     * @param userId
     * @return
     */
    @Override
    public AppChannelStatisticsDetailVO selectAppChannelStatisticsDetailByUserId(Integer userId) {
        AppChannelStatisticsDetailVO appChannelStatisticsDetailVO = new AppChannelStatisticsDetailVO();
        AppChannelStatisticsDetail appChannelStatisticsDetail = appChannelStatisticsDetailDao.findByUserId(userId);
        if (appChannelStatisticsDetail != null){
            BeanUtils.copyProperties(appChannelStatisticsDetail, appChannelStatisticsDetailVO);
            return appChannelStatisticsDetailVO;
        }
        return null;
    }


}
