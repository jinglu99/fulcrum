package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.annotation.handler.BootBeanAnnotationHandler;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCreationException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBootStrapContext extends AbstractBootStrapContext {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBootStrapContext.class);

    private Vertx vertx = null;

    private final BootBeanAnnotationHandler bootBeanAnnotationHandler = new BootBeanAnnotationHandler();

    private BeanClassLoader beanClassLoader = null;

    private Set<Class> bootBeanClasses;

    private Map<String, BeanDefinition> definitionMap = new HashMap<>();
    private Map<String, BeanHolder> beanHolderMap = new HashMap<>();


    @Override
    public Future<Void> init() {
        return loadBeanDefinitions()
            .compose(aVoid -> parseBeanDefinitions())
            .compose(aVoid -> instantiateBeans())
            .compose(aVoid -> initializeBeans());
    }

    /**
     * Load bean classes by the strategies that defined in {@link DefaultBootStrapContext#beanClassLoader}.
     */
    public Future<Void> loadBeanDefinitions() {
        return Future.future(promise -> {
                try {
                    logger.info("Start to load beanDefinitions!");

                    loadBeanClasses();

                    logger.info("Finish to load beanDefinitions!");
                    promise.complete();
                } catch (DefinitionLoadException e) {
                    promise.fail(e);
                } catch (Exception e) {
                    logger.error("Failed to load bean definitions!", e);
                    promise
                        .fail(new DefinitionLoadException("Failed to load bean definitions!", e));
                }
            }
        );
    }


    private void loadBeanClasses() throws DefinitionLoadException {
        logger.info("Start to load BeanClasses...");

        if (beanClassLoader == null) {
            logger.error("No BeanClassLoader set!");
            throw new DefinitionLoadException("No BeanClassLoader set!");
        }

        bootBeanClasses = beanClassLoader.loadBeanClasses();

        logger.info("Finished to load BeanClasses!");
    }


    private Future<Void> parseBeanDefinitions() {
        return Future.future(promise -> {
            try {
                logger.info("Start to parse BeanDefinitions...");

                for (Class clazz : bootBeanClasses) {
                    if (bootBeanAnnotationHandler.isTargetBean(clazz)) {
                        BeanDefinition definition = bootBeanAnnotationHandler
                            .parseBeanDefinition(this, clazz);
                        definitionMap.put(definition.getId(), definition);
                    } else {
                        logger.warn("Class {} is not a BootBean!", clazz.getSimpleName());
                    }
                }
                logger.info("Finished to parse BeanDefinitions");
                promise.complete();
            } catch (BeanDefinitionParseException e) {
                promise.fail(e);
            } catch (Exception e) {
                logger.error("Failed to parse BeanDefinition", e);
                promise
                    .fail(new BeanDefinitionParseException("Failed to parse BeanDefinition!", e));
            }
        });
    }


    @Override
    public void setBeanClassLoader(BeanClassLoader loader) {
        this.beanClassLoader = loader;
    }

    @Override
    public BeanClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public Set<Class> listBeanClasses() {
        return new HashSet<>(bootBeanClasses);
    }

    private Future<Void> instantiateBeans() {
        return Future.future(promise -> {
            try {
                logger.info("Start to instantiated beans...");
                for (BeanDefinition definition : listBeanDefinitions()) {
                    beanHolderMap.put(definition.getId(),
                        bootBeanAnnotationHandler.creatBean(this, definition));
                }
                logger.info("Finished to instantiated beans!");
                promise.complete();
            } catch (BeanCreationException e) {
                promise.fail(e);
            } catch (Exception e) {
                logger.error("Failed to instantiated beans", e);
                promise.fail(new BeanCreationException("Failed to instantiated beans!", e));
            }
        });
    }

    private Future<Void> initializeBeans() {
        return Future.future(promise -> {
            try {
                logger.info("Start to initialize beans...");
                for (BeanHolder holder : listBeanHolders()) {
                    bootBeanAnnotationHandler
                        .initBean(this, getBeanDefinition(holder.getId()), holder);
                }
                logger.info("Finished to initialize beans!");
                promise.complete();
            } catch (BeanInitializeException e) {
                promise.fail(e);
            } catch (Exception e) {
                logger.error("Failed to initialize beans", e);
                promise.fail(new BeanInitializeException("Failed to initialize beans!", e));
            }
        });
    }

    @Override
    public Future<Void> close() {
        return null;
    }

    @Override
    public List<BeanDefinition> listBeanDefinitions() {
        return new ArrayList<>(definitionMap.values());
    }

    @Override
    public List<BeanHolder> listBeanHolders() {
        return new ArrayList<>(beanHolderMap.values());
    }

    @Override
    public BeanDefinition getBeanDefinition(String id) {
        return definitionMap.get(id);
    }

    @Override
    public BeanHolder getBeanHolder(String id) {
        return beanHolderMap.get(id);
    }

    @Override
    public Vertx getVertx() {
        return vertx;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }
}
