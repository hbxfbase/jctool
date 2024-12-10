package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 混合查询BO
 * 用于一个实体的360度查询
 * @author justin
 */
@SuperBuilder
@Data
public class HybridDisplayBo {

    /**
     * 与另一个表的链接
     */
    private List<LinkBo> link;
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
     * 列表名称。显示表格的名称
     */
    private String tableDesc;

    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
