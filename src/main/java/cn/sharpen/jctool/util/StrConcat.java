package cn.sharpen.jctool.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.sharpen.jctool.consts.SymbolConst;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 字符串拼接
 * @author justin
 */
public class StrConcat {

    public static String signStrOrdinal(Map<String, Object> param, String itemSplit){
        return signStrOrdinal(param, true, itemSplit, SymbolConst.EQUALS);
    }
    public static String signStrOrdinal(Map<String, Object> param, Boolean asAsc, String itemSplit, String con){
        return signStrOrdinal(param, asAsc, itemSplit, con, null);
    }
    public static String signStrOrdinal(Map<String, Object> param, Boolean asAsc, String itemSplit, String con, Set<String> ignoreKeys){
        if(MapUtil.isEmpty(param)) {
            return null;
        }
        TreeSet<String> treeSet = new TreeSet(param.keySet());
        if(Boolean.FALSE.equals(asAsc)) {
            treeSet = (TreeSet)treeSet.descendingSet();
        }
        StringBuilder sb = new StringBuilder();
        for(String elem : treeSet) {
            Object val = param.get(elem);
            String valStr = val == null ? null : val.toString();
            if(StrUtil.hasBlank(elem, valStr)) {
                continue;
            }
            if(CollectionUtil.contains(ignoreKeys, elem)) {
                continue;
            }
            sb.append(elem).append(con).append(valStr).append(itemSplit);
        }
        String target = StrUtil.removeSuffix(sb, itemSplit);
        return target;
    }

}

