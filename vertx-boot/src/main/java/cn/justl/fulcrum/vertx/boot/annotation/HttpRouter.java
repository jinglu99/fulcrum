package cn.justl.fulcrum.vertx.boot.annotation;

import cn.justl.fulcrum.vertx.boot.web.WebConstants;
import io.vertx.core.http.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date : 2019/12/2
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpRouter {
    String value() default "/";
    HttpMethod method() default HttpMethod.GET;
    int port() default -1;
}
