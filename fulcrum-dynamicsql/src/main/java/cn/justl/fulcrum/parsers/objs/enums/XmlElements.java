package cn.justl.fulcrum.parsers.objs.enums;

import org.w3c.dom.Node;

import java.util.Arrays;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public enum XmlElements {

    SQL("sql"),
    IF("if"),
    FOR("for");
    private String name;

    XmlElements(String name) {
        this.name = name;
    }

    public boolean isMatch(Node node) {
        return name.toLowerCase().equals(node.getNodeName().toLowerCase());
    }

    public static boolean isDefaultElement(Node node) {
        return Arrays.stream(XmlElements.values()).anyMatch(x -> x.name.equals(node.getNodeName().toLowerCase()));
    }
}
