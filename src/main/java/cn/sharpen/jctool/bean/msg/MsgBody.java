package cn.sharpen.jctool.bean.msg;


import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.ObjTool;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 消息头。一般用于保存请求参数
 * @author justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MsgBody<T> implements Serializable {

  private static final long serialVersionUID = 895L;
  /**
   * 业务分类。分：add,addBatch,nullify,nullifyBatch,nullifyPhysical,modify,primaryQuery,listQuery
   */
  private String bizSort = null;
  /**
   * 操作人ID
   */
  private String operatorUid = null;

  /**
   * 一般用于放主键之类的标识
   */
  private String mark;
  private String identify;
  /**
   * 上一次查询的最后一个ID
   */
  private String lastId;
  /**
   * 是否向后,是为Y，否为N，默认为Y
   */
  private String asNext;
  //反序
  private String asReverse;
  private String msgStr;
  private Boolean processResult;
  private T entity;
  private List<T> entityList;
  /**
   * 扩展
   */
  private Map<String, Object> extMap;

  /**
   * jqgrid,如果没有下面的变量，就会出现Bad Request的请求错误
   */
  private String search;

  private String nd;

  private List<MsgOrder> msgOrders;

  private String cursorAsc;

  /**
   * 分页类的参数
   */

  /**
   * 查询第几页。从1开始
   */
  private Long reqPageNum =1L;

  /**
   * 每页显示多少条
   */
  private Integer reqRecordNum = 15;
  /** 请求开始的条数。一般用于批量导出 */
  private Long reqStartIdx;

  /**
   * 共有多少条记录
   */
  private Long respRecordCount =0L;

  /**
   * 共有多少条页
   */
  private Long respPageCount =0L;

  public static <T> MsgBody<T> withEntityList(List<T> list){
    MsgBody body = new MsgBody<>();
    body.setEntityList(list);
    return body;
  }
  public static <T> MsgBody<T> withEntity(T entity){
    MsgBody body = new MsgBody<>();
    body.setEntity(entity);
    return body;
  }

  public String toJson(){
    return JsonTool.obj2json(this);
  }

}
