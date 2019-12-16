package cn.justl.fulcrum.vertx.boot.definition.loader;

import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import java.util.Set;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * A BeanDefinitionLoader define the {@link BeanDefinition} loading strategy
 * for {@link cn.justl.fulcrum.vertx.boot.context.Context}
 */
public interface BeanClassLoader {

    /**
     * Load definitions.
     * @return
     */
    Set<Class> loadBeanClasses() throws DefinitionLoadException;

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
