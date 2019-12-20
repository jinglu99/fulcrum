package cn.justl.fulcrum.vertx.boot.definition.loader;

import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import java.util.Arrays;
import java.util.HashSet;
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

    private ClassPathBeanClassLoader classPathBeanClassLoader;

    public VertxScanBeanClassLoader(Class clazz) throws DefinitionLoadException {
        this(clazz, null);
    }

    public VertxScanBeanClassLoader(Class clazz, BeanClassLoader loader) throws DefinitionLoadException {
        this.clazz = clazz;
        vertxScan = (VertxScan) clazz.getAnnotation(VertxScan.class);
        setParentLoader(loader);
    }

    @Override
    public Set<Class> loadBeanClasses() throws DefinitionLoadException {
        if (vertxScan == null) {
            logger.error("Can't found VertxScan annotation in Class {}", clazz.getName());
            throw new DefinitionLoadException(
                "Can't found VertxScan annotation in Class " + clazz.getName());
        }
        String[] paths = vertxScan.value();
        if (vertxScan.value().length == 0) {
            paths = new String[]{clazz.getPackage().getName()};
        }

        classPathBeanClassLoader = new ClassPathBeanClassLoader(Arrays.asList(paths));
        Set<Class> classSet = classPathBeanClassLoader.loadBeanClasses();

        classSet.forEach(x -> {
            logger.info("Load BootBean {}", x.getSimpleName());
        });

        if (parent != null) {
            classSet.addAll(parent.loadBeanClasses());
        }
        return classSet;
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
