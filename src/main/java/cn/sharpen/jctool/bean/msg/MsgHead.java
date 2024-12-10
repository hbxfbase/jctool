package cn.sharpen.jctool.bean.msg;


import cn.sharpen.jctool.util.JsonTool;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息头。一般用于保存请求参数
 * @author sharpen-auto
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MsgHead implements Serializable {
  public static final String PROJ_ID = "projId";
  public static final String USER_PROJ_ID = "userProjId";
  public static final String SECRET_KEY = "secretKey";
  public static final String SERVICE_ID = "serviceId";

  private String code = "0";
  private String msg = "";
  /**
   * 业务描述
   */
  private String bizDesc ;
  private Long reqTime = null;
  private Long respTime = null;

  /**
   * 请求流水号
   */
  private String requestId = null;

  /**
   * 服务流水号
   */
  private String serviceId = null;

  /**
   * 会话标识，如果需要的话
   */
  private String sessionId = null;
  /**
   * 客户端版本
   */
  private String clientVer = null;
  /**
   * 每个手机唯一标识。CDMA设备的标识；imei GSM、WCDMA设备的标识；
   */
  private String meid = null;
  /**
   * 每个手机唯一标识。CDMA设备的标识；imei GSM、WCDMA设备的标识；
   */
  private String deviceMark = null;

  /**
   * 项目ID
   */
  private String projId = null;
  /**
   * 项目密钥
   */
  private String secretKey = null;
  /**
   * 16进制token, 例：ABCDEF1234567890
   */
  private String token = null;
  /**
   * token过期时间，单位：秒
   */
  private String tokenSecond = null;
  /**
   * 16进制形式
   */
  private String pubKey = null;
  private String cipherKey = null;

  /**
   * 是否间接调用。默认为N，如果是Y，需要转到另一个系统
   */
  private String indirectCall = null;
  /**
   * 间接调用的系统。根据间接调用的系统，找到相应的转发service
   */
  private String indirectSys = null;

  /**
   * 客户端要求加密。Y为要求加密，即使后台没有配置加密
   */
  private String clientCipher = null;


  public MsgHead(String code, String msg){
    this.code = code;
    this.msg = msg;
  }

  public MsgHead(String code, String msg, String bizDesc){
    this.code = code;
    this.msg = msg;
    this.bizDesc = bizDesc;
  }

  public String toJson(){
    return JsonTool.obj2json(this);
  }

}
