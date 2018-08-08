package com.hyjf.cs.user.interceptor;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD,ElementType.TYPE})  
@Documented
@Order(1)
public @interface SignValidate {
    String value() default "";
}
