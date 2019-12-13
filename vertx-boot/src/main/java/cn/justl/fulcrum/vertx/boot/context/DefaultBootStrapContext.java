package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.annotation.handler.AnnotationHandler;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinitionRegister;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootInitializeException;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;
import io.vertx.core.Vertx;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBootStrapContext extends AbstractBootStrapContext {

    private Vertx vertx = null;

    private BeanDefinitionRegister definitionRegister = new DefaultBootStrapContext();

    private final ServiceLoader<AnnotationHandler> annotationHandlers = ServiceLoader
        .load(AnnotationHandler.class);

    private Set<Class> bootBeanClasses;

    @Override
    public void init() throws VertxBootInitializeException {
        try {

            loadBeanDefinitions();

            createBeans();

            initializeBeans();

        } catch (VertxBootException e) {
            throw e;
        }


    }

    @Override
    public void close() throws VertxBootException {

    }

    @Override
    public void closeBeans() {

    }

    @Override
    public void createBeans() {

    }

    @Override
    public void parseBeanDefinitions() throws BeanDefinitionParseException {
        for (Class clazz : bootBeanClasses) {
            Iterator iterator = annotationHandlers.iterator();
            while (iterator.hasNext()) {
                AnnotationHandler handler = (AnnotationHandler) iterator.next();
                if (handler.isTargetBean(clazz)) {
                    registerBeanDefinition(handler.parseBeanDefinition(this, clazz));
                    break;
                }
            }
        }
    }

    @Override
    public Vertx getVertx() {
        return vertx;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    public void loadBeanDefinitions() throws VertxBootInitializeException {
        BeanClassLoader loader = getBeanClassLoader();

        try {
            this.bootBeanClasses = loader.loadBeanClasses();

            parseBeanDefinitions();

        } catch (DefinitionLoadException e) {
            throw new VertxBootInitializeException("Failed to load bean definitions!", e);
        } catch (BeanDefinitionParseException e) {
            throw new VertxBootInitializeException("Failed to parse bean definitions!", e);
        }
    }

    @Override
    public void setBeanClassLoader(BeanClassLoader beanClassLoader) {
        definitionRegister.setBeanClassLoader(beanClassLoader);
    }

    @Override
    public BeanClassLoader getBeanClassLoader() {
        return definitionRegister.getBeanClassLoader();
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        definitionRegister.registerBeanDefinition(beanDefinition);
    }

    @Override
    public void unregisterBeanDefinition(String id) {
        definitionRegister.unregisterBeanDefinition(id);
    }

    @Override
    public BeanDefinition getBeanDefinition(String id) {
        return definitionRegister.getBeanDefinition(id);
    }

    @Override
    public List<BeanDefinition> listBeanDefinitions() {
        return definitionRegister.listBeanDefinitions();
    }


    @Override
    public void initializeBeans() {

    }
}
