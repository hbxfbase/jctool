package cn.sharpen.jctool.util;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static cn.hutool.core.text.StrPool.COMMA;

/**
 * map的扩展工具
 * @author justin
 */
public class MapTool {

    /**
     * 从list中的map中获取值
     * 用于更高的map的锁并发
     * @param modelInt list中的map的尺寸
     * @param list 放map的list
     * @param key 键
     * @param <T> 元素类型
     * @return 对应key的元素值
     */
    public static <T> T multiMapGet(int modelInt, List<ConcurrentMap<String, T>> list, String key) {
        if (list == null) {
            return null;
        }

        modelInt = Math.max(modelInt, 1);
        long modelNum = Math.abs(StrTool.strHex2long(key)) % modelInt;
        if (list.size() < modelInt) {
            return null;
        }
        ConcurrentMap<String, T> map = list.get(new Long(modelNum).intValue());
        return map.get(key);
    }

    /**
     * 从list中的map中删除值
     * @param modelInt list中的map的尺寸
     * @param list 放map的list
     * @param key 键
     * @param <T> 元素类型
     * @return 删除的元素
     */
    public static <T> T multiMapRemove(int modelInt, List<ConcurrentMap<String, T>> list, String key) {
        T result = null;
        if (list != null) {
            modelInt = Math.max(modelInt, 1);
            long modelNum = Math.abs(StrTool.strHex2long(key)) % modelInt;
            if (list.size() >= modelInt) {
                ConcurrentMap<String, T> map = list.get(new Long(modelNum).intValue());
                T obj = map.remove(key);
                result = obj;
            }
        }

        return result;
    }


