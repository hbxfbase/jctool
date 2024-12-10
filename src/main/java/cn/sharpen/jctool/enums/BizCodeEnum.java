package cn.sharpen.jctool.enums;


/**
 * 业务码枚举
 *
 * <pre>
 * 响应码新增原则:
 * 1. 在当前响应码不能满足用户提示需求，考虑新增
 * 2. 如果大类能够满足前端的错误处理需求，请勿新增
 * 3. 在不能明确响应的错误分类再考虑新增分类
 * </pre>
 *
 * @author sharpen-auto
 */
public enum BizCodeEnum {

  /**
   * 业务正确响应
   */
  BIZ_SUCCESS("0", "业务提交成功"),
  BIZ_FAIL("-1", "业务提交失败"),

  /**
   * 客户端异常，包含请求参数类型错误、格式错误、缺少必要字段等
   */
  CLIENT_ABNORMAL("1000001", "客户端异常"),
  CLIENT_LACK_PARAM("1000002", "缺少参数"),
  CLIENT_ILLEGAL_PARAM("1000003", "非法参数"),
  CLIENT_PARAM_TYPE_WRONG("1000004", "参数类型错误"),
  CLIENT_MSG_FORM("1000005", "报文格式错误"),
  CLIENT_LACK_TOKEN("1000006", "登录token不能为空"),
  CLIENT_LACK_USER_DATA("1000007", "缺少用户数据"),
  CLIENT_SIGN_MISTAKE("1000027", "用户签名错误"),
  CLIENT_REQ_FREQUENT("1000029", "请求太频繁"),
  CLIENT_REQUIRE_PARAM_VIRTUAL_CARD_NAME("1000030", "输入非法字符，请姓名仅支持小写英文!"),
  CLIENT_REQUIRE_PARAM_VIRTUAL_CARD_COUNTRY("1000031", "国家必填!"),

  /**
   * 服务端异常，包含网络请求超时、IO异常、数据库链接失败等
   */
  SERVER_ABNORMAL("2000001", "服务端异常"),
  SERVER_NETWORK_TIMEOUT("2000002", "服务端网络请求超时"),
  SERVER_NETWORK_CALL_FAIL("2000003", "服务端调用失败"),
  SERVER_REMOTE_CALL_FAIL("2000004", "服务端远程调用失败"),
  SERVER_ABSENT_DATA("2000005", "服务端远程缺少配置"),
  SERVER_BUSY("2000013", "服务器繁忙，请稍后再试"),
  SERVER_CALL_MISTAKEN("2000014", "服务调用错误"),
  SERVER_ERROR_DATABASE("2000015", "数据库异常"),
  SERVER_PROVIDER_FAIL("2000017", "服务供应商处理失败"),
  SERVER_LACK_CONF("2000031", "服务端缺少配置"),
  SERVER_STATUS_REJECT("2000036", "服务端业务状态拒绝此请求"),

  /**
   * 业务相关异常，包含权限认证失败、业务流程不合法等
   */
  BIZ_NO_PROVIDED("30000009", "业务暂未提供"),
  BIZ_ERROR("30000010", "业务异常"),
  BIZ_DATA_EXIST("3000011", "数据已存在"),
  BIZ_DATA_ABSENT("3000012", "数据不存在"),
  BIZ_DATA_MISMATCH("3000013", "数据不匹配"),
  BIZ_DATA_FORMAT_ERROR("3000014", "数据格式错误"),
  BIZ_DATA_OVERRUN("3000015", "数据超过限制范围"),
  BIZ_DATA_STATUS_ERROR("3000016", "数据状态错误"),
  BIZ_DATA_LACK_BALANCE("3000017", "缺少余额"),
  BIZ_UPDATE_INEXISTENT("3000022", "请求更新的数据不存在"),
  BIZ_NULLIFY_INEXISTENT("3000023", "请求作废的数据已存在"),
  BIZ_SAVE_DUPLICATE("3000024", "请求保存的数据重复"),
  BIZ_BIZDATA_ABSENT("3000025", "业务数据不存在"),
  BIZ_DATA_USER_MISMATCH("3000026", "业务数据所有者与登录用户不匹配"),
  BIZ_POWER_MISMATCH("3000027", "业务权限不匹配"),
  BIZ_DATA_OVER("3000028", "请求的数据已超量"),
  BIZ_VERIFYCODE_OVERDUE("3000031", "验证码已过期"),
  BIZ_VERIFYCODE_MISMATCH("3000032", "验证码不匹配"),

