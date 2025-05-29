package cn.sharpen.jctool.util;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadTool {
    public static void startMany(FutureTask ...threads){
        if(ArrayUtil.isNotEmpty(threads)) {
            for(FutureTask thread : threads) {
                new Thread(thread).start();
            }
        }
    }

    public static void startMany(Runnable ...threads) {
        if(ArrayUtil.isNotEmpty(threads)) {
            for(Runnable thread : threads) {
                new Thread(thread).start();
            }
        }
    }

}
