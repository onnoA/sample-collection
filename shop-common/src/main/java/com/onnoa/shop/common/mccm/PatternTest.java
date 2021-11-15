package com.onnoa.shop.common.mccm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月24日 21:21
 */
public class PatternTest {

    private static final Pattern FREEMARKER_PATTERN = Pattern.compile("\\$\\{\\(*(.+?)\\}");

    private static Pattern funcPattern = Pattern.compile("[(, ]*([_a-zA-Z][_0-9a-zA-Z]*)[,) ]");

    public static void main(String[] args) {
//        List<String> list = parseParamFromTemplate("<p><img src=\"http://172.16.25.133/portal-web/image/1606911283173051696.jpg\" title=\"1606911283173051696.jpg\" alt=\"质量红线牢记2.JPG\"/>${CAMPAIGN_NAME}</p>");
        List<String> list = parseParamFromTemplate("${hello,your age is ${age},your gender is ${gender},you are high_flux${IS_HIGH_FLUX},your subsid is ${SUBS_ID},your email address is ${email}}");
        System.out.println(JSON.toJSON(list));
    }

    public static List<String> parseParamFromTemplate(String template) {
        List<String> result = new ArrayList<>();
        Set<String> exist = new HashSet<>();
        Matcher matcher = FREEMARKER_PATTERN.matcher(template);
        while (matcher.find()) {
            String match = matcher.group(1);
            if (match.contains("?")) {
                match = match.split("\\?")[0];
            }
            Matcher secondMatcher = funcPattern.matcher(match);
            boolean found = false;
            while (secondMatcher.find()) {
                found = true;
                String second = secondMatcher.group(1);
                if (!exist.contains(second)) {
                    exist.add(second);
                    result.add(second);
                }
            }
            if (!found && !exist.contains(match)) {
                exist.add(match);
                result.add(match);
            }
        }
        return result;
    }
}
