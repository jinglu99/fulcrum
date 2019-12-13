package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinitionRegister;
import cn.justl.fulcrum.vertx.boot.definition.loader.BeanClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBeanDefinitionRegister implements BeanDefinitionRegister {

    private BeanClassLoader beanClassLoader;

    private Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();

    @Override
    public void setBeanClassLoader(BeanClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        definitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void unregisterBeanDefinition(String id) {
        definitionMap.remove(id);
    }

    @Override
    public BeanDefinition getBeanDefinition(String id) {
        return definitionMap.get(id);
    }

    @Override
    public List<BeanDefinition> listBeanDefinitions() {
        return new ArrayList<>(definitionMap.values());
    }
}
