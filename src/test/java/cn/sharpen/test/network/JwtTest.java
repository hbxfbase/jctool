package cn.sharpen.test.network;

import cn.hutool.core.codec.Base64;
import cn.sharpen.jctool.bean.utilbean.ExtMap;
import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.JwtTool;
import cn.sharpen.jctool.util.StrTool;
import org.junit.Test;

/**
 * JWT相关的测试
 */
public class JwtTest {

    @Test
    public void beans2mapListTest(){
        ExtMap jwtHead = ExtMap.inst().put("typ","JWT").put("alg","HS256");
        String json = JsonTool.obj2json(jwtHead.get());
        StrTool.plf("jwtHead={} base64={}", json, Base64.encode(json));
    }


    /**
     * 验证jwt的payload参数获取功能
     */
    @Test
    public void getStrValTest(){
        JwtTool.getStrVal(null, "ss");
    }
}
