package cn.justl.fulcrum.vertx.boot.definition.loader;

import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @Date : 2019/12/20
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBeanClassLoader implements BeanClassLoader {

    private BeanClassLoader parent;
    private Class[] classes;

    public DefaultBeanClassLoader(Class[] classes) {
        this(classes, null);
    }

    public DefaultBeanClassLoader(Class[] classes, BeanClassLoader parent) {
        this.parent = parent;
        this.classes = classes;
    }

    @Override
    public Set<Class> loadBeanClasses() throws DefinitionLoadException {
        Set<Class> classSet = new HashSet<>(
            Arrays.asList(Optional.ofNullable(classes)
                .orElse(new Class[0])));
        if (parent != null) {
            classSet.addAll(parent.loadBeanClasses());
        }
        return classSet;
    }

    @Override
    public BeanClassLoader getParentLoader() {
        return null;
    }

    @Override
    public void setParentLoader(BeanClassLoader loader) {

    }
}
