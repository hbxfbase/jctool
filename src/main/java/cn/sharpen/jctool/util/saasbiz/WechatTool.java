package cn.sharpen.jctool.util.saasbiz;

import cn.hutool.crypto.SecureUtil;

public class WechatTool {
    public static String weixinSmsSign(String appkey, String rnd, String time, String mobile) {
        String plaintext = "appkey=" + appkey + "&random=" + rnd + "&time=" + time + "&mobile=" + mobile;
        return SecureUtil.sha256(plaintext);
    }
}
