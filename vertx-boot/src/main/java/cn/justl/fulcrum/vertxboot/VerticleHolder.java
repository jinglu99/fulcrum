package cn.justl.fulcrum.vertxboot;

/**
 * @Date : 2019-11-27
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class  VerticleHolder<T> {
    private String name;

    private T verticle;


    public VerticleHolder() {
    }

    public VerticleHolder(String name, T verticle) {
        this.name = name;
        this.verticle = verticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getVerticle() {
        return verticle;
    }

    public void setVerticle(T verticle) {
        this.verticle = verticle;
    }
}
