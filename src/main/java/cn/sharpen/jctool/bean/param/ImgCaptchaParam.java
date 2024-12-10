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
public class ImgCaptchaParam {

  /**
   * tokenId
   */
  private String tid;
  /**
   * 分类
   */
  private String type;
  /**
   * 用户输入的图形验证码
   */
  private String captcha;

  public static final long serialVersionUID = 67001L;


}