  BIZ_PHONE_VERIFYCODE_OVERDUE("3000033", "手机短信验证码已过期"),
  BIZ_PHONE_VERIFYCODE_MISMATCH("3000034", "手机短信验证码不匹配"),

  BIZ_EMAIL_VERIFYCODE_OVERDUE("3000035", "邮箱验证码已过期"),
  BIZ_EMAIL_VERIFYCODE_MISMATCH("3000036", "邮箱短信验证码不匹配"),

  BIZ_IMG_CAPTCHA_MISMATCH("3000037", "图形验证码不匹配"),

  BIZ_PARAM_BLANK("3000041", "缺少请求参数,参数不能为空"),
  BIZ_SUPPLY_FAIL("3000042", "缺少请求参数,补参数失败"),
  BIZ_MUST_FILL_PHONE("3000043", "请填写手机联系方式"),

  DATA_NO_EXIST("3000055", "操作的数据不存在"),
  DATA_BAN_OPERATE("3000055", "禁止如此操作数据"),
  DATA_POWER_MISMATCH("3000055", "数据权限不匹配"),
  DATA_STATUS_BAN_OPERATE("3000056", "数据权限不匹配"),

  CODE_SEND_FAIL("3000061", "验证码发送失败"),
  CODE_SEND_TOO_OFTEN("3000062", "验证码发送太频繁"),
  CODE_SEND_EXCEEDED("3000063", "今日发送超过限制"),


  USER_NO_LOGIN("3000101", "用户未登录或登录超时，请重新登录"),
  USER_NULL_LOGIN("3000102", "登录信息不能为空,请求重新登录"),
  USER_NO_MANAGE("3000103", "登录用户为非管理者"),
  @Deprecated
  USER_NO_AUTHORITY("3000104", "登录用户为非管理者"),
  USER_ID_MISMATCH("3000105", "登录身份用户不匹配当前操作"),
  LOGIN_PWD_MISTAKE("3000107", "密码有误"),
  USER_NO_REGISTER("3000110", "账户未注册"),
  USER_NULL_REGISTER("3000111", "注册信息不能为空"),
  USER_ACCOUNT_LOGIN("3000112", "用户名不能为纯数字"),
  USER_TYPE_FORBID("3000113", "用户类弄不允许进行此操作"),
  USER_DAY_QUOTA_INSUFFICIENT("3000115", "当前操作超过今日限额"),



  PROJ_NO_EXIST("3000301", "项目不存在"),
  PROJ_DATA_NO_PUB("3000303", "数据非公开，禁止访问"),
  PROJ_SECRET_KEY_MISMATCH("3000312", "项目的密钥不匹配"),

  WALLET_NOT_ACTIVE("3001001", "钱包未激活"),
  WALLET_LACK_BALANCE("3001002", "余额不足"),


  WALLET_BUSY("3001005", "钱包业务繁忙"),

  SHOP_GOODS_STATUS_ERROR("4000001", "该商品无库存或已下架"),

  NO_WITHDRAWAL_PERMISSION("4000002", "没有提现权限"),
  UP_NO_WITHDRAWAL_PERMISSION("4000003", "上级没有提现权限"),

  ;




  // 翻译配置，从Oracle改到mysql了
  //增加枚举值需要配置 ngdesuam.t_ot_translate_conf，中英文都需要。

  public String code;
  public String msg;

  BizCodeEnum(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  /**
   * 根据code获取枚举
   *
   * @param code 错误码
   * @return 错误枚举
   */
  public static BizCodeEnum getEnumByCode(Integer code) {
    BizCodeEnum result = null;
    for (BizCodeEnum bizCodeEnum : BizCodeEnum.values()) {
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

  public String getMsg() {
    return this.msg;
  }

}
