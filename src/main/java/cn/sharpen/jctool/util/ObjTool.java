package cn.sharpen.jctool.util;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sharpen.jctool.bean.base.RandomGeneratorExt;
import cn.sharpen.jctool.bean.utilbean.H2Map;
import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.consts.SymbolConst;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

import static cn.sharpen.jctool.consts.SignConst.*;
import static cn.sharpen.jctool.consts.SymbolConst.BLANK;

/**
 * 对象工具
 * @author justin
 */
public class ObjTool {

    /**
     * 前两个值相等返回默认值，否则返回条件值（第二个参数）
     * @param eqStr 判断值
     * @param condVal 条件值
     * @param defVal 默认值
     * @return 判断后获取的值
     */
    public static String blankOrStrDefault(String eqStr, String condVal, String defVal) {
        if (StringUtils.isBlank(condVal) || StringUtils.equals(eqStr, condVal)) {
            return defVal;
        } else {
            return condVal;
        }
    }


    public static String obj2str(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * 对象转string再转成long
     * @param obj
     * @return 对应转string再转成long的值
     */
    public static Long obj2long(Object obj) {
        if(obj == null) {
            return null;
        }
        String str = obj.toString();
        str = StrUtil.removeAll(str, "\"");
        return NumberUtil.isNumber(str) ? Long.valueOf(str) : null;
    }

    public static String map2str(Map map, Object key) {
        if (map == null || map.get(key) == null) {
            return null;
        }
        return map.get(key).toString();
    }

    public static boolean noEqualsNumber(Number obj1, Number obj2) {
        return !equalsNumber(obj1, obj2);
    }

    public static boolean equalsNumber(Number num1, Number num2) {
        if (num1 == null && num2 == null) {
            return true;
        }
        if (num1 == null || num2 == null) {
            return false;
        }

        if(num1 instanceof Long){
            return NumberUtil.equals(num1.longValue(), num2.longValue());
        } else if(num1 instanceof Integer){
            return NumberUtil.equals(num1.intValue(), num2.intValue());
        } else if(num1 instanceof Double){
            return NumberUtil.equals(num1.doubleValue(), num2.doubleValue());
        } else if(num1 instanceof Float){
            return NumberUtil.equals(num1.floatValue(), num2.floatValue());
        }else if(num1 instanceof Byte){
            return NumberUtil.equals(num1.byteValue(), num2.byteValue());
        }
        return false;
    }

    /**
     * 数字相等比较
     * @param number
     * @param nums
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean numberEqualAny(T number, T ...nums) {
        if(ArrayUtils.isEmpty(nums) || number==null) {
            return false;
        }
        for(T elem : nums) {
            boolean result = equalsNumber(number, elem);
            if(result) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否是允许的数字。空或者数字是允许的
     * @param decimalStr
     * @return
     */
    public static boolean allowDecimal(String decimalStr) {
        return decimalStr == null || NumberUtil.isNumber(decimalStr);
    }

    /**
     * 是否包含非数字
     * @param strs 数字字符串数组
     * @return
     */
    public static boolean hasNoNumber(String ...strs) {
        if(ArrayUtil.isEmpty(strs)) {
            return false;
        }
        for(String str : strs) {
            if(!NumberUtil.isNumber(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 从MAP中获取Map对象
     *
     * @param map
     * @param key
     * @return
     */
    public static Map mapGetMap(Map map, Object key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        Object valObj = map.get(key);
        if (valObj == null) {
            return null;
        }
        if (valObj instanceof Map) {
            return (Map) valObj;
        }
        return null;
    }

    /**
     * 从MAP中获取Map对象
     *
     * @param map
     * @param key
     * @return
     */
    public static <T> T mapGetObj(Map map, Object key, Class cls) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        Object valObj = map.get(key);
        if (valObj == null) {
            return null;
        }
        if (cls.isAssignableFrom(valObj.getClass())) {
            return (T) valObj;
        }
        return null;
    }


    /**
     * 数据库中的值转成字符串
     * @param list 数据库中的数据
     */
    public static void dbValListMap2str(List<LinkedHashMap<String, Object>> list) {
        if(CollectionUtils.isEmpty(list)) {
            return;
        }
        for(Map<String, Object> map : list){
            dbValMap2str(map);
        }
    }

    /**
     * 将数据库中的数据转成字符串
     * @param map 一条数据
     */
    public static void dbValMap2str(Map<String, Object> map){
        if(MapUtils.isEmpty(map)) {
            return;
        }
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Entry<String, Object> entry=it.next();
            map.put(entry.getKey(), dbVal2str(entry.getValue()));
        }
    }
    /**
     * 数据库中的值转成字符串
     * @param obj 数据库字段的值
     * @return 数据库字段的值字符串形式
     */
    public static String dbVal2str(Object obj){
        if(obj==null){
            return "";
        }
        Class objCls = obj.getClass();
        if(Date.class.isAssignableFrom(objCls)){
            return DatePattern.NORM_DATETIME_FORMAT.format((Date)obj);
        }
        return obj.toString();
    }

    /**
     * 将对象转成字符串后判断值是否相等
     * @param aa 第一个参数
     * @param bb 第二个参数
     * @return 大于为大于0，小于为小于0，等于时返回0
     */
    public static boolean objStrCompare(Object aa, Object bb) {
        if (aa == null && bb == null) {
            return true;
        }
        String a = aa == null ? null : aa.toString();
        String b = bb == null ? null : bb.toString();
        return StringUtils.equals(a, b);
    }


    /**
     * condition为true返回trueVal, 否则返回falseVal值
     *
     * @param condition 条件
     * @param trueVal 源值
     * @param falseVal 替换值
     * @param <T> 要返回的对象类型
     * @return condition返回trueVal，否则返回falseVal
     */
    public static <T> T trueVal(Boolean condition, T trueVal, T falseVal) {
        if (condition == null) {
            condition = false;
        }
        return Boolean.TRUE.equals(condition) ? trueVal : falseVal;
    }


    public static <T> T trueFunc(Boolean condition, T defVal, Function<List, T> trueFunc, List params) {
        if (condition == null) {
            condition = false;
        }
        return Boolean.TRUE.equals(condition) ? trueFunc.apply(params) : defVal;
    }

    /**
     * orig不为空,返回orign, 否则返回val值
     *
     * @param judgeVal 判断值
     * @param defVal 默认值
     * @param <T> 值类型
     * @return 判断后获取到的值
     */
    public static <T> T nullDefault(T judgeVal, T defVal) {
        return judgeVal != null ? judgeVal : defVal;
    }

    /**
     * 如果时间为空，则使用当前时间
     * @param date
     * @return
     */
    public static Date nullCurrentDate(Date date){
        return date==null ? Calendar.getInstance().getTime() : date;
    }

    /**
     * orig不为空,返回func(参数为orig)的调用, 否则返回null值
     *
     * @param obj 判断的对象
     * @param func 执行的方法
     * @param <T> 判断的对象的类型
     * @param <R> 返回的对象类型
     * @return
     */
    public static <T, R> R notNullCalc(T obj, Function<T, R> func) {
        return obj != null ? null : func.apply(obj);
    }

    /**
     * 是否为非空或0的数字
     * @param number 被判断的数字
     * @return 是否为非空或0的数字
     */
    public static boolean noNullNumber(Number number) {
        return !nullNumber(number);
    }
    /**
     * 是否为非空或0的数字字符串
     * @param number 被判断的数字
     * @return 是否为非空或0的数字
     */
    public static boolean null0Number(String number) {
        return StringUtils.isBlank(number) || (NumberUtil.isNumber(number) && new BigDecimal(number).longValue() == 0);
    }


    /**
     * 判断为空或等于0的小数
     * @param number
     * @return
     */
    public static boolean null0Decimal(String number) {
        return StringUtils.isBlank(number) || (NumberUtil.isNumber(number) && new BigDecimal(number).equals(0));
    }

    /**
     * 获取非空数字（找到第一个不为空或者0的数字）
     * @param nums 要判断的数字
     * @return 第一个不为空或者0的数字
     */
    public static String numberNoNull(String ...nums) {
        if (ArrayUtil.isEmpty(nums)) {
            return null;
        }
        for (String num : nums) {
            if (!null0Number(num)) {
                return num;
            }
        }
        return null;
    }


    /**
     * 获取非空数字（找到第一个不为空或者0的数字，支持小数）
     * @param nums 要判断的数字
     * @return 第一个不为空或者0的数字
     */
    public static String decimalNoNull(String ...nums) {
        if (ArrayUtil.isEmpty(nums)) {
            return null;
        }
        for (String num : nums) {
            if (!null0Decimal(num)) {
                return num;
            }
        }
        return null;
    }

    /**
     * 返回数字数组中第一个不为0或者空的数字
     * @param nums
     * @param <T>
     * @return
     */
    public static <T extends Number> T numberNoNull(T ...nums) {
        if (ArrayUtil.isEmpty(nums)) {
            return null;
        }
        for (T num : nums) {
            if (num!=null && num.floatValue()!= 0) {
                return num;
            }
        }
        return null;
    }

    /**
     * 是否为空或0的数字
     * @param number 被判断的数字
     * @return 是否为空或0的数字
     */
    public static boolean nullNumber(Number number) {
        return number == null || number.intValue() == 0;
    }

    /**
     * 是否是空数据（如果是数字，不能为0，如果是字符串，不能为空白，否则返回true）
     * @param obj
     * @return 是否为空值
     */
    public static boolean nullData(Object obj) {
        if(obj == null){
            return true;
        }
        if(obj instanceof Number){
            return nullNumber((Number) obj);
        }
        if(obj instanceof String){
            return StringUtils.isBlank(obj.toString());
        }
        return false;
    }

    /**
     * 判断没有0的数字
     * @param numbers
     * @return 是否全为非0
     */
    public static boolean no0Any(Number ...numbers) {
        for(Number num : numbers){
            if(nullNumber(num)){
                return false;
            }

        }
        return true;
    }

    /**
     * 数字是否等于任意一个
     * @param origNum     原数字，有值的对象
     * @param nums     用于判断的数字
     * @return 如果有一个相等则返回true
     */
    public static boolean valNoEqualAny(Long origNum, Long ...nums) {
        return !valEqualAny(origNum, nums);
    }


    /**
     * 数字是否等于任意一个
     * @param origNum     原数字，有值的对象
     * @param nums     用于判断的数字
     * @return 如果有一个相等则返回true
     */
    public static boolean valEqualAny(Long origNum, Long ...nums) {
        if (origNum == null || ArrayUtil.isEmpty(nums)) {
            return false;
        }
        for (Long num : nums) {
            if (origNum.equals(num)) {
                return true;
            }
        }
        return false;
    }

    public static boolean nullObj(Object obj) {
        return obj == null;
    }

    public static boolean noNullObj(Object obj) {
        return obj != null;
    }

    /**
     * 判断数组中是否有空对象
     * @param objs
     * @return
     */
    public static boolean hasNullObj(Object ...objs) {
        if(ArrayUtil.isEmpty(objs)) {
            return false;
        }
        for(Object obj : objs) {
            if(obj == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean noBlankObj(Object obj) {
        return obj != null && StringUtils.isNotBlank(obj.toString());
    }
    public static <T> T noNullObj(T ...objs) {
        if(ArrayUtil.isEmpty(objs)) {
            return null;
        }
        for(T obj : objs) {
           if(obj!=null) {
               return obj;
           }
        }
        return null;
    }
    /**
     * 对象属性值拷贝
     *
     * @param origObj     原对象，有值的对象
     * @param distObj     目标对象，需要被设置值的对象
     * @param propMapStrs 属性映射字符串数组
     * @param conv        属性的键值对是否要转换
     */
    public static void objCopy(Object origObj, Object distObj, String[] propMapStrs, Boolean conv) {
        if (origObj == null || distObj == null || ArrayUtils.isEmpty(propMapStrs)) {
            return;
        }
        conv = conv == null ? false : conv;
        Map<String, String> propMap = CollectionTool.array2map(propMapStrs);
        if (conv) {
            propMap = MapUtils.invertMap(propMap);
        }
        objCopy(origObj, distObj, propMap);
    }

    /**
     * 对象属性值拷贝
     *
     * @param origObj 原对象，有值的对象
     * @param distObj 目标对象，需要被设置值的对象
     */
    public static void objCopy(Object origObj, Object distObj, Map<String, String> propMap) {
        if (origObj == null || distObj == null || MapUtils.isEmpty(propMap)) {
            return;
        }
        for (Entry<String, String> enty : propMap.entrySet()) {
            Method origMethod = getMethodByNameFromObj(origObj, enty.getKey());
            if (origMethod == null) {
                continue;
            }
            try {
                Object origPropVal = origMethod.invoke(origObj);
                if (origPropVal != null) {
                    Method distPropSetMethod = setMethodByNameFromObj(distObj, enty.getValue(),
                            origPropVal.getClass());
                    distPropSetMethod.invoke(distObj, origPropVal);
                }
            } catch (Exception e) {
                return;
            }
        }
    }


    /**
     * 获取bean对象的get方法
     *
     * @param obj  对象
     * @param prop 对象的属性
     * @return 获取属性的get方法
     */
    public static Method getMethodByNameFromObj(Object obj, String prop) {
        if (obj == null || StringUtils.isBlank(prop)) {
            return null;
        }
        Class cla = obj.getClass();
        return getMethodByName(cla, prop);
    }


    /**
     * 获取bean对象的get方法
     *
     * @param cla  对象的类
     * @param prop 对象的属性
     * @return 获取属性的get方法
     */
    public static Method getMethodByName(Class cla, String prop) {
        String methodName = SignConst.METHOD_GET + prop.substring(0, 1).toUpperCase() + prop.substring(1);
        Method method = null;
        try {
            method = cla.getMethod(methodName);
        } catch (Exception e) {
            try {
                method = cla.getMethod(prop);
            } catch (Exception e2) {
                return null;
            }
        }
        return method;
    }


    /**
     * 获取bean对象的set方法
     *
     * @param obj    对象
     * @param prop   对象的属性
     * @param valCla 对象的属性的类
     * @return 获取属性的set方法
     */
    public static <T> Method setMethodByNameFromObj(Object obj, String prop, Class<T> valCla) {
        if (obj == null || StringUtils.isBlank(prop)) {
            return null;
        }
        Class cla = obj.getClass();
        return setMethodByName(cla, prop, valCla);
    }

    /**
     * 获取bean对象的set方法
     *
     * @param cla    对象的类
     * @param prop   对象的属性
     * @param valCla 对象的属性的类
     */
    public static Method setMethodByName(Class cla, String prop, Class valCla) {
        String methodName = SignConst.METHOD_SET + prop.substring(0, 1).toUpperCase() + prop.substring(1);
        Method method = null;
        try {
            method = cla.getMethod(methodName, valCla);
        } catch (Exception e) {
            try {
                method = cla.getMethod(prop, valCla);
            } catch (Exception e2) {
                return null;
            }
        }
        return method;
    }

    // 将method的值设置到对象中

    /**
     * 将map的key作为属性的值设置到对应的对象的属性中
     * @param propValMap 属性值映射
     * @param obj 要设置值的属性
     * @param <T>
     * @return 重置过值的对象
     */
    public static <T> T setMapProp2Obj(Map<String, String> propValMap, T obj) {
        if(MapUtils.isEmpty(propValMap) || obj == null) {
            return obj;
        }
        for(Map.Entry<String, String> elem : propValMap.entrySet()) {
            String key = elem.getKey(), val = elem.getValue();
            if(StrUtil.hasBlank(key, val)) {
                continue;
            }
            Method setMethod = setMethodByName(obj.getClass(), key, String.class);
            if(setMethod==null) {
                StrTool.plf("failMethodProp={}", key);
                continue;
            }
            try {
                setMethod.invoke(obj, val);
            }catch (Exception e){
                StrTool.plf("failProp={} val={}", key, val);
                continue;
            }
        }
        return obj;
    }

    /**
     * 将map的key作为属性的值设置到对应的对象的属性中
     * @param propValMap 属性值映射
     * @param objs 要设置值的属性的对象列表
     * @param idProp ID属性名
     * @param <T>
     * @return 重置过值的对象
     */
    public static <T> List<T> setH2MapProp2Obj(H2Map<Long, String, String> propValMap, List<T> objs, String idProp) {
        if(H2Map.isEmpty(propValMap) || CollectionUtils.isEmpty(objs)) {
            return objs;
        }
        Method idGetMethod = getMethodByName(objs.get(0).getClass(), idProp);
        if(idGetMethod == null) {
            return objs;
        }
        for(T obj : objs) {
            try{
                Long idVal = obj2long(idGetMethod.invoke(obj));
                HashMap<String, String> propVal = propValMap.get(idVal);
                for (Map.Entry<String, String> elem : propVal.entrySet()) {
                    String key = elem.getKey(), val = elem.getValue();
                    if (StrUtil.hasBlank(key, val)) {
                        continue;
                    }
                    Method method = setMethodByName(obj.getClass(), key, String.class);
                    if (method == null) {
                        StrTool.plf("failMethodProp={}", key);
                        continue;
                    }
                    method.invoke(obj, val);
                }
            } catch (Exception e) {
                StrTool.plf("failPropMsg={}", e.getMessage());
                continue;
            }
        }
        return objs;
    }


    // 收集bit位的值

    /**
     * 将long的为1的位拆出来
     * @param src 数字
     * @return 拆分后的数字集合
     */
    public static List<Long> splitBit(long src) {
        List<Long> bi = new ArrayList<Long>();
        while (src > 0) {
            long v = -src & src;
            bi.add(v);
            src -= v;
        }
        return bi;
    }

    /**
     * 数字字符串转成long
     * @param str 数字字符串
     * @return 字符串对应的long值，如果字符串为空，则返回空
     */
    public static Long str2long(String str) {
        if (StringUtils.isBlank(str) || !NumberUtil.isNumber(str)) {
            return null;
        }
        return Long.parseLong(str);
    }

    /**
     * 数字字符串转成long
     * @param strs 数字字符串
     * @return 字符串对应的long值，如果字符串为空，则返回空
     */
    public static List<Long> strParseLong(Collection<String> strs) {
        List<Long> list = new ArrayList<>();
        if(CollectionUtils.isEmpty(strs) ) {
            return list;
        }
        for(String str : strs) {
            if(NumberUtil.isLong(str)) {
                list.add(str2long(str));
            }
        }
        return list;
    }

    /**
     * 数字字符串转成int
     * @param str 数字字符串
     * @return 字符串对应的int值，如果字符串为空，则返回空
     */
    public static Integer str2int(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return Integer.valueOf(str);
    }

    /**
     * 数字字符串转成日期对象
     * @param str 数字字符串
     * @return 字符串对应的日期对象，如果字符串为空，则返回空
     */
    public static Date str2date(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return DateUtil.parse(str);
    }

    /**
     * 将字符串转换成数据库decimal可接受的值
     * @param str
     * @return
     */
    public static String str2decimalStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return str;
    }

    /**
     * long转成字符串
     * @param obj 数字字符串
     * @return long值的字符串格式
     */
    public static String long2str(Long obj) {
        return obj == null ? BLANK : obj.toString();
    }

    /**
     * int转成字符串
     * @param obj 数字字符串
     * @return int值的字符串格式
     */
    public static String int2str(Integer obj) {
        return obj == null ? BLANK : obj.toString();
    }

    /**
     * 时间转成字符串
     * @param obj 数字字符串
     * @return 时间值的字符串格式
     */
    public static String date2str(Date obj) {
        return obj == null ? BLANK : DateUtil.formatDateTime(obj);
    }

    /**
     * 解析日期，如果为空返回当前时间
     * @param dateStr
     * @return
     */
    public static Date dayOrCurrent(String dateStr){
        return StrUtil.isBlank(dateStr)? new Date() : DateUtil.parse(dateStr);
    }

    /**
     * 将字符串解析成时间
     * @param dateStr
     * @param format
     * @return
     */
    public static DateTime parseDateTime(CharSequence dateStr, String format){
        if(StrUtil.isBlank(dateStr)) {
            return null;
        }
        return DateUtil.parse(StrUtil.trim(dateStr), format);
    }

    /**
     * int转long,空值返回空
     * @param original
     * @return
     */
    public static Long int2long(Integer original){
        if(original == null) {
            return null;
        }
        return Integer.valueOf(original).longValue();
    }

    /**
     * long转int,空值返回空
     * @param original
     * @return
     */
    public static Integer long2int(Long original){
        if(original == null) {
            return null;
        }
        return Long.valueOf(original).intValue();
    }

    public static int unboxInt(Integer val) {
        return val==null ? 0 : val.intValue();
    }
    public static long unboxLong(Long val) {
        return val==null ? 0 : val.longValue();
    }
    public static float unboxFloat(Float val) {
        return val==null ? 0 : val.floatValue();
    }
    public static double unboxDouble(Double val) {
        return val==null ? 0 : val.doubleValue();
    }

    /**
     * 合并map。将属性map中的值作为KEY,val的map作为value
     * @param valMap
     * @param propMap
     * @return 合并后的map
     */
    public Map<String, Object> mergeMap(Map<String, Object> valMap, Map<String, String> propMap) {
        if (MapUtil.isEmpty(valMap) || MapUtil.isEmpty(propMap)) {
            return valMap;
        }
        Map<String, Object> newMap = new HashMap<>();
        for (Entry<String, String> prop : propMap.entrySet()) {
            Object val = valMap.get(prop.getKey());
            if (val != null) {
                newMap.put(prop.getValue(), val);
            }
        }
        return newMap;
    }

    // 获取常用对象

    // 获取今天天的init值，例： 20260415
    public static Integer todayInt(){
        return Integer.valueOf(DatePattern.PURE_DATE_FORMAT.format(System.currentTimeMillis()));
    }

    // 获取昨天的init值，例： 20260415
    public static Integer yesterdayInt(){
        return Integer.valueOf(DatePattern.PURE_DATE_FORMAT.format(System.currentTimeMillis() - (86400*1000)));
    }

    public static Date parseRippleTime(Long time){
        if(time == null){
            return null;
        }
        return new Date((time+ SignConst.RIPPLE_BASE_SECOND)*1000);
    }


    public static String rippleTimeStr(Long time){
        return DateUtil.formatDateTime(parseRippleTime(time));
    }

    /**
     * 获取当前秒，例：1646067990
     */
    public static Long currSecond(){
        return System.currentTimeMillis()/1000;
    }
    public static Long currSecond(long time){
        return time/1000;
    }


    /**
     * 比较两个对象的属性变化
     * @param map 属性-提示语映射
     * @param oldObj
     * @param newObj
     * @return
     */
    public static String compObj2tip(Map<String, String> map, Object oldObj, Object newObj){
        if(MapUtils.isEmpty(map) || oldObj == null || newObj == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey(), val = entry.getValue();
            Object old = BeanUtil.getProperty(oldObj, key), ne = BeanUtil.getProperty(newObj, key);
            if(old==null && ne!=null) {
                sb.append(STR_ZH_ADD).append(SymbolConst.BLANK_SPACE).append(val).append(StrPool.COMMA);
            } else if(old!=null && ne==null)  {
                sb.append(STR_ZH_DEL).append(SymbolConst.BLANK_SPACE).append(val).append(StrPool.COMMA);
            } else if(old!=null && ne!=null && !old.equals(ne))  {
                sb.append(STR_ZH_UPDATE).append(SymbolConst.BLANK_SPACE).append(val).append(StrPool.COMMA);
            }
        }
        if(StrUtil.endWith(sb.toString(), StrPool.COMMA)) {
            return sb.substring(0, sb.length()-1);
        }
        return sb.toString();
    }

    /**
     * 生成自定义的图形验证码
     * @param width
     * @param height
     * @param codeCount
     * @param interfereCount
     * @param code
     * @return
     */
    public static CircleCaptcha getImgCode(int width, int height, int codeCount, int interfereCount, String code){
        CodeGenerator generator = new RandomGeneratorExt(5, code);
        CircleCaptcha img  = new CircleCaptcha(width, height, codeCount, interfereCount);
        img.setGenerator(generator);
        return img;
    }
}
