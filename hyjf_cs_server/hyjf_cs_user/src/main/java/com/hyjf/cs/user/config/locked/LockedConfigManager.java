/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.config.locked;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 前后台登录失败配置管理，单例模式
 *
 * @author cui
 * @version LockedConfigManager, v0.1 2018/7/13 9:30
 */
public class LockedConfigManager {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static class SingletonHolder {
        private static final LockedConfigManager instance = new LockedConfigManager();
    }

    public static LockedConfigManager getInstance() {
        return SingletonHolder.instance;
    }

    private LockedConfigManager() {

        boolean isExist = RedisUtils.exists(RedisConstants.LOCKED_CONFIG);
        if (!isExist) {

            LockedConfig config = buildDefaultConfig();

            String configString = JSON.toJSONString(config);

            RedisUtils.set(RedisConstants.LOCKED_CONFIG, configString);

        }

    }


    /**
     * 设置前台登录失败配置
     *
     * @param config
     */
    public void setWebConfig(LockedConfig.Config config) {

        Preconditions.checkArgument(config.getLockLong() != null && config.getMaxLoginErrorNum() != null);

        String configString = RedisUtils.get(RedisConstants.LOCKED_CONFIG);

        LockedConfig lockedConfig = JSON.parseObject(configString, LockedConfig.class);

        lockedConfig.setWebConfig(config);

        RedisUtils.set(RedisConstants.LOCKED_CONFIG, JSON.toJSONString(lockedConfig));

    }


    /**
     * 设置后台登录失败配置
     *
     * @param config
     */
    public void setAdminConfig(LockedConfig.Config config) {

        Preconditions.checkArgument(config.getLockLong() != null && config.getMaxLoginErrorNum() != null);

        String configString = RedisUtils.get(RedisConstants.LOCKED_CONFIG);

        LockedConfig lockedConfig = JSON.parseObject(configString, LockedConfig.class);

        lockedConfig.setAdminConfig(config);

        RedisUtils.set(RedisConstants.LOCKED_CONFIG, JSON.toJSONString(lockedConfig));
    }


    public LockedConfig.Config getWebConfig() {

        String configString = RedisUtils.get(RedisConstants.LOCKED_CONFIG);

        Preconditions.checkArgument(!Strings.isNullOrEmpty(configString), "未配置登录失败");

        LockedConfig LockedConfig = JSON.parseObject(configString, LockedConfig.class);

        return LockedConfig.getWebConfig();

    }

    public LockedConfig.Config getAdminConfig() {
        String configString = RedisUtils.get(RedisConstants.LOCKED_CONFIG);

        Preconditions.checkArgument(!Strings.isNullOrEmpty(configString), "未配置登录失败");

        LockedConfig LockedConfig = JSON.parseObject(configString, LockedConfig.class);

        return LockedConfig.getAdminConfig();
    }

    /**
     * 生成默认配置信息
     *
     * @return
     */
    private LockedConfig buildDefaultConfig() {

        LockedConfig config = new LockedConfig();

        LockedConfig.Config defaultConfig = new LockedConfig.Config();

        defaultConfig.setLockLong(24);

        defaultConfig.setMaxLoginErrorNum(5);

        config.setWebConfig(defaultConfig);

        LockedConfig.Config defaultConfig1 = new LockedConfig.Config();
        BeanUtils.copyProperties(defaultConfig, defaultConfig1);
        config.setAdminConfig(defaultConfig1);
        return config;
    }

}
