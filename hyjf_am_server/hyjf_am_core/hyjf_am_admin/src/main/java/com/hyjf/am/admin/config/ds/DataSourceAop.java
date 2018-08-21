package com.hyjf.am.admin.config.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {
	
//	private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    private static final String PACKAGE_REFIX = "com.hyjf.am.";
    private final String[] QUERY_PREFIX = {"select","query","count","search","get","find","check"};

//    @Pointcut("execution( * com.hyjf.am.user.service..*.*(..))")
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
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
