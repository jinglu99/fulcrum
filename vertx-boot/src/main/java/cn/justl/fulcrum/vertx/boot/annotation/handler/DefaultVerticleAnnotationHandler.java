package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolderImpl;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.DefaultBeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCreationException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanInitializeException;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/19
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVerticleAnnotationHandler implements BootBeanAnnotationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultBootBeanAnnotationHandler.class);

    private static final ServiceLoader<VerticleAnnotationHandler> services = ServiceLoader.load(VerticleAnnotationHandler.class);

    @Override
    public BeanDefinition parseBeanDefinition(Context context, Class clazz)
        throws BeanDefinitionParseException {
        Verticle verticle = (Verticle) AnnotationHelper.getAnnotation(clazz, Verticle.class);
        BeanDefinition definition = new DefaultBeanDefinition();

        String id =
            verticle.value().equals("") ? clazz.getSimpleName().substring(0, 1).toLowerCase()
                + clazz.getSimpleName().substring(1) : verticle.value();
        definition.setId(id);
        definition.setClazz(clazz);
        return definition;
    }

    @Override
    public BeanHolder createBean(Context context, BeanDefinition beanDefinition)
        throws BeanCreationException {
        try {
            BeanHolder holder = new BeanHolderImpl();
            holder.setId(beanDefinition.getId());
//            holder.setVerticle(beanDefinition.getClazz().newInstance());
////            holder.setVerticleDefinition(beanDefinition);
////            holder.setTrueVerticle(createVerticle(context, verticleDefinition, holder));
            return holder;
        } catch (Throwable e) {
            logger.error("Failed to instantiate Verticle");
            throw new BeanCreationException(
                "Failed to instantiate Verticle: " + beanDefinition.getClazz().getName(), e);
        }
    }

    @Override
    public BeanHolder initBean(Context context, BeanDefinition beanDefinition,
        BeanHolder beanHolder) throws BeanInitializeException {
        return null;
    }

    @Override
    public void close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder)
        throws BeanCloseException {

    }

    @Override
    public boolean isTargetBean(Class clazz) {
        return AnnotationHelper.hasAnnotation(clazz, Verticle.class);
    }
}
