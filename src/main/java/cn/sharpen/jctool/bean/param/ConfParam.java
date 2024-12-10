package cn.sharpen.jctool.bean.param;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 配置参数
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class ConfParam {

  /**
    * 公开程度.sys仅系统，user所属用户，pub公开可见
    */
  private String pubLevel;
  /**
    * 分类
    */
  private String category;
  /**
    * 扩展键
    */
  private String bond;
  /**
    * 扩展值
    */
  private String val;
  /**
   * 扩展名
   */
  private String name;

  public static final long serialVersionUID = 67001L;


}