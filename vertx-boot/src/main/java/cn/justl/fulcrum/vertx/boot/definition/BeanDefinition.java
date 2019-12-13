package cn.justl.fulcrum.vertx.boot.definition;

/**
 * @Date : 2019/11/27
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface BeanDefinition<T> {

    /**
     * Set ID for Verticle.
     */
    void setId(String id);

    /**
     * Get ID of Verticle.
     * @return
     */
    String getId();

    /**
     * Set Class for verticle.
     */
    void setClazz(Class<T> clazz);

    /**
     * Get the Class of Verticle.
     * @return
     */
    Class<T> getClazz();

    /**
     * Set dependOn parameter for verticle.
     * @param dependOn
     */
    void setDependOn(String[] dependOn);

    /**
     * Get dependOn parameter for verticle.
     * @return
     */
    String[] getDependOn();

    /**
     * Set the level of deploy order.
     * @param level
     */
    void setDeployLevel(int level);

    /**
     * Get the level of deploy order.
     * @return
     */
    int getDeployLevel();

}