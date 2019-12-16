package cn.justl.fulcrum.vertx.boot.bean;

/**
 * @Date : 2019/12/16
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BeanHolderImpl implements BeanHolder {

    private String id;
    private Object instance;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getInstance() {
        return instance;
    }

    @Override
    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
