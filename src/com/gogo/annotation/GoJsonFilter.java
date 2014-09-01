package com.gogo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GoJsonFilter {
	Class<?> mixin() default Object.class;
	Class<?> target() default Object.class;
}
