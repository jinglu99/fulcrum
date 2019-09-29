package cn.justl.fulcrum.parsers.utils;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public final class JavaCodeUtils {

    private static final String SQL_BUILDER = "$$SqlBuilder";
    private static final String PARAM = "$$param";

    public static StringBuilder start() {
        StringBuilder builder = new StringBuilder();
        builder.append("StringBuilder " + SQL_BUILDER + " = new StringBuilder();\n");
        return builder;
    }

    public static StringBuilder append(StringBuilder sb, String text) {
        sb.append(SQL_BUILDER).append(".append(").append(text).append(");\n");
        return sb;
    }

    public static StringBuilder append(StringBuilder sb, StringBuilder text) {
        sb.append(SQL_BUILDER).append(".append(").append(text).append(");\n");
        return sb;
    }

    public static StringBuilder If(StringBuilder sb, StringBuilder cond,
                                         boolean needElse) {
        if (needElse) sb.append("else ");
        sb.append("if (").append(cond).append(") {\n");
        return sb;
    }

    public static StringBuilder EndIf(StringBuilder sb) {
        sb.append("}\n");
        return sb;
    }

    public static StringBuilder Else(StringBuilder sb) {
        sb.append("else {\n");
        return sb;
    }

    public static StringBuilder EndElse(StringBuilder sb) {
        sb.append("}\n");
        return sb;
    }

    public static StringBuilder writeCode(StringBuilder sb, StringBuilder code) {
        sb.append(code).append("\n");
        return sb;
    }

    public static StringBuilder getParam(StringBuilder name) {
        return new StringBuilder(PARAM).append(".get(").append(name).append(")");
    }

}
