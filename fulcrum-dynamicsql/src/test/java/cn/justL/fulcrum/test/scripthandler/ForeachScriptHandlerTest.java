package cn.justL.fulcrum.test.scripthandler;

import cn.justl.fulcrum.contexts.ScriptContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import cn.justl.fulcrum.scripthandler.handlers.ForeachScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.TextScriptHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * @Date : 2019-11-03
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test ForeachScriptHandler")
public class ForeachScriptHandlerTest {

    @Test
    public void listForeachTest() throws ScriptFailedException {

        List list = Arrays.asList(1, 2, 3, 4, 5);

        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("collection", list);
        }});

        ForeachScriptHandler handler = new ForeachScriptHandler("collection", "item", "index", ",");
        handler.setChild(new TextScriptHandler("{$index}:{$item}"));

        ScriptResult sr = handler.process(context);

        assertEquals(10, sr.getValueHolders().size());
        assertEquals("?:?,?:?,?:?,?:?,?:?", sr.getSql().toString());

        assertEquals(0, sr.getValueHolders().get(0).getVal());
        assertEquals(1, sr.getValueHolders().get(1).getVal());

        assertEquals(1, sr.getValueHolders().get(2).getVal());
        assertEquals(2, sr.getValueHolders().get(3).getVal());

        assertEquals(2, sr.getValueHolders().get(4).getVal());
        assertEquals(3, sr.getValueHolders().get(5).getVal());

        assertEquals(3, sr.getValueHolders().get(6).getVal());
        assertEquals(4, sr.getValueHolders().get(7).getVal());

        assertEquals(4, sr.getValueHolders().get(8).getVal());
        assertEquals(5, sr.getValueHolders().get(9).getVal());

    }

    @Test
    public void objectListForeachTest() throws ScriptFailedException {
        List list = new ArrayList();
        list.add(new HashMap() {
            {
                put("a", "1");
            }
        });
        list.add(new HashMap() {
            {
                put("a", "2");
            }
        });


        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("collection", list);
        }});

        ForeachScriptHandler handler = new ForeachScriptHandler("collection", "item", "index", ",");
        handler.setChild(new TextScriptHandler("{$index}:{$item.a}"));

        ScriptResult sr = handler.process(context);

        assertEquals(4, sr.getValueHolders().size());
        assertEquals("?:?,?:?", sr.getSql().toString());

        assertEquals(0, sr.getValueHolders().get(0).getVal());
        assertEquals("1", sr.getValueHolders().get(1).getVal());

        assertEquals(1, sr.getValueHolders().get(2).getVal());
        assertEquals("2", sr.getValueHolders().get(3).getVal());

    }

    @Test
    public void embedListForeachTest() throws ScriptFailedException {
        List list = new ArrayList();
        list.add(Arrays.asList(1,2));
        list.add(Arrays.asList(3,4));


        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("collection", list);
        }});

        ForeachScriptHandler handler = new ForeachScriptHandler("collection", "item", "index", ",");
        handler.setChild(new TextScriptHandler("{$index}:{$item[0]}"));

        ScriptResult sr = handler.process(context);

        assertEquals(4, sr.getValueHolders().size());
        assertEquals("?:?,?:?", sr.getSql().toString());

        assertEquals(0, sr.getValueHolders().get(0).getVal());
        assertEquals(1, sr.getValueHolders().get(1).getVal());

        assertEquals(1, sr.getValueHolders().get(2).getVal());
        assertEquals(3, sr.getValueHolders().get(3).getVal());

    }

    @Test
    public void arrayListForeachTest() throws ScriptFailedException {
        List list = new ArrayList();
        list.add(new int[] {1,2});
        list.add(new int[] {3,4});


        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("collection", list);
        }});

        ForeachScriptHandler handler = new ForeachScriptHandler("collection", "item", "index", ",");
        handler.setChild(new TextScriptHandler("{$index}:{$item[0]}"));

        ScriptResult sr = handler.process(context);

        assertEquals(4, sr.getValueHolders().size());
        assertEquals("?:?,?:?", sr.getSql().toString());

        assertEquals(0, sr.getValueHolders().get(0).getVal());
        assertEquals(1, sr.getValueHolders().get(1).getVal());

        assertEquals(1, sr.getValueHolders().get(2).getVal());
        assertEquals(3, sr.getValueHolders().get(3).getVal());

    }

    @Test
    public void mapForeachTest() throws ScriptFailedException {
        Map map = new HashMap() {{
            put("a", "A");
            put("b", "B");
        }};

        ScriptContext context = new ScriptContext();
        context.setParams(new HashMap() {{
            put("col", map);
        }});

        ForeachScriptHandler handler = new ForeachScriptHandler("col", "item", "index", ",");
        handler.setChild(new TextScriptHandler("{$index}:{$item.key}:{$item.value}"));


        ScriptResult sr = handler.process(context);

        assertEquals("?:?:?,?:?:?", sr.getSql().toString());
        assertEquals(6, sr.getValueHolders().size());

        assertEquals(0, sr.getValueHolders().get(0).getVal());
        assertEquals("a", sr.getValueHolders().get(1).getVal());
        assertEquals("A", sr.getValueHolders().get(2).getVal());

        assertEquals(1, sr.getValueHolders().get(3).getVal());
        assertEquals("b", sr.getValueHolders().get(4).getVal());
        assertEquals("B", sr.getValueHolders().get(5).getVal());
    }

    @Test
    public void useBlankSeperator() throws ScriptFailedException {
        ForeachScriptHandler handler = new ForeachScriptHandler("col", "item", "index", " ");
        handler.setChild(new TextScriptHandler("{$item}"));

        ScriptContext context = new ScriptContext() {{
            setParams(new HashMap() {{
                put("col", Arrays.asList(1,2,3));
            }});
        }};

        ScriptResult sr = handler.process(context);

        assertEquals("? ? ?", sr.getSql().toString());
        assertEquals(3, sr.getValueHolders().size());
    }

    @Test
    public void collectionWithNullValueTest() throws ScriptFailedException {
        ForeachScriptHandler handler = new ForeachScriptHandler("col", "item", "index", " ");
        handler.setChild(new TextScriptHandler("{$item}"));

        ScriptContext context = new ScriptContext() {{
            setParams(new HashMap() {{
                put("col", Arrays.asList(1,null,3));
            }});
        }};

        ScriptResult sr = handler.process(context);

        assertEquals("? ?", sr.getSql().toString());
        assertEquals(2, sr.getValueHolders().size());
    }

}
