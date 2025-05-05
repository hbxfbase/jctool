package cn.sharpen.test;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.sharpen.jctool.bean.utilbean.ExtMap;
import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.StrConcat;
import cn.sharpen.jctool.util.StrTool;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * JWT相关的测试
 */
public class StrConcatTest {

    @Test
    public void signStrOrdinalTest() {
        ExtMap param = ExtMap.inst().put("mobile","+86-17091951001").put("appid","8930239101299120")
                .put("content","123421 is your mobile text messages verification code");
        String check = StrConcat.signStrOrdinal(param.get(), false, "&","=");
        StrTool.plf("signStrOrdinal={}", check);

    }

    @Test
    public void stripTest() {
        StrTool.plf("stripTest={}", StrUtil.strip(",123,104,,10401,100,", ","));
    }

    @Test
    public void chuiXiongNan1Test() {
        int i = 1000000, end = 9999999;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(;i<end; ++i) {
            if(matchChui(i)) {
                sb.append(i).append("\r\n");
                ++count;
            }
        }
        StrTool.pl("resultStr="+count);
        String filePath = "D:\\datum\\code\\study\\gogs3\\sharpen\\jctool\\asdoc\\test.json";
        new FileWriter(filePath).write(sb.toString());
    }

    @Test
    public void chuiXiongNan2Test() {
        int i = 1000000, end = 9999999;
        StringBuilder sb = new StringBuilder();
        List<String> numList = new ArrayList<>();
        int count = 0;
        for(;i<end; ++i) {
            if(matchChui(i)) {
                numList.add(i+"");
                sb.append(i).append("\r\n");
                ++count;
            }
        }

        Collections.sort(numList, (aa, bb) -> Integer.valueOf(strMaxChar(aa)) - Integer.valueOf(strMaxChar(bb)));
        StringBuilder sb2 = new StringBuilder();
        for(String str : numList) {
            sb2.append(str).append("\r\n");
        }
        String filePath2 = "D:\\datum\\code\\study\\gogs3\\sharpen\\jctool\\asdoc\\chui2.txt";
        new FileWriter(filePath2).write(sb2.toString());
    }

    @Test
    public void chuiXiongNan3Test() {
        int i = 1000000, end = 9999999;
        StringBuilder sb = new StringBuilder();
        List<String> numList = new ArrayList<>();
        int count = 0;
        for(;i<end; ++i) {
            if(matchChui(i)) {
                numList.add(i+"");
                sb.append(i).append("\r\n");
                ++count;
            }
        }

        Collections.sort(numList, (aa, bb) -> Integer.valueOf(strMaxChar(aa)) - Integer.valueOf(strMaxChar(bb)));
        StringBuilder sb2 = new StringBuilder();
        for(String str : numList) {
            sb2.append(toShortChinese(str)).append("\r\n");
        }
        String filePath2 = "D:\\datum\\code\\study\\gogs3\\sharpen\\jctool\\asdoc\\chui3.txt";
        new FileWriter(filePath2).write(sb2.toString());
    }

    public static String strMaxChar(String str){
        char[] chars = str.toCharArray();

        String maxStr = "0";
        for(char ch : chars) {
            String strElem = new Character(ch).toString();
            if(Integer.valueOf(strElem) > Integer.valueOf(maxStr)) {
                maxStr = strElem;
            }
        }
        return maxStr;
    }

    public static boolean matchChui(int num){
        String str = num+"";
        List<String> list = new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();
        String lastStr = "0";
        String[] strs = StrUtil.cut(num+"", 1);
        for(String strElem : strs) {
            if(StringUtils.equals(strElem, SignConst.STR_ZERO)) {
                return false;
            }
            list.add(strElem);
            List<String> listElem = map.get(strElem);
            if(listElem == null) {
                listElem = new ArrayList<>();
                map.put(strElem, listElem);
            }
            listElem.add(strElem);
            if(Integer.valueOf(strElem) < Integer.valueOf(lastStr)) {
                return false;
            }
            lastStr = strElem;
        }
        for(List<String> listElem : map.values()) {
            if(CollectionUtils.size(listElem)>4) {
                return false;
            }
        }

        return true;
    }

    public static String toShortChinese(String num){
        StringBuilder sb = new StringBuilder();
        for(char ch : (num+"").toCharArray()) {
            sb.append(NumberChineseFormatter.numberCharToChinese(ch,false));
        }
        return sb.toString();
    }

    private static String toChinese(String str) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println(" "+result);
        }
        System.out.println(result);
        return result;
    }

    @Test
    public void timeParseTest(){
        StrTool.pl(DateUtil.parse("2023-07-16"));
    }

    @Test
    public void streamMaxTest(){
        Long maxID = 123L;
        List<Long> memberIds = CollectionUtil.list(false, 98L, 25L, 155L,132L);
        maxID = memberIds.stream().max(Long::compareTo).orElse(maxID);
        Long minId = memberIds.stream().min(Long::compareTo).orElse(maxID);
        StrTool.plf("streamMaxTest={} min={}", maxID.toString(), minId.toString());
    }

    @Test
    public void vec2strTest(){
        String template = "Kazah-female-asr-${ordinal}.wav";
        String ordinal = 51+"";
        String result =StrTool.vec2str(null, template, ExtMap.inst().put("ordinal", ordinal).get());
        StrTool.plf("vec2strTest={}", result);
    }

    @Test
    public void amountFormat1(){
        String amountStr = NumberUtil.decimalFormat(",###.##", new BigDecimal("26999809.65"));
        StrTool.plf("amountStr={}", amountStr);
    }

    @Test
    public void getNodeListTest(){
        String path = "D:\\datum\\code\\study\\gogs3\\sharpen\\jctool\\asdoc\\test.json";
        String json = FileUtil.readString(path, StandardCharsets.UTF_8);
        List<JsonNode> list = JsonTool.getNodeList(JsonTool.json2node(json),"data", "list");
        StrTool.plf("getNodeListTestStr=\n\n{}\n\n", JsonTool.obj2json(list));
    }

}
