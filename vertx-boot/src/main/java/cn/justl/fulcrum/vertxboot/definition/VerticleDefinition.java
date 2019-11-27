package cn.justl.fulcrum.vertxboot.definition;

/**
 * @Date : 2019/11/27
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface VerticleDefinition {

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
    void setClazz(Class clazz);

    /**
     * Get the Class of Verticle.
     * @return
     */
    Class getClazz();



}
