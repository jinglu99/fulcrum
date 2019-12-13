package cn.justl.fulcrum.vertx.boot.definition.loader;

import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxScanBeanClassLoader implements BeanClassLoader {

    private static final Logger logger = LoggerFactory
        .getLogger(VertxScanBeanClassLoader.class);

    private Class clazz = null;
    private VertxScan vertxScan = null;

    private BeanClassLoader parent;

    public VertxScanBeanClassLoader(Class clazz) throws DefinitionLoadException {
        this.clazz = clazz;
        vertxScan = (VertxScan) clazz.getAnnotation(VertxScan.class);

    }

    @Override
    public Set<Class> loadBeanClasses() throws DefinitionLoadException {
        if (vertxScan == null) {
            logger.error("Can't found VertxScan annotation in Class {}", clazz.getName());
            throw new DefinitionLoadException(
                "Can't found VertxScan annotation in Class " + clazz.getName());
        }
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
