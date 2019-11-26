package cn.justL.fulcrum.test.script;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.dynamicsql.ExecuteContext;
import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSQLParseException;
import cn.justl.fulcrum.dynamicsql.exceptions.ScriptFailedException;
import cn.justl.fulcrum.dynamicsql.script.ScriptExecutor;
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
@DisplayName("Test for ScriptExecutor")
public class ScriptExecutorTest extends DBTest {

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

        ScriptExecutor runner = new ScriptExecutor();
        runner.parse(ScriptExecutorTest.class.getResourceAsStream(dynamicSqlPath));

        ExecuteContext context = new ExecuteContext() {{
            setParams(new HashMap() {{
                put("items", Arrays.asList("EST-1","EST-2"));
            }});
        }};



        runner.execute(context);
        assertNotNull(context.getSql());
        assertNotNull(context.getValueHolders());
        assertEquals(2, context.getValueHolders().size());
        assertEquals("EST-1", context.getValueHolders().get(0).getVal());
        assertEquals("EST-2", context.getValueHolders().get(1).getVal());

    }


}
