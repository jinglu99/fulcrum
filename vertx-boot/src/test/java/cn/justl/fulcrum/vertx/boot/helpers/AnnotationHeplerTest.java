package cn.justl.fulcrum.vertx.boot.helpers;

import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for AnnotationHelper")
public class AnnotationHeplerTest {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface A1 {

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface A2 {

    }


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @A1
    private @interface B1 {

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @A1
    @A2
    private @interface B2 {

    }


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @B2
    @A1
    private @interface C1 {

    }

    @Test
    public void getClassAnnotationsTest() {
        @C1
        class test {

        }

        Set<Annotation> annotations = AnnotationHelper.getAnnotations(test.class);
        assertEquals(4, annotations.size());
    }


    @Test
    public void hasAnnotationsTest() {
        @C1
        class test {

        }
        assertTrue(AnnotationHelper.hasAnnotation(test.class, C1.class));
        assertTrue(AnnotationHelper.hasAnnotation(test.class, B2.class));
        assertTrue(AnnotationHelper.hasAnnotation(test.class, A1.class));
        assertTrue(AnnotationHelper.hasAnnotation(test.class, A2.class));
        assertFalse(AnnotationHelper.hasAnnotation(test.class, B1.class));
    }
}
