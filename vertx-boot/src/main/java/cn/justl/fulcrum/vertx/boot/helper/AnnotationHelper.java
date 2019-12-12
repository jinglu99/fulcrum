package cn.justl.fulcrum.vertx.boot.helper;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Native;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AnnotationHelper {

    public static Set<Annotation> getAnnotations(Class clazz) {
        Set<Annotation> annotations = new HashSet<>();
        Set<Annotation> curAnnotations = Stream.of(clazz.getAnnotations())
            .filter(x->!isJavaAnnotation(x))
            .collect(Collectors.toSet());
        if (curAnnotations == null || curAnnotations.size() == 0) return annotations;
        for (Annotation annotation : curAnnotations) {
            annotations.addAll(getAnnotations(annotation.annotationType()));
        }

        annotations.addAll(curAnnotations);
        return annotations;
    }

    public static Annotation getAnnotation(Class clazz, Class<? extends Annotation> annotationClass) {
        return getAnnotations(clazz)
            .stream()
            .filter(x->annotationClass.isAssignableFrom(x.getClass()))
            .findFirst()
            .orElse(null);
    }

    public static boolean hasAnnotation(Class clazz, Class<? extends Annotation> annotationClass) {
        return getAnnotation(clazz, annotationClass) != null;
    }

    /**
     * Judge an {@link} Annotation Object is Java Annotation(
     * {@link Documented}, {@link Target}, {@link Inherited}
     * {@link Retention}, {@link Native}, {@link Repeatable}
     * )
     * @param annotation
     * @return
     */
    public static boolean isJavaAnnotation(Annotation annotation) {
        if (annotation instanceof Documented) {
            return true;
        }
        if (annotation instanceof Target) {
            return true;
        }
        if (annotation instanceof Inherited) {
            return true;
        }
        if (annotation instanceof Retention) {
            return true;
        }
        if (annotation instanceof Native) {
            return true;
        }
        if (annotation instanceof Repeatable) {
            return true;
        }
        return false;
    }

}
