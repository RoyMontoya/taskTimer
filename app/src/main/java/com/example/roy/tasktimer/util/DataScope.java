package com.example.roy.tasktimer.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Roy on 8/2/17.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
}
