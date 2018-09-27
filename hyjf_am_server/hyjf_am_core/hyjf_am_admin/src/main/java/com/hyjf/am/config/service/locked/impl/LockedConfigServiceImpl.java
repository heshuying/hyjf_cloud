/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.locked.impl;

import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.config.controller.admin.locked.LockedConfigManager;
import com.hyjf.am.config.dao.mapper.auto.AdminMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.service.locked.LockedConfigService;
import com.hyjf.am.user.dao.mapper.auto.LockedUserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.dao.model.auto.LockedUserInfoExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.calculate.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author cui
 * @version LockedConfigServiceImpl, v0.1 2018/9/27 11:13
 */
@Service
public class LockedConfigServiceImpl implements LockedConfigService {

    private Logger logger=LoggerFactory.getLogger(getClass());

    @Autowired
    private LockedUserInfoMapper lockedUserInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminCustomizeMapper adminMapper;


    @Override
    public void saveWebConfig(LockedConfig.Config webConfig) {
        int oldMaxNum = LockedConfigManager.getInstance().getWebConfig().getMaxLoginErrorNum();

        LockedConfigManager.getInstance().setWebConfig(webConfig);

        if (webConfig.getMaxLoginErrorNum() > oldMaxNum) {
            //设置出错次数比原来的大，清除锁定用户记录
            LockedUserInfoExample example = new LockedUserInfoExample();
            example.or().andFrontEqualTo(1);
            lockedUserInfoMapper.deleteByExample(example);
        } else if (webConfig.getMaxLoginErrorNum() < oldMaxNum) {
            //设置出错次数比原来值小，遍历redis中，判断是否需要加入到锁定列表
            Set<String> frontKeys = getLoginErrorRedisKey(RedisConstants.PASSWORD_ERR_COUNT_ALL);
            for (String key : frontKeys) {
                String loginErrorNum = RedisUtils.get(key);
                Integer intNum = Integer.parseInt(loginErrorNum);
                if (intNum >= webConfig.getMaxLoginErrorNum() && intNum < oldMaxNum) {

                    String userId = key.substring(key.lastIndexOf("_") + 1);
                    User users = userMapper.selectByPrimaryKey(Integer.parseInt(userId));

                    LockedUserInfo lockUser = new LockedUserInfo();
                    lockUser.setFront(1);
                    lockUser.setUnlocked(0);
                    lockUser.setUserid(users.getUserId());
                    lockUser.setUsername(users.getUsername());
                    lockUser.setMobile(users.getMobile());
                    lockUser.setLockTime(new Date());
                    Integer loginLockTime = LockedConfigManager.getInstance().getAdminConfig().getLockLong();
                    lockUser.setUnlockTime(nowDateAddDate(loginLockTime));

                    lockedUserInfoMapper.insert(lockUser);

                    logger.info("因为配置出错次数设置比原来小了，前台锁定用户【{}】", users.getUsername());

                }
            }
        }
    }

    @Override
    public void saveAdminConfig(LockedConfig.Config adminConfig) {
        int oldMaxNum = LockedConfigManager.getInstance().getAdminConfig().getMaxLoginErrorNum();

        LockedConfigManager.getInstance().setAdminConfig(adminConfig);

        if (adminConfig.getMaxLoginErrorNum() > oldMaxNum) {
            LockedUserInfoExample example = new LockedUserInfoExample();
            example.or().andFrontEqualTo(0);
            lockedUserInfoMapper.deleteByExample(example);
        } else if (adminConfig.getMaxLoginErrorNum() < oldMaxNum) {
            //设置出错次数比原来值小，遍历redis中，判断是否需要加入到锁定列表
            Set<String> backKey = getLoginErrorRedisKey(RedisConstants.PASSWORD_ERR_COUNT_ADMIN);
            for (String key : backKey) {
                String loginErrorNum = RedisUtils.get(key);
                Integer intNum = Integer.parseInt(loginErrorNum);
                if (intNum >= adminConfig.getMaxLoginErrorNum() && intNum < oldMaxNum) {
                    String userName = key.substring(key.lastIndexOf("_") + 1);
                    List<Admin> lstUsers = adminMapper.selectByUsername(userName);
                    if (lstUsers.size() == 1) {
                        Admin users = lstUsers.get(0);
                        LockedUserInfo lockUser = new LockedUserInfo();
                        lockUser.setFront(0);
                        lockUser.setUnlocked(0);
                        lockUser.setUserid(users.getId());
                        lockUser.setUsername(users.getUsername());
                        lockUser.setMobile(users.getMobile());
                        lockUser.setLockTime(new Date());
                        Integer loginLockTime = LockedConfigManager.getInstance().getAdminConfig().getLockLong();
                        lockUser.setUnlockTime(nowDateAddDate(loginLockTime));
                        lockedUserInfoMapper.insert(lockUser);
                        logger.info("因为配置出错次数设置比原来小了，后台锁定用户【{}】", users.getUsername());
                    }
                }
            }
        }
    }

    private Date nowDateAddDate(Integer loginLockTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, loginLockTime);
        return calendar.getTime();
    }

    /**
     * @param redisKeyPrifix
     * @return
     */
    public Set<String> getLoginErrorRedisKey(String redisKeyPrifix) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = RedisUtils.getPool();
            jedis = pool.getResource();
            return jedis.keys(redisKeyPrifix + "*");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            // 返还到连接池
            RedisUtils.returnResource(pool, jedis);
        }
        throw new IllegalArgumentException("获取出错次数失败");
    }
}
