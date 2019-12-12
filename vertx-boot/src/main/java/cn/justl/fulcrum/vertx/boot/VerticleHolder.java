package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import io.vertx.core.Verticle;

/**
 * @Date : 2019-11-27
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class  VerticleHolder<T> {
    private String id;

    private T verticle;

    private BeanDefinition verticleDefinition;

    private Verticle trueVerticle;

    private String trueVerticleId;


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


    public BeanDefinition getVerticleDefinition() {
        return verticleDefinition;
    }

    public void setVerticleDefinition(BeanDefinition verticleDefinition) {
        this.verticleDefinition = verticleDefinition;
    }


    public Verticle getTrueVerticle() {
        return trueVerticle;
    }

    public void setTrueVerticle(Verticle trueVerticle) {
        this.trueVerticle = trueVerticle;
    }


    public String getTrueVerticleId() {
        return trueVerticleId;
    }

    public void setTrueVerticleId(String trueVerticleId) {
        this.trueVerticleId = trueVerticleId;
    }

    @Override
    public String toString() {
        return "VerticleHolder{" +
                "id='" + id + '\'' +
                ", verticle=" + verticle +
                '}';
    }
}
