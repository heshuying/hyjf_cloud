package com.hyjf.am.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.common.constants.MQConstant;
import org.apache.commons.lang3.StringUtils;
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
 * 账户余额mybatis同步拦截器
 * 在mybatis基础上增加一层拦截器,所有对数据的update(写操作),都会被拦截，
 * 此处单独处理ht_account的余额更新操作，同步信息到crm，其他操作拦截但不附加其他处理
 *
 * @author zhangyk
 * @date 2018/8/2 15:18
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component("syncAccountInterceptor")
public class SyncAccountInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String INTERCEPTOR_NAME = "<<账户余额同步mybatis拦截器>>: ";

    @Autowired
    private CommonProducer commonProducer;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object paramObj = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(paramObj);

        String realSql = boundSql.getSql();
        String methodName = invocation.getMethod().getName();

        // 执行sql
        Object result = invocation.proceed();
        // try catch 防止因为拦截器业务处理，影响原来的mybatis的基础功能。
        String jsonStr = "";
        try {
            // 拦截sql中包含"update","ht_account",不包含"ht_account_"的目标,ht_account_xxxx是账户的其他信息表,不在此次拦截范围之内
            if (StringUtils.containsIgnoreCase(realSql, "update") && StringUtils.containsIgnoreCase(realSql, "ht_account") && !StringUtils.containsIgnoreCase(realSql, "ht_account_")) {
                logger.info("=====" + INTERCEPTOR_NAME + "拦截到账户更新请求,准备发送账户信息到mq,进行crm账户余额同步处理=====");
                logger.info("====="+INTERCEPTOR_NAME+" paramObj=[{}] =====", JSON.toJSON(paramObj));
                if (paramObj instanceof Map){
                    Object record = ((Map) paramObj).get("record");
                    if (record != null){
                        Account account = (Account) record;
                        if (account !=null && account.getUserId() != null){
                            jsonStr = JSON.toJSONString(account);
                        } else {
                            logger.error("=====" + INTERCEPTOR_NAME + "存在使用原生方法更新account表,暂不处理(am-admin)======");
                            return result;
                        }
                    }
                } else {
                    jsonStr = JSON.toJSONString(boundSql.getParameterObject());
                }
                // 延迟等级为9 (5分钟)
                commonProducer.messageSendDelay(new MessageContent(MQConstant.SYNC_ACCOUNT_TOPIC, UUID.randomUUID().toString(), jsonStr) , 9);
                logger.info("=====" + INTERCEPTOR_NAME + "发送账户信息[{}]到mq [成功]=====", jsonStr);
            }
        } catch (Exception e) {
            logger.error("=====" + INTERCEPTOR_NAME + "发送账户信息[{}]到mq [失败!!]=====", jsonStr);
            logger.error("=====" + INTERCEPTOR_NAME + "发送mq异常, errorStack:" + e);
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
