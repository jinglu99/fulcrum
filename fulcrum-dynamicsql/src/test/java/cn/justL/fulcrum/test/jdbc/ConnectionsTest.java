package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.exceptions.StatementExecuteException;
import cn.justl.fulcrum.jdbc.Connections;
import cn.justl.fulcrum.jdbc.FulcrumStatement;
import cn.justl.fulcrum.scripthandler.BoundSql;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Date : 2019-11-05
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Connections Test")
public class ConnectionsTest extends DBTest {

    private static Connection conn;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException, IOException {
        createBlogDataSource();
    }

    @BeforeEach
    public void connect() throws SQLException, ClassNotFoundException {
        conn = createConnection();
    }


    @Test
    public void prepareStatementTest() throws SQLException, StatementExecuteException {

        BoundSql sr = new BoundSql() {{
            setSql(new StringBuilder("select * from author"));
        }};
        FulcrumStatement fulcrumStatement = Connections.prepareStatement(conn, sr);

        assertNotNull(fulcrumStatement);
        assertEquals(conn, fulcrumStatement.getConn());
    }

    @Test
    public void prepareStatementWhenConnIsCloseTest() throws SQLException {
        conn.close();
        BoundSql sr = new BoundSql() {{
            setSql(new StringBuilder("select * from author"));
        }};

        assertThrows(StatementExecuteException.class, () -> {
            Connections.prepareStatement(conn, sr);
        });
    }


}
