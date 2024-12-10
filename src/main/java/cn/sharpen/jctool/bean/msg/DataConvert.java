package cn.sharpen.jctool.bean.msg;

import cn.sharpen.jctool.enums.BizCodeEnum;
import cn.sharpen.jctool.util.JsonTool;

public class DataConvert {

    public static String msgBeanStr(BizCodeEnum bce) {
        return JsonTool.obj2json(MsgBean.inst(bce.getCode(), bce.getMsg()));
    }
    public static String msgBeanStr(BizCodeEnum bce, String descibe){
        return JsonTool.obj2json(MsgBean.inst(bce.getCode(), bce.getMsg(), descibe));
    }
    public static MsgBean msgBeanInst(BizCodeEnum bce, String descibe){
        return MsgBean.inst(bce.getCode(), bce.getMsg(), descibe);
    }

    public static MsgBean msgBeanInst(BizCodeEnum bce) {
        return MsgBean.inst(bce.getCode(), bce.getMsg());
    }


}
