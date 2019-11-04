package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Date : 2019-11-04
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class FulcrumStatementTest extends DBTest {

    private static Connection conn;

    @BeforeAll
    public static void setup() throws SQLException, ClassNotFoundException, IOException {
        conn = createConnection();
        createBlogDataSource();
    }

    @Test
    public void test() throws SQLException {
        PreparedStatement ps1 = conn.prepareStatement("select * from author ");
        ResultSet set = ps1.executeQuery();
        System.out.println(set);
    }


}
