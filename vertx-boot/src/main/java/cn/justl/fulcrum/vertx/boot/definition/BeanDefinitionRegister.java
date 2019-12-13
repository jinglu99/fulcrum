package cn.justl.fulcrum.vertx.boot.definition;

import cn.justl.fulcrum.vertx.boot.context.BeanDefinitionParser;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import java.util.List;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface BeanDefinitionRegister extends BeanDefinitionParser {

    void setBeanClassLoader(BeanClassLoader beanClassLoader);

    BeanClassLoader getBeanClassLoader();

    /**
     * register a {@link BeanDefinition} in Context.
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);

    /**
     * unregister a {@link BeanDefinition} by id.
     * @param id
     */
    void unregisterBeanDefinition(String id);

    /**
     * Get a {@link BeanDefinition} by given ID.
     */
    BeanDefinition getBeanDefinition(String id);

    /**
     * List all {@link BeanDefinition} registered in Context.
     */
    List<BeanDefinition> listBeanDefinitions();
}
