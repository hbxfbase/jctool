package cn.sharpen.jctool.bean.utilbean;

import lombok.Data;

/**
 * 综合查询的参数
 */
@Data
public class CombQueryParam {

  /**
   * 参数数目
   */
  private String paramCount;
  /**
   * 当前查询的表名，只能有一个表
   */
  private String table;
  /**
   * 依赖的表。多个用英文逗号分隔
   */
  private String depTable;
  /**
   * 列名与显示的属性的映射，例： "colDisMap":"user_account:登录账户;name:名称;",
   */

  private String colDisMap;
  /**
   * 查询的sql
   */
  private String sql;
}
