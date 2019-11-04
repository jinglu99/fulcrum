package cn.justL.fulcrum.test.scripthandler;

import cn.justl.fulcrum.contexts.ScriptContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.scripthandler.ScriptHandler;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import cn.justl.fulcrum.scripthandler.handlers.ForeachScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.IfScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.ListableScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.TextScriptHandler;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @Date : 2019/11/4
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("ListableScriptHandler Test")
public class ListableScriptHandlerTest {

    @Test
    public void simpleTest() throws ScriptFailedException {
        ListableScriptHandler handler = new ListableScriptHandler();
        handler.addNext(textScriptHandler());
        handler.addNext(ifScriptHandler());
        handler.addNext(foreachScriptHandler());

        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("text", "a");
            put("a", 3);
            put("col", Arrays.asList(1,2));
        }});

        ScriptResult sr = handler.process(context);
        assertEquals("text:? if:a=other ? ? ", sr.getSql().toString());
        assertEquals(3, sr.getValueHolders().size());
    }

    public static ScriptHandler textScriptHandler() {
        return new TextScriptHandler("text:{$text}");
    }

    public static ScriptHandler ifScriptHandler() {
        ScriptHandler handler =  new IfScriptHandler();
        ((IfScriptHandler) handler).addCase("a == 1", new TextScriptHandler("if:a=1"));
        ((IfScriptHandler) handler).addCase("a == 2", new TextScriptHandler("if:a=2"));
        ((IfScriptHandler) handler).addElse(new TextScriptHandler("if:a=other"));
        return handler;
    }

    public static ScriptHandler foreachScriptHandler() {
        ScriptHandler handler = new ForeachScriptHandler("col", "item", "index", " ");
        handler.setChild(new TextScriptHandler("{$item}"));
        return handler;
    }

}
