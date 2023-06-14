package com.st1020.shuttermall.annotation;

import com.st1020.shuttermall.enums.UserType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    UserType value() default UserType.USER;
}