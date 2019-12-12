package cn.justl.fulcrum.vertx.boot.context.loader;

import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import java.util.Set;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * A BeanDefinitionLoader define the {@link BeanDefinition} loading strategy
 * for {@link BootStrapContext}
 */
public interface BeanClassLoader {

    /**
     * Load definitions.
     * @return
     */
    Set<Class> loadBeanClasses();

    /**
     * Get parent BeanDefinitionLoader.
     * @return
     */
    BeanClassLoader getParentLoader();

    /**
     * Set parent BeanDefinitionLoader
     */
    void setParentLoader(BeanClassLoader loader);
}
