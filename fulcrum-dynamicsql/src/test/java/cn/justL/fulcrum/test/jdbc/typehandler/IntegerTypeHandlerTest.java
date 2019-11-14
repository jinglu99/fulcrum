package cn.justL.fulcrum.test.jdbc.typehandler;

import cn.justl.fulcrum.ValueHolder;
import cn.justl.fulcrum.exceptions.TypeHandleException;
import cn.justl.fulcrum.jdbc.typehandler.IntegerTypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Date : 2019-11-06
 * @Author : Jinglu.Wang [wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for IntegerTypeHandler")
public class IntegerTypeHandlerTest extends TypeHandlerBaseTest {
    private TestPrepareStatement ps;
    private List<Object> params;
    private TypeHandler handler = new IntegerTypeHandler();

    @BeforeEach
    public void createConnAndPS() {
        ps = new TestPrepareStatement();
        params = ps.getParams();
    }

    @BeforeAll
    public static void newDBANDHandler() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }

    @Test
    public void inputNullValueHolder() throws SQLException, ClassCastException {
        assertFalse(handler.isMatch(null));
        assertThrows(TypeHandleException.class, () -> {
            handler.setParam(ps, 1, null);
        });
    }

    @Test
    public void theTypeOfValueIsByte() throws TypeHandleException, SQLException {
        ValueHolder holder = new ValueHolder("test", (byte) 1, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Byte.class, params.get(0).getClass());
        assertEquals((byte)1, params.get(0));
    }

    @Test
    public void theTypeOfValueIsShort() throws TypeHandleException, SQLException {
        ValueHolder holder = new ValueHolder("test", (short) 1, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Short.class, params.get(0).getClass());
        assertEquals((short) 1, params.get(0));
    }

    @Test
    public void theTypeOfValueIsInteger() throws TypeHandleException, SQLException {
        ValueHolder holder = new ValueHolder("test", 1, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Integer.class, params.get(0).getClass());
        assertEquals(1, params.get(0));
    }

    @Test
    public void theTypeOfValueIsLong() throws SQLException, TypeHandleException {
        ValueHolder holder = new ValueHolder("test", 100L, null, null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Long.class, params.get(0).getClass());
        assertEquals(100L, params.get(0));
    }

    @Test
    public void theTypeOfValueIsFloat() throws TypeHandleException, SQLException {
        ValueHolder holder = new ValueHolder("test", 1.0, null, null);

        assertFalse(handler.isMatch(holder));
    }


    @Test
    public void numberValueNotSupportTypeButDeclareTypeIsInt() throws SQLException, TypeHandleException {
        ValueHolder holder = new ValueHolder("test", 1.0, "int", null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Long.class, params.get(0).getClass());
        assertEquals(1L, params.get(0));
    }

    @Test
    public void stringValueNotSupportTypeButDeclareTypeIsInt() throws SQLException, TypeHandleException {
        ValueHolder holder = new ValueHolder("test", "1", "int", null);

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Long.class, params.get(0).getClass());
        assertEquals(1L, params.get(0));
    }

    @Test
    public void nullValueAndDefaultIsNull() {
        ValueHolder holder = new ValueHolder("test", null, "int", null);

        assertTrue(handler.isMatch(holder));

        assertThrows(TypeHandleException.class, () -> {
            handler.setParam(ps, 1, holder);
        });
    }

    @Test
    public void correctDefaultExpExist() throws SQLException, TypeHandleException {
        ValueHolder holder = new ValueHolder("test", null, "int", "1");

        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Long.class, params.get(0).getClass());
        assertEquals(1L, params.get(0));
    }


    @Test
    public void incorrectDefaultExp() throws TypeHandleException, SQLException {
        ValueHolder holder = new ValueHolder("test", null, "int", "number");

        assertTrue(handler.isMatch(holder));

        assertThrows(TypeHandleException.class, () -> {
            handler.setParam(ps, 1, holder);
        });
    }


}
