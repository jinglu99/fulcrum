package cn.justL.fulcrum.test.xml;

import cn.justl.fulcrum.parsers.exceptions.XmlParseException;
import cn.justl.fulcrum.parsers.xml.XMLSqlParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class XMLSqlParserTest {

    @Test
    public void XMLSqlParserSimpleTest() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, XmlParseException {
        XMLSqlParser parser = new XMLSqlParser(XMLSqlParser.class.getResourceAsStream("/sql.xml"));
        parser.parse();
    }
}
