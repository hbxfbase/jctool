package cn.sharpen.jctool.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.sharpen.jctool.consts.SignConst;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static cn.sharpen.jctool.consts.SignConst.*;
import static java.math.BigDecimal.*;

/**
 * 数学工具
 * @author Justin
 */
public class MathTool {

    /**
     * 获取指定数目的随机数
     * @param rang 随机数范围
     * @param count 随机数的数量
     * @return 随机数集合
     */
    public static Set<Integer> getRandom(int rang, int count){
        Set<Integer> set = new HashSet<>(12);
        Random random = new Random();
        while (set.size() < count){
            set.add(random.nextInt(rang));
        }
        return set;
    }

    public static <T extends Number> Integer modeNumInt(T num, int mode) {
        return modeNum(num, mode).intValue();
    }

    /**
     * 求模数，如果余数大于0则最后加1
     * @param num
     * @param mode
     * @param <T>
     * @return
     */
    public static <T extends Number> Long modeNum(T num, int mode) {
        if(num == null) {
            return 0L;
        }
        long remainder = num.longValue() % mode;
        return num.longValue() / mode + (remainder==0 ? 0 : 1);
    }

    /**
     * 字符串数据转成int, 小数全部去掉
     * @param numStr
     * @return
     */
    public static String num2IntDown(String numStr) {
        if(StringUtils.isBlank(numStr)) {
            return STR_ZERO;
        }
        return new BigDecimal(numStr).intValue()+"";
    }

    /**
     * 乘于100万并转成整数
     * @param num 数字
     * @return 乘于100万后的数字
     */
    public static long multiplyMilliLong(String num) {
        if(StringUtils.isBlank(num)) {
            num = STR_ZERO;
        }
        if(StringUtils.isBlank(num)){
            return 0L;
        }
        return new BigDecimal(num).multiply(SignConst.BD_MILLI).longValue();
    }

    /**
     * 乘于100万并转成整数，保留6位小数
     * @param num 数字
     * @
     *            return 乘于100万并转成整数
     */
    public static String divideMilliStr(Long num) {
        if(num==null || num==0){
            return STR_ZERO;
        }
        return new BigDecimal(num).divide(SignConst.BD_MILLI, 6, ROUND_DOWN).stripTrailingZeros().toPlainString();
    }

