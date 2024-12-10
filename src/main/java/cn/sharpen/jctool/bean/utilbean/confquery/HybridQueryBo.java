package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 混合查询BO
 * 用于一个实体的360度查询
 * @author justin
 */
@Data
public class HybridQueryBo {

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
