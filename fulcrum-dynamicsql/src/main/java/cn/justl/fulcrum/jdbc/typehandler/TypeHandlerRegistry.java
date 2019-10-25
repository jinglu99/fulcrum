package cn.justl.fulcrum.jdbc.typehandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class TypeHandlerRegistry {
    private static Map<Class, TypeHandler> registry = new HashMap() {{
        // register the Type handler at here.
    }};

    private static TypeHandler otherTypeHander = new OtherTypeHandler();

    public static TypeHandler getTypeHandler(Object val) {
        for (Map.Entry<Class, TypeHandler> entry : registry.entrySet()) {
            if (entry.getValue().isMatch(val)) return entry.getValue();
        }

        return otherTypeHander;
    }


}
