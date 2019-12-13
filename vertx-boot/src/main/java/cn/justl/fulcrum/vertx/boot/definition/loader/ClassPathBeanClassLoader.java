package cn.justl.fulcrum.vertx.boot.definition.loader;

import cn.justl.fulcrum.vertx.boot.annotation.BootBean;
import cn.justl.fulcrum.vertx.boot.excetions.DefinitionLoadException;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;
import cn.justl.fulcrum.vertx.boot.helper.ClassHelper;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc : An implement of {@link BeanClassLoader} to scan the {@link
 * cn.justl.fulcrum.vertx.boot.annotation.BootBean} annotated class from given packages.
 */
public class ClassPathBeanClassLoader implements BeanClassLoader {

    private static final Logger logger = LoggerFactory
        .getLogger(ClassPathBeanClassLoader.class);

    private BeanClassLoader parent;
    private List<String> paths;

    public ClassPathBeanClassLoader(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public Set<Class> loadBeanClasses() throws DefinitionLoadException {
        if (paths == null) {
            return Collections.emptySet();
        }

        Set<Class> classSet = new HashSet<>();

        for (String path : paths) {

            try {
                classSet.addAll(
                    Optional
                        .ofNullable(ClassHelper.scan(path,
                            clazz -> AnnotationHelper.hasAnnotation(clazz, BootBean.class)))
                        .orElse(Collections.emptySet())
                );
            } catch (Exception e) {
                throw new DefinitionLoadException("Failed to load definitions from package " + path, e);
            }
        }

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
