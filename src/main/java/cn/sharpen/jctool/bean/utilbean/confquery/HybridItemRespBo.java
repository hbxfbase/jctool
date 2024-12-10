package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 查询返回BO
 *
 * @author justin
 */
@Data
public class HybridItemRespBo {

    /**
     * 返回的数据列表
     */
    private List<LinkedHashMap<String, Object>> rowList;
    /**
     * 此列表的查询参数
     */
    private LinkedHashMap<String, Object> queryDepMap;
    /**
     * 表名
     */
    private String tableName;

    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
