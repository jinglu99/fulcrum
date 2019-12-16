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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/16
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BootBeanAnnotationHandler implements AnnotationHandler {

    private static final Logger logger = LoggerFactory.getLogger(BootBeanAnnotationHandler.class);

    @Override
    public BeanDefinition parseBeanDefinition(Context context, Class clazz)
        throws BeanDefinitionParseException {
        BeanDefinition definition = new DefaultBeanDefinition();
        definition.setId(clazz.getSimpleName().substring(0, 1).toLowerCase()
            + clazz.getSimpleName().substring(1));
        definition.setClazz(clazz);
        return definition;
    }

    @Override
    public BeanHolder creatBean(Context context, BeanDefinition beanDefinition)
        throws BeanCreationException {
        try {
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
        return null;
    }

    @Override
    public void close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder)
        throws BeanCloseException {

    }

    @Override
    public boolean isTargetBean(Class clazz) {
        return AnnotationHelper.hasAnnotation(clazz, BootBean.class);
    }
}
