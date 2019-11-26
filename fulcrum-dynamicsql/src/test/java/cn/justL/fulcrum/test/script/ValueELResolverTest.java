package cn.justL.fulcrum.test.script;

import cn.justl.fulcrum.dynamicsql.ValueHolder;
import cn.justl.fulcrum.dynamicsql.exceptions.ValueElParseExcetion;
import cn.justl.fulcrum.dynamicsql.script.ValueELResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019-11-03
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test ValueELResolver")
public class ValueELResolverTest {
    @Test
    @DisplayName("getValueHolder test only with param name")
    public void getValueHolderTestWithParamName() throws ValueElParseExcetion {
        ValueHolder holder = ValueELResolver.getValueHolder(x->"testValue", "name");

        assertEquals("testValue", holder.getVal());
        assertEquals("name", holder.getParamName());
        assertNull(holder.getType());
        assertNull(holder.getDefaultExp());
    }


    @Test
    @DisplayName("getValueHolder test with param name and value type")
    public void getValueHolderTestWithValueType() throws ValueElParseExcetion {
        ValueHolder holder = ValueELResolver.getValueHolder(x->"testValue", "name:String");

        assertEquals("testValue", holder.getVal());
        assertEquals("name", holder.getParamName());
        assertEquals("String", holder.getType());
        assertNull(holder.getDefaultExp());
    }

    @Test
    @DisplayName("getValueHolder test with param name, valueType and default val expression")
    public void getValueHolderTestWithFullExpression() throws ValueElParseExcetion {
        ValueHolder holder = ValueELResolver.getValueHolder(x->"testValue", "name:String:test");

        assertEquals("testValue", holder.getVal());
        assertEquals("name", holder.getParamName());
        assertEquals("String", holder.getType());
        assertEquals("test", holder.getDefaultExp());
    }
}
