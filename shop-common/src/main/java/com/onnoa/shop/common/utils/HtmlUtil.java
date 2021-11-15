package com.onnoa.shop.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author onnoA
 * @Description html 标签处理工具类
 * @date 2021年07月23日 16:15
 */
public class HtmlUtil {
    
    /**
     * @Description 替换指定标签的属性值
     * @author onnoA
     * @date 2021/7/23 16:16
     * @param str
     * @param tag
     * @param tagAttrib
     * @param startTag
     * @param endTag 
     * @return java.lang.String
     */
    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*";
        String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"";
        Pattern patternForTag = Pattern.compile(regxpForTag, Pattern.CASE_INSENSITIVE);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib, Pattern.CASE_INSENSITIVE);
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer("<" + tag + " ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                String attributeStr = matcherForAttrib.group(1);
                matcherForAttrib.appendReplacement(sbreplace, startTag + attributeStr + endTag);
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBuffer content = new StringBuffer();
        content.append("<p><img src=\"http://172.16.25.133/portal-web/image/1625570781602096612.jpeg\" title=\"1625570781602096612.jpeg\" alt=\"提醒图标.jpeg\"/></p><p>this is test</p>");
        content.append("<li><img id=\"150628\" src=\"uploads/allimg/150628/1-15062Q12247.jpg\" class=\"src_class\"></li></ul>");
        System.out.println("原始字符串为:" + content.toString());
        String newStr = replaceHtmlTag(content.toString(), "img", "src", "src=\"http://junlenet.com/", "\"");
        System.out.println("       替换后为:" + newStr);
    }
}
