package com.hyjf.am.user.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.producer.AmUserProducer;
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
    private AmUserProducer amUserProducer;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        Object paramObj = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(paramObj);
        
//        String sqlId = mappedStatement.getId();
//        String namespace = sqlId.substring(0, sqlId.indexOf('.'));
//        Executor exe = (Executor)invocation.getTarget();
        String realSql = boundSql.getSql();
        String idMethod = mappedStatement.getId();
        String methodName = invocation.getMethod().getName();
        
        // 执行sql
        Object result = invocation.proceed();

        try {
            // 执行成功后发送消息同步，下面判断先ht_user_info 是有原因的
            // crm客户入离职更新名下客户单独处理 update by wgx 2018/11/20
            if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.customize.UserLeaveCustomizeMapper.updateSpreadAttribute")) {
                Map<String, Object> parameterMap = Maps.newHashMap();
                parameterMap.put("attribute", "0");
                parameterMap.put("referrer", ((Map)paramObj).get("referrer"));
                sendToMq(parameterMap, methodName, "ht_user_info_referrer");
            } else if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.customize.UserEntryCustomizeMapper.updateSpreadAttribute")) {
                Map<String, Object> parameterMap = Maps.newHashMap();
                parameterMap.put("attribute", "1");
                parameterMap.put("referrer", ((Map)paramObj).get("referrer"));
                sendToMq(parameterMap, methodName, "ht_user_info_referrer");
            } else if (StringUtils.containsIgnoreCase(realSql, "insert into ht_user_info")) {
                sendToMq(paramObj, methodName, "ht_user_info");
            } else if (StringUtils.containsIgnoreCase(realSql, "update ht_user_info")) {
                // updateByExample单独处理 update by wgx 2018/11/20
                if (paramObj instanceof Map) {
                    Object record = ((Map) paramObj).get("record");
                    if (record != null) {
                        sendToMq(record, methodName, "ht_user_info");
                    } else {
                        logger.info("【am-user/{}/ht_user_info】record为null", methodName);
                        sendToMq(paramObj, methodName, "ht_user_info");
                    }
                } else {
                    sendToMq(paramObj, methodName, "ht_user_info");
                }
                // 注册一般，更新用户表基本不需要同步rUser
            } else if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.auto.UserMapper.insert")) {
                sendToMq(paramObj, methodName, "ht_user");

                // 更新用户状态
            } else if (StringUtils.containsIgnoreCase(idMethod, "com.hyjf.am.user.dao.mapper.auto.UserMapper.updateByPrimaryKey")) {

                sendToMq(paramObj, methodName, "up_ht_user");

            } else if (StringUtils.containsIgnoreCase(realSql, "insert into ht_spreads_user")) {
                sendToMq(paramObj, methodName, "ht_spreads_user");
            } else if (StringUtils.containsIgnoreCase(realSql, "update ht_spreads_user")) {
                // updateByExample单独处理 update by wgx 2018/11/20
                if (paramObj instanceof Map) {
                    Object record = ((Map) paramObj).get("record");
                    if (record != null) {
                        sendToMq(record, methodName, "ht_spreads_user");
                    } else {
                        logger.info("【am-user/{}/ht_spreads_user】record为null", methodName);
                        sendToMq(paramObj, methodName, "ht_spreads_user");
                    }
                } else {
                    sendToMq(paramObj, methodName, "ht_spreads_user");
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
        amUserProducer.messageSend(new MessageContent(MQConstant.SYNC_RUSER_TOPIC, tagName, UUID.randomUUID().toString(), JSON.toJSONBytes(parameterObject)));
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
