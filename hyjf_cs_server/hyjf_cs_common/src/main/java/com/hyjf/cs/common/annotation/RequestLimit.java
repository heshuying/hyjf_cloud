/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.common.annotation;

import java.lang.annotation.*;

/**
 * @author dxj
 * @version RequestLimit.java, v0.1 2018年7月20日 下午5:15:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestLimit {

	/**
	 * 多长时间不允许重复，默认三秒
	 */
	int seconds() default 3;
}
