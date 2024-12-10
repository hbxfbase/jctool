package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 选择值查询BO
 * 用于一个实体的选择值时的查询
 * @author justin
 */
@Data
public class SelectValBo {

    /**
     * 查询条件
     */
    private List<ConditionBo> conditionList;
    /**
     * 显示列
     */
    private List<DisplayColBo> displayCol;
    /**
     * 固定查询条件，例：enable_flag='Y' and status='F'
     */
    private String stableCond;
    /**
     * 排序字符串
     */
    private String orderByStr;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 列表名
     */
    private String listName;

    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
