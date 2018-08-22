package com.hyjf.am.config.config.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hyjf.common.constants.CommonConstant;

@Aspect
@Component
public class DataSourceAop implements Ordered{
	
//	private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);
    
//    @Pointcut("execution( * com.hyjf.am.user.service..*.*(..))")
    @Pointcut("execution(* com.hyjf..*Service.*(..))")
    public void serviceAspect() {
    }

    @Before("serviceAspect()")
    public void switchDataSource(JoinPoint point) {
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
        }
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
