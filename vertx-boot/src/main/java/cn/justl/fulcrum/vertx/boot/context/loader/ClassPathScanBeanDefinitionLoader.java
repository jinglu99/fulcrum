package cn.justl.fulcrum.vertx.boot.context.loader;

import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import java.util.List;
import java.util.Set;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * An implement of {@link BeanClassLoader} to scan the {@link cn.justl.fulcrum.vertx.boot.annotation.BootBean}
 * annotated class.
 */
public class ClassPathScanBeanDefinitionLoader implements BeanClassLoader {

    private BeanClassLoader parent;
    private List<String> paths;

    public ClassPathScanBeanDefinitionLoader(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public Set<Class> loadBeanClasses() {
        return null;
    }

    @Override
    public BeanClassLoader getParentLoader() {
        return parent;
    }

    @Override
    public void setParentLoader(BeanClassLoader loader) {
        this.parent = loader;
    }
}
