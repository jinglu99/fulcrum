package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.jdbc.FulcrumStatement;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private ScriptResult sr;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException, IOException {
        createBlogDataSource();
    }

    @BeforeEach
    public void connect() throws SQLException, ClassNotFoundException {
        conn = createConnection();

        sr = new ScriptResult() {{
            setSql(new StringBuilder("select * from author where id = ?"));
        }};

        sr.addValue(new ValueHolder("id", 101, null, null));
    }

    @Test
    public void simpleTest() throws SQLExecuteException, SQLException {
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
    public void simpleTestWhenConnIsClosed() throws SQLException, SQLExecuteException {
        conn.close();
        assertThrows(SQLExecuteException.class, () -> {
            FulcrumStatement statement = new FulcrumStatement(conn, sr);
        });
    }

    @Test
    public void connectionClosedBeforeExecute() throws SQLExecuteException, SQLException {
        FulcrumStatement statement = new FulcrumStatement(conn, sr);
        conn.close();
        assertThrows(SQLExecuteException.class, () -> {
            statement.execute();
        });
    }
}
