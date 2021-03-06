package io.github.vipcxj.beanknife.runtime.annotations.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratedView {
    Class<?> targetClass();
    Class<?> configClass();
}
