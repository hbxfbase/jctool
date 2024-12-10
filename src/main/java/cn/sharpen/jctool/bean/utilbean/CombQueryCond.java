package cn.sharpen.jctool.bean.utilbean;

import lombok.Data;

/**
 * 查询条件
 */
@Data
public class CombQueryCond {

  /**
   * 参数类型
   */
  private String sort;
  /**
   * 参数类型显示名称
   */
  private String name;
  /**
   * 参数类型
   */
  private String val;


}
