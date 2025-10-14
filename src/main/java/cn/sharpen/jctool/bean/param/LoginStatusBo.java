package cn.sharpen.jctool.bean.param;


import cn.sharpen.jctool.bean.utilbean.ExtMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 登录状态（除了userId，其它跟数据库无关，无可修改的持久化数据）
 *
 * @author Justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginStatusBo {

  /**
   * 是否为第三方登录，Y为是
   */
  private String thirdLogin=null;
  /**
   * 用户ID
   */
  private Long userId = null;
  /**
   * 登录tokenId
   */
  private String tid = null;
  /**
   * 页面登录token
   */
  private String pageToken = null;
  /**
   * 登录终端类型。例：快递公司express_company，快递员courier
   */
  private String terminalType = null;

  /**
   * 登录账户类型，分邮箱，手机号，账户，telegram
   */
  private String type = null;

  /**
   * 登录账户
   */
  private String account = null;
  /**
   * 过期时长，单位：秒
   */
  private Long expireSecond = null;
  /**
   * 扩展参数
   */
  private Map extParam=ExtMap.inst().get();

  public static final long serialVersionUID = 203L;

}
