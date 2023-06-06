package com.st1020.shuttermall.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    @AliasFor("admin") boolean value() default false;

    @AliasFor("value") boolean admin() default false;
}