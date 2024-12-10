package cn.sharpen.jctool.util;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.sharpen.jctool.bean.param.Tuple2Param;
import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.consts.SymbolConst;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static cn.sharpen.jctool.consts.SignConst.STR_NULL_DESC;

/**
 * JSON相关工具
 *
 * @author justin
 */
@Slf4j
public class JsonTool {
    /**
     * json处理相关的对象
     */

    public static TypeReference<HashMap<String, String>> typeMap = new TypeReference<HashMap<String, String>>() {
    };
    public static ObjectMapper jacksonMapper = new ObjectMapper();

    static {
        jacksonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * 将json字符串转成Map对象，属性为key
     *
     * @param json JSON格式字符串
     * @return
     */
    public static HashMap<String, String> json2MapStr(String json) {
        return json2obj(json, typeMap);
    }

    /**
     * 将对象转成json字符串
     *
     * @param obj
     * @return json字符串
     */
    public static String obj2json(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return jacksonMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.info("jacksonMapperFail {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转成对象
     *
     * @param json JSON格式字符串
     * @return json对应的对象
     */
    public static <T> T json2obj(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return jacksonMapper.readValue(json, cls);
        } catch (Exception e) {
            log.info("jacksonMapperFail json={} msg={}", json, e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转成对象
     *
     * @param json JSON格式字符串
     * @return json对应的对象
     */
    public static <T> T json2obj(String json, TypeReference<T> type) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return jacksonMapper.readValue(json, type);
        } catch (Exception e) {
            log.info("json2objTypeFail {} json={}", e.getMessage(), json, e);
        }
        return null;
    }


    public static <T> List<T> jsonObj2list(Object jsonObj, Class<T> c) {
        return json2list(obj2json(jsonObj), c);
    }

    /**
     * 将json字符串转成对象数组.
     * 参考： http://bcxw.net/article/301.html
     *
     * @param json JSON格式字符串
     * @return json对应的对象列表
     */
    public static <T> List<T> json2list(String json, Class<T> c) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            CollectionType t = jacksonMapper.getTypeFactory().constructCollectionType(ArrayList.class, c);
            List<T> objs = jacksonMapper.readValue(json, t);
            return objs;
        } catch (Exception e) {
            log.error("json2listFail {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转成对象列表。格式不对会抛出异常
     *
     * @param json JSON格式字符串
     * @return json对应的对象列表
     */
    public static <T> List<T> json2listStrict(String json, Class<T> c) throws Exception {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        CollectionType t = jacksonMapper.getTypeFactory().constructCollectionType(ArrayList.class, c);
        return jacksonMapper.readValue(json, t);
    }

    /**
     * 从JsonNode转成对象
     * @param node jsonNode
     * @param c 对象类型
     * @return node对应的对象
     */
    public static <T> T node2obj(JsonNode node, Class<T> c){
        if (node==null||node.isNull()||node.isEmpty()) {
            return null;
        }
        try {
            return jacksonMapper.treeToValue(node, c);
        } catch (Exception e) {
            log.info("node2objTypeFail {} json={}", e.getMessage(), node, e);
        }
        return null;
    }
    /**
     * 从JsonNode转成对象列表
     * @param node jsonNode
     * @param c 对象类型
     * @return json对应的对象列表
     */
    public static <T> List<T> node2list(JsonNode node, Class<T> c){
        if (node==null||node.isNull()||!node.isArray()) {
            return null;
        }
        try {
            CollectionType t = jacksonMapper.getTypeFactory().constructCollectionType(ArrayList.class, c);

            return jacksonMapper.treeToValue(node, t);
        } catch (Exception e) {
            log.info("node2listTypeFail {} json={}", e.getMessage(), node, e);
        }
        return null;
    }
    /**
     * 将json节点中的数组
     *
     * @return json对应的对象列表
     */
    public static List<JsonNode> getNodeList(String nodeStr) {
        return getNodeList(json2node(nodeStr), null);
    }

    /**
     * 将json节点中的数组
     *
     * @return json对应的对象列表
     */
    public static List<JsonNode> getNodeList(String nodeStr, String... props) {
        return getNodeList(json2node(nodeStr), props);
    }

    /**
     * 将json节点中的数组
     *
     * @return json对应的对象列表
     */
    public static List<JsonNode> getNodeList(JsonNode node, String... props) {
        if (node == null) {
            return null;
        }

        if (props == null || props.length == 0) {
            if (node.isArray()) {
                return ListUtil.toList(node.elements());
            }
            return null;
        }
        if (props.length >= 1) {
            node = json2node(getJsonVal(node, props));
        }

        if (node != null && node.isArray()) {
            return ListUtil.toList(node.elements());
        }
        return null;
    }

    /**
     * 将json字符串转成JSON对象
     *
     * @param json JSON格式字符串
     * @return JSON对象
     */
    public static JsonNode json2node(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            JsonNode jsonObj = jacksonMapper.readTree(json);
            return jsonObj;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取json结点对应路径的值结点
     *
     * @param jsonObj JSON结点
     * @param propStr 属性路径，例：result,data,name
     * @return JSON结点
     */
    public static JsonNode getJsonNode(JsonNode jsonObj, String propStr) {
        if (jsonObj == null || StringUtils.isBlank(propStr)) {
            return null;
        }
        String[] props = propStr.split(StrPool.COMMA);
        return getJsonNode(jsonObj, props);
    }

    /**
     * 获取json结点对应路径的值结点
     *
     * @param jsonObj JSON结点
     * @param props   属性路径，例：result,data,name
     * @return JSON结点
     */
    public static JsonNode getJsonNode(JsonNode jsonObj, String[] props) {
        JsonNode jo = jsonObj;
        if (jo == null || props == null || props.length == 0) {
            return null;
        }
        JsonNode node = null;
        for (int i = 0, l = props.length; i < l; ++i) {
            String p = props[i];
            node = jo.get(p);
            if (node == null) {
                return null;
            }
            jo = node;
        }
        return node;
    }

    /**
     * 获取JSON第一个数组
     * 获取json结点对应路径的值结点
     *
     * @param jsonStr JSON字符串
     * @return JSON第一个数组
     */
    public static JsonNode getJsonArrayFirst(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        JsonNode jo = json2node(jsonStr);
        if (jo.isArray()) {
            for (JsonNode elem : jo) {
                return elem;
            }
        }
        return null;
    }


    /**
     * 获取JSON节点的文本
     *
     * @param jsonNode json节点
     * @return JSON节点的文本
     */
    public static String jsonNodeTextVal(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        if (jsonNode.isValueNode()) {
            return jsonNode.asText();
        } else {
            try {
                return jacksonMapper.writeValueAsString(jsonNode);
            } catch (Exception e) {
                log.info("writeValueAsStringFail {}", e.getMessage(), e);
            }
        }
        return null;
    }


    /**
     * 获取json里面的值
     *
     * @param json    json字符串
     * @param propStr 用英文逗号分隔的属性名，例："results,status"
     * @return JSON节点属性的值
     */
    public static String getJsonVal(String json, String propStr) {
        if (StringUtils.isBlank(propStr)) {
            return json;
        }
        String[] props = propStr.split(StrPool.COMMA);
        return getJsonVal(json, props);
    }

    /**
     * 获取json对应路径的值
     *
     * @param json  json字符串
     * @param props json属性
     * @return json对应属性的值的字符串
     */
    public static String getJsonVal(String json, String[] props) {
        if (StringUtils.isBlank(json) || props == null || props.length == 0) {
            return json;
        }
        try {
            JsonNode jsonObj = jacksonMapper.readTree(json);
            return getJsonVal(jsonObj, props);
        } catch (Exception e) {
            log.info("readTreeFail {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取json对应路径的值
     *
     * @param jsonObj json对象
     * @param propStr json属性路径，例：result,data,name
     * @return json对应属性的值的字符串
     */
    public static String getJsonVal(JsonNode jsonObj, String propStr) {
        if (jsonObj == null || StringUtils.isBlank(propStr)) {
            return null;
        }
        String[] props = propStr.split(StrPool.COMMA);
        return getJsonVal(jsonObj, props);
    }

    /**
     * 获取json对应路径的值
     *
     * @param jsonObj json对象
     * @param props   json属性路径，例：result,data,name
     * @return json对应属性的值的字符串
     */
    public static String getJsonVal(JsonNode jsonObj, String[] props) {
        JsonNode jo = jsonObj;
        if (jo == null || props == null || props.length == 0) {
            return null;
        }
        JsonNode node = null;
        for (int i = 0, l = props.length; i < l; ++i) {
            String p = props[i];
            node = jo.get(p);
            if (node == null|| node.isNull()) {
                return null;
            }
            if (i == l - 1) {
                String str = node.asText();
                if (StringUtils.isBlank(str)) {

                    try {
                        String val = jacksonMapper.writeValueAsString(node);
                        if (StrUtil.equals(val, STR_NULL_DESC)) {
                            return SymbolConst.BLANK;
                        }
                        return val;
                    } catch (Exception e) {
                        log.info("writeValueAsStringFail {}", e.getMessage(), e);
                    }
                }
            }
            jo = node;
        }
        return node.asText();
    }


    public static Object jsonGetByPath(String jsonStr, String pahtExpress) {
        try {
            return JSONUtil.getByPath(JSONUtil.parse(jsonStr), pahtExpress);
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T jsonGetByPath(String jsonStr, String pahtExpress, Class<T> T) {
        try {
            return (T) JSONUtil.getByPath(JSONUtil.parse(jsonStr), pahtExpress);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 拆分json键值成健值列表的格式。空行设置为空值
     *
     * @param json
     * @return 健值列表
     */
    public static List<Tuple2Param<String, String>> splitJson(String json) {
        List<Tuple2Param<String, String>> list = new ArrayList<>();
        String jsonText = StrTool.removeTrimPreSuf(json, StrPool.DELIM_START, StrPool.DELIM_END);
        jsonText = jsonText.trim();
        String[] strs = jsonText.split(StrPool.LF);
        StringBuilder sb = new StringBuilder("{\r\n");
        for (String elem : strs) {
            String[] strs2 = elem.split(StrPool.COLON);
            if (ArrayUtils.getLength(strs2) < 2) {
                list.add(Tuple2Param.empty());
                continue;
            }
            String strA = strs2[0].trim(), strB = strs2[1].trim();

            strA = StrTool.removeTrimPreSuf(strA, SymbolConst.DOUBLE_QUOTE);
            strB = StrTool.removeTrimPreSuf(strB, StrPool.COMMA).trim();
            strB = StrTool.removeTrimPreSuf(strB, SymbolConst.DOUBLE_QUOTE);
            list.add(Tuple2Param.of(strA, strB));
        }
        return list;
    }

    /**
     * 拆分json键值成map。空行跳过
     *
     * @param json
     * @return 键值映射
     */
    public static LinkedHashMap<String, String> split2map(String json) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String jsonText = StrTool.removeTrimPreSuf(json, StrPool.DELIM_START, StrPool.DELIM_END);
        jsonText = jsonText.trim();
        String[] strs = jsonText.split(StrPool.LF);
        StringBuilder sb = new StringBuilder("{\r\n");
        for (String elem : strs) {
            String[] strs2 = elem.split(StrPool.COLON);
            if (ArrayUtils.getLength(strs2) < 2) {
                continue;
            }
            String strA = strs2[0].trim(), strB = strs2[1].trim();

            strA = StrTool.removeTrimPreSuf(strA, SymbolConst.DOUBLE_QUOTE);
            strB = StrTool.removeTrimPreSuf(strB, StrPool.COMMA).trim();
            strB = StrTool.removeTrimPreSuf(strB, SymbolConst.DOUBLE_QUOTE);
            map.put(strA, strB);
        }
        return map;
    }

    /**
     * 将键值列表转成json格式字符串
     *
     * @param list
     * @return json格式字符串
     */
    public static String val2json(List<Tuple2Param<String, String>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{\r\n");
        for (Tuple2Param<String, String> elem : list) {
            if (elem == null || elem.getFirst() == null || elem.getSecond() == null) {
                sb.append("\n");
                continue;
            }
            sb.append("    ").append("\"").append(elem.getFirst()).append("\" : \"").append(elem.getSecond())
                    .append("\",\n");
        }
        String target = StrUtil.removeSuffix(sb.toString().trim(), StrPool.COMMA) + "\n}";
        return target;
    }

}
