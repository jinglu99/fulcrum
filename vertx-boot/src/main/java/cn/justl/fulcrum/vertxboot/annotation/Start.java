package cn.justl.fulcrum.vertxboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Start {
}
