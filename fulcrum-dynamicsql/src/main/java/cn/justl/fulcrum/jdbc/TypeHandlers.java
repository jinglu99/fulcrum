package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.jdbc.typehandler.IntegerTypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Date : 2019-10-29
 * @Author : jinglu.wang[jingl.wang123@gmail.com]
 * @Desc :
 */
public enum TypeHandlers {
    INTEGER(new IntegerTypeHandler()),
    ;

    TypeHandler handler;

    TypeHandlers(TypeHandler handler) {
        this.handler = handler;
    }

    public TypeHandler getHandler() {
        return handler;
    }

    public static List<TypeHandler> getHandlers() {
        return Stream.of(TypeHandlers.values())
                .map(type -> type.getHandler())
                .collect(Collectors.toList());
    }
}
