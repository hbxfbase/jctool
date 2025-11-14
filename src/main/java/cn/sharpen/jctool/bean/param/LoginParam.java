package cn.sharpen.jctool.bean.param;


import cn.sharpen.jctool.bean.utilbean.ExtMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 登录参数
 *
 * @author Justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginParam {

  /**
   * 是否为第三方登录，Y为是
   */
  private String thirdLogin=null;
  /**
   * 操作类型。register,login,resetPwd
   */
  private String operate=null;

  //bdfp里loginUser的主键
  private String loginUid=null;
  /**
   * 用户所属的项目（支持A项目的用户，登录B系统，在B系统中配置A的projId)
   */
  private String uidProjId=null;
  /**
   * 登录tokenId
   */
  private String tid = null;
  /**
   * 登录终端类型。例：快递公司express_company，快递员courier
   */
  private String terminalType = null;

  /**
   * 登录账户类型，分邮箱，手机号，账户，telegram
   */
  private String type = null;

  /**
   * 发送哪个?，分邮箱，手机号，账户 email,cellphone
   */
  private String sendType = null;

  /**
   * 登录账户
   */
  private String account = null;

  /**
   * 新账户 (新邮箱/新手机/新帐户名),一般用于修改绑定邮箱/手机/账户
   */
  private String newAccount = null;
  /**
   * 注册账户类型
   */
  private String category = null;
  /**
   * 账户验证码
   */
  private String verifyCode = null;

  /**
   * 登录附加验证码类型,比如动态验证码,邮箱验证码(密码登录也要的验证码)
   */
  private String verifyCodeType = null;

  /**
   * 新邮箱验证码 (一般用于修改新邮箱场景)
   */
  private String newVerifyCode = null;
  /**
   * 图形验证码
   */
  private String imgCode = null;
  /**
   * 登录密码
   */
  private String password = null;

  /**
   * 新密码密码(修改密码场景)
   */
  private String newPassword = null;

  //邀请码 (一般用于注册时候,登录时候)
  private String inviteCode = null;

  /**
   * 过期时长，单位：秒
   */
  private String expireSecond = null;

  /**
   * google登录credential
   */
  private String credential = null;

  /**
   * 扩展参数
   */
  private Map extParam=ExtMap.inst().get();

  public static final long serialVersionUID = 203L;

}
