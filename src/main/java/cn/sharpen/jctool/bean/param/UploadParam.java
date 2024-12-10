package cn.sharpen.jctool.bean.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 上传文件参数
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class UploadParam {

  /** 上传时，要删除的旧文件名 */
  private String oldDel;
  /** 公开级别，分:public_read, private_read */
  private String level;
  /** URL的全路径的前缀 */
  private String urlPre;
  /** 代表是否有缩略图，用于更新图片时，删除旧的缩略图。Y为存在 */
  private String hasThumb;
  /** 缩略图高度 */
  private String thumbHeight;
  /** 请求ID */
  private String requestId;

  public static final long serialVersionUID = 67001L;


}