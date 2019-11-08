package cn.justL.fulcrum.test.jdbc;

import cn.justL.fulcrum.test.databases.DBTest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @Date : 2019/11/7
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class JDBCTest extends DBTest {

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }

    @Test
    public void nativeSQLTest() throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();

        connection.setReadOnly(true);

        PreparedStatement ps = connection.prepareStatement("select * from post where id = ? and section = ? and created_on = ?");

        System.out
            .println(((PreparedStatement) ps).getParameterMetaData().getParameterClassName(1));

        System.out
            .println(((PreparedStatement) ps).getParameterMetaData().getParameterClassName(2));

        System.out
            .println(((PreparedStatement) ps).getParameterMetaData().getParameterClassName(3));

    }

}
