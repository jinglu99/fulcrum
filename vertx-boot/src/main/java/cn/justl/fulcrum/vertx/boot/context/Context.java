package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootInitializeException;
import io.vertx.core.Future;
import java.util.List;
import java.util.Set;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface Context extends VertxContext {

    /**
     * Initialize Vertx-Boot.
     * @return
     */
    Future<Void> init();

    /**
     * Close current Vertx-Boot.
     * @return
     */
    Future<Void> close();

    /**
     * Set {@link BeanClassLoader} for loading beanClasses.
     * @param loader
     */
    void setBeanClassLoader(BeanClassLoader loader);

    /**
     * Get the {@link BeanClassLoader}.
     * @return
     */
    BeanClassLoader getBeanClassLoader();

    /**
     * List the bean classes that current context loaded.
     * @return
     */
    Set<Class> listBeanClasses();

    /**
     * List the {@link BeanDefinition} that current context loaded.
     * @return
     */
    List<BeanDefinition> listBeanDefinitions();

    /**
     * List the {@link BeanHolder} that current context contained.
     * @return
     */
    List<BeanHolder> listBeanHolders();

    /**
     * Get {@link BeanDefinition} by the given id.
     * @param id
     * @return
     */
    BeanDefinition getBeanDefinition(String id);

    /**
     * Get {@link BeanHolder} by the given id.
     * @param id
     * @return
     */
    BeanHolder getBeanHolder(String id);

}
