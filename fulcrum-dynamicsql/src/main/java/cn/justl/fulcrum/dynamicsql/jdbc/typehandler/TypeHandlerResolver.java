package cn.justl.fulcrum.dynamicsql.jdbc.typehandler;

import cn.justl.fulcrum.dynamicsql.ValueHolder;
import cn.justl.fulcrum.dynamicsql.jdbc.TypeHandlers;
import org.apache.commons.lang3.StringUtils;

/**
 * @Date : 2019-10-29
 * @Author : jinglu.wang[jingl.wang123@gmail.com]
 * @Desc :
 */
public class TypeHandlerResolver {
    public static TypeHandler resolveTypeHandler(ValueHolder valueHolder) {
        if (valueHolder != null &&  (valueHolder.getVal() != null || StringUtils.isNotBlank(valueHolder.getType()))) {
            for (TypeHandler handler : TypeHandlers.getHandlers()) {
                if (handler.isMatch(valueHolder)) return handler;
            }
        }
        return OtherTypeHandler.getInstance();
    }
}
