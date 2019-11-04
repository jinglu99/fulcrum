package cn.justL.fulcrum.test.scripthandler;

import cn.justl.fulcrum.contexts.ScriptContext;
import cn.justl.fulcrum.contexts.ValueHolder;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import cn.justl.fulcrum.scripthandler.handlers.TextScriptHandler;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019-11-03
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test Text ScriptHandler")
public class TextScriptHandlerTest {
    @Test
    @DisplayName("simple test")
    public void textScriptHandlerSimpleTest() throws ScriptFailedException {
        TextScriptHandler handler = new TextScriptHandler("hello world!");

        ScriptContext context = new ScriptContext();

        ScriptResult rs = handler.process(context);

        assertEquals("hello world!", rs.getSql().toString());
        assertEquals(0, rs.getValueHolders().size());
    }

    @Test
    @DisplayName("test with params")
    public void textScriptHandlerTestWithParams() throws ScriptFailedException {
        TextScriptHandler handler = new TextScriptHandler("hello {$name1} and {$name2}!");

        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("name1", "Lily");
            put("name2", "Bob");
        }});

        ScriptResult rs =  handler.process(context);

        assertEquals("hello ? and ?!", rs.getSql().toString());
        assertEquals(2, rs.getValueHolders().size());

        ValueHolder name1 = rs.getValueHolders().get(0);
        assertEquals("name1", name1.getParamName());
        assertEquals("Lily", name1.getVal());
        assertNull(name1.getType());
        assertNull(name1.getType());
        assertNull(name1.getDefaultExp());

        ValueHolder name2 = rs.getValueHolders().get(1);
        assertEquals("name2", name2.getParamName());
        assertEquals("Bob", name2.getVal());
        assertNull(name1.getType());
        assertNull(name1.getType());
        assertNull(name1.getDefaultExp());
    }

}