    /**
     * 从list中的map中添加值
     * @param modelInt list中的map的尺寸
     * @param list 放map的list
     * @param key 键
     * @param val 值
     * @param <T> map中的值类型
     * @return 添加的值，成功的话为val,失败为空
     */
    public static <T> T multiMapPut(int modelInt, List<ConcurrentMap<String, T>> list, String key, T val) {
        if (list == null) {
            return null;
        }

        long modelNum = Math.abs(StrTool.strHex2long(key)) % modelInt;
        if (list.size() < modelInt) {
            synchronized (list) {
                if (list.size() < modelInt) {
                    for (int i = 0, leng = modelInt - list.size(); i < leng; ++i) {
                        list.add(new ConcurrentHashMap<String, T>());
                    }
                }
            }
        }
        try {
            ConcurrentMap<String, T> map = list.get(new Long(modelNum).intValue());
            map.put(key, val);
            return val;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 以bitLeng为模的多map获取值
     * 维护一个多ConcurrentMap的list的放值。取指定bitLeng的bit跟key相与，作为list的索引，然后根据索引取对应的map来操作
     * @param bitLeng map的容量指数。底数为2
     * @param list 放map的list
     * @param key 键
     * @param <K> 值类型
     * @param <T> map中的值类型
     * @return 添加的值，成功的话为val,失败为空
     */
    public static <K, T> T multiMapBitGet(int bitLeng, List<ConcurrentMap<K, T>> list, K key) {
        if (list == null || key == null) {
            return null;
        }

        bitLeng = Math.min(bitLeng, 10);
        long keyInt = key instanceof Long ? (Long) key : key instanceof Integer ? (Integer) key :
                NumberUtil.isNumber(key.toString()) ? Long.parseLong(key.toString())
                        : StrTool.strHex2long(key.toString());
        keyInt = Math.abs(keyInt);
        int modelInt = 1 << bitLeng;
        long modelNum = (modelInt - 1) & keyInt;
        if (list.size() < modelInt) {
            return null;
        }
        ConcurrentMap<K, T> map = list.get(new Long(modelNum).intValue());
        return map.get(key);
    }

    /**
     * 以bitLeng为模的多map删除key的值
     * 维护一个多ConcurrentMap的list的放值。取指定bitLeng的bit跟key相与，作为list的索引，然后根据索引取对应的map来操作
     * @param bitLeng map的容量指数。底数为2
     * @param list 放map的list
     * @param key 键
     * @param <K> 值类型
     * @param <T> map中的值类型
     * @return 被删除的值
     */
    public static <K, T> T multiMapBitRemove(int bitLeng, List<ConcurrentMap<K, T>> list, K key) {
        if (list == null || key == null) {
            return null;
        }

        bitLeng = Math.min(bitLeng, 10);
        long keyInt = key instanceof Long ? (Long) key : key instanceof Integer ? (Integer) key :
                NumberUtil.isNumber(key.toString()) ? Long.parseLong(key.toString())
                        : StrTool.strHex2long(key.toString());
        keyInt = Math.abs(keyInt);
        int modelInt = 1 << bitLeng;
        long modelNum = (modelInt - 1) & keyInt;
        if (list.size() < modelInt) {
            return null;
        }
        ConcurrentMap<K, T> map = list.get(new Long(modelNum).intValue());
        return map.remove(key);
    }

    /**
     * 以bitLeng为模的多map放值
     * 维护一个多ConcurrentMap的list的放值。取指定bitLeng的bit跟key相与，作为list的索引，然后根据索引取对应的map来操作
     *
     * @param bitLeng map的容量指数。底数为2
     * @param list 放map的list
     * @param key 键
     * @param <K> 值类型
     * @param <T> map中的值类型
     * @return 添加的值
     */
    public static <K, T> T multiMapBitPut(int bitLeng, List<ConcurrentMap<K, T>> list, K key, T val) {
        if (list == null || key == null) {
            return null;
        }

        bitLeng = bitLeng > 10 ? 10 : bitLeng;
        long keyInt = key instanceof Long ? (Long) key : key instanceof Integer ? (Integer) key :
                key.toString().matches("-?[0-9]+") ? Long.valueOf(key.toString())
                        : StrTool.strHex2long(key.toString());
        keyInt = Math.abs(keyInt);
        int modelInt = 1 << bitLeng;
        long modelNum = (modelInt - 1) & keyInt;
        if (list.size() < modelInt) {
            synchronized (list) {
                if (list.size() < modelInt) {
                    for (int i = 0, leng = modelInt - list.size(); i < leng; ++i) {
                        list.add(new ConcurrentHashMap<>());
                    }
                }
            }
        }
        try {
            ConcurrentMap<K, T> map = list.get(new Long(modelNum).intValue());
            map.put(key, val);
            return val;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取map的第一个键值对
     * @param map 要获取值的map
     * @param <K> map的key类型
     * @param <V> map的value类型
     * @return map的第一个健值对
     */
    public static <K, V> Entry<K, V> getHead(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        return map.entrySet().iterator().next();
    }

    /**
     * 获取map的第一个值
     * @param map 要获取值的map
     * @param <K> map的key类型
     * @param <V> map的value类型
     * @return map的第一个值
     */
    public static <K, V> V getHeadVal(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        return map.entrySet().iterator().next().getValue();
    }

    /**
     * 获取map的最后一个键值对
     * @param map 要获取值的map
     * @param <K> map的key类型
     * @param <V> map的value类型
     * @return map的最后一个健值对
     */
    public static <K, V> Entry<K, V> getTail(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
        Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }

    /**
     * 获取map的最后一个值
     * @param map 要获取值的map
     * @param <K> map的key类型
     * @param <V> map的value类型
     * @return map的最后一个值
     */
    public static <K, V> V getTailVal(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
        Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            return iterator.next().getValue();
        }
        return null;
    }

    public static List<Map<String, Object>> beans2mapList(List list, Collection<String> allowKey){
        List<Map<String, Object>> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return result;
        }
        for(Object elem : list) {
            Map<String, Object> map = BeanUtil.beanToMap(elem, false, true);
            List<String> keys = new ArrayList(map.keySet());
            for(String key : keys){
                if(!allowKey.contains(key)){
                    map.remove(key);
                }
            }
            if(MapUtils.isNotEmpty(map)) {
                result.add(map);
            }
        }
        return result;
    }

    public static <K, V> void putNoNullVal(final Map<K, V> map, K key, V val){
        if(map == null || val == null){
            return;
        }
        map.put(key, val);
    }

    public static <K> void putNoBlank(Map<K, Object> map, K key, String val){
        if(map == null ||  StringUtils.isBlank(val)){
            return;
        }
        map.put(key, val);
    }

    public static String joinByKeys(Map map, String separator, Object ...objs){
        if(MapUtil.isEmpty(map)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(Object obj : objs) {
            Object val = map.get(obj);
            if(val==null) {
                continue;
            }
            if(StrUtil.isNotBlank(sb)) {
                sb.append(COMMA);
            }
            sb.append(val);
        }
        return sb.toString();
    }
}
