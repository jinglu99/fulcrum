package cn.justL.fulcrum.test.xml;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.parsers.exceptions.ScriptFailedException;
import cn.justl.fulcrum.parsers.exceptions.XmlParseException;
import cn.justl.fulcrum.parsers.handlers.ScriptHandler;
import cn.justl.fulcrum.parsers.xml.XMLSqlParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class XMLSqlParserTest {

    @Test
    public void XMLSqlParserSimpleTest() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, XmlParseException, ScriptFailedException {
        Map param = new HashMap();
        param.put("a", 1);
        param.put("list", new ArrayList() {{
            add(new HashMap() {{
                put("a",2);
            }});
            add(new HashMap() {{
                put("a",3);
            }});
        }});

        ExecuteContext context = new ExecuteContext();
        context.getParams().setParams(param);

        XMLSqlParser parser = new XMLSqlParser(XMLSqlParser.class.getResourceAsStream("/sql.xml"));
        ScriptHandler scriptHandler = parser.parse();
        StringBuilder process = scriptHandler.process(context);
        System.out.println(process.toString());
    }
}
