package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.jdbc.typehandler.IntTypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;

/**
 * @Date : 2019-10-29
 * @Author : 汪京陆(Ben Wang)[jingl.wang123@gmail.com]
 * @Desc :
 */
public enum SupportTypes {

//    STRING(String.class, "string", null),
//    BOOLEAN(Boolean.class, "boolean", null),
//    BYTE(Byte.class, "byte", null),
    INTEGER(Integer.class, "int", new IntTypeHandler()),
    ;
    Class clazz;
    String name;
    TypeHandler handler;

    SupportTypes(Class clazz, String name, TypeHandler handler) {
        this.clazz = clazz;
        this.name = name;
        this.handler = handler;
    }

    public Class getClazz() {
        return clazz;
    }


    public String getName() {
        return name;
    }


    public TypeHandler getHandler() {
        return handler;
    }
}
