package cn.justL.fulcrum.test;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.DynamicSQLExecutor;
import cn.justl.fulcrum.DynamicSQLParams;
import cn.justl.fulcrum.DynamicSQLResult;
import cn.justl.fulcrum.DynamicSql;
import cn.justl.fulcrum.exceptions.DynamicSqlConstructionException;
import cn.justl.fulcrum.exceptions.DynamicSqlExecutionException;
import org.apache.commons.collections.MapUtils;
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
import java.util.List;
import java.util.Map;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for DynamicSQLExecutor")
public class DynamicSQLExecutorTest extends DBTest {
    public Connection conn;


    private static final String dynamicSqlPath = "/cn/justL/fulcrum/test/xmls/dynamicSql.xml";
    private static final String staticSqlPath = "/cn/justL/fulcrum/test/xmls/staticSql.xml";

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
        createJPetstoreDataSource();
    }


    @BeforeEach
    public void createConn() throws SQLException, ClassNotFoundException {
        conn = createConnection();
    }

    @Test
    public void dynamicSqlExecutorSimpleTest() throws DynamicSqlConstructionException, DynamicSqlExecutionException {
        DynamicSql dynamicSql = new DynamicSql();
        dynamicSql.setSql(DynamicSQLExecutorTest.class.getResourceAsStream(dynamicSqlPath));

        DynamicSQLExecutor executor = new DynamicSQLExecutor(dynamicSql);

        // select
        //    itemid, productid, listprice, unitcost, supplier, status
        //    from table
        //    where 1 = 1
        //      and itemid = 'EST-1'

        DynamicSQLParams params1 = new DynamicSQLParams();
        params1.setParams(new HashMap() {{
            put("itemId", "EST-1");
        }});
        params1.setConnection(conn);
        DynamicSQLResult rs1 = executor.execute(params1);

        assertNotNull(rs1);
        assertEquals(1, rs1.getResult().size());
        Map firstRowData = rs1.getResult().get(0);
        assertNotNull(firstRowData);
        assertFalse(firstRowData.containsKey("attr1"));
        assertTrue(firstRowData.containsKey("attr2"));
        assertNull(firstRowData.get("attr2"));
        assertEquals("FI-SW-01", firstRowData.get("productid"));


        // select
        //    itemid, productid, listprice, unitcost, supplier, status
        //    from table
        //    where 1 = 1
        //      and itemid = 'EST-1'

        DynamicSQLParams params2 = new DynamicSQLParams();
        params1.setParams(new HashMap() {{
            put("itemIds", Arrays.asList("EST-1","EST-2"));
        }});
        params1.setConnection(conn);
        DynamicSQLResult rs2 = executor.execute(params1);


        // select
        //    itemid, productid, listprice, unitcost, supplier, status
        //    from table
        //    where 1 = 1
        //      and (1 = 2 or itemid = 'EST-1' or itemid = 'EST-2')
        assertNotNull(rs1);
        assertEquals(2, rs1.getResult().size());
        Map firstRowData1 = rs1.getResult().get(0);
        assertNotNull(firstRowData1);
        assertFalse(firstRowData1.containsKey("attr1"));
        assertTrue(firstRowData1.containsKey("attr2"));
        assertNull(firstRowData1.get("attr2"));
        assertEquals("FI-SW-01", firstRowData1.get("productid"));
    }


    @Test
    public void staticSqlTest() throws SQLException, ClassNotFoundException {
        String targetSql = "select * from author where id = 101";
        List goalResult = executeSql(targetSql);



    }


}
