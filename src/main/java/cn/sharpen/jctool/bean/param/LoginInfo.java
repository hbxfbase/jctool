package cn.sharpen.jctool.bean.param;


import cn.sharpen.jctool.bean.utilbean.ExtMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 登录信息
 *
 * @author Justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginInfo {

    /**
     * 登录后token
     */
    private String token;

    /**
     * tokenId
     */
    private String tid;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 分超级管理员admin，平台管理员manager，会员member
     */
    private String grade;
    /**
     * 登录终端类型。例：快递公司express_company，快递员courier
     */
    private String terminalType = null;
    /**
     * 登录账户
     */
    private String account = null;
    /**
     * 呢称
     */
    private String nickname = null;
    /**
     * 使用状态。依赖enum_param。type为userStatus。正常normal，锁定lock
     */
    private String status = null;

    /**
     * 过期时长，单位：秒
     */
    private String expSecond = null;
    /**
     * 扩展参数
     */
    private Map extParam=ExtMap.inst().get();

    public static final long serialVersionUID = 203L;


}
