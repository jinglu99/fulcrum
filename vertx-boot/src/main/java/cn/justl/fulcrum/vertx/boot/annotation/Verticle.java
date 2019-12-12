package cn.justl.fulcrum.vertx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@BootBean
public @interface Verticle {
    String value() default "";
}
