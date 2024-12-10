package cn.sharpen.jctool.bean.utilbean;

import cn.sharpen.jctool.util.JsonTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展map
 * @param <K> key
 * @param <V> value
 * @author justin
 */
public class ExtMap<K, V> {
    private HashMap<K, V> map = new HashMap();

    public HashMap<K, V> get(){
        return map;
    }

    public V get(K k){
        return map==null ? null : map.get(k);
    }

    public String toJson(){
        return JsonTool.obj2json(map);
    }

    public ExtMap setMap(HashMap<K, V> mapParam){
        this.map = mapParam;
        return this;
    }
    public ExtMap put(K key, V val){
        this.map.put(key, val);
        return this;
    }
    public ExtMap putMap(Map<K, V> map){
        if(map==null) {
            return this;
        }
        this.map.putAll(map);
        return this;
    }
    public ExtMap putExtMap(ExtMap<K, V> extMap){
        if(extMap==null || extMap.get()==null) {
            return this;
        }
        this.map.putAll(extMap.get());
        return this;
    }
    public ExtMap putNoNull(K key, V val){
        if(val==null) {
            return this;
        }
        this.map.put(key, val);
        return this;
    }

    public static <K, V> ExtMap init(Map<K, V> map){
        ExtMap extMap = new ExtMap();
        extMap.get().putAll(map);
        return extMap;
    }

    public static <K, V> ExtMap initDep(Map<K, V> map){
        ExtMap extMap = new ExtMap();
        if(map!=null) {
            extMap.setMap(new HashMap(map));
        }
        return extMap;
    }

    public static ExtMap inst(){
        ExtMap map = new ExtMap();
        return map;
    }

    public static int size(ExtMap h2Map){
        return h2Map == null ? 0 : h2Map.get().size();
    }

    @Override
    public String toString(){
        return JsonTool.obj2json(get());
    }

}
