package cn.justl.fulcrum.vertx.boot.definition;

/**
 * @Date : 2019/11/27
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVerticleDefinition implements VerticleDefinition {

    private String id;
    private Class clazz;
    private String[] dependOn;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class getClazz() {
        return this.clazz;
    }

    @Override
    public void setDependOn(String[] dependOn) {
        this.dependOn = dependOn;
    }

    @Override
    public String[] getDependOn() {
        return this.dependOn;
    }

}