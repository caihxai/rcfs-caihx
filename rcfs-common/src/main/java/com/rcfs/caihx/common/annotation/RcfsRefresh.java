package com.rcfs.caihx.common.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Scope("RcfsRefresh")
@Documented
public @interface RcfsRefresh {
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
}
