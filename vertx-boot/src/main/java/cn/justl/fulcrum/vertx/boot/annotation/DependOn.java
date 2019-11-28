package cn.justl.fulcrum.vertx.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date : 2019/11/27
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DependOn {
    String[] value() default {};
}
