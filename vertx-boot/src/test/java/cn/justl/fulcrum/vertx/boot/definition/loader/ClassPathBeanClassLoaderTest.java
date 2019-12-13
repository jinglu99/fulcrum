package cn.justl.fulcrum.vertx.boot.definition.loader;

import cn.justl.fulcrum.vertx.boot.definition.loader.beans.BootBean1;
import cn.justl.fulcrum.vertx.boot.definition.loader.beans.TestBootBean1;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for ClassPathBeanDefinitionLoader")
public class ClassPathBeanClassLoaderTest {



    @Test
    public void loadBeanDefinitionsTest() throws DefinitionLoadException {
        String path = ClassPathBeanClassLoaderTest.class.getPackage().getName() + ".beans";
        BeanClassLoader loader = new ClassPathBeanClassLoader(Arrays.asList(path));

        Set<Class> classSet = loader.loadBeanClasses();

        assertEquals(2, classSet.size());
        assertTrue(classSet.contains(BootBean1.class));
        assertTrue(classSet.contains(TestBootBean1.class));
    }



}
