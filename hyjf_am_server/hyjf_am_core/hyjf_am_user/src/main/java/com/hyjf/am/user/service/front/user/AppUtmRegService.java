package com.hyjf.am.user.service.front.user;

import java.util.List;

import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;

/**
 * @author fuqiang
 * @version AppUtmRegService, v0.1 2018/11/8 17:18
 */
public interface AppUtmRegService {
    /**
     * 查询app推广用户信息
     * @param userId
     * @return
     */
    AppUtmReg findByUserId(Integer userId);

    /**
     * 修改app推广用户信息
     * @param entity
     */
    void update(AppUtmReg entity);

    /**
     * 保存app推广用户信息
     * @param entity
     */
    void insert(AppUtmReg entity);

    /**
     * 导出app渠道数据
     * @param request
     * @return
     */
    List<AppUtmReg> exportStatisticsList(AppChannelStatisticsDetailRequest request);

    List<AppUtmReg> getAppUtmRegVO(AppChannelStatisticsRequest request);
}
