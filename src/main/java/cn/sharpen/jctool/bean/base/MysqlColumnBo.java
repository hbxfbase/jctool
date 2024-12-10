package cn.sharpen.jctool.bean.base;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * mysql列对象
 */
@Data
public class MysqlColumnBo implements Serializable {
    /**
     * 列名
     */
    private String column;
    /**
     * 列数据类型
     */
    private String type;
    /**
     * 列数据类型
     */
    private String allowNull;
    /**
     * 列长度
     */
    private Integer strLength;
    /**
     * 列长度
     */
    private Integer intLength;
    /**
     * 默认值。例：0，''
     */
    private String def;
    /**
     * 备注
     */
    private String comment;


    public static final long serialVersionUID = 673125L;
    @Override
    public String toString(){
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
