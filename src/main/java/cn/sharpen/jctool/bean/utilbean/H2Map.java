package cn.sharpen.jctool.bean.utilbean;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * 双层map
 * @param <K1> 一层key
 * @param <K2> 二层key
 * @param <V> 值
 */
public class H2Map<K1, K2, V> {
    private HashMap<K1, HashMap<K2, V>> map = new HashMap();
    public V put(K1 k1, K2 k2, V val){
        HashMap<K2, V> map1 = map.get(k1);
        if(map1==null){
            map1 = new HashMap<>();
            map.put(k1, map1);
        }
        return map1.put(k2, val);
    }

    public HashMap<K1, HashMap<K2, V>> get(){
        return map;
    }
    public V get(K1 k1, K2 k2){
        HashMap<K2, V> map1 = map.get(k1);
        if(map1==null){
            return null;
        }
        return map1.get(k2);
    }
    public HashMap<K2, V> get(K1 k1){
        HashMap<K2, V> map1 = map.get(k1);
        return map1;
    }
    public static <PK1, PK2, PV> PV getV(H2Map<PK1, PK2, PV> h2map, PK1 k1, PK2 k2){
        if(h2map==null) {
            return null;
        }
        return h2map.get(k1,k2);
    }
    public static <PK1, PK2, PV> HashMap<PK2, PV> getV(H2Map<PK1, PK2, PV> h2map, PK1 k1){
        if(h2map==null) {
            return null;
        }
        return h2map.get(k1);
    }

    public static <T, K1, K2, V> H2Map init(List<T> list, Function<T, K1> k1, Function<T, K2> k2,
                                                                  Function<T, V> val){
        H2Map h2Map = inst();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        for(T obj : list){
            h2Map.put(k1.apply(obj), k2.apply(obj), val.apply(obj));
        }
        return h2Map;
    }

    public static int size(H2Map h2Map){
        return h2Map == null ? 0 : h2Map.get().size();
    }

    public static boolean isEmpty(H2Map h2Map) {
        return size(h2Map) == 0;
    }

    public static H2Map inst(){
        H2Map map = new H2Map();
        return map;
    }

}
