package cn.justL.fulcrum.test.scripthandler;

import cn.justl.fulcrum.contexts.ScriptContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import cn.justl.fulcrum.scripthandler.handlers.IfScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.TextScriptHandler;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

/**
 * @Date : 2019-11-03
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test If ScriptHandler")
public class IfScriptHandlerTest {
    @Test
    public void IfSimpleTest() throws ScriptFailedException {
        IfScriptHandler handler = new IfScriptHandler();
        handler.addCase("a == nil", new TextScriptHandler("a=null"));
        handler.addCase("a == 1", new TextScriptHandler("a=1"));
        handler.addCase("a == 2", new TextScriptHandler("a=2"));
        handler.addElse(new TextScriptHandler("a is val:{$a}"));

        ScriptContext aIsNull = new ScriptContext();
        aIsNull.setParams(new HashMap() {{
            put("a", null);
        }});
        ScriptResult rs = handler.process(aIsNull);
        assertEquals("a=null", rs.getSql().toString());
        assertEquals(0, rs.getValueHolders().size());

        ScriptContext aIs1 = new ScriptContext();
        aIs1.setParams(new HashMap() {{
            put("a", 1);
        }});
        rs = handler.process(aIs1);
        assertEquals("a=1", rs.getSql().toString());
        assertEquals(0, rs.getValueHolders().size());

        ScriptContext aIs2 = new ScriptContext();
        aIs2.setParams(new HashMap() {{
            put("a", 2);
        }});
        rs = handler.process(aIs2);
        assertEquals("a=2", rs.getSql().toString());
        assertEquals(0, rs.getValueHolders().size());

        ScriptContext aIs3 = new ScriptContext();
        aIs3.setParams(new HashMap() {{
            put("a", 3);
        }});
        rs = handler.process(aIs3);
        assertEquals("a is val:?", rs.getSql().toString());
        assertEquals(1, rs.getValueHolders().size());
        assertEquals(3, rs.getValueHolders().get(0).getVal());
    }


    @Test
    public void paramAbsentTest() {
        IfScriptHandler handler = new IfScriptHandler();
        handler.addCase("a = nil", new TextScriptHandler("a=null"));
        handler.addCase("a = 1", new TextScriptHandler("a=1"));
        handler.addCase("a = 2", new TextScriptHandler("a=2"));
        handler.addElse(new TextScriptHandler("a is val:{$a}"));

        ScriptContext context = new ScriptContext();

        assertThrows(ScriptFailedException.class, ()->{
            handler.process(context);
        });
    }


    @BeforeAll
    public static void start() {
        System.out.println("start IfScriptHandlerTest...");
    }

    @AfterAll
    public static void end() {
        System.out.println("All tests in IfScriptHandlerTest are executed!");
    }
}
