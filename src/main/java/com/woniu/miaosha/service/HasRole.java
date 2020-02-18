package com.woniu.miaosha.service;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface HasRole {
    String value();
}
