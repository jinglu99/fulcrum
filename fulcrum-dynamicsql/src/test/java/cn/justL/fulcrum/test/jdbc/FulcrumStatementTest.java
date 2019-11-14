package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.ValueHolder;
import cn.justl.fulcrum.exceptions.StatementExecuteException;
import cn.justl.fulcrum.jdbc.FulcrumStatement;
import cn.justl.fulcrum.script.BoundSql;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019-11-04
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class FulcrumStatementTest extends DBTest {

    private Connection conn;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException, IOException {
        createBlogDataSource();
    }

    @BeforeEach
    public void connect() throws SQLException, ClassNotFoundException {
        conn = createConnection();
    }

    @Test
    public void simpleTest() throws StatementExecuteException, SQLException {

        String sql = "select * from author where id = ?";
        List<ValueHolder> valueHolders = new ArrayList() {{
            add(new ValueHolder("id", 101, null, null));
        }};

        FulcrumStatement statement = new FulcrumStatement(conn, sql, valueHolders);
        ResultSet rs = statement.execute();
        assertNotNull(rs);

        int cn = 0;
        while (rs.next()) {
            cn++;
        }
        assertEquals(1, cn);
    }

    @Test
    public void simpleTestWhenConnIsClosed() throws SQLException, StatementExecuteException {
        conn.close();

        String sql = "select * from author where id = ?";
        List<ValueHolder> valueHolders = new ArrayList() {{
            add(new ValueHolder("id", 101, null, null));
        }};
        assertThrows(StatementExecuteException.class, () -> {
            FulcrumStatement statement = new FulcrumStatement(conn, sql, valueHolders);
        });
    }

    @Test
    public void connectionClosedBeforeExecute() throws StatementExecuteException, SQLException {
        String sql = "select * from author where id = ?";
        List<ValueHolder> valueHolders = new ArrayList() {{
            add(new ValueHolder("id", 101, null, null));
        }};

        FulcrumStatement statement = new FulcrumStatement(conn, sql, valueHolders);
        conn.close();
        assertThrows(StatementExecuteException.class, () -> {
            statement.execute();
        });
    }
}
