package cn.justl.fulcrum.vertx.boot.definition.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import cn.justl.fulcrum.vertx.boot.definition.loader.beans.BootBean1;
import cn.justl.fulcrum.vertx.boot.definition.loader.beans.TestBootBean1;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for VertxScanBeanDefinitionLoader")
@VertxScan
public class VertxScanBeanDefinitionLoaderTest {
    @Test
    public void vertxScanTest() throws DefinitionLoadException {
        BeanClassLoader loader = new VertxScanBeanClassLoader(this.getClass());

        Set<Class> classSet = loader.loadBeanClasses();

        assertEquals(2, classSet.size());
        assertTrue(classSet.contains(BootBean1.class));
        assertTrue(classSet.contains(TestBootBean1.class));
    }
}
