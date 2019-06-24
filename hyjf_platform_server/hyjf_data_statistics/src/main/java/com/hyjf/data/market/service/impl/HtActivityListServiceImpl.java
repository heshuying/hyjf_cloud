package com.hyjf.data.market.service.impl;

import com.hyjf.data.market.entity.HtActivityList;
import com.hyjf.data.market.mapper.HtActivityListMapper;
import com.hyjf.data.market.service.IHtActivityListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动列表 服务实现类
 * </p>
 *
 * @author auto
 * @since 2019-06-24
 */
@Service
public class HtActivityListServiceImpl extends ServiceImpl<HtActivityListMapper, HtActivityList> implements IHtActivityListService {

    @Autowired
    HtActivityListMapper htActivityListMapper;

    @Override
    public HtActivityList selectById() {
        int id=1;
        return htActivityListMapper.selectById(id);
    }
}
