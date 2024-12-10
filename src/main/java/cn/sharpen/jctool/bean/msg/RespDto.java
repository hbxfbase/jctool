package cn.sharpen.jctool.bean.msg;


import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应对象
 *
 * @author sharpen-auto
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RespDto implements Serializable {
    private String code;
    private String subCode;
    private String msg;

    boolean asSuccess;
    private String respStr;

    public static RespDto instFail(){
        RespDto dto = new RespDto();
        dto.setAsSuccess(false);
        return dto;
    }
    public static RespDto inst(String code, String subCode, String msg, boolean asSuccess, String respStr) {
        RespDto dto = new RespDto();
        dto.setCode(code);
        dto.setSubCode(subCode);
        dto.setMsg(msg);
        dto.setRespStr(respStr);
        dto.setAsSuccess(asSuccess);
        return dto;
    }

}
