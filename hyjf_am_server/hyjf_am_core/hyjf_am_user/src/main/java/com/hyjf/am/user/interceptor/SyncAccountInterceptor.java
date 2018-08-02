package com.hyjf.am.user.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 账户余额mybatis同步拦截器
 * 在mybatis基础上增加一层拦截器,所有对数据的update(写操作),都会被拦截，
 * 此处单独处理ht_account的余额更新操作，同步信息到crm，其他操作拦截但不附加其他处理
 * @author zhangyk
 * @date 2018/8/2 15:18
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component("syncRuserInterceptor")
public class SyncAccountInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String  INTERCEPTOR_NAME  = "<<账户余额同步mybatis拦截器>>: ";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        Object paramObj = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(paramObj);

        String realSql = boundSql.getSql();
        String methodId = mappedStatement.getId();
        String methodName = invocation.getMethod().getName();

        // 执行sql
        Object result = invocation.proceed();
        // try catch 防止因为拦截器业务处理，影响原来的mybatis的基础功能。
        String jsonStr = "";
        try {
            // 自动生成的Mapper里面有四种方式update,每个方法都是以update开头, 此处用关键词匹配到updateXXXX,
            if(StringUtils.containsIgnoreCase(methodId,"com.hyjf.am.trade.dao.mapper.auto.AccountMapper.update")){

                logger.info("====="+INTERCEPTOR_NAME +"拦截到账户更新请求,准备发送账户信息到mq,进行crm账户余额同步处理=====");
                jsonStr = JSON.toJSONString(boundSql.getParameterObject()).toLowerCase();
                // TODO: 2018/8/2 发送自产自销mq
                logger.info("====="+INTERCEPTOR_NAME +"发送账户信息[{}]到mq [成功]=====",jsonStr);
            }
        }catch (Exception e){
            logger.error("====="+INTERCEPTOR_NAME +"发送账户信息[{}]到mq [失败!!]=====",jsonStr);
            logger.error("====="+INTERCEPTOR_NAME +"发送mq异常, errorStack:" + e);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
