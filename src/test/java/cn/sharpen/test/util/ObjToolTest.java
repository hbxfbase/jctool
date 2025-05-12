package cn.sharpen.test.util;

import cn.hutool.core.text.StrPool;
import cn.sharpen.jctool.bean.param.LoginParam;
import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.util.CollectionTool;
import cn.sharpen.jctool.util.MathTool;
import cn.sharpen.jctool.util.ObjTool;
import cn.sharpen.jctool.util.StrTool;
import cn.sharpen.jctool.util.algorithm.geo.GeoHash;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static cn.sharpen.jctool.consts.SignConst.INT10;

/**
 * JWT相关的测试
 */
public class ObjToolTest {

    @Test
    public void beans2mapListTest(){
        boolean result = ObjTool.numberEqualAny(1234L, 1234L, 122L);
        StrTool.plf("true result={}", result+"");
        result = ObjTool.numberEqualAny(1234L, 12343L, 122L);
        StrTool.plf("false result={}", result+"");
        result = ObjTool.numberEqualAny(1234, 1234, 122);
        StrTool.plf("true result={}", result+"");
        result = ObjTool.numberEqualAny(1234, 12345, 122);
        StrTool.plf("false result={}", result+"");
        result = ObjTool.numberEqualAny(1234.5, 122.3, 1234.5);
        StrTool.plf("true result={}", result+"");
        result = ObjTool.numberEqualAny(1234.5, 1234.51, 122.3);
        StrTool.plf("false result={}", result+"");


        result = ObjTool.numberEqualAny(Integer.valueOf(2551), Integer.valueOf(25513), Integer.valueOf(2551));
        StrTool.plf("true result={}", result+"");
        result = ObjTool.numberEqualAny(Integer.valueOf(2551), Integer.valueOf(25513), Integer.valueOf(25514));
        StrTool.plf("false result={}", result+"");
    }


    @Test
    public void compObj2tipTest(){
        LoginParam obj1 = LoginParam.builder().thirdLogin(SignConst.Y).operate("register").uidProjId("201").build();
        LoginParam obj2 = LoginParam.builder().thirdLogin(SignConst.Y).operate("login").tid("2012").build();
        String tipMapStr = "thirdLogin:是否三方登录,operate:操作类型,uidProjId:用户所属项目ID,tid:登录Token";
        Map<String, String> tipMap = StrTool.str2map(tipMapStr, StrPool.COMMA, StrPool.COLON);
        String tip = ObjTool.compObj2tip(tipMap, obj1, obj2);
        StrTool.plf("tipStr={}", tip);
    }

    @Test
    public void takeMultipleTest(){
        String result = MathTool.takeIntMultiple("12352", "10");
        StrTool.plf("resultVal={}", result);
        result = MathTool.takeIntMultiple("12352", "20");
        StrTool.plf("resultVal={}", result);
        result = MathTool.takeIntMultiple("1235200", "20");
        StrTool.plf("resultVal={}", result);
        result = MathTool.takeIntMultiple("153.5200", "20");
        StrTool.plf("resultVal={}", result);
        result = INT10.toString();
        StrTool.plf("resultVal={}", result);
    }

    @Test
    public void geoHahByGoogleMapTest(){
        try {
            StrTool.pl2(GeoHash.geoHahByGoogleMap("24.852534250077223, 118.60776118561964"));
        }catch (Exception e){
            StrTool.plf("geoHahByGoogleMapFail={}", e.getMessage(), e);
        }
    }
    @Test
    public void replaceCheckTest(){
        String str = "{\"token\":\"51759655-6d5bfcbae9485575bada9f994ccc66b0\",\"receiver_email\":\"abc-123@hotmail.com\",\"order_code\":\"VA1000000752295\",\"total_amount\":50000,\"bank_code\":\"BIDV\",\"order_description\":\"KL-140823-17:28:01 768252\",\"tax_amount\":null,\"discount_amount\":null,\"fee_shipping\":null,\"return_url\":\"\",\"cancel_url\":\"\",\"buyer_fullname\":\"along\",\"buyer_email\":\"\",\"buyer_mobile\":\"\",\"buyer_address\":\"N/A\",\"affiliate_code\":\"\",\"transaction_id\":98037264,\"payment_type\":1,\"transaction_status\":4}";
        try {
            StrTool.pl2("replaceCheckTest"+str.replaceAll("\\/", "\\\\/"));
        }catch (Exception e){
            StrTool.plf("replaceCheckTestFail={}", e.getMessage(), e);
        }
    }

    @Test
    public void mapValStrTest(){
        Map map = new HashMap();
        map.put("aa", "bb");
        map.put("cc",null);
        try {
            StrTool.pl2("mapValStrTest="+ CollectionTool.mapValStr(map, "cc"));
        }catch (Exception e){
            StrTool.plf("replaceCheckTestFail={}", e.getMessage(), e);
        }
    }

}
