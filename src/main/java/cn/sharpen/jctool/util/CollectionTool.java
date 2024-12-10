package cn.sharpen.jctool.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CollectionTool {

    /**
     * 反转list里的元素。
     * hutool的对应的方法的clone有bug
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> reverseNew(List<T> list) {
        List<T> newList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return newList;
        }
        for (int i = 0, end = list.size() - 1; i <= end; --end) {
            newList.add(list.get(end));
        }
        return newList;
    }

    /**
     * 所有容器都为空
     *
     * @param colts
     * @return
     */
    public static boolean allEmpty(Collection... colts) {
        if (ArrayUtils.isEmpty(colts)) {
            return true;
        }
        for (Collection colt : colts) {
            if (!CollectionUtils.isEmpty(colt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 所有容器都不为空
     *
     * @param colts
     * @return
     */
    public static boolean allNotEmpty(Collection... colts) {
        if (ArrayUtils.isEmpty(colts)) {
            return false;
        }
        for (Collection colt : colts) {
            if (CollectionUtils.isEmpty(colt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 从list中的map中获取值
     * 用于更高的map的锁并发
     *
     * @param modelInt list中的map的尺寸
     * @param list     放map的list
     * @param key      键
     * @param <T>      元素类型
     * @return 对应key的元素值
     */
    public static <T> T multiMapGet(int modelInt, List<ConcurrentMap<String, T>> list, String key) {
        if (list == null) {
            return null;
        }

        modelInt = modelInt < 1 ? 1 : modelInt;
        long modelNum = Math.abs(StrTool.strHex2long(key)) % modelInt;
        if (list.size() < modelInt) {
            return null;
        }
        ConcurrentMap<String, T> map = list.get(new Long(modelNum).intValue());
        T obj = map.get(key);
        return obj;
    }

    /**
     * 从list中的map中删除值
     *
     * @param modelInt list中的map的尺寸
     * @param list     放map的list
     * @param key      键
     * @param <T>      元素类型
     * @return 删除的元素
     */
    public static <T> T multiMapRemove(int modelInt, List<ConcurrentMap<String, T>> list, String key) {
        if (list == null) {
            return null;
        }

        modelInt = modelInt < 1 ? 1 : modelInt;
        long modelNum = Math.abs(StrTool.strHex2long(key)) % modelInt;
        if (list.size() < modelInt) {
            return null;
        }
        ConcurrentMap<String, T> map = list.get(new Long(modelNum).intValue());
        T obj = map.remove(key);
        return obj;
    }


    /**
     * 从list中的map中添加值
     *
     * @param modelInt list中的map的尺寸
     * @param list     放map的list
     * @param key      键
     * @param val      值
     * @param <T>      map中的值类型
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
     *
     * @param bitLeng map的容量指数。底数为2
     * @param list    放map的list
     * @param key     键
     * @param <K>     值类型
     * @param <T>     map中的值类型
     * @return 添加的值，成功的话为val,失败为空
     */
    public static <K, T> T multiMapBitGet(int bitLeng, List<ConcurrentMap<K, T>> list, K key) {
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
            return null;
        }
        ConcurrentMap<K, T> map = list.get(new Long(modelNum).intValue());
        T obj = map.get(key);
        return obj;
    }

    /**
     * 以bitLeng为模的多map删除key的值
     * 维护一个多ConcurrentMap的list的放值。取指定bitLeng的bit跟key相与，作为list的索引，然后根据索引取对应的map来操作
     *
     * @param bitLeng map的容量指数。底数为2
     * @param list    放map的list
     * @param key     键
     * @param <K>     值类型
     * @param <T>     map中的值类型
     * @return 被删除的值
     */
    public static <K, T> T multiMapBitRemove(int bitLeng, List<ConcurrentMap<K, T>> list, K key) {
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
            return null;
        }
        ConcurrentMap<K, T> map = list.get(new Long(modelNum).intValue());
        T obj = map.remove(key);
        return obj;
    }

    /**
     * 以bitLeng为模的多map放值
     * 维护一个多ConcurrentMap的list的放值。取指定bitLeng的bit跟key相与，作为list的索引，然后根据索引取对应的map来操作
     *
     * @param bitLeng map的容量指数。底数为2
     * @param list    放map的list
     * @param key     键
     * @param <K>     值类型
     * @param <T>     map中的值类型
     * @return
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
                        list.add(new ConcurrentHashMap<K, T>());
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
     *
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
     *
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
     *
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
     *
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
            tail = iterator.next();
        }
        return tail.getValue();
    }


    /**
     * 将对象放到集合中
     *
     * @param elems 多个元素
     * @param <T>   元素类型
     * @return 元素的集合
     */
    public static <T> Set<T> toSet(T... elems) {
        Set<T> targSet = new HashSet<>();
        if (ArrayUtils.isEmpty(elems)) {
            return targSet;
        }
        for (T elem : elems) {
            if (elem == null) {
                continue;
            }
            targSet.add(elem);
        }
        return targSet;
    }


    /**
     * 元素是否包含key
     *
     * @param map 要判断的map
     * @param key 要判断的key
     * @param <T> key类型
     * @return map是否包含key, 如果map为空，返回false
     */
    public static <T> boolean mapContainsKey(Map map, T key) {
        if (MapUtils.isEmpty(map)) {
            return false;
        }
        Object obj = map.get(key);
        return obj != null;
    }

    public static <T> LinkedHashSet<T> noEmptySet(LinkedHashSet<T>... sets) {
        for (LinkedHashSet<T> set : sets) {
            if (CollectionUtils.isNotEmpty(set)) {
                return set;
            }
        }
        return null;
    }

    /**
     * 将List转成值为List的Map
     *
     * @param objs 需要抽取属性值的对象列表
     * @param prop 属性名称或者方法名称
     * @return 属性值列表的映射
     */
    public static <T> Map<Object, List<T>> list2mapList(List<T> objs, String prop) {
        Map<Object, List<T>> vals = new LinkedHashMap<Object, List<T>>();
        if (CollectionUtils.isEmpty(objs)) {
            return null;
        }
        // 属性方法
        Method method = null;
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            if (method == null) {
                method = ObjTool.getMethodByNameFromObj(obj, prop);
                if (method == null) {
                    return vals;
                }
            }
            try {
                Object retnObj = method.invoke(obj);
                if (retnObj != null) {
                    List<T> list = vals.get(retnObj);
                    if (list == null) {
                        list = new ArrayList<>();
                        vals.put(retnObj, list);
                    }
                    list.add(obj);
                }
            } catch (Exception e) {
                return vals;
            }
        }
        return vals;
    }

    /**
     * 将List转成Map
     *
     * @param objs 需要抽取属性值的对象列表
     * @param prop 属性名称或者方法名称
     * @return 属性值集合
     */
    public static <T> Map<Object, T> list2map(List<T> objs, String prop) {
        Map<Object, T> vals = new LinkedHashMap<Object, T>();
        if (CollectionUtils.isEmpty(objs)) {
            return vals;
        }
        // 属性方法
        Method method = null;
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            if (method == null) {
                method = ObjTool.getMethodByNameFromObj(obj, prop);
                if (method == null) {
                    return vals;
                }
            }
            try {
                Object retnObj = method.invoke(obj);
                if (retnObj != null) {
                    vals.put(retnObj, obj);
                }
            } catch (Exception e) {
                return vals;
            }
        }
        return vals;
    }


    /**
     * 将List转成Map
     *
     * @param objs    需要抽取属性值的对象列表
     * @param propKey 键的属性名称或者方法名称
     * @param propVal 值的属性名称或者方法名称
     * @return 属性值集合
     */
    public static <T, K, V> Map<K, V> list2mapProp(List<T> objs, String propKey, String propVal, Class<K> kc,
                                                   Class<V> vc) {
        Map<K, V> targMap = new HashMap<>();
        Map<Object, Object> map = list2mapProp(objs, propKey, propVal);
        if (MapUtils.isNotEmpty(map)) {
            for (Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey(), v = entry.getValue();
                if (k == null || v == null) {
                    continue;
                }
                if (kc.isAssignableFrom(k.getClass()) && vc.isAssignableFrom(v.getClass())) {
                    targMap.put((K) k, (V) v);
                }
            }
        }
        return targMap;
    }

    /**
     * 将List转成Map
     *
     * @param objs    需要抽取属性值的对象列表
     * @param propKey 键的属性名称或者方法名称
     * @param propVal 值的属性名称或者方法名称
     * @return 属性值集合
     */
    public static <T> Map<String, String> list2mapPropStr(List<T> objs, String propKey, String propVal) {
        Map<Object, Object> map = list2mapProp(objs, propKey, propVal);
        if (MapUtils.isNotEmpty(map)) {
            Map<String, String> strMap = new HashMap<>();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                strMap.put(entry.getKey().toString(), entry.getValue().toString());
            }
            return strMap;
        }
        return null;
    }

    /**
     * 将List转成Map
     *
     * @param objs    需要抽取属性值的对象列表
     * @param propKey 键的属性名称或者方法名称
     * @param propVal 值的属性名称或者方法名称
     * @return 属性值集合
     */
    public static <T> Map<Object, Object> list2mapProp(List<T> objs, String propKey, String propVal) {
        Map<Object, Object> vals = new LinkedHashMap<>();
        if (CollectionUtils.isEmpty(objs)) {
            return null;
        }
        // 属性方法
        Method methodKey = null;
        Method methodVal = null;
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            if (methodKey == null) {
                methodKey = ObjTool.getMethodByNameFromObj(obj, propKey);
                if (methodKey == null) {
                    return vals;
                }
            }
            if (methodVal == null) {
                methodVal = ObjTool.getMethodByNameFromObj(obj, propVal);
                if (methodVal == null) {
                    return vals;
                }
            }
            try {
                Object keyObj = methodKey.invoke(obj);
                Object valObj = methodVal.invoke(obj);
                if (keyObj != null && valObj != null) {
                    vals.put(keyObj, valObj);
                }
            } catch (Exception e) {
                return vals;
            }
        }
        return vals;
    }

    /**
     * 将List转成Map
     *
     * @param objs 需要抽取属性值的对象列表
     * @param prop 属性名称或者方法名称
     * @param cla  属性的数据类型
     * @return 属性值集合
     */
    @SuppressWarnings("unchecked")
    public static <K, T> Map<K, T> list2map(List<T> objs, String prop, Class<K> cla) {
        Map<K, T> vals = new LinkedHashMap<K, T>();
        if (CollectionUtils.isEmpty(objs)) {
            return null;
        }
        // 属性方法
        Method method = null;
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            if (method == null) {
                method = ObjTool.getMethodByNameFromObj(obj, prop);
                if (method == null) {
                    return vals;
                }
            }
            try {
                Object retnObj = method.invoke(obj);
                if (retnObj != null) {
                    vals.put((K) retnObj, obj);
                }
            } catch (Exception e) {
                return vals;
            }
        }
        return vals;
    }

    public static boolean hasEmptyList(Collection... list) {
        if (ArrayUtils.isEmpty(list)) {
            return true;
        }
        for (Collection elem : list) {
            if (CollectionUtils.isEmpty(elem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 求两个list的交集
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> listIntersection(Collection<T> list1, Collection<T> list2) {
        List<T> result = new ArrayList<>();
        if (hasEmptyList(list1, list2)) {
            return result;
        }
        for (T elem : list1) {
            if (list2.contains(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

    /**
     * 将数组拆分成Map。 自然序列的奇数为key, 偶数为value 例：{"cnyPrice", "10.2","devOrderId","15292090210000000","devOrderSubject","gussing","payType","PYC","totalamount","2"}
     * 返回cnyPrice为key,10.2为value
     *
     * @param origin 需要处理的源数组
     */
    public static <T> Map<T, T> array2map(T[] origin) {
        if (origin == null || origin.length < 2) {
            return null;
        }
        Map<T, T> retnMap = new LinkedHashMap<>();
        for (int i = 0, leng = origin.length - 1; i < leng; i += 2) {
            retnMap.put(origin[i], origin[i + 1]);
        }
        return retnMap;
    }


    /**
     * 抽取对象列表的属性值
     *
     * @param objs 需要抽取属性值的对象列表
     * @param prop 属性名称或者方法名称
     * @param cl   属性的数据类型
     * @return 属性值集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> abstract2list(List objs, String prop, Class<T> cl) {
        return new ArrayList<>(abstrict(objs, prop, cl));
    }

    /**
     * 抽取对象列表的属性值
     *
     * @param objs 需要抽取属性值的对象列表
     * @param prop 属性名称或者方法名称
     * @return 属性值集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Set<String> abstrictStrTrim(List objs, String prop) {
        Set<String> set = abstrict(objs, prop, String.class);
        Set<String> setTrim = new HashSet<>(set.size());
        for (String str : set) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            setTrim.add(str.trim());
        }
        return setTrim;
    }

    /**
     * 抽取对象列表的属性值
     *
     * @param objs 需要抽取属性值的对象列表
     * @param prop 属性名称或者方法名称
     * @param cl   属性的数据类型
     * @return 属性值集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Set<T> abstrict(List objs, String prop, Class<T> cl) {
        Set<T> vals = new LinkedHashSet<T>();
        if (CollectionUtils.isEmpty(objs) || StringUtils.isBlank(prop)) {
            return vals;
        }

        // 属性方法
        Method method = null;
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            if (method == null) {
                method = ObjTool.getMethodByNameFromObj(obj, prop);
                if (method == null) {
                    return vals;
                }
            }
            try {
                Object retnObj = method.invoke(obj);
                if (retnObj != null) {
                    vals.add((T) retnObj);
                }
            } catch (Exception e) {
                return vals;
            }
        }

        return vals;
    }


    /**
     * 批量抽取对象列表的属性
     *
     * @param objs    需要抽取属性值的对象列表
     * @param propMap 键为属性名称，值为抽取的结果需要存在的集合
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void abstrictBatch(List objs, Map<String, Set> propMap) {
        if (CollectionUtils.isEmpty(objs) || MapUtils.isEmpty(propMap)) {
            return;
        }

        Map<String, Method> propMethod = new LinkedHashMap<String, Method>();

        Method method = null;
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            for (Entry<String, Set> enty : propMap.entrySet()) {
                String key = enty.getKey();
                Set val = enty.getValue();
                if (key == null) {
                    continue;
                }
                method = propMethod.get(key);
                if (method == null) {
                    method = ObjTool.getMethodByNameFromObj(obj, key);
                    if (method == null) {
                        return;
                    }
                }
                try {
                    Object retnObj = method.invoke(obj);
                    if (retnObj != null) {
                        val.add(retnObj);
                    }
                } catch (Exception e) {
                    return;
                }
            }
        }

        return;
    }

    /**
     * 从一批对象中选取指定的属性的值在propVals中的对象
     */
    public static <K, T> List<T> listChoose(Collection<T> objs, String prop, Collection<K> propVals) {
        List<T> vals = new ArrayList<T>();
        if (CollectionUtils.isEmpty(objs) || CollectionUtils.isEmpty(propVals) || StringUtils
                .isBlank(prop)) {
            return vals;
        }
        Map<Object, T> objPropMap = list2map(new ArrayList<>(objs), prop);
        // 属性方法
        Method method = null;
        for (K propVal : propVals) {
            if (propVal == null) {
                continue;
            }
            T obj = objPropMap.get(propVal);

            if (method == null) {
                method = ObjTool.getMethodByNameFromObj(obj, prop);
                if (method == null) {
                    return vals;
                }
            }
            try {
                Object retnObj = method.invoke(obj);
                if (retnObj != null) {
                    if (propVal == retnObj) {
                        vals.add(obj);
                    } else if (propVal instanceof String && StringUtils
                            .equals(propVal.toString(), retnObj.toString())) {
                        vals.add(obj);
                    } else if (propVal instanceof Integer && retnObj instanceof Integer) {
                        if (((Integer) propVal).intValue() == (Integer) retnObj) {
                            vals.add(obj);
                        }
                    } else if (propVal instanceof Long && retnObj instanceof Long) {
                        if (((Long) propVal).longValue() == (Long) retnObj) {
                            vals.add(obj);
                        }
                    } else if (propVal.equals(retnObj)) {
                        vals.add(obj);
                    }
                }
            } catch (Exception e) {
                return vals;
            }
        }

        return vals;
    }


    /**
     * 抽取map中的对象，然后放到一个数组列表里面。用于2层结构
     */
    public static <T> List<T> abstrictMapObj2(Map<String, Map<String, T>> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        List<T> objs = new ArrayList<>();
        for (Entry<String, Map<String, T>> enty1 : map.entrySet()) {
            objs.addAll(enty1.getValue().values());
        }
        return objs;
    }

    public static String colt2str(Collection colt) {
        if (CollectionUtils.isEmpty(colt)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : colt) {
            sb.append(obj.toString()).append(StrPool.COMMA);
        }
        return sb.toString();

    }


    /**
     * 将List转成3层Map
     *
     * @param objs  需要抽取属性值的对象列表
     * @param prop1 属性名称或者方法名称。只支持String类型
     * @param prop2 属性名称或者方法名称。只支持String类型
     * @param prop3 属性名称或者方法名称。只支持String类型
     * @return 属性值集合
     */
    public static <T> Map<Object, Map<Object, Map<Object, T>>> list2map3(List<T> objs, String prop1,
                                                                         String prop2, String prop3) {
        Map<Object, Map<Object, Map<Object, T>>> vals = new LinkedHashMap<>();
        Map<String, Method> methodMap = new HashMap<>(3);
        if (CollectionUtils.isEmpty(objs)) {
            return null;
        }
        // 属性方法
        Method method1 = null;
        Method method2 = null;
        Method method3 = null;
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            method1 = methodMap.get(prop1);
            if (method1 == null) {
                method1 = ObjTool.getMethodByNameFromObj(obj, prop1);
                if (method1 == null) {
                    return vals;
                }
                methodMap.put(prop1, method1);
            }
            method2 = methodMap.get(prop2);
            if (method2 == null) {
                method2 = ObjTool.getMethodByNameFromObj(obj, prop2);
                if (method2 == null) {
                    return vals;
                }
                methodMap.put(prop2, method2);
            }
            method3 = methodMap.get(prop3);
            if (method3 == null) {
                method3 = ObjTool.getMethodByNameFromObj(obj, prop3);
                if (method3 == null) {
                    return vals;
                }
                methodMap.put(prop3, method3);
            }
            try {
                Object obj1 = method1.invoke(obj);
                Object obj2 = method2.invoke(obj);
                Object obj3 = method3.invoke(obj);
                if (obj1 != null && obj2 != null && obj3 != null) {
                    Map<Object, Map<Object, T>> map2 = vals.get(obj1);
                    if (map2 == null) {
                        map2 = new HashMap<>();
                        vals.put(obj1, map2);
                    }
                    Map<Object, T> map3 = map2.get(obj2);
                    if (map3 == null) {
                        map3 = new HashMap<>();
                        map2.put(obj2, map3);
                    }
                    map3.put(obj3, obj);
                }
            } catch (Exception e) {
                return vals;
            }
        }
        return vals;
    }

    public static List<Object> listStr2obj(List<String> strs) {
        List<Object> objs = new ArrayList<>();
        if (strs == null) {
            return objs;
        }
        for (String str : strs) {
            objs.add(str);
        }
        return objs;
    }

    /**
     * 只保存retain中包含的元素
     *
     * @param origin
     * @param retain
     * @param <K>
     * @return
     */
    public static <K> Collection<K> listRetain(Collection<K> origin, Collection<K> retain) {
        if (CollectionUtils.isEmpty(origin) || CollectionUtils.isEmpty(retain)) {
            return origin;
        }
        List<K> remove = new ArrayList<>();
        for (K elem : origin) {
            if (!retain.contains(elem)) {
                remove.add(elem);
            }
        }
        if (CollectionUtils.isNotEmpty(remove)) {
            origin.removeAll(remove);
        }
        return origin;
    }

    public static <E> void addMany(Collection<E> collection, E... elems) {
        if (collection == null) {
            return;
        }
        if (ArrayUtils.isEmpty(elems)) {
            return;
        }
        for (E elem : elems) {
            collection.add(elem);
        }
    }

    public static <E> Collection<E> removeVal(Collection<E> collection, E val) {
        if (collection == null) {
            return collection;
        }
        while (collection.contains(val)) {
            collection.remove(val);
        }
        return collection;
    }

    /**
     * 将collection中比retain多的元素删除
     *
     * @param collection
     * @param retain
     * @param <E>
     * @return
     */
    public static <E> List<E> removeSurplus(Collection<E> collection, Collection<E> retain) {
        if (collection == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(retain)) {
            return null;
        }
        Collection removeCollection = new ArrayList();
        for (E elem : collection) {
            if (!retain.contains(elem)) {
                removeCollection.add(elem);
            }
        }
        collection.removeAll(removeCollection);
        return new ArrayList<>(collection);
    }

    /**
     * 合并两个List返回一个新对象
     * @param aa
     * @param bb
     * @param <E>
     * @return
     */
    public static <E> List<E> merge(List<E> aa, List<E> bb) {
        List<E> target = new ArrayList<>();
        CollectionUtil.addAll(target, aa);
        CollectionUtil.addAll(target, bb);
        return target;
    }

    /**
     * 从队列中取出指定数目的元素。如果不够的话有多少就取多少
     * @param queue 队列
     * @param qty 要获取的元素的数量
     * @param <E>
     * @return
     */
    public static <E> List<E> popMany(Queue<E> queue, int qty) {
        List<E> list = new ArrayList<>(qty);
        if(CollectionUtils.isEmpty(queue)) {
            return list;
        }
        for(int i=0; i<qty; ++i) {
            if(queue.isEmpty()) {
                return list;
            }
            list.add(queue.remove());
        }
        return list;
    }

    /**
     * 反转map的健值.健变为值,值变为键
     *
     * @param map
     * @return
     */
    public static <K,V> Map<String, Object> mapObjConvert(Map<K, V> map) {
        Map<String, Object> targ = new LinkedHashMap<>();
        if (MapUtils.isNotEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                targ.put(ObjTool.obj2str(entry.getKey()), entry.getValue());
            }
        }
        return targ;
    }

    /**
     * 反转map的健值.健变为值,值变为键
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> convertMap(Map<V, K> map) {
        Map<K, V> targ = new LinkedHashMap<>();
        if (MapUtils.isNotEmpty(map)) {
            for (Entry<V, K> entry : map.entrySet()) {
                targ.put(entry.getValue(), entry.getKey());
            }
        }
        return targ;
    }

    public static <T> T first(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection) ? null : collection.iterator().next();
    }

    public static <K, V> String valStr(Map<K, V> map, K key) {
        String s = MapUtils.isEmpty(map) || map.get(key) == null ? null : map.get(key).toString();
        return s;
    }

    public static <K, V> Integer valInteger(Map<K, V> map, K key) {
        Integer s = MapUtils.isEmpty(map) || map.get(key) == null ? null : Integer.valueOf(map.get(key).toString());
        return s;
    }


    /**
     * 从map里面获对象并转成字符串
     *
     * @param map
     * @return
     */
    public static String mapValStr(Map map, Object key) {
        //新增取值为null的场合
        if (map == null || key == null || !map.containsKey(key) || map.get(key) == null) {
            return null;
        }
        return map.get(key).toString();
    }

    public static <K, V> Map<K, V> mapRemove(Map<K, V> map, Collection<K> keys) {
        if (MapUtils.isEmpty(map) || CollectionUtils.isEmpty(keys)) {
            return map;
        }
        for (K key : keys) {
            map.remove(key);
        }
        return map;
    }

    public static <K, V> Map<K, V> mapRetain(Map<K, V> map, Collection<K> keys) {
        if (MapUtils.isEmpty(map) || CollectionUtils.isEmpty(keys)) {
            return map;
        }
        Set<K> keySet = new HashSet<>(map.keySet());
        for (K key : keySet) {
            if (!keys.contains(key)) {
                map.remove(key);
            }
        }
        return map;
    }

    /**
     * 判断两个map中的元素的值转成字符串后是否相等
     *
     * @param target
     * @param original
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean mapStrEqual(Map<K, V> target, Map<K, V> original) {
        if (MapUtils.isEmpty(target) && MapUtils.isEmpty(target)) {
            return true;
        }
        if ((MapUtils.isEmpty(target) && MapUtils.isNotEmpty(target))
                || (MapUtils.isNotEmpty(target) && MapUtils.isEmpty(target))) {
            return false;
        }
        if (target.size() != target.size()) {
            return false;
        }

        for (Entry<K, V> entry : target.entrySet()) {
            V originalVal = original.get(entry.getKey());
            if (ObjTool.objStrCompare(entry.getValue(), originalVal) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断第二个map有没有修改值
     *
     * @param oldMap
     * @param modifyMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean mapAsModify(Map<K, V> oldMap, Map<K, V> modifyMap) {
        Map<K, V> map = mapModify(oldMap, modifyMap);
        return MapUtils.isNotEmpty(map);
    }

    /**
     * 找出第二个map修改的值的map(删除相同的值）
     *
     * @param oldMap
     * @param modifyMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> mapModify(Map<K, V> oldMap, Map<K, V> modifyMap) {
        if (MapUtils.isEmpty(oldMap) || MapUtils.isEmpty(modifyMap)) {
            return modifyMap;
        }
        Set<K> equalsKeys = new HashSet<>();
        for (Entry<K, V> entry : modifyMap.entrySet()) {
            K key = entry.getKey();
            V val = entry.getValue();
            V oldVal = oldMap.get(key);
            if (ObjTool.objStrCompare(val, oldVal)) {
                equalsKeys.add(key);
            }
        }
        // 把相同的值的key删除，剩下的就是修改的key
        mapRemove(modifyMap, equalsKeys);
        return modifyMap;
    }

    /**
     * 同时从map里面获取多个值
     *
     * @param map
     * @param <V>
     * @return
     */
    public static <K, V> V mapGet(Map<K, V> map, K key) {
        if (map == null || key == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 同时从map里面获取多个值
     *
     * @param map
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> List<V> mapGets(Map<K, V> map, Collection<K> keys) {
        List<V> list = new ArrayList<>();
        if (MapUtils.isEmpty(map) || CollectionUtils.isEmpty(keys)) {
            return list;
        }
        for (K key : keys) {
            if (map.containsKey(key) == false) {
                list.add(map.get(key));
            }
        }
        return list;
    }

    public static <K, V> V list1mapVal(List<Map<K, V>> listMap, K key) {
        if (CollectionUtils.isEmpty(listMap)) {
            return null;
        }
        Map<K, V> map = listMap.get(0);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return map.get(key);
    }

    public static <K, V> String list1mapValStr(List<Map<K, V>> listMap, K key) {
        V val = list1mapVal(listMap, key);
        if (val == null) {
            return null;
        }
        return val.toString();
    }

    /**
     * 判断map中指的key列表有空值
     *
     * @param map  要判断的map
     * @param keys 要判断值key列表
     * @param <T>
     * @return 是否有空值
     */
    public static <T> boolean mapHasBlankVal(Map<String, T> map, String... keys) {
        if (MapUtils.isEmpty(map) || ArrayUtils.isEmpty(keys)) {
            return true;
        }
        for (String elem : keys) {
            T val = map.get(elem);
            if (val == null || StringUtils.isBlank(val.toString())) {
                return true;
            }
        }
        return false;
    }

    public static <E> Collection<E> removeAll(Collection<E> original, Collection<E> remove) {
        if (CollectionUtils.isEmpty(original)) {
            return null;
        }
        if (CollectionUtils.isEmpty(remove)) {
            return original;
        }
        return CollectionUtils.removeAll(original, remove);
    }

}
