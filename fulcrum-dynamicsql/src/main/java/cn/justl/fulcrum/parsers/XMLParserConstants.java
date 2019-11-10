package cn.justl.fulcrum.parsers;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class XMLParserConstants {
    public static final String DEFAULT_INDEX_NAME = "##_INDEX";
    public static final String DEFAULT_SEPARATOR = "";


    public static final String NAMESPACE_URL = "http://jingl.wang/fulcrum-dynamic-sql.xsd";

    public static final String SQL_ELEMENT = "SQL";
    public static final String IF_ELEMENT = "if";
    public static final String IF_CASE_ELEMENT = "case";
    public static final String IF_ELSE_ELEMENT = "else";
    public static final String FOR_ELEMENT = "for";

    public static final String IF_CASE_COND_ATTR = "cond";

    public static final String FOR_COLLECTION_ATTR = "collection";
    public static final String FOR_ITEM_ATTR = "item";
    public static final String FOR_INDEX_ATTR = "index";
    public static final String FOR_SEPARATOR_ATTR = "separator";
}
