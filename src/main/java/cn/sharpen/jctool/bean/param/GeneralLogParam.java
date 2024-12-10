package cn.sharpen.jctool.bean.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 通用日志参数
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class GeneralLogParam {

    private String projId;
    private String userMark;
    private String category;
    private String bizMark;
    private String content;
    private String keyWord;

    public static final long serialVersionUID = 67003L;


}