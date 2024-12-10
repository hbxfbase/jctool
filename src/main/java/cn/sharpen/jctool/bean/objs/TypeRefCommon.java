package cn.sharpen.jctool.bean.objs;

import cn.sharpen.jctool.bean.msg.MsgBean;
import cn.sharpen.jctool.bean.param.ConfParam;
import cn.sharpen.jctool.bean.param.LoginInfo;
import cn.sharpen.jctool.bean.param.LoginStatusBo;
import cn.sharpen.jctool.bean.param.UserLoginInfo;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * TypeReference常用对象
 * @author justin
 */
public class TypeRefCommon {
    public static TypeReference<MsgBean> typeMsg = new TypeReference<MsgBean>(){};
    public static TypeReference<MsgBean<HashMap<String, String>>> typeMsgMap = new TypeReference<MsgBean<HashMap<String, String>>>(){};
    public static TypeReference<MsgBean<ConfParam>> typeMsgConfParam = new TypeReference<MsgBean<ConfParam>>(){};
    public static TypeReference<MsgBean<LoginInfo>> typeMsgLoginInfo = new TypeReference<MsgBean<LoginInfo>>(){};

    public static TypeReference<MsgBean<LoginStatusBo>> typeMsgLoginStatus = new TypeReference<MsgBean<LoginStatusBo>>(){};
    public static TypeReference<MsgBean<UserLoginInfo>> typeMsgUserLoginInfo = new TypeReference<MsgBean<UserLoginInfo>>(){};

    public static TypeReference<HashMap<String, String>> typeHashStr = new TypeReference<HashMap<String, String>>(){};
    public static TypeReference<HashMap<String, Object>> typeHashObj = new TypeReference<HashMap<String, Object>>(){};
    public static TypeReference<LinkedHashMap<String, Object>> typeLinkedMapObj = new TypeReference<LinkedHashMap<String, Object>>(){};




}
