package cn.justl.fulcrum.vertx.boot.context.loader;

import cn.justl.fulcrum.vertx.boot.annotation.BootBean;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import cn.justl.fulcrum.vertx.boot.annotation.WebVerticle;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.AnnotationUtils;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle
@WebVerticle
public class Main {

    @Test
    public void test() {

        AnnotationHelper.getAnnotations(Main.class)
            .forEach(x-> System.out.println(x.annotationType().getSimpleName()));
    }

    List<Annotation> getAnnotations(Class clazz) {
        List<Annotation> annotations = new ArrayList<>();
        List<Annotation> curAnnotations = Stream.of(clazz.getAnnotations())
            .filter(x->!isJavaAnnotation(x))
            .collect(Collectors.toList());
        if (curAnnotations == null || curAnnotations.size() == 0) return annotations;
        for (Annotation annotation : curAnnotations) {
            annotations.addAll(getAnnotations(annotation.annotationType()));
        }

        annotations.addAll(curAnnotations);
        return annotations;
    }

    boolean isJavaAnnotation(Annotation annotation) {
        if (annotation instanceof Documented) return true;
        if (annotation instanceof Target) return true;
        if (annotation instanceof Inherited) return true;
        if (annotation instanceof Retention) return true;
        else return false;
    }

}
