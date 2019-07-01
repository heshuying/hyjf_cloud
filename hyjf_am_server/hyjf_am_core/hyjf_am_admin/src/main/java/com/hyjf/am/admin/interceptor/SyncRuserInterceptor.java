package com.hyjf.am.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.SpreadsUserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * 同步用户信息的mybaitis 拦截器
 * 
 * @author dxj
 * @date 2018/07/26
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
//  , @Signature(type = Executor.class, method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
    })
@Component("syncRuserInterceptor")
public class SyncRuserInterceptor implements Interceptor {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CommonProducer commonProducer;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        Object paramObj = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(paramObj);
        String realSql = boundSql.getSql();
        String idMethod = mappedStatement.getId();
        String methodName = invocation.getMethod().getName();
        // 执行sql
        Object result = invocation.proceed();

        try {
            // 执行成功后发送消息同步，下面判断先ht_user_info 是有原因的
            if (StringUtils.containsIgnoreCase(realSql, "insert into ht_user_info")) {
                sendToMq(paramObj, methodName, "ht_user_info");
            } else if (StringUtils.containsIgnoreCase(realSql, "update ht_user_info")) {
                // updateByExample单独处理 update by wgx 2018/11/20
                if (paramObj instanceof Map) {
                    Object record = ((Map) paramObj).get("record");
                    if (record != null) {
                        try {
                            UserInfo user = (UserInfo) (record);
                            if (user.getUserId() == null) {
                                Object example = ((Map) paramObj).get("example");
                                if (example != null) {
                                    logger.info("【am-admin/{}/ht_user_info】user_id为null,取example中的user_id", methodName);
                                    UserInfoExample userExample = (UserInfoExample) example;
                                    for (UserInfoExample.Criterion criterion : userExample.getOredCriteria().get(0).getAllCriteria()) {
                                        if ("user_id =".equals(criterion.getCondition())) {
                                            if (criterion.getValue() != null) {
                                                user.setUserId((int) criterion.getValue());
                                                sendToMq(user, methodName, "ht_user_info");
                                                return result;
                                            }
                                        }
                                    }
                                    logger.error("【am-admin/{}/ht_user_info】example中user_id为null", methodName);
                                } else {
                                    logger.error("【am-admin/{}/ht_user_info】user_id为null", methodName);
                                }
                                return result;
                            }
                        } catch (Exception e) {
                            logger.error("【am-admin/{}/ht_user_info】发生异常！", methodName, e);
                        }
                        sendToMq(record, methodName, "ht_user_info");
                    } else {
                        logger.info("【am-admin/{}/ht_user_info】record为null", methodName);
                        sendToMq(paramObj, methodName, "ht_user_info");
                    }
                } else {
                    sendToMq(paramObj, methodName, "ht_user_info");
                }
            } else if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.auto.UserMapper.insert")) {
                sendToMq(paramObj, methodName, "ht_user");// 注册
            } else if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.auto.UserMapper.updateByPrimaryKey")) {
                sendToMq(paramObj, methodName, "up_ht_user");// 更新用户状态
            } else if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.auto.UserMapper.deleteByPrimaryKey")) {
                Map<String,Object> userMap = new HashMap<>();
                userMap.put("userId",paramObj);
                sendToMq(userMap, methodName, "del_ht_user");// 未开户用户销户
            } else if (StringUtils.containsIgnoreCase(realSql, "insert into ht_spreads_user")) {
                sendToMq(paramObj, methodName, "ht_spreads_user");
            } else if (StringUtils.containsIgnoreCase(realSql, "update ht_spreads_user")) {
                // updateByExample单独处理 update by wgx 2018/11/20
                if (paramObj instanceof Map) {
                    Object record = ((Map) paramObj).get("record");
                    if (record != null) {
                        try {
                            SpreadsUser spreadsUser = (SpreadsUser) (record);
                            if (spreadsUser.getUserId() == null) {
                                Object example = ((Map) paramObj).get("example");
                                if (example != null) {
                                    logger.info("【am-admin/{}/ht_spreads_user】record中user_id为null,取example中的user_id", methodName);
                                    SpreadsUserExample spreadsUserExample = (SpreadsUserExample) example;
                                    for (SpreadsUserExample.Criterion criterion : spreadsUserExample.getOredCriteria().get(0).getAllCriteria()) {
                                        if ("user_id =".equals(criterion.getCondition())) {
                                            if (criterion.getValue() != null) {
                                                spreadsUser.setUserId((int) criterion.getValue());
                                                sendToMq(spreadsUser, methodName, "ht_spreads_user");
                                                return result;
                                            }
                                        }
                                    }
                                    logger.error("【am-admin/{}/ht_spreads_user】example中user_id为null", methodName);
                                } else {
                                    logger.error("【am-admin/{}/ht_spreads_user】user_id为null", methodName);
                                }
                                return result;
                            }
                        } catch (Exception e) {
                            logger.error("【am-admin/{}/ht_spreads_user】发生异常！", methodName, e);
                        }
                        sendToMq(record, methodName, "ht_spreads_user");
                    } else {
                        logger.info("【am-admin/{}/ht_spreads_user】record为null", methodName);
                        sendToMq(paramObj, methodName, "ht_spreads_user");
                    }
                }
            } else {
                return result;
            }
        } catch (MQException e) {
            logger.error("发送用户信息同步失败....", e);
        }
        return result;
    }

    private void sendToMq(Object parameterObject, String methodName, String tagName) throws MQException {
        commonProducer.messageSend(new MessageContent(MQConstant.SYNC_RUSER_TOPIC, tagName, UUID.randomUUID().toString(), parameterObject));
        logger.info("【{}】发送用户信息同步,同步信息：{}", methodName + "/" + tagName, JSON.toJSON(parameterObject).toString());
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
//        this.properties = properties;
    }

}
