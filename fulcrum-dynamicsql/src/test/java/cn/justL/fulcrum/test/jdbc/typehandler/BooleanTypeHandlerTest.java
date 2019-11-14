package cn.justL.fulcrum.test.jdbc.typehandler;

import cn.justl.fulcrum.ValueHolder;
import cn.justl.fulcrum.exceptions.TypeHandleException;
import cn.justl.fulcrum.jdbc.typehandler.BooleanTypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @Date : 2019/11/8
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for BooleanTypeHandler")
public class BooleanTypeHandlerTest extends TypeHandlerBaseTest {

    private TestPrepareStatement ps;
    private List<Object> params;
    private TypeHandler handler = new BooleanTypeHandler();

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }

    @BeforeEach
    public void createPS() {
        ps = new TestPrepareStatement();
        params = ps.getParams();
    }


    @Test
    public void theValueIsBooleanTypeIsNullAndDefaultIsNull() throws TypeHandleException {
        ValueHolder holderTrue = new ValueHolder("testTrue", true, null, null);
        ValueHolder holderFalse = new ValueHolder("testFalse", false, null, null);

        assertTrue(handler.isMatch(holderTrue));
        assertTrue(handler.isMatch(holderFalse));

        handler.setParam(ps, 1, holderTrue);
        handler.setParam(ps, 2, holderFalse);

        assertNotNull(params.get(0));
        assertEquals(Boolean.class, params.get(0).getClass());
        assertEquals(true, params.get(0));

        assertNotNull(params.get(1));
        assertEquals(Boolean.class, params.get(1).getClass());
        assertEquals(false, params.get(1));
    }

    @Test
    public void theValueIsNumberTypeIsNullAndDefaultIsNull() {
        ValueHolder byteHolder = new ValueHolder("testByte", 1, null, null);

        assertFalse(handler.isMatch(byteHolder));
    }

    @Test
    public void theValueIsStringTypeIsNullAndDefaultIsNull() {
        ValueHolder holder = new ValueHolder("test", "true", null, null);

        assertFalse(handler.isMatch(holder));
    }

    @Test
    public void theValueIsNumberTypeIsBoolAndDefaultIsNull() throws TypeHandleException {
        ValueHolder trueHolder = new ValueHolder("test",  1, "bool", null);
        ValueHolder falseHolder = new ValueHolder("test",  0, "bool", null);

        assertTrue(handler.isMatch(trueHolder));
        assertTrue(handler.isMatch(falseHolder));

        handler.setParam(ps, 1, trueHolder);
        handler.setParam(ps, 2, falseHolder);

        assertNotNull(params.get(0));
        assertNotNull(params.get(1));

        assertEquals(Boolean.class, params.get(0).getClass());
        assertEquals(Boolean.class, params.get(1).getClass());

        assertEquals(true, params.get(0));
        assertEquals(false, params.get(1));
    }

    @Test
    public void theValueIsStringTypeIsBoolAndDefaultIsNull() throws TypeHandleException {
        ValueHolder trueHolderLower = new ValueHolder("test",  "true", "bool", null);
        ValueHolder trueHolderUpper = new ValueHolder("test",  "TRUE", "bool", null);
        ValueHolder falseHolderLower = new ValueHolder("test",  "false", "bool", null);
        ValueHolder falseHolderUpper = new ValueHolder("test",  "FALSE", "bool", null);
        ValueHolder wrongStrHolder = new ValueHolder("test", "test", "bool", null);

        assertTrue(handler.isMatch(trueHolderLower));
        assertTrue(handler.isMatch(trueHolderUpper));
        assertTrue(handler.isMatch(falseHolderLower));
        assertTrue(handler.isMatch(falseHolderUpper));
        assertTrue(handler.isMatch(wrongStrHolder));

        handler.setParam(ps, 1, trueHolderLower);
        assertNotNull(params.get(0));
        assertEquals(Boolean.class, params.get(0).getClass());
        assertEquals(true, params.get(0));

        handler.setParam(ps, 2, trueHolderUpper);
        assertNotNull(params.get(1));
        assertEquals(Boolean.class, params.get(1).getClass());
        assertEquals(true, params.get(1));

        handler.setParam(ps, 3, falseHolderLower);
        assertNotNull(params.get(2));
        assertEquals(Boolean.class, params.get(2).getClass());
        assertEquals(false, params.get(2));

        handler.setParam(ps, 4, falseHolderUpper);
        assertNotNull(params.get(3));
        assertEquals(Boolean.class, params.get(3).getClass());
        assertEquals(false, params.get(3));

        assertThrows(TypeHandleException.class, () -> {
            handler.setParam(ps, 5, wrongStrHolder);
        });
    }

    @Test
    public void defaultExpTest() throws TypeHandleException {
        ValueHolder trueExpLower = new ValueHolder("test", null, "bool", "true");
        ValueHolder trueExpUpper = new ValueHolder("test", null, "bool", "TRUE");

        ValueHolder falseExpLower = new ValueHolder("test", null, "bool", "false");
        ValueHolder falseExpUpper = new ValueHolder("test", null, "bool", "FALSE");

        ValueHolder wrongExp = new ValueHolder("test", null, "bool", "Test");

        handler.setParam(ps, 1, trueExpLower);
        assertEquals(true, params.get(0));

        handler.setParam(ps, 2, trueExpUpper);
        assertEquals(true, params.get(1));

        handler.setParam(ps, 3, falseExpLower);
        assertEquals(false, params.get(2));

        handler.setParam(ps, 4, falseExpUpper);
        assertEquals(false, params.get(3));

        assertThrows(TypeHandleException.class, () -> {
            handler.setParam(ps, 5, wrongExp);
        });

    }



}
