package cn.sharpen.jctool.bean.utilbean.biz;


import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 币种信息-视图对象
 * 关联PO:    com.ebct.biz.provider.domain.model.mysql.CoinInfoPo
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class JwtPayloadBo {

    /**
     * token id
     */
    private String tid;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 登录的项目ID
     */
    private String pid;
    /**
     * 创建时间
     */
    private String iat;


    public static final long serialVersionUID = 673125L;
    @Override
    public String toString(){
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }

}