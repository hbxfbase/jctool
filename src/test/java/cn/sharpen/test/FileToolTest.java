package cn.sharpen.test;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.sharpen.jctool.bean.base.RandomGeneratorExt;
import cn.sharpen.jctool.util.FileTool;
import cn.sharpen.jctool.util.ObjTool;
import cn.sharpen.jctool.util.StrTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;

public class FileToolTest {


    private static final Logger log = LoggerFactory.getLogger(FileToolTest.class);

    @Test
    public void localConfTest(){
        StrTool.pl(FileTool.localConf("ignoreVerifyCode"));
        StrTool.pl(FileTool.ignoreOff("ignoreVerifyCode"));
    }

    @Test
    public void imgCodeTest(){
        String filePath = "E:\\datum\\temp\\img\\testImgCode1.jpg";
        File outputFile = new File(filePath);
        int width = 300,  height = 80, codeCount=5, circleCount=3;
        CodeGenerator generator = new RandomGeneratorExt(5, "as仍有2");
        CircleCaptcha img  = new CircleCaptcha(width, height, codeCount, circleCount);
        img.setGenerator(generator);
        try {
            img.write(outputFile);
        }catch (Exception e){
            log.info("imgCodeTestFail={}", e.getMessage(), e);
        }
    }

    /* 验证自定义生成图形验证码 */
    @Test
    public void imgCode2Test(){
        String filePath = "E:\\datum\\temp\\img\\testImgCode1.png";
        File outputFile = new File(filePath);
        int width = 500,  height = 80, codeCount=5, circleCount=3;
        CircleCaptcha img  = ObjTool.getImgCode(width, height, codeCount, circleCount, "ah仍28有");
        try {
            img.write(outputFile);
        }catch (Exception e){
            log.info("imgCodeTestFail={}", e.getMessage(), e);
        }
    }

    /* 验证MD5*/
    @Test
    public void md5Test(){
        String plaintext = "aa";
        String digest = SecureUtil.md5(plaintext);
        StrTool.plf("md5TestFail={}", digest);
    }

}
