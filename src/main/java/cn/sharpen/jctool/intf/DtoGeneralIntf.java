package cn.sharpen.jctool.intf;

import java.util.HashMap;
import java.util.Map;

/**
 * VO类通用接口
 * @author sharpen
 */
public interface DtoGeneralIntf {
    Map extMap = new HashMap();
    /**
     * 设置扩展属性的值
     * @param key
     * @param val
     */
    default void setExtVal(String key, Object val){
        extMap.put(key, val);
    }
}
