package cn.sharpen.jctool.enums;


/**
 * 用户来源哪个平台(网站)枚举
 *
 * @author flyn
 */
public enum UserSourcePlatformEnum {


  PLATFORM_BDFP("bdfp", "bdfp平台"),
  PLATFORM_DEX("dex", "dex交易所"),


  ;





  public String code;
  public String describe;

  UserSourcePlatformEnum(String code, String msg) {
    this.code = code;
    this.describe = msg;
  }


  public static UserSourcePlatformEnum getEnumByCode(Integer code) {
    UserSourcePlatformEnum result = null;
    for (UserSourcePlatformEnum bizCodeEnum : UserSourcePlatformEnum.values()) {
      if (bizCodeEnum.code.equals(code)) {
        result = bizCodeEnum;
        break;
      }
    }
    return result;
  }

  public String getCode() {
    return this.code;
  }

  public String getDescribe() {
    return this.describe;
  }

}
