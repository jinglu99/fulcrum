package cn.justl.fulcrum.core.data;

import cn.justl.fulcrum.vertx.boot.annotation.CodeC;
import java.io.Serializable;

/**
 * @Date : 2019/12/11
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@CodeC
public class FulcrumResource implements  Serializable {
    private Integer id;
    private String resourceId;
    private String dynamicSql;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDynamicSql() {
        return dynamicSql;
    }

    public void setDynamicSql(String dynamicSql) {
        this.dynamicSql = dynamicSql;
    }


    @Override
    public String toString() {
        return "FulcrumResource{" +
            "id=" + id +
            ", resourceId='" + resourceId + '\'' +
            ", dynamicSql='" + dynamicSql + '\'' +
            '}';
    }


}
