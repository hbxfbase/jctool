package cn.sharpen.jctool.bean.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 操作日志参数
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class OperateLogParam {

    private String projId;
    private String loginId;
    private String bizTraceNo;
    private String reqPath;
    private String logModule;
    private String logMethod;
    private String reqContent;

    public static final long serialVersionUID = 67003L;


}