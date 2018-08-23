package com.hyjf.am.admin.config.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hyjf.common.constants.CommonConstant;

@Aspect
@Component
public class DataSourceAop  implements Ordered{
	
    private static final String PACKAGE_REFIX = "com.hyjf.am.";

    @Pointcut("execution(* com.hyjf..*Service.*(..))")
    public void serviceAspect() {
    }

    @Before("serviceAspect()")
    public void switchDataSource(JoinPoint point) {
        // 读操作切换读数据源
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            return;
        }

        // 写操作根据包切换不同的写数据源
        String packageStr = point.getTarget().toString();
        if (packageStr.startsWith(PACKAGE_REFIX + "trade")) {
            DynamicDataSourceContextHolder.useMasterTradeDataSource();
        } else if (packageStr.startsWith(PACKAGE_REFIX + "user")) {
            DynamicDataSourceContextHolder.useMasterUserDataSource();
        }else if (packageStr.startsWith(PACKAGE_REFIX + "config")) {
            DynamicDataSourceContextHolder.useMasterConfigDataSource();
        }else if (packageStr.startsWith(PACKAGE_REFIX + "market")) {
            DynamicDataSourceContextHolder.useMasterMarketDataSource();
        }
        return;
    }

    @After("serviceAspect())")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

    @AfterThrowing("serviceAspect())")
    public void restoreDataSourceException(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

    private Boolean isQueryMethod(String methodName) {
        for (String prefix : CommonConstant.DATASOURCE_QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

	@Override
	public int getOrder() {
		return CommonConstant.DATASOURCE_AOP_DS;
	}
}
