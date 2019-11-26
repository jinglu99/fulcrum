package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.dynamicsql.ExecuteContext;
import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSqlException;
import cn.justl.fulcrum.dynamicsql.jdbc.SQLExecutor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/11/14
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for SQLExecutor")
public class SQLExecutorTest extends DBTest {

    private Connection conn;

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }

    @BeforeEach
    public void connect() throws SQLException, ClassNotFoundException {
        conn = createConnection();
    }

    @Test
    public void simpleTest() throws SQLException, ClassNotFoundException, DynamicSqlException {

        String sql = "select * from author where id = 101";

        ExecuteContext context = new ExecuteContext() {{
            setConnection(conn);
            setSql(sql);
        }};

        SQLExecutor sqlExecutor = new SQLExecutor();
        sqlExecutor.execute(context);

        List<Map> goalResult = executeSql(sql);

        assertNotNull(context.getResultSet());
        assertTrue(compareResult(goalResult, convertList(context.getResultSet())));

    }

}
