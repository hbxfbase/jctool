package cn.sharpen.test.algorithm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.sharpen.jctool.util.StrTool;
import cn.sharpen.jctool.util.algorithm.GoogleAuthenticator;
import cn.sharpen.jctool.util.algorithm.SecureTool;
import org.junit.Test;

/**
 * @author justin
 */
public class VietQrCheckTest {
    public static String tempFile = "D:\\datum\\data\\appdata\\java\\gc_secret.txt";
    @Test
    public void googleCodeGenerate(){
        String secret = GoogleAuthenticator.generateSecretKey();
        FileUtil.writeUtf8String(secret, tempFile);
        String url = GoogleAuthenticator.getOtpauthURL("jimmy","polygon.com", secret);
        StrTool.plf("googleCodeGenerateUrl={} secret={}", url, secret);
    }

    /**
     * 验证google验证码是否正确。兼容3个
     */
    @Test
    public void googleCodeScan(){
        String secret = FileUtil.readUtf8String(tempFile);
        String host = "polygon.com";
        String result = GoogleAuthenticator.getURL("polygon:","jimmy", host, secret);
        StrTool.plf("googleCodeScan={} secret={}", result, secret);
    }

    /**
     * 验证google验证码是否正确。兼容3个
     */
    @Test
    public void verifyGoogleCode(){
        String secret = FileUtil.readUtf8String(tempFile);
        // 778934
        String code = "564332";
        Boolean result = GoogleAuthenticator.authCode(2, code, secret);
        StrTool.plf("verifyGoogleCode={} result={} secret={}", code, result.toString(), secret);
    }

    /**
     * PKCS8私钥长度有多少位
     */
    @Test
    public void getRSAPublicKeyLengthTest(){
        String keyBase64 = "D:\\datum\\receive\\friends\\anson\\laem\\test3key\\cucatest3.pem";
        String content = FileUtil.readUtf8String(keyBase64);
        Integer result = SecureTool.getPkcsRSAPrivateKeyLength(content);
        StrTool.plf("secretVal={}", result);
    }

    /**
     * PKCS8私钥长度有多少位
     */
    @Test
    public void bCryptTest(){
        String strong_salt = BCrypt.gensalt(10);
        String stronger_salt = BCrypt.gensalt(12);
        String plain_password = "12345671234567";
        String pw_hash = BCrypt.hashpw(plain_password, stronger_salt);
        boolean result = BCrypt.checkpw(plain_password, pw_hash);


        StrTool.plf("secretVal={} plain_password={} pw_hash={}", result, plain_password, pw_hash );
    }





}
