package com.hyjf.am.user.interceptor;

import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.producer.AmUserProducer;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

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
        String methodName = invocation.getMethod().getName();
        
        // 执行sql
        Object result = invocation.proceed();

        try {
//            jObj.put("test", "johsndfasdfsd");
            
            // 执行成功后发送消息同步，下面判断先ht_user_info 是有原因的
            if(StringUtils.containsIgnoreCase(realSql, "insert into ht_user_info") || StringUtils.containsIgnoreCase(realSql, "update ht_user_info")) {
                sendToMq(boundSql, methodName, "ht_user_info");
                
                // 注册一般，更新用户表基本不需要同步rUser
            }else if(StringUtils.containsIgnoreCase(realSql, "insert into ht_user")) {
                sendToMq(boundSql, methodName, "ht_user");
                
            }else if(StringUtils.containsIgnoreCase(realSql, "insert into ht_spreads_user") || StringUtils.containsIgnoreCase(realSql, "update ht_spreads_user")) {
                sendToMq(boundSql, methodName, "ht_spreads_user");
                
            }else {
                return result;
            }
            
            
        } catch (MQException e) {
            logger.error("发送用户信息同步失败....", e);
        }
        
        return result;

    }

    private void sendToMq(BoundSql boundSql, String methodName, String tagName) throws MQException {
//        JSONObject jObj = JSONObject.parseObject(JSON.toJSONString(boundSql.getParameterObject()).toLowerCase());
        amUserProducer.messageSend(new MessageContent(MQConstant.SYNC_RUSER_TOPIC, tagName, UUID.randomUUID().toString(), JSON.toJSONBytes(boundSql.getParameterObject())));
        logger.info(methodName+" 发送用户信息同步 "+tagName);
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
