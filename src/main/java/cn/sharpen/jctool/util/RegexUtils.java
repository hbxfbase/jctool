package cn.sharpen.jctool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static void main(String[] args) {

    }

    /**
     * 只要匹配一个，就返回
     *
     * @param src   被匹配的数据
     * @param regex 需要匹配的正则表达式
     * @return
     */
    public static String getMatchOne(String src, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher c = p.matcher(src);
        c.matches();
        while (c.find()) {
            String resultStr = c.group();
            return resultStr;
        }
        return null;
    }

    /**
     * 检查字符串是否符合正则标准
     *
     * @param src   被匹配的数据
     * @param regex 需要匹配的正则表达式
     * @return
     */
    public static boolean matchStr(String src, String regex) {
        if (src == null || regex == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(src).matches();
    }

}
