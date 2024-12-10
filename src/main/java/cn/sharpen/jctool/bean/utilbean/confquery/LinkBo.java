package cn.sharpen.jctool.bean.utilbean.confquery;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 链接BO。用于从另一个表链接过来
 *
 * @author justin
 */
@Data
public class LinkBo {

    /**
     * 列名
     */
    private String col;
    /**
     * eq,num,like
     */
    private String type;
    /**
     * 依赖表
     */
    private String depTable;
    /**
     * 依赖列
     */
    private String depCol;

    public static final long serialVersionUID = 673125L;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }
}
