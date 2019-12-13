//package cn.justl.fulcrum.vertx.boot;
//
//import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
//import cn.justl.fulcrum.vertx.boot.context.Context;
//import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
//import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
//import cn.justl.fulcrum.vertx.boot.properties.DefaultFulcrumProperties;
//import cn.justl.fulcrum.vertx.boot.properties.FulcrumProperties;
//import io.vertx.core.Vertx;
//
//import java.io.IOException;
//import java.util.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @Date : 2019/11/29
// * @Author : Jingl.Wang [jingl.wang123@gmail.com]
// * @Desc :
// */
//public abstract class AbstractBootStrapContext implements BootStrapContext, BootStrapHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(AbstractBootStrapContext.class);
//
//    private Vertx vertx;
//    private FulcrumProperties properties = new DefaultFulcrumProperties();
//
//    private Map<String, BeanDefinition> definitionMap = new HashMap<>();
//    private Map<String, VerticleHolder> verticles = new HashMap<>();
//
//    @Override
//    public Vertx getVertx() {
//        return vertx;
//    }
//
//    @Override
//    public void setVertx(Vertx vertx) {
//        this.vertx = vertx;
//    }
//
//
//    @Override
//    public void loadProperties(List<String> propertiesPath) throws VertxBootException {
//        try {
//            if (propertiesPath.size() == 0) {
//                properties.load(Arrays.asList(Constants.DEFAULT_PROPERTIES_FILE));
//            }
//            properties.load(propertiesPath);
//        } catch (IOException e) {
//            throw new VertxBootException("Load properties failed!", e);
//        }
//    }
//
//    @Override
//    public BeanDefinition getBeanDefinition(String id) {
//        return definitionMap.get(id);
//    }
//
//    @Override
//    public List<BeanDefinition> listBeanDefinitions() {
//        return new ArrayList<>(definitionMap.values());
//    }
//
//    @Override
//    public void registerBeanDefinition(BeanDefinition verticleDefinition) {
//        if (definitionMap.containsKey(verticleDefinition.getId())) {
//            logger.warn("VerticleDefinition {}:{} has been registered, it will by replaced by {}",
//                    verticleDefinition.getId(), getBeanDefinition(verticleDefinition.getId()).getClazz().getName(),
//                    verticleDefinition.getClazz().getName());
//        }
//        definitionMap.put(verticleDefinition.getId(), verticleDefinition);
//    }
//
//    @Override
//    public void unregisterBeanDefinition(String id) {
//        definitionMap.remove(id);
//    }
//
//    @Override
//    public VerticleHolder getVerticleHolder(String id) {
//        return verticles.get(id);
//    }
//
//    @Override
//    public List<VerticleHolder> listVerticleHolders() {
//        return new ArrayList<>(verticles.values());
//    }
//
//    @Override
//    public void registerVerticle(VerticleHolder verticleHolder) {
//        if (verticles.containsKey(verticleHolder.getId())) {
//            logger.warn("Verticle {}:{} has been registered, it will by replaced by {}",
//                    verticleHolder.getId(), getVerticleHolder(verticleHolder.getId()).getVerticle(),
//                    verticleHolder.getVerticle());
//        }
//        verticles.put(verticleHolder.getId(), verticleHolder);
//    }
//
//    @Override
//    public void unregisterVerticle(String id) {
//        verticles.remove(id);
//    }
//
//    @Override
//    public FulcrumProperties getProperties() {
//        return properties;
//    }
//}
