package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.StatementExecuteException;
import cn.justl.fulcrum.jdbc.FulcrumStatement;
import cn.justl.fulcrum.scripthandler.BoundSql;
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
    private BoundSql sr;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException, IOException {
        createBlogDataSource();
    }

    @BeforeEach
    public void connect() throws SQLException, ClassNotFoundException {
        conn = createConnection();

        sr = new BoundSql() {{
            setSql(new StringBuilder("select * from author where id = ?"));
        }};

        sr.addValue(new ValueHolder("id", 101, null, null));
    }

    @Test
    public void simpleTest() throws StatementExecuteException, SQLException {
        FulcrumStatement statement = new FulcrumStatement(conn, sr);
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
        assertThrows(StatementExecuteException.class, () -> {
            FulcrumStatement statement = new FulcrumStatement(conn, sr);
        });
    }

    @Test
    public void connectionClosedBeforeExecute() throws StatementExecuteException, SQLException {
        FulcrumStatement statement = new FulcrumStatement(conn, sr);
        conn.close();
        assertThrows(StatementExecuteException.class, () -> {
            statement.execute();
        });
    }
}
