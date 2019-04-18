package com.hyjf.am.config.service.app.impl;

import com.hyjf.am.config.service.app.AppRechargeService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wgx
 * @date 2019/4/17
 */
@Service
public class AppRechargeServiceImpl implements AppRechargeService {

    /**
     * 获取充值规则
     * @return
     */
    @Override
    public List getRechargeRule() {
        return RedisUtils.getObj(RedisConstants.APP_RECHARGE + "rule", List.class);
    }

}
