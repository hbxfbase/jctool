package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.experimental.SuperBuilder;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 混合查询配置BO
 * 用于一个实体的360度查询，对应于一条配置记录
 * @author justin
 */
@SuperBuilder
@Data
public class HybridConfBo {

    /**
     * 与另一个表的链接
     */
    private List<ConditionBo> conditionList;
    /**
     * 与另一个表的链接
     */
    private List<HybridDisplayBo> displayConf;

    public static final long serialVersionUID = 673125L;
    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
