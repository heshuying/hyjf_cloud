package com.hyjf.data.market.service;

import com.hyjf.data.market.entity.HtActivityList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动列表 服务类
 * </p>
 *
 * @author auto
 * @since 2019-06-24
 */
public interface IHtActivityListService extends IService<HtActivityList> {

    HtActivityList selectById();
}
