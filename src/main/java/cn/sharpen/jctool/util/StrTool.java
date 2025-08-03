package cn.sharpen.jctool.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.extra.template.TemplateEngine;
import cn.sharpen.jctool.consts.RegexConst;
import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.consts.SymbolConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.MDC;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import static cn.hutool.core.text.StrPool.CRLF;
import static cn.sharpen.jctool.consts.SignConst.*;
import static cn.sharpen.jctool.consts.SymbolConst.*;

/**
 * 字符串相关的工具
 */
@Slf4j
public class StrTool {

    public static final char[] VIEW_CODE="QT23456789ABCDEFGHKMNPRSVWXY".toCharArray();

    public StrTool(){};

    /**
     * 打印字符串
     * @param o 要打印的对象
     */
    public static void p(Object o) {
        System.out.print(o);
    }

    /**
     * 打印字符串并换行
     * @param o 要打印的对象
     */
    public static void pl(Object o) {
        System.out.println(o);
    }
    /**
     * 打印字符串并换行
     * @param o 要打印的对象
     */
    public static void pl2(Object o) {
        System.out.println();
        System.out.println(o);
        System.out.println();
    }

    /**
     * 打印字换行
     */
    public static void pl2() {
        System.out.println();
        System.out.println();
    }
    /**
     * 打印字换行
     */
    public static void pl() {
        System.out.println();
    }
    /**
     * 打印字换行
     */
    public static void plf(String format, String ...places) {
        System.out.println(StrUtil.format(format,places));
    }

    public static void plf(String format, Object ...places) {
        System.out.println(StrUtil.format(format,places));
    }

    /**
     * 字符是否等于Y
     * @param str
     * @return
     */
    public static boolean asy(String str){
        return StringUtils.equals(str, Y);
    }

    public static boolean asy(String str, Boolean def){
        return StringUtils.isBlank(str) ? def : StringUtils.equals(str, Y);
    }
    /**
     * 字符是否不等于Y
     * @param str
     * @return
     */
    public static boolean asny(String str){
        return !StringUtils.equals(str, Y);
    }

    public static boolean asny(String str, Boolean def){
        return StringUtils.isBlank(str) ? def : !StringUtils.equals(str, Y);
    }



    /**
     * 将多个字符串用spliceStr拼接起来
     * @param spliceStr 拼接的字符串
     * @return
     */
    public static String splice(String spliceStr, String ...strs){
        if(CollectionUtils.size(strs)<2){
            return strs[0];
        }
        StringBuilder sb = new StringBuilder(strs[0]);
        for(int i=1, size = strs.length; i<size; ++i){
            sb.append(spliceStr).append(strs[i]);
        }
        return sb.toString();
    }

    /**
     * 将JAVA包转成路径（例com.company.util转成com/company/util）
     * @param pack 包，例：com.company.util
     * @return 路径字符串
     */
    public static String pack2dir(String pack){
        if(StringUtils.isBlank(pack)){
            return SymbolConst.BLANK;
        }
        return pack.replaceAll(RegexConst.DOT, Matcher.quoteReplacement(File.separator));
    }

    public static String parentheses(Number origin) {
        return origin == null ?null : parentheses(origin.toString());
    }
    public static String parentheses(CharSequence origin){
        return StrUtil.wrap(origin, OPEN_PARENTHESES, CLOSE_PARENTHESES);
    }

