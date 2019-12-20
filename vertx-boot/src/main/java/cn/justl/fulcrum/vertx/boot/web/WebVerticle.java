package cn.justl.fulcrum.vertx.boot.web;

import cn.justl.fulcrum.vertx.boot.annotation.BootBean;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date : 2019/12/2
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@BootBean()
public @interface WebVerticle {
    String value() default "";
}
