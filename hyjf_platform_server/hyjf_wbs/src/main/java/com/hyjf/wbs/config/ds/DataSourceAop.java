package com.hyjf.wbs.config.ds;

import com.hyjf.common.constants.CommonConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop  implements Ordered{
	
    private static final String PACKAGE_REFIX = "com.hyjf.am.";

    @Pointcut("execution(* com.hyjf..*Service.*(..))")
    public void serviceAspect() {
    }

    @Before("serviceAspect()")
    public void switchDataSource(JoinPoint point) {
        // 获取包名
        String packageStr = point.getTarget().toString();
        // 读操作切换读数据源
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            if (packageStr.startsWith(PACKAGE_REFIX + "trade")) {
                DynamicDataSourceContextHolder.useSlaveTradeDataSource();
            } else if (packageStr.startsWith(PACKAGE_REFIX + "user")) {
                DynamicDataSourceContextHolder.useSlaveUserDataSource();
            } else if (packageStr.startsWith(PACKAGE_REFIX + "config")) {
                DynamicDataSourceContextHolder.useSlaveConfigDataSource();
            } else if (packageStr.startsWith(PACKAGE_REFIX + "market")) {
                DynamicDataSourceContextHolder.useSlaveMarketDataSource();
            }
            return;
//            DynamicDataSourceContextHolder.useSlaveDataSource();
//            return;
        }

        // 写操作根据包切换不同的写数据源
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
