package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 显示列BO，用于配置返回结果列表的显示
 *
 * @author justin
 */
@Data
public class DisplayColBo {

    /**
     * 列名
     */
    private String col;
    /**
     * 查询条件显示名称
     */
    private String display;
    /**
     * 枚举分类
     */
    private String enu;

    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
