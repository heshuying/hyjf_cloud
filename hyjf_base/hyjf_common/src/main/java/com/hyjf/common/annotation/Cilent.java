package com.hyjf.common.annotation;

/**
 * @author xiasq
 * @version Cilent, v0.1 2018/7/6 11:22
 */

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface Cilent {
	/**
	 * The value may indicate a suggestion for a logical component name, to be
	 * turned into a Spring bean in case of an autodetected component.
	 * 
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Service.class)
	String value() default "";
}
