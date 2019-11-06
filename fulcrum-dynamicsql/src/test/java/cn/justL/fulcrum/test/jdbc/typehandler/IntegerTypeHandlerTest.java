package cn.justL.fulcrum.test.jdbc.typehandler;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.jdbc.typehandler.IntegerTypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @Date : 2019-11-06
 * @Author : Jinglu.Wang [wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for IntegerTypeHandler")
public class IntegerTypeHandlerTest extends DBTest {
    private PreparedStatement ps;
    private TypeHandler handler = new IntegerTypeHandler();

    @BeforeAll
    public static void setupDB() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }

    @BeforeEach
    public void createPrepareStatement() throws SQLException, ClassNotFoundException {
        ps = createConnection().prepareStatement("select * from author where id = ?");
    }

    @Test
    public void inputNullValueHolder() throws SQLException, ClassCastException {
        assertFalse(handler.isMatch(null));
        assertThrows(SQLExecuteException.class, () -> {
            handler.setParam(ps, 1, null);
        });
    }

    @Test
    public void theTypeOfValueIsByte() throws SQLExecuteException, SQLException {
        ValueHolder holder = new ValueHolder("test", (byte) 1, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }

    @Test
    public void theTypeOfValueIsShort() throws SQLExecuteException, SQLException {
        ValueHolder holder = new ValueHolder("test", (short) 1, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }

    @Test
    public void theTypeOfValueIsInteger() throws SQLExecuteException, SQLException {
        ValueHolder holder = new ValueHolder("test", 1, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }

    @Test
    public void theTypeOfValueIsLong() throws SQLException, SQLExecuteException {
        ValueHolder holder = new ValueHolder("test", 100l, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }

    @Test
    public void theTypeOfValueIsFloat() throws SQLExecuteException, SQLException {
        ValueHolder holder = new ValueHolder("test", 1.0, null, null);

        assertFalse(handler.isMatch(holder));
    }


    @Test
    public void numberValueNotSupportTypeButDeclareTypeIsInt() throws SQLException, SQLExecuteException {
        ValueHolder holder = new ValueHolder("test", 1.0, "int", null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }

    @Test
    public void stringValueNotSupportTypeButDeclareTypeIsInt() throws SQLException, SQLExecuteException {
        ValueHolder holder = new ValueHolder("test", "1", "int", null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }

    @Test
    public void nullValueAndDefaultIsNull() {
        ValueHolder holder = new ValueHolder("test", null, "int", null);

        assertTrue(handler.isMatch(holder));

        assertThrows(SQLExecuteException.class, () -> {
            handler.setParam(ps, 1, holder);
        });
    }

    @Test
    public void correctDefaultExpExist() throws SQLException, SQLExecuteException {
        ValueHolder holder = new ValueHolder("test", null, "int", "1");

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertEquals(Types.INTEGER, ps.getParameterMetaData().getParameterType(1));
    }


    @Test
    public void incorrectDefaultExp() throws SQLExecuteException, SQLException {
        ValueHolder holder = new ValueHolder("test", null, "int", "number");

        assertTrue(handler.isMatch(holder));

        assertThrows(SQLExecuteException.class, () -> {
            handler.setParam(ps, 1, holder);
        });
    }

}
