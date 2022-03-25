package com.unsafe_sparkdata.annotation;/**
 * @author Evgeny Borisov
 */

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface Transient {
}
