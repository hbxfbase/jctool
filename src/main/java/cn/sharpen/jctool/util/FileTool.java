package cn.sharpen.jctool.util;

import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import cn.sharpen.jctool.bean.base.BizData;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

import static cn.sharpen.jctool.consts.SignConst.Y;

/**
 * 文件工具
 * @author justin
 */
public class FileTool {

    public static boolean localTap(String key){
        return StringUtils.equals(localConf(key), Y);
    }
    public static boolean ignoreOff(String key){
        return StringUtils.equals(localConf(key), Y);
    }

    public static String localConf(String key){
        if(BizData.localBizConf==null) {
            String localPath = null;
            OsInfo os = SystemUtil.getOsInfo();

            if (os.isWindows()) {
                localPath = BizData.WIN_CONF;
            } else if (os.isMac()) {
                localPath = BizData.MAC_CONF;
            } else if (os.isLinux()) {
                localPath = BizData.LINUX_CONF;
            } else {
                localPath = BizData.LINUX_CONF;
            }
            File file = new File(localPath + BizData.localConfFile);
            if(!file.exists()) {
                BizData.localBizConf = new Props();
            } else {
                BizData.localBizConf = PropsUtil.get(localPath + BizData.localConfFile);
            }
        }
        if(BizData.localBizConf==null) {
            return null;
        }
        return BizData.localBizConf.getStr(key);
    }

    /**
     * 格式化文件路径，正确的路径开头：classpath，file， http。否则，加上：file:
     *
     * @param filePath 需要格式化的文件路径
     * @author Justin
     */
    public static String formatPath(String filePath) {
        if (StringUtils.isNotBlank(filePath) && !filePath.startsWith("classpath")
                && !filePath.startsWith("file") && !filePath.startsWith("http")) {
            filePath = "file:" + filePath;
        }
        return filePath;
    }
}
