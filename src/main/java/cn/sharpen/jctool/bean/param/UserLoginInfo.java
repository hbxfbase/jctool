package cn.sharpen.jctool.bean.param;


import cn.sharpen.jctool.bean.utilbean.ExtMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 用户登录信息
 *
 * @author Justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginInfo {

    /**
     * 用户ID
     */
    private String id = null;

    /**
     * 手机区域码
     */
    private String areaCode = null;

    /**
     * 用户手机号
     */
    private String cellphone = null;
    /**
     * 用户类型
     */
    private String category = null;
    /**
     * 用户账户
     */
    private String loginAccount = null;
    /**
     * 用户昵称
     */
    private String nickname = null;

    /**
     * 填写描述，用户填写
     */
    private String dataDescribe = null;


    /**
     * 扩展参数
     */
    private Map extParam = ExtMap.inst().get();

    public static final long serialVersionUID = 203L;

}