    /**
     * 四舍五入保留小数位为6
     * @param num 数字
     * @return
     */
    public static String halfUpDecimal6(String num) {
        if(StringUtils.isBlank(num)) {
            num = STR_ZERO;
        }
        return new BigDecimal(num).setScale(6, ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

    /**
     * 向下保留小数位
     * @param num 数字
     * @return 向下保留小数位后的数字的字符串形式
     */
    public static String downDecimal6(String num) {
        if(StringUtils.isBlank(num)) {
            num = STR_ZERO;
        }
        return new BigDecimal(num).setScale(6, ROUND_DOWN).stripTrailingZeros().toPlainString();
    }
    /**
     * 向下保留小数位
     * @param num 数字
     * @return 向下保留小数位的字符串形式
     */
    public static String downDecimal(String num, int decimal) {
        if(StringUtils.isBlank(num)) {
            num = STR_ZERO;
        }
        return new BigDecimal(num).setScale(decimal, ROUND_DOWN).stripTrailingZeros().toPlainString();
    }


    /**
     * 求对数值
     * @param base 底数
     * @param real 真数
     * @return 对数值
     */
    public static double log(double base, double real){
        return Math.log(real)/Math.log(base);
    }

    /**
     * 求绝对值
     * @param num 数字
     * @return
     */
    public static String abs(String num) {
        if(StringUtils.isBlank(num)) {
            return num;
        }
        return new BigDecimal(num).abs().stripTrailingZeros().toPlainString();
    }

    /**
     * 求完全n叉数树的层数,从1开始
     * @param base 树的分支数
     * @param ordinal 节点编号,从1开始
     * @return 完全n叉数树的层数
     */
    public static int treeLayer(Integer base, Long ordinal){
        if(base==0 || ordinal==null || ordinal==0){
            return 1;
        }
        int val = (int)(Math.log(ordinal.doubleValue())/Math.log(base.doubleValue()));
        return val+1;
    }

    /**
     * 字符串转为负数的字符串形式
     * @param num
     * @return 负数的字符串形式
     */
    public static String negative(String num){
        return StrPool.DASHED+num;
    }

    /**
     * 整数求倍数。比如，10的倍数即为：num/10*10
     * @param intNum
     * @param multiple
     * @return
     */
    public static String takeIntMultiple(String intNum, String multiple) {
        if(StrUtil.hasBlank(intNum, multiple) || !NumberUtil.isNumber(intNum) || !NumberUtil.isNumber(multiple)) {
            return intNum;
        }
        Long numLong = new BigDecimal(intNum).longValue(), multipleLong = new BigDecimal(multiple).longValue();
        return ((numLong/ multipleLong) * multipleLong)+"";
    }
    // 加法

    /**
     * 字符串相加。保留6位小数
     * @param aa 加数
     * @param bb 被加数
     * @return 和
     */
    public static String strAddDown2(String aa, String bb){
        return strAdd(aa, bb, 2, ROUND_DOWN);
    }

    /**
     * 字符串相加。保留6位小数
     * @param aa 加数
     * @param bb 被加数
     * @return 和
     */
    public static String strAddDown4(String aa, String bb){
        return strAdd(aa, bb, 4, ROUND_DOWN);
    }

    /**
     * 字符串相加。保留6位小数
     * @param aa 加数
     * @param bb 被加数
     * @return 和
     */
    public static String strAddDown6(String aa, String bb){
        return strAdd(aa, bb, 6, ROUND_DOWN);
    }

    /**
     * 字符串相加
     * @param aa 加数
     * @param bb 被加数
     * @return 和
     */
    public static String strAdd(String aa, String bb){
        return strAdd(aa, bb, 6, null);
    }
    /**
     * 字符串相加
     * @param aa 加数
     * @param bb 被加数
     * @param roundingMode 圆整方式
     * @return 和
     */
    public static String strAdd(String aa, String bb, int scale, Integer roundingMode){
        if(StringUtils.isBlank(aa)) {
            aa = STR_ZERO;
        }
        if(StringUtils.isBlank(bb)) {
            bb = STR_ZERO;
        }
        BigDecimal bd = new BigDecimal(aa).add(new BigDecimal(bb));
        if(roundingMode!=null) {
            bd = bd.setScale(scale, roundingMode);
        }
        return bd.stripTrailingZeros().toPlainString();
    }

    /**
     * 多个字符串数字相加
     * @param nums 加数
     * @return 和
     */
    public static String strAddManyDown6(String ...nums) {
        return strAddMany(6, ROUND_DOWN, nums);
    }
    public static String strAddManyDown4(String ...nums) {
        return strAddMany(4, ROUND_DOWN, nums);
    }
    public static String strAddManyDown2(String ...nums) {
        return strAddMany(2, ROUND_DOWN, nums);
    }
    /**
     * 多个字符串数字相加
     * @param nums 加数
     * @param roundingMode 圆整方式
     * @return 和
     */
    public static String strAddMany(int scale, Integer roundingMode, String ...nums) {
        BigDecimal bd = new BigDecimal(0);
        if(ArrayUtils.isEmpty(nums)) {
            return STR_ZERO;
        }
        for(String num : nums) {
            if(!NumberUtil.isNumber(num)) {
                continue;
            }
            String numStr = StrTool.valNoBlank(num, STR_ZERO);
            bd = bd.add(new BigDecimal(numStr));
        }

        if(roundingMode!=null) {
            bd = bd.setScale(scale, roundingMode);
        }
        return bd.stripTrailingZeros().toPlainString();
    }

    // 减法

    /**
     * 字符串相减。保留4位小数
     * @param aa 被减数
     * @param bb 减数
     * @return 差
     */
    public static String strSubtractDown4(String aa, String bb){
        return strSubtract(aa, bb, 4, ROUND_DOWN);
    }
    public static String strSubtractDown2(String aa, String bb){
        return strSubtract(aa, bb, 2, ROUND_DOWN);
    }

    /**
     * 字符串相减。保留6位小数
     * @param aa 被减数
     * @param bb 减数
     * @return 差
     */
    public static String strSubtractDown6(String aa, String bb){
        return strSubtract(aa, bb, 6, ROUND_DOWN);
    }

    /**
     * 字符串相减
     * @param aa 被减数
     * @param bb 减数
     * @return 差
     */
    public static String strSubtract(String aa, String bb){
        return strSubtract(aa, bb, 6, null);
    }
    /**
     * 字符串相减
     * @param aa 被减数
     * @param bb 减数
     * @param roundingMode 圆整方式
     * @return 差
     */
    public static String strSubtract(String aa, String bb, int scale, Integer roundingMode){
        if(StringUtils.isBlank(aa)) {
            aa = STR_ZERO;
        }
        bb = StrTool.valNoBlank(bb, STR_ZERO);
        BigDecimal bd = new BigDecimal(aa).subtract(new BigDecimal(bb));
        if(roundingMode!=null) {
            bd = bd.setScale(scale, roundingMode);
        }
        return bd.stripTrailingZeros().toPlainString();
    }

    // 乘法
    /**
     * 字符串相乘。保留4位小数
     * @param aa 被乘数
     * @param bb 减数
     * @return 积
     */
    public static String strMultiplyDown4(String aa, String bb){
        return strMultiply(aa, bb, 4, ROUND_DOWN);
    }
    public static String strMultiply3Down4(String aa, String bb, String cc){
        return strMultiply(strMultiply(aa, bb, 4, ROUND_DOWN), cc, 4, ROUND_DOWN);
    }
    public static String strMultiplyDown2(String aa, String bb){
        return strMultiply(aa, bb, 2, ROUND_DOWN);
    }
    public static String strMultiply3Down2(String aa, String bb, String cc){
        return strMultiply(strMultiply(aa, bb, 2, ROUND_DOWN), cc, 2, ROUND_DOWN);
    }
    public static String strMultiplyDown0(String aa, String bb){
        return strMultiply(aa, bb, 0, ROUND_DOWN);
    }
    /**
     * 字符串相乘。保留6位小数
     * @param aa 被乘数
     * @param bb 减乘
     * @return 积
     */
    public static String strMultiplyDown6(String aa, String bb){
        return strMultiply(aa, bb, 6, ROUND_DOWN);
    }

    /**
     * 字符串相乘。保留6位小数
     * @param aa 被乘数
     * @param bb 减乘
     * @return 积
     */
    public static String strMultiplyUp2(String aa, String bb){
        return strMultiply(aa, bb, 2, ROUND_UP);
    }

    /**
     * 字符串相乘。保留6位小数
     * @param aa 被乘数
     * @param bb 减乘
     * @return 积
     */
    public static String strMultiplyUp6(String aa, String bb){
        return strMultiply(aa, bb, 6, ROUND_UP);
    }

    /**
     * 字符串相乘
     * @param aa 被乘数
     * @param bb 乘数
     * @return 积
     */
    public static String strMultiply(String aa, String bb){
        return strMultiply(aa, bb, 6, null);
    }
    /**
     * 字符串相乘
     * @param aa 被乘数
     * @param bb 乘数
     * @param roundingMode 圆整方式
     * @return 积
     */
    public static String strMultiply(String aa, String bb, int scale, Integer roundingMode){
        if(StringUtils.isBlank(aa)) {
            aa = STR_ZERO;
        }
        bb = StrTool.valNoBlank(bb, STR_ZERO);
        BigDecimal bd = new BigDecimal(aa).multiply(new BigDecimal(bb));
        if(roundingMode!=null) {
            bd = bd.setScale(scale, roundingMode);
        }
        return bd.stripTrailingZeros().toPlainString();
    }


    // 除法
    /**
     * 字符串相除。保留6位小数
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String longDivideDown6(long aa, long bb){
        return strDivide(aa+"", bb+"", 6, ROUND_DOWN);
    }
    /**
     * 字符串相减。保留6位小数
     * @param aa 被除数
     * @param bb 减除
     * @return 商
     */
    public static String intDivideDown6(int aa, int bb){
        return strDivide(aa+"", bb+"", 6, ROUND_DOWN);
    }
    /**
     * 字符串相除，向下保留2位小数
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String strDivideDown2(String aa, String bb){
        return strDivide(aa, bb, 2, ROUND_DOWN);
    }
    /**
     * 字符串相除
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String strDivideDown4(String aa, String bb){
        return strDivide(aa, bb, 4, ROUND_DOWN);
    }
    /**
     * 字符串相除
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String strDivideDown6(String aa, String bb){
        return strDivide(aa, bb, 6, ROUND_DOWN);
    }

    public static String strDivideHalfUp6(String aa, String bb){
        return strDivide(aa, bb, 6, ROUND_HALF_UP);
    }

    /**
     * 字符串相除
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String strDivideUp2(String aa, String bb){
        return strDivide(aa, bb, 2, ROUND_UP);
    }

    /**
     * 字符串相除
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String strDivideUp6(String aa, String bb){
        return strDivide(aa, bb, 6, ROUND_UP);
    }

    /**
     * 字符串相除
     * @param aa 被除数
     * @param bb 除数
     * @return 商
     */
    public static String strDivideDef(String aa, String bb){
        return strDivide(aa, bb, 6, null);
    }
    /**
     * 字符串相除
     * @param aa 被除数
     * @param bb 除数
     * @param roundingMode 圆整方式
     * @return 商
     */
    public static String strDivide(String aa, String bb, int scale, Integer roundingMode){
        if(StringUtils.isBlank(aa)) {
            return STR_ZERO;
        }
        bb = StrTool.valNoBlank(bb, STR_ONE);
        roundingMode = roundingMode == null ? BigDecimal.ROUND_DOWN : roundingMode;
        BigDecimal bd = new BigDecimal(aa).divide(new BigDecimal(bb), scale, roundingMode);
        return bd.stripTrailingZeros().toPlainString();
    }

    public static int addManyInt(Integer ...vals) {
        if(vals.length<1) {
            return 0;
        }
        int result = 0;
        for(Integer val : vals) {
            result += val==null ? 0 : val;
        }
        return result;
    }
    public static long addManyLong(Long ...vals) {
        if(vals.length<1) {
            return 0;
        }
        int result = 0;
        for(Long val : vals) {
            result += val==null ? 0 : val;
        }
        return result;
    }

    /**
     * 去掉小数末尾多余的0
     * @param numStr
     * @return
     */
    public static String decimalNo0(String numStr){
        if(StringUtils.isBlank(numStr)) {
            return STR_ZERO;
        }
        return new BigDecimal(numStr).stripTrailingZeros().toPlainString();
    }

    /**
     * 保留小数位
     * @param numStr
     * @param qty
     * @return
     */
    public static String decimalDown(String numStr, int qty){
        if(StringUtils.isBlank(numStr)) {
            return STR_ZERO;
        }
        return new BigDecimal(numStr).setScale(qty, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

    public static final DecimalFormat df = new DecimalFormat("#0.00000000");

    /**
     * DecimalFormat.format()会导致四舍五入，用户转账全部资产的时候会出现余额不足 直接截取8位 ref: com.ngdes.api.wallet.service.common.util.StringUtil
     */
    public static String formatAsset(BigDecimal avail) {
        if (avail == null || avail.compareTo(new BigDecimal("0")) == 0) {
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
     * 日期添加天数
     * @param day 格式：yyyyMMdd
     * @return 相加后的日期数字，格式：yyyyMMdd
     */
    public static Integer dataAddDay(int day, int changeDay){
        try {
            Date date = DatePattern.PURE_DATE_FORMAT.parse(day + "");
            Date newDate = DateUtils.addDays(date, changeDay);
            return Integer.valueOf(DatePattern.PURE_DATE_FORMAT.format(newDate));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // 比较

    /**
     * 数字上是否相等
     * @param aa 比较数字
     * @param bb 比较数字
     * @return 是否相等
     */
    @Deprecated
    public static boolean eqaul(String aa, String bb){
        if (aa == null) {
            aa = STR_ZERO;
        }
        if (bb == null) {
            bb = STR_ZERO;
        }
        return new BigDecimal(aa).compareTo(new BigDecimal(bb)) == 0;
    }

    public static boolean equal(String aa, String bb){
        if (aa == null) {
            aa = STR_ZERO;
        }
        if (bb == null) {
            bb = STR_ZERO;
        }
        return new BigDecimal(aa).compareTo(new BigDecimal(bb)) == 0;
    }

    /**
     * 数字上是否大于
     * @param aa 比较数字
     * @param bb 比较数字
     * @return 是否大于
     */
    public static boolean greater(String aa, String bb){
        if (aa == null) {
            aa = STR_ZERO;
        }
        if (bb == null) {
            bb = STR_ZERO;
        }
        return new BigDecimal(aa).compareTo(new BigDecimal(bb))>0;
    }
    public static boolean greater0(String aa){
        if (aa == null) {
            aa = STR_ZERO;
        }
        return new BigDecimal(aa).compareTo(new BigDecimal(STR_ZERO))>0;
    }
    /**
     * 数字上是否大于等于
     * @param aa 比较数字
     * @param bb 比较数字
     * @return 是否大于等于
     */
    public static boolean greaterEqual(String aa, String bb){
        if(StringUtils.isBlank(aa)) {
            return false;
        }
        bb = StrTool.valNoBlank(bb, STR_ZERO);
        return new BigDecimal(aa).compareTo(new BigDecimal(bb))>=0;
    }
    /**
     * 数字上是否大于等于0
     * @param aa 比较数字
     * @return 是否大于等于
     */
    public static boolean greaterEqual0(String aa){
        if(StringUtils.isBlank(aa)) {
            return false;
        }
        return new BigDecimal(aa).compareTo(new BigDecimal(STR_ZERO))>=0;
    }
    /**
     * 数字上是否小于
     * @param aa 比较数字
     * @param bb 比较数字
     * @return 是否小于
     */
    public static boolean less(String aa, String bb){
        if(StringUtils.isBlank(bb)) {
            return true;
        }
        bb = StrTool.valNoBlank(bb, STR_ZERO);
        return new BigDecimal(aa).compareTo(new BigDecimal(bb))<0;
    }

    public static boolean less0(String aa){
        return less(aa, STR_ZERO);
    }
    /**
     * 数字上是否小于等于
     * @param aa 比较数字
     * @param bb 比较数字
     * @return 是否小于等于
     */
    public static boolean lessEqual(String aa, String bb){
        aa = StrTool.valNoBlank(aa, STR_ZERO);
        bb = StrTool.valNoBlank(bb, STR_ZERO);
        return new BigDecimal(aa).compareTo(new BigDecimal(bb))<=0;
    }
    /**
     * 数字上是否小于等于
     * @param aa 比较数字
     * @return 是否小于等于
     */
    public static boolean lessEqual0(String aa){
        aa = StrTool.valNoBlank(aa, STR_ZERO);
        return new BigDecimal(aa).compareTo(new BigDecimal(STR_ZERO))<=0;
    }

    /**
     * 数字有0
     * @param decimalStr
     * @return
     */
    public static boolean hasEqualsZero(String ...decimalStr){
        for(String str : decimalStr) {
            if(NumberUtil.equals(new BigDecimal("0.0"), new BigDecimal(str))){
                return true;
            }
        }
        return false;
    }

    public static String min(String num1, String num2){
        if(StringUtils.isBlank(num1)) {
            return num2;
        }
        if(StringUtils.isBlank(num2)) {
            return num1;
        }
        if(greater(num1, num2)){
            return num2;
        }else {
            return num1;
        }
    }

    public static String minMany(String ...nums){
        String minVal = null;
        if(ArrayUtils.isEmpty(nums)) {
            return minVal;
        }
        for(String num : nums) {
            minVal = min(minVal, num);
        }
        return minVal;
    }

    /**
     * 判断字符串转数字后的最大值
     * @param num1
     * @param num2
     * @return
     */
    public static String max(String num1, String num2){
        if(StringUtils.isBlank(num1)) {
            return num2;
        }
        if(StringUtils.isBlank(num2)) {
            return num1;
        }
        if(greater(num2, num1)){
            return num2;
        }else {
            return num1;
        }
    }

    /**
     * 判断字符串转数字后的最大值。如果非数字，就忽略
     * @param nums 要判断的数字的数组
     * @return
     */
    public static String maxMany(String ...nums){
        String maxVal = null;
        if(ArrayUtils.isEmpty(nums)) {
            return maxVal;
        }
        for(String num : nums) {
            if(!NumberUtil.isNumber(num)) {
                continue;
            }
            maxVal = max(maxVal, num);
        }
        return maxVal;
    }

}
