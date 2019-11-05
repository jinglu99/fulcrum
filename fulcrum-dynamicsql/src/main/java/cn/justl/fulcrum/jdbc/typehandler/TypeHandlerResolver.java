package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.jdbc.SupportTypes;
import org.apache.commons.lang3.StringUtils;

/**
 * @Date : 2019-10-29
 * @Author : 汪京陆(Ben Wang)[jingl.wang123@gmail.com]
 * @Desc :
 */
public class TypeHandlerResolver {
    public static TypeHandler resolveTypeHandler(ValueHolder valueHolder) {
        if (StringUtils.isNotBlank(valueHolder.getType())) {
            for (SupportTypes type : SupportTypes.values()) {
                if (StringUtils.equals(type.name().toLowerCase(), valueHolder.getType().toLowerCase())) {
                    return type.getHandler();
                }
            }
        }

        for (SupportTypes type : SupportTypes.values()) {
            if (type.getHandler().isMatchByType(valueHolder)) return type.getHandler();
        }

        return OtherTypeHandler.getInstance();
    }
}
