package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 查询条件BO
 *
 * @author justin
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class ConditionBo {

    /**
     * 列名
     */
    private String col;
    /**
     * 查询条件显示名称
     */
    private String display;
    /**
     * eq,num,like
     */
    private String type;
    /**
     * 前端的值
     */
    private String val;
    /**
     * 查询的表
     */
    private String table;

    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
