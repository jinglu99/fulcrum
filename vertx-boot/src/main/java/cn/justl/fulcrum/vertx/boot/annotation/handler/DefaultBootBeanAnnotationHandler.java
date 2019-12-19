package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.annotation.BootBean;
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
import java.util.Iterator;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/16
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBootBeanAnnotationHandler implements AnnotationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBootBeanAnnotationHandler.class);

    private static final ServiceLoader<BootBeanAnnotationHandler> services = ServiceLoader.load(BootBeanAnnotationHandler.class);

    @Override
    public BeanDefinition parseBeanDefinition(Context context, Class clazz)
        throws BeanDefinitionParseException {

        Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
        while (iterator.hasNext()) {
            BootBeanAnnotationHandler handler = iterator.next();
            if (handler.isTargetBean(clazz)) {
                return handler.parseBeanDefinition(context, clazz);
            }
        }

        BeanDefinition definition = new DefaultBeanDefinition();
        definition.setId(clazz.getSimpleName().substring(0, 1).toLowerCase()
            + clazz.getSimpleName().substring(1));
        definition.setClazz(clazz);
        return definition;
    }

    @Override
    public BeanHolder createBean(Context context, BeanDefinition beanDefinition)
        throws BeanCreationException {
        try {

            Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
            while (iterator.hasNext()) {
                BootBeanAnnotationHandler handler = iterator.next();
                if (handler.isTargetBean(beanDefinition.getClazz())) {
                    return handler.createBean(context, beanDefinition);
                }
            }

            Object bean = beanDefinition.getClazz().newInstance();
            BeanHolder holder = new BeanHolderImpl();
            holder.setId(beanDefinition.getId());
            holder.setInstance(bean);
            return holder;
        } catch (Exception e) {
            logger.error("Failed to create bean instance of bean " + beanDefinition.getId(), e);
            throw new BeanCreationException("Failed to create bean instance of bean " + beanDefinition.getId(), e);
        }
    }

    @Override
    public BeanHolder initBean(Context context, BeanDefinition beanDefinition,
        BeanHolder beanHolder) throws BeanInitializeException {
        Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
        while (iterator.hasNext()) {
            BootBeanAnnotationHandler handler = iterator.next();
            if (handler.isTargetBean(beanDefinition.getClazz())) {
                return handler.initBean(context, beanDefinition, beanHolder);
            }
        }
        return beanHolder;
    }

    @Override
    public void close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder)
        throws BeanCloseException {
        Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
        while (iterator.hasNext()) {
            BootBeanAnnotationHandler handler = iterator.next();
            if (handler.isTargetBean(beanDefinition.getClazz())) {
                handler.close(context, beanDefinition, beanHolder);
                return;
            }
        }
    }

    @Override
    public boolean isTargetBean(Class clazz) {
        return AnnotationHelper.hasAnnotation(clazz, BootBean.class);
    }
}
