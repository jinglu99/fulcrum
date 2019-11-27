package cn.justl.fulcrum.vertxboot;

import cn.justl.fulcrum.vertxboot.definition.VerticleDefinition;

/**
 * @Date : 2019-11-27
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class  VerticleHolder<T> {
    private String id;

    private T verticle;

    private VerticleDefinition verticleDefinition;


    public VerticleHolder() {
    }

    public VerticleHolder(String id, T verticle) {
        this.id = id;
        this.verticle = verticle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getVerticle() {
        return verticle;
    }

    public void setVerticle(T verticle) {
        this.verticle = verticle;
    }


    public VerticleDefinition getVerticleDefinition() {
        return verticleDefinition;
    }

    public void setVerticleDefinition(VerticleDefinition verticleDefinition) {
        this.verticleDefinition = verticleDefinition;
    }

    @Override
    public String toString() {
        return "VerticleHolder{" +
                "id='" + id + '\'' +
                ", verticle=" + verticle +
                '}';
    }
}
