package cn.sharpen.test.network;

import cn.hutool.core.codec.Base64;
import cn.sharpen.jctool.bean.utilbean.ExtMap;
import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.JwtTool;
import cn.sharpen.jctool.util.StrTool;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestProxy;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.http.ForestResponse;
import org.junit.Test;

import java.time.Duration;

/**
 * JWT相关的测试
 */
public class HttpTest {

    @Test
    public void httpProxyTest(){
        String chainUrl = "https://bsc.nodereal.io";
        String body="{\"method\":\"eth_getBlockByNumber\",\"id\":56,\"jsonrpc\":\"2.0\",\"params\":[\"0x2053462\",true]}";
        ForestRequest forRequest =  Forest.request().setType(ForestRequestType.POST).setRetryEnabled(false)
                .setUrl(chainUrl)
                .addBody(body)
                .contentTypeJson()
                .connectTimeout(Duration.ofSeconds(5));

        ForestProxy socks5Proxy = ForestProxy.socks("185.179.198.192",64075)
                .setUsername("Lnmtrt95").setPassword("1kxJXXUJ");
        forRequest.setProxy(socks5Proxy);
        ForestResponse resp = forRequest.executeAsResponse();
        String respBody = resp.getContent();
        StrTool.plf("respBodyResp={}", respBody);

    }

    /**
     * 验证代理IP是否设置成功-验证成功
     */
    @Test
    public void httpProxyGetTest(){
        String chainUrl = "https://api.futureworld.shop/bdfpapi/v1/open/tool/rip";
        ForestRequest forRequest =  Forest.request().setType(ForestRequestType.GET).setRetryEnabled(false)
                .setUrl(chainUrl)
                .connectTimeout(Duration.ofSeconds(5));

        ForestProxy socks5Proxy = ForestProxy.socks("185.179.198.192",64075)
                .setUsername("Lnmtrt95").setPassword("1kxJXXUJ");
        forRequest.setProxy(socks5Proxy);
        ForestResponse resp = forRequest.executeAsResponse();
        String respBody = resp.getContent();
        StrTool.plf("respBodyResp={}", respBody);

    }



}
