/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.VipInfo;
import com.hyjf.am.user.service.front.user.VipInfoService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version VipInfoServiceImpl, v0.1 2018/7/23 10:57
 */
@Service
public class VipInfoServiceImpl extends BaseServiceImpl implements VipInfoService {
    @Override
    public VipInfo findVipInfoById(Integer vipId) {
        VipInfo vipInfo = vipInfoMapper.selectByPrimaryKey(vipId);
        return vipInfo;
    }
}
