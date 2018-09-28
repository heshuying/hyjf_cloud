/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.interceptor;

import java.lang.annotation.*;

/**
 * @author DongZeShan
 * @version AuthorityAnnotation.java, v0.1 2018年6月22日 下午12:44:02
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityAnnotation {
	 String key();
	 String value();
}
