package cn.hengzq.orange.ai.common.util;

import cn.hutool.core.convert.Convert;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderUtils {

    // 正则：匹配 {{variable}}，捕获 variable 部分
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)}}");


    /**
     * 替换字符串中所有的 {{key}} 占位符为对应值
     *
     * @param text   原始模板字符串
     * @param values 变量映射表，如 {"name": "Alice"}
     * @return 替换后的字符串，未匹配的占位符默认保留原样
     */
    public static String replacePlaceholders(String text, Map<String, Object> values) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        if (values == null || values.isEmpty()) {
            return text;
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1).trim(); // 提取变量名并去空格
            String replacement = Convert.toStr(values.getOrDefault(key, matcher.group(0))); // 找不到则保留原占位符
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String template = "Hello, {{name}}! You are {{age}} years old.";
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Alice");
        values.put("age", 25);

        String result = PlaceholderUtils.replacePlaceholders(template, values);
        System.out.println(result); // 输出: Hello, Alice! You are 25 years old.
    }

}
