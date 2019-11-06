package cn.justl.fulcrum.scripthandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.ValueElParseExcetion;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date : 2019-10-29
 * @Author : jinglu.wang[jingl.wang123@gmail.com]
 * @Desc :
 */
public class ValueELResolver {
    private final static Pattern pattern = Pattern.compile("(?<name>[^:]+)(:(?<type>[^:]+))?(:(?<defaultVal>[\\S\\s]+))?");

    public static ValueHolder getValueHolder(Function<String, Object> supplier, String exp) throws ValueElParseExcetion {
        Matcher matcher = pattern.matcher(exp);
        if (matcher.find()) {
            String name = matcher.group("name");
            String type = matcher.group("type");
            String defaultVal = matcher.group("defaultVal");
            return new ValueHolder(name, supplier.apply(name), type, defaultVal);
        } else
            throw new ValueElParseExcetion("<" + exp + "> can't be resolved!");
    }


    public static void main(String[] args) {
        String str = "test";


        String exp = "(?<name>[^:]+)(:(?<type>[^:]+))?(:(?<defaultVal>[\\S\\s]+))?";

        Pattern pattern = Pattern.compile(exp);

        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
