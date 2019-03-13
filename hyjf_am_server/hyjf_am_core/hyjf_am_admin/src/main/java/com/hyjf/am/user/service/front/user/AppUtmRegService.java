package com.hyjf.am.user.service.front.user;

import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.common.paginator.Paginator;

import java.util.List;

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

    /***
     * 开户更新开户渠道统计开户时间
     * @author Zha Daojian
     * @date 2019/3/11 11:19
     * @param entity
     * @return void
     **/
    void updateByPrimaryKeySelective(AppUtmReg entity);

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


    /**
     * 查询总数
     * @param request
     * @return
     */
    Integer countAppUtmReg(AppChannelStatisticsDetailRequest request);

    /**
     * 列表查询
     * @param request
     * @param paginator
     * @return
     */
    List<AppUtmReg> findAppUtmReg(AppChannelStatisticsDetailRequest request,Paginator paginator);
}
