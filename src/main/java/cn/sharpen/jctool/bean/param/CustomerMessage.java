package cn.sharpen.jctool.bean.param;


import cn.sharpen.jctool.bean.utilbean.ExtMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 客服消息
 * @author Justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerMessage {

  /**
   * PK系统生成，数据的唯一标识
   */
  private String id;
  /**
   * 项目ID。依赖proj_info.id
   */
  private String projId;
  /**
   * 快递员ID。依赖login_user.id
   */
  private String uid;
  /**
   * 客服ID。依赖login_user.id
   */
  private String staffUid;
  /**
   * 消息板块。备用
   */
  private String messagePlate;
  /**
   * 消息方向。相对于平台来讲。用户发送in，客服回复out
   */
  private String direction;
  /**
   * 内容类型。枚举分类content_type文本text，图片img
   */
  private String contentType;
  /**
   * 消息内容
   */
  private String content;
  /**
   * 扩展参数
   */
  private Map extParam=ExtMap.inst().get();

  public static final long serialVersionUID = 203L;

}
