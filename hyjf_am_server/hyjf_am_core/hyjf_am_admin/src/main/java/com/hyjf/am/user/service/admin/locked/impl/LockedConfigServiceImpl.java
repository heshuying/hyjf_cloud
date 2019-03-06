/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.locked.impl;

import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.config.dao.mapper.customize.AdminCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.user.controller.admin.locked.LockedConfigManager;
import com.hyjf.am.user.dao.mapper.auto.LockedUserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.dao.model.auto.LockedUserInfoExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.admin.locked.LockedConfigService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

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

    @Autowired
    private RestTemplate restTemplate;


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
     *
     * @param userName 用户名，手机号
     * @param loginPassword 登录密码
     * @return
     */
    public Map<String, String> insertErrorPassword(String userName, String loginPassword){
            Map<String, String> r=new HashMap<>();
            UserExample userExample=new UserExample();
            userExample.createCriteria().andUsernameEqualTo(userName);
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size()<1){
                return r;
            }
            //1.获取该用户密码错误次数
            String passwordErrorNum=RedisUtils.get(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + users.get(0).getUsername());
            //2.获取用户允许输入的最大错误次数
            Integer maxLoginErrorNum=LockedConfigManager.getInstance().getAdminConfig().getMaxLoginErrorNum();
            //3.redis配置的超限有效时间
            long retTime  = RedisUtils.ttl(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + users.get(0).getUsername());
            //判断密码错误次数是否超限
            if (!StringUtils.isEmpty(passwordErrorNum)&&Integer.parseInt(passwordErrorNum)>maxLoginErrorNum) {
//			CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_TOO_MAX,DateUtils.SToHMSStr(retTime));
                r.put("info","您的登录失败次数超限，请"+retTime+"之后重试!");
            }
            //查询到的密码
            String passwordDb = users.get(0).getPassword();
            // 页面传来的密码
            String password = MD5.toMD5Code(loginPassword);
            logger.info("passwordDB:[{}],password:[{}],相等:[{}]",passwordDb,password,password.equals(passwordDb));
            if (!password.equals(passwordDb)) {
                long value = this.insertPassWordCount(RedisConstants.PASSWORD_ERR_COUNT_ADMIN+ users.get(0).getUsername());//以用户手机号为key
                for (int i=1;i<4;i++){
                    if (maxLoginErrorNum-value == i){
//					CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_MAX,i);
                        r.put("info","登录失败,您的登录机会还剩"+i+"次!");
                    }
                }
                if (maxLoginErrorNum - value == 0){
                    Integer	loginLockTime=LockedConfigManager.getInstance().getAdminConfig().getLockLong();//获取Redis配置的登录错误次数有效时间
                    // 同步输错密码超限锁定用户信息接口
                    String  requestUrl= "http://am-user/lockeduser/insertLockedUser";
                    LockedUserInfo lockedUserInfoVO=new LockedUserInfo();
                    lockedUserInfoVO.setUserid(users.get(0).getUserId());
                    lockedUserInfoVO.setUsername(users.get(0).getUsername());
                    lockedUserInfoVO.setMobile(users.get(0).getMobile());
                    lockedUserInfoVO.setLockTime(new Date());
                    lockedUserInfoVO.setUnlockTime(DateUtils.nowDateAddDate(loginLockTime));
                    lockedUserInfoVO.setFront(1);
                    int insert=lockedUserInfoMapper.insert(lockedUserInfoVO);
                }
            }
            return  r;
    }
    /**
     * redis增加
     * @param key
     */
    private long insertPassWordCount(String key) {
        long retValue  = RedisUtils.incr(key);
//		RedisUtils.expire(key,RedisUtils.getRemainMiao());//给key设置过期时间
        Integer	loginErrorConfigManager=LockedConfigManager.getInstance().getAdminConfig().getLockLong();
        RedisUtils.expire(key,loginErrorConfigManager*3600);//给key设置过期时间
        return retValue;
    }
    /**
     * @param redisKeyPrifix
     * @return
     */
    public Set<String> getLoginErrorRedisKey(String redisKeyPrifix) {
        JedisPool poolNew = null;
        Jedis jedis = null;
        try {
        	poolNew = RedisUtils.getPool();
            jedis = poolNew.getResource();
            return jedis.keys(redisKeyPrifix + "*");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            // 释放redis对象
        	poolNew.close();
            // 返还到连接池
            RedisUtils.returnResource(poolNew, jedis);
        }
        throw new IllegalArgumentException("获取出错次数失败");
    }
}