    /**
     * 文件名时间
     * @return 文件名中的时间格式字符串，yyyyMMddHHmmss
     */
    public static String fnt(){
        return DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);
    }
    public static Date timeDef(String dateStr){
        return DateUtil.parseDateTime(dateStr);
    }
    /**
     * 当前时间的打印格式的字符串
     * @return 文件名中的时间格式字符串，yyyy-MM-dd HH:mm:ss
     */
    public static String printTime(){
        return DatePattern.NORM_DATETIME_FORMAT.format(new Date());
    }

    /**
     * 获取可打印的时间
     * @param date
     * @return
     */
    public static String printTime(Date date){
        if(date==null) {
            return null;
        }
        return DatePattern.NORM_DATETIME_FORMAT.format(date);
    }

    public static String printDate(Date date){
        if(date==null) {
            return null;
        }
        return DatePattern.NORM_DATE_FORMAT.format(date);
    }
    public static Integer timePrint2int(String timeStr) {
        return Integer.valueOf(DatePattern.PURE_DATE_FORMAT.format(DateUtil.parse(timeStr)));
    }

    public static String tagDay(Date date){
        return DatePattern.PURE_DATE_FORMAT.format(date);
    }

    public static Long spendMilli(long aaNano, long bbNano){
        return Math.abs((aaNano - bbNano)/1000000);
    }

    public static Long spendMilli(long bbNano){
        return Math.abs((System.nanoTime() - bbNano)/1000000);
    }

    /**
     * 字符串的首字母转成大写，例filedName被转成FieldName
     * @param originStr
     * @return 首字母转成大写的字符串
     */
    public static String upperFirst(String originStr){
        if(StringUtils.isNotBlank(originStr)){
            return originStr.substring(0,1).toUpperCase()+originStr.substring(1);
        }
        return originStr;
    }

    /**
     * 是否包含空白字符串
     * @param strs
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(String ...strs){
        if(strs == null){
            return true;
        }
        for(String str : strs){
            if(StringUtils.isBlank(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否无空白，即不含空白字符串
     * @param strs
     * @return 是否全部不为空白
     */
    public static Boolean noBlank(String ...strs){
        if(ArrayUtils.isEmpty(strs)){
            return true;
        }
        for(String str : strs ){
            if(StringUtils.isBlank(str)){
                return false;
            }
        }
        return true;
    }


    /**
     * 获取第一个不为空白的字符串
     * @param strs
     * @return 为空白的字符串
     */
    public static String valNoBlank(String ...strs){
        if(ArrayUtils.isEmpty(strs)){
            return null;
        }
        for(String str : strs ){
            if(StringUtils.isNotBlank(str)){
                return str;
            }
        }
        return null;
    }

    /**
     * 是否包含字符串
     * @param judge 判断的字符串
     * @param strs 比对字符串
     * @return 是否包含
     */
    public static boolean inAny(String judge, String ...strs){
        if(strs == null){
            return false;
        }
        for(String str : strs){
            if(StringUtils.equals(judge, str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 值转换。如果没有匹配的值，返回默认值，否则返回。参数中的数组，第1，3，5...个是匹配值，第2，4，6...个是返回值，
     * @param matchVal 判断值
     * @param defVal 默认值
     * @param candidate 比对值数组
     * @return 判断后符合的值
     */
    public static String valConvert(String matchVal, String defVal, String ...candidate){
        if(candidate == null || candidate.length<2){
            return defVal;
        }
        for(int i=0, leng = candidate.length-1; i<leng; i+=2){
            if(StringUtils.equals(matchVal,candidate[i])){
                return candidate[i+1];
            }
        }
        return defVal;
    }

    /**
     * 值匹配。如果没有匹配的值，返回默认值，否则返回matchVal
     * @param matchVal 判断值
     * @param defVal 默认值
     * @param candidate 比对值数组
     * @return 判断后符合的值
     */
    public static String valMatchReturn(String matchVal, String defVal, String ...candidate){
        if(candidate == null || candidate.length==0){
            return defVal;
        }
        for(int i=0, leng = candidate.length-1; i<=leng; i++){
            if(StringUtils.equals(matchVal,candidate[i])){
                return candidate[i];
            }
        }
        return defVal;
    }

    public static void main(String[] args) {
        String a = StrTool.valMatchReturn("cellphone", "email", "cellphone_code", "cellphone");
        System.out.println(a);
    }



    /**
     * 表的属性转成变量名
     * @param dbStr
     * @return 变量名
     */
    public static String db2var(String dbStr){
        if(StringUtils.isBlank(dbStr) || !StringUtils.contains(dbStr, StrPool.UNDERLINE)){
            return dbStr;
        }
        StringBuilder sb = new StringBuilder();
        for(String str : dbStr.split(StrPool.UNDERLINE)){
            sb.append(StringUtils.isBlank(sb) ? str : upperFirst(str));
        }
        return sb.toString();
    }

    public static String db2cls(String dbStr){
        return upperFirst(db2var(dbStr));
    }


    /**
     * 去掉16进制的0x前缀
     * @param hex 需要处理的16进制字符串
     * @return 处理后的值
     */
    public static String removeHexPre(String hex) {
        return StrUtil.removePrefix(hex, PRE_0X);
    }
    /**
     * 删除开始的65279字符
     * @param str 需要处理的字符串
     * @return 处理后的值
     */
    public static String removeStart65279(String str) {
        char cha = 65279;
        while (str.startsWith(cha + "")) {
            if (str.length() == 1) {
                return "";
            }
            str = str.substring(1);
        }
        return str;
    }

    public static boolean endStartWithStr(String str, String judgeStr) {
        return StrUtil.startWith(str, judgeStr) || StrUtil.endWith(str, judgeStr);
    }

    /**
     * 删除前后的指定所有字符串
     * @param str 需要处理的字符串
     * @return 处理后的值
     */
    public static String removeTrimPreSufAny(String str, String ...removeStr) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        int length = str.length();
        int newLength = 0;

        do {
            length = str.length();
            for(String elem : removeStr) {
                str = str.trim();
                str = removeTrimPreSuf(str, elem, elem);
                str = str.trim();
            }
            // 一直删除到删除不了为止
            newLength = str.length();
        }while (newLength<length);
        return str;
    }

    /**
     * 删除前后的指定字符串
     * @param str 需要处理的字符串
     * @return 处理后的值
     */
    public static String removeTrimPreSuf(String str, String pre, String suf) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        str = str.trim();
        str = StrUtil.removeSuffix(StrUtil.removePrefix(str, pre), suf);
        str = str.trim();
        return str;
    }

    /**
     * 删除前后的指定字符串
     * @param str 需要处理的字符串
     * @return 处理后的值
     */
    public static String removeTrimPreSuf(String str, String removeStr) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        str = str.trim();
        str = StrUtil.removeSuffix(StrUtil.removePrefix(str, removeStr), removeStr);
        str = str.trim();
        return str;
    }


    public static StringWriter vec2sw(VelocityEngine ve, String content, VelocityContext context){
        StringWriter writer = new StringWriter();
        ve.evaluate(context, writer, "vec2sw", content);
        return writer;
    }


    public static String vec2str(VelocityEngine ve, String content, Map<String, String> params){
        VelocityContext context = new VelocityContext();
        if(MapUtil.isNotEmpty(params)) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                context.put(param.getKey(), param.getValue());
            }
        }
        ve = ve ==null ? defaultStrVe(null) : ve ;
        StringWriter writer = new StringWriter();
        ve.evaluate(context, writer, vec2str, content);
        return writer.toString();
    }

    public static StringWriter ve2sw(VelocityEngine ve, String vePath, VelocityContext context){
        StringWriter writer = new StringWriter();
        Template template = ve.getTemplate(vePath, CharsetUtil.UTF_8);
        template.merge(context, writer);
        return writer;
    }
    public static StringWriter ve2sw(VelocityEngine ve, Template template, VelocityContext context){
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    // static VelocityEngine veDefault = new VelocityEngine();
    /**
     * 获取默认VelocityEngine对象
     * ResourceLoader参考: https://www.iteye.com/blog/zhouzba-2093650
     */
    public static VelocityEngine defaultVe(){
        return defaultVe(null);
    }

    public static VelocityEngine veAbsolutePath(){
        Properties prop = new Properties();
        prop.put("resource.loader", "file");
        prop.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "");
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        VelocityEngine ve = StrTool.defaultVe(prop);
        return ve;
    }

    // static VelocityEngine veDefault = new VelocityEngine();
    /**
     * 获取默认VelocityEngine对象
     * ResourceLoader参考: https://www.iteye.com/blog/zhouzba-2093650
     */
    public static VelocityEngine defaultVe(Properties prop){
        VelocityEngine veDefault = new VelocityEngine();
        veDefault.setProperty("input.encoding", StandardCharsets.UTF_8.name());
        Properties p = new Properties();
        p.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.put("directive.foreach.counter.name","velocityCount");
        p.put("directive.foreach.counter.initial.value", 1);
        p.put("input.encoding", StandardCharsets.UTF_8.name());
        p.put("output.encoding", StandardCharsets.UTF_8.name());
        if(MapUtils.isNotEmpty(prop)){
            p.putAll(prop);
        }

        veDefault.init(p);
        return veDefault;
    }

    public static VelocityEngine defaultStrVe(Properties prop){
        VelocityEngine veDefault = new VelocityEngine();
        veDefault.setProperty("input.encoding", StandardCharsets.UTF_8.name());
        Properties p = new Properties();
        p.put("directive.foreach.counter.name","velocityCount");
        p.put("directive.foreach.counter.initial.value", 1);
        p.put("input.encoding", StandardCharsets.UTF_8.name());
        p.put("output.encoding", StandardCharsets.UTF_8.name());
        if(MapUtils.isNotEmpty(prop)){
            p.putAll(prop);
        }

        veDefault.init(p);
        return veDefault;
    }

    /**
     * 字符串掩码
     * @param str 需要处理的字符串
     * @return 掩码后的字符串
     */
    public static String mask(String str) {
        return mask(str, 2, 6);
    }

    /**
     * 字符串掩码
     * @param str 需要处理的字符串
     * @param type 掩码类型，1隐藏前面，2，隐藏中间，3，隐藏末尾，4，隐藏中间。
     * @param hideLeng 掩码的数量
     * @return 掩码后的字符串
     */
    public static String mask(String str, Integer type, Integer hideLeng) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        int start = 0, end = str.length(), leng = str.length();
        if (leng > hideLeng) {
            if (type == SignConst.ONE.intValue()) {
                // 隐藏前面
                end = hideLeng;
            } else if (type == SignConst.TWO.intValue()) {
                // 隐藏中间
                start = (leng - hideLeng) / 2;
                end = start + hideLeng;
            } else if (type == SignConst.THREE.intValue()) {
                // 隐藏末尾
                start = leng - hideLeng;
            } else if (type == SignConst.FOUR.intValue()) {
                // 隐藏两边
                start = leng - hideLeng;
                return StrUtil.hide(StrUtil.hide(str, 0, (leng - hideLeng) / 2), (leng + hideLeng) / 2, end);
            }
        }
        //
        return StrUtil.hide(str, start, end);
    }



    public static AtomicInteger order = new AtomicInteger(9999999);

    /**
     * 获取ID默认方式
     * @return 默认获取的不重复的ID
     */
    public static String getId(){
        return System.currentTimeMillis()/1000+"" + order.addAndGet(1);
    }

    /**
     * 获取ID默认方式
     * @return 默认获取的不重复的ID
     */
    public static long getIdLong(){
        return Long.valueOf(getId());
    }
    /**
     * 获取ID默认方式
     * @return 默认获取的不重复的ID
     */
    public static int getIdInteger(){
        return Long.valueOf(getId()).intValue();
    }
    /**
     * 获取ID默认方式
     * @return 默认获取的不重复的ID
     */
    public static String getIdStr(){
        return Long.valueOf(getId()).toString();
    }

    // 将数字转成高可读性的28进制
    public static String getViewCodeStr(long num){
        // 获取原生的28进制
        String radixStr = Long.toString(num, 28);
        // 易读版的可视化28进制
        StringBuffer viewRadix = new StringBuffer();
        for(char str : radixStr.toCharArray()){
            char radix28 = VIEW_CODE[Integer.valueOf(str+"", 28)];
            viewRadix.append(radix28);
        }
        return viewRadix.toString();
    }

    /**
     * 获取栈字符串
     * @return 栈字符串
     */
    public static String getStackStr() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        return getStackStr(stacks);
    }
    /**
     * 获取栈字符串
     * @return 栈字符串
     */
    public static String getStackStr(StackTraceElement[] stackElements) {
        StringBuilder sb = new StringBuilder();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                sb.append(TAB_SPACE+TAB_SPACE).append(stackElements[i].getClassName() + TAB_SPACE);
                sb.append(stackElements[i].getFileName() + TAB_SPACE);
                sb.append(stackElements[i].getLineNumber() + TAB_SPACE);
                sb.append(stackElements[i].getMethodName() + TAB_SPACE+ CRLF);
            }
        }
        return sb.toString();
    }

    /**
     * 获取异常的栈字符串
     * @param e 异常
     * @return 栈字符串
     */
    public static String getStackStr(Exception e) {
        if (e == null) {
            return null;
        }
        StackTraceElement[] stackElements = e.getStackTrace();
        return getStackStr(stackElements);
    }


    public static void newTraceNo() {
        MDC.put(TRACE_NO, RandomStringUtils.randomAlphanumeric(12));
    }

    public static void clearTraceNo() {
        MDC.remove(TRACE_NO);
    }
    public static String getTraceNo() {
        return MDC.get(TRACE_NO);
    }
    // 时间相关，参考老项目的StringUtil
    public static final DecimalFormat df = new DecimalFormat("#0.00000000");


    /**
     * 格式化资产
     * DecimalFormat.format()会导致四舍五入，用户转账全部资产的时候会出现余额不足 直接截取8位
     * ref: com.ngdes.api.wallet.service.common.util.StringUtil
     * @param avail 处理的数字
     * @return 格式化后的数字
     */
    public static String formatAsset(BigDecimal avail) {
        if (avail == null || avail.compareTo(new BigDecimal(SignConst.STR_ZERO)) == 0) {
            return df.format(0);
        }

        //没有小数点，或者小数点后不足8位小数
        String availString = avail.toPlainString();
        if (availString.indexOf(".") == -1
                    || availString.substring(availString.indexOf(".") + 1).length() < 8) {
            return df.format(avail);
        }

        return availString.substring(0, availString.lastIndexOf(".") + 9);
    }


    /**
     * 将字符串拆分，过滤掉空白，截掉前后的空白。去重
     * @param origin 需要处理的源字符串
     * @return 字符串拆分后放到集合中
     */
    public static LinkedHashSet<String> str2set(String origin) {
        List<String> list = str2list(origin, null);
        LinkedHashSet<String> set = new LinkedHashSet<>(list);
        return set;
    }


    /**
     * 将字符串拆分，过滤掉空白，截掉前后的空白。去重
     *
     * @param origin 需要处理的源字符串
     * @param split 拆分的分隔字符串。如果为空，默认为英文逗号:","
     * @return 拆分字符串后的集合
     */
    public static Set<String> str2set(String origin, String split) {
        List<String> list = str2list(origin, split);
        Set<String> set = new HashSet<>(list);
        return set;
    }

    /**
     * 数字字符串转成Long后放到集合中
     * @param strs 数字字符
     * @return 数字集合
     */
    public static Set<Long> str2Long4set(Collection<String> strs) {
        if (CollectionUtils.isEmpty(strs)) {
            return null;
        }
        Set<Long> set = new HashSet<>();
        for (String str : strs) {
            if (NumberUtil.isNumber(str)) {
                set.add(Long.valueOf(str));
            }
        }
        return set;
    }

    public static List<List<String>> csvText2list(String csvText){
        List<List<String>> list = new ArrayList<>();
        if(StringUtils.isBlank(csvText)) {
            return list;
        }
        String[] csvList = csvText.split(StrPool.LF);
        for(String elem : csvList) {
            String[] rowList = elem.split(StrPool.TAB);
            list.add(Arrays.asList(rowList));
        }
        return list;
    }

    /**
     * 根据list的索引取值组成map
     * @param list
     * @param keyIdx
     * @param valIdx
     * @return list转map
     */
    public static Map<String, String> listIdxVal2map(List<List<String>> list, int keyIdx, int valIdx) {
        Map<String, String> map = new HashMap<>();
        if(CollectionUtils.isEmpty(list)) {
            return map;
        }
        int maxIdx = Math.max(keyIdx, valIdx);
        for(List<String> elem : list) {
            int listMaxIdx = CollectionUtils.size(elem)-1;
            if(maxIdx>listMaxIdx) {
                continue;
            }
            map.put(elem.get(keyIdx), elem.get(valIdx));
        }
        return map;
    }

    /**
     * 将字符串拆分，过滤掉空白，截掉前后的空白
     * @param origin 需要处理的源字符串
     * @return 字符串列表
     */
    public static List<String> splitManyGeneral(String origin){
        return splitManyGeneral(origin, COMMON_SPLITS);
    }
    /**
     * 将字符串拆分，过滤掉空白，截掉前后的空白
     *
     * @param origin 需要处理的源字符串
     * @param splits 拆分的分隔字符串。如果为空，默认为英文逗号:","
     * @return 字符串列表
     */
    public static List<String> splitManyGeneral(String origin, String ...splits) {
        List<String> list = new ArrayList<>();
        if(StringUtils.isBlank(origin)){
            return list;
        }
        if(ArrayUtils.isEmpty(splits)){
            splits = new String[]{StrPool.COMMA};
        }
        for(String split : splits){
            if(CollectionUtils.isEmpty(list)){
                list = str2list(origin, split);
            }else {
                List<String> temp = new ArrayList<>();
                for(String str : list){
                    temp.addAll(str2list(str, split));
                }
                list = temp;
            }
        }
        return list;
    }

    /**
     * 将字符串拆分，过滤掉空白，截掉前后的空白
     *
     * @param origin 需要处理的源字符串
     * @param split 拆分的分隔字符串。如果为空，默认为英文逗号:","
     * @return 字符串列表
     */
    public static List<String> str2list(String origin, String split) {
        List<String> list = new ArrayList<>();

        if (StringUtils.isBlank(origin)) {
            return list;
        }
        origin = origin.trim();
        if (StringUtils.isBlank(split)) {
            split = StrPool.COMMA;
        }
        String[] strs = origin.split(split);
        for (String str : strs) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            list.add(str.trim());
        }
        return list;
    }

    /**
     * 将字符串拆分，过滤掉空白，截掉前后的空白
     *
     * @param listParam 需要处理的源字符串
     * @return 字符串列表
     */
    public static List<String> strListTrim(List<String> listParam) {
        if (CollectionUtils.isEmpty(listParam)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String str : listParam) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            list.add(str.trim());
        }
        return list;
    }

    /**
     * 将字符串转换成map。
     *
     * @param str 需要转换的字符串。例：0,买;1,卖
     * @param rowSplit 用于拆分每条记录的字符串
     * @param colSplit 用于拆分key, value的字符串。只取前两个元素作为key, value。
     * @return 字符串map
     */
    public static Map<String, String> str2map(String str, String rowSplit, String colSplit) {
        return str2map(str, rowSplit, colSplit, true);
    }
    /**
     * 将字符串转换成map。
     *
     * @param str 需要转换的字符串。例：0,买;1,卖
     * @param rowSplit 用于拆分每条记录的字符串
     * @param colSplit 用于拆分key, value的字符串。只取前两个元素作为key, value。
     * @param origin 是否反转
     * @return 字符串map
     */
    public static Map<String, String> str2map(String str, String rowSplit, String colSplit, boolean origin) {
        Map<String, String> retnMap = new HashMap<>();
        if(StringUtils.isBlank(str)){
            return retnMap;
        }
        String[] strs = str.split(rowSplit);
        for (String strRow : strs) {
            if (StringUtils.isBlank(strRow)) {
                continue;
            }
            strRow = strRow.trim();
            String[] strs2 = strRow.split(colSplit);
            if (strs2.length < 2) {
                continue;
            }
            if (StringUtils.isNotBlank(strs2[0])) {
                if(origin) {
                    retnMap.put(strs2[0].trim(), strs2[1].trim());
                }else {
                    retnMap.put(strs2[1].trim(), strs2[0].trim());
                }
            }
        }
        return retnMap;
    }

    /**
     * 将字符串转换成map。
     *
     * @param str 需要转换的字符串。例：0,买;1,卖
     * @param rowSplit 用于拆分每条记录的字符串
     * @param colSplit 用于拆分key, value的字符串。只取前两个元素作为key, value。
     * @param origin 是否反转
     * @return 字符串map
     */
    public static Map<String, String> str2map(
            Map<String, String> map, String str, String rowSplit, String colSplit, boolean origin) {
        if(map == null ){
            return str2map(str, rowSplit, colSplit, origin);
        }
        if(MapUtils.isEmpty(map)) {
            synchronized (map) {
                if(MapUtils.isEmpty(map)) {
                    map.putAll(str2map(str, rowSplit, colSplit, origin));
                }
            }
        }
        return map;
    }

    /**
     * 将字符串转换成map嵌套map。
     * @param str 需要转换的字符串。例：aa,a2,val1;bb,b2,val2
     * @param rowSplit 用于拆分每条记录的字符串。例：”；“
     * @param colSplit 用于拆分key, value的字符串。只取前三个元素作为key, key, value。例：”,“
     * @return 双层map
     */
    public static Map<String, Map<String, String>> str2map2(String str, String rowSplit, String colSplit) {
        Map<String, Map<String, String>> retnMap = new HashMap<>();
        String[] strs = str.split(rowSplit);
        for (String strRow : strs) {
            if (StringUtils.isBlank(strRow)) {
                continue;
            }
            strRow = strRow.trim();
            String[] strs2 = strRow.split(colSplit);
            Map<String, String> rowMap = retnMap.get(strs2[0].trim());
            if (rowMap == null) {
                rowMap = new HashMap();
                retnMap.put(strs2[0].trim(), rowMap);
            }
            if (StringUtils.isNotBlank(strs2[2])) {
                rowMap.put(strs2[1].trim(), strs2[2].trim());
            }
        }
        return retnMap;
    }


    /**
     * 将字符串转换成map。相同的KEY的值放到list中。跟str2map2的区别是：最终的值是个List，用于处理2级key重复
     *
     * @param str 需要转换的字符串。例：aa,val1;aa,val2;bb,val2
     * @param rowSplit 用于拆分每条记录的字符串
     * @param colSplit 用于拆分key, value的字符串。只取前三个元素作为key, key, value。
     * @return 包含list的map
     */
    public static Map<String, Map<String, List<String>>> str2mapList(String str, String rowSplit, String colSplit) {
        Map<String, Map<String, List<String>>> retnMap = new HashMap<>();
        String[] strs = str.split(rowSplit);
        for (String strRow : strs) {
            if (StringUtils.isBlank(strRow)) {
                continue;
            }
            strRow = strRow.trim();
            String[] strs2 = strRow.split(colSplit);
            Map<String, List<String>> rowMap = retnMap.get(strs2[0].trim());
            if (rowMap == null) {
                rowMap = new HashMap<>();
                retnMap.put(strs2[0].trim(), rowMap);
            }
            List<String> elemList = rowMap.get(strs2[1].trim());
            if (elemList == null) {
                elemList = new ArrayList<>();
                rowMap.put(strs2[1].trim(), elemList);
            }
            if (StringUtils.isNotBlank(strs2[2])) {
                elemList.add(strs2[2].trim());
            }
        }
        return retnMap;
    }

    /**
     * map转成url路径，例：aa=a1&bb=b2&cc=c3
     * @param map 键值对
     * @param <T> map值值
     * @return url字符串
     */
    public static <T> String map2urlPath(Map<String, T> map){
        return map2urlPath(map, null);
    }
    public static <T> String mapStr2urlPath(Map<String, T> map, String ignoreKey){
        return map2urlPath(map, new HashSet<>(Arrays.asList(ignoreKey)));
    }
    public static <T> String map2urlPath(Map<String, T> map, Set<String> ignoreKeys){
        StringBuilder sb = new StringBuilder();
        TreeMap<String, T> treeMap = new TreeMap<>(map);
        int i=0, leng = treeMap.size();
        for(Map.Entry<String, T> entry : treeMap.entrySet()){
            ++i;
            if(ignoreKeys!=null && ignoreKeys.contains(entry.getKey())){
                continue;
            }
            sb.append(entry.getKey()).append(SymbolConst.EQUALS).append(entry.getValue());
            if(i!=leng){
                sb.append(SymbolConst.AMPERSAND);
            }
        }
        return sb.toString();
    }

    /**
     * 16进制转成long类型
     * @param hex 16进制字符串
     * @return long值
     * @author Justin
     */
    public static long hex2long(String hex) {
        if (StringUtils.isBlank(hex)) {
            return 0;
        }
        byte[] bys = HexUtil.decodeHex(hex);
        long lng = 0;
        for (byte by : bys) {
            lng = (lng << 8) | (by & 0xff);
        }
        return lng;
    }



    /**
     * 字符串转成摘要，再转成long类型
     * @param str 要转换的字符串
     * @return 字符串摘要的long格式
     */
    public static long strHex2long(String str) {
        String md5Str = MD5.create().digestHex(str);
        int leng = md5Str.length();
        return hex2long(md5Str.substring(leng - 16, leng));
    }


    /**
     * 获取本地所有IP地址
     * @return IP地址集合
     */
    public static Set<String> localIPAll(){
        Set<String> ips = new HashSet<>();

        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
                 ifaces.hasMoreElements(); ) {
                NetworkInterface iface = ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();
                     inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            ips.add(inetAddr.getHostAddress());
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                ips.add(candidateAddress.getHostAddress());
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }

        return ips;
    }


    /**
     * 验证身份证格式
     * <p>
     * 身份证15位编码规则：dddddd yymmdd xx p
     * dddddd：6位地区编码
     * yymmdd: 出生年(两位年)月日，如：910215
     * xx: 顺序编码，系统产生，无法确定
     * p: 性别，奇数为男，偶数为女
     * <p>
     * 身份证18位编码规则：dddddd yyyymmdd xxx y
     * dddddd：6位地区编码
     * yyyymmdd: 出生年(四位年)月日，如：19910215
     * xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女
     * y: 校验码，该位数值可通过前17位计算获得
     * <p>
     * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
     * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
     * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替
     * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
     * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
     */
    public static boolean validateIdCard(String idCard) {
        idCard = idCard.trim();
        //15位和18位身份证号码的正则表达式
        String regIdCard = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //如果通过该验证，说明身份证格式正确，但准确性还需计算
        if (idCard.matches(regIdCard) && idCard.length() == 18) {
            Integer[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; //将前17位加权因子保存在数组里
            Integer[] idCardY = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2}; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
            int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (int i = 0; i < 17; i++) {
                idCardWiSum += Integer.valueOf(idCard.substring(i, i + 1)) * idCardWi[i];
            }

            Integer idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            String idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                return StringUtils.equalsIgnoreCase(idCardLast, "x");
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                return Integer.valueOf(idCardLast).equals(idCardY[idCardMod]);
            }
        }
        return false;
    }


}
