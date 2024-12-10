package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 查询请求BO，用于前端请求参数
 *
 * @author justin
 */
@Data
public class QueryReqBo {

    /**
     * 查询标识
     */
    private String mark;
    /**
     * 查询条件
     */
    private List<ConditionBo> condList;
    /**
     * 页数
     */
    private Integer pageNumber;
    /**
     * 页数
     */
    private Integer pageSize;
    /**
     * 查询参数
     */
    private LinkedHashMap<String, Object> queryDepMap;


    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
