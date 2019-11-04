package cn.justL.fulcrum.test.parsers;

import cn.justl.fulcrum.exceptions.XmlParseException;
import cn.justl.fulcrum.parsers.XMLSqlParser;
import cn.justl.fulcrum.scripthandler.ScriptHandler;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Date : 2019/11/4
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("XMLSqlParser Test")
public class XMLSqlParserTest {

    private final static String ROOT = "/cn/justL/fulcrum/test/parses/xmls/";
    private static InputStream simpleSql = XMLSqlParserTest.class.getResourceAsStream(  ROOT + "simpleSql.xml");

    @Test
    public void simpleTest() throws IOException, XmlParseException {
        XMLSqlParser parser = new XMLSqlParser(simpleSql);
        ScriptHandler handler = parser.parse();
    }

}
