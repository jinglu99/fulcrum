package cn.justl.fulcrum.vertxboot.excetions;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AnnotationNotFoundException extends AnnotationHandleException {

    public AnnotationNotFoundException(String message) {
        super(message);
    }

    public AnnotationNotFoundException(Class annotation, Class clazz) {
        this("Can't found annotation " + annotation.getSimpleName() + " in Class " + clazz
            .getSimpleName());
    }
}
