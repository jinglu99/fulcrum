package cn.justL.fulcrum.test.script;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.DynamicSQLParams;
import cn.justl.fulcrum.data.ExecuteContext;
import cn.justl.fulcrum.exceptions.DynamicSQLParseException;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.script.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @Date : 2019-11-13
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for ScriptRunner")
public class ScriptRunnerTest extends DBTest {

    private final static String dynamicSqlPath = "/cn/justL/fulcrum/test/xmls/script/dynamicSql.xml";

    private Connection conn;

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }

    @BeforeEach
    public void createConn() throws SQLException, ClassNotFoundException {
        conn = createConnection();
    }

    @Test
    public void testScriptRunner() throws DynamicSQLParseException, ScriptFailedException {

        ScriptRunner runner = new ScriptRunner(ScriptRunnerTest.class.getResourceAsStream(dynamicSqlPath));

        ExecuteContext context = new ExecuteContext();
        context.setDynamicSQLParams(new DynamicSQLParams() {{
            setConnection(conn);
            setParams(new HashMap() {{
                put("items", Arrays.asList("EST-1","EST-2"));
            }});
        }});

        runner.execute(context);

        assertNotNull(context.getBoundSql());
        assertNotNull(context.getBoundSql().getSql());
        assertNotNull(context.getBoundSql().getValueHolders());
        assertEquals(2, context.getBoundSql().getValueHolders().size());
        assertEquals("EST-1", context.getBoundSql().getValueHolders().get(0).getVal());
        assertEquals("EST-2", context.getBoundSql().getValueHolders().get(1).getVal());

    }


}
