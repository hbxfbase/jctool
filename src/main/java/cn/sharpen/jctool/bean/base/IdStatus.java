package cn.sharpen.jctool.bean.base;

import cn.hutool.core.lang.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 用于从bdfp获取ID的当前获取的值的状态
 * @author justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IdStatus {

    public static String ID_LOCAL= "local";
    public static String ID_WORKER= "worker";
    public static String ID_BDFP = "bdfp";

    /**
     * bdfp当前ID
     */
    private transient AtomicLong currId;
    /**
     * 当前允许的最大ID
     */
    private transient AtomicLong currMaxId;

    public static IdStatus inst(Long currId, Long currMaxId) {
        return inst(new AtomicLong(currId), new AtomicLong(currMaxId));
    }
    public static IdStatus inst(AtomicLong currId, AtomicLong currMaxId){
        IdStatus obj = new IdStatus();
        obj.setCurrId(currId);
        obj.setCurrMaxId(currMaxId);
        return obj;
    }

    public Pair<Long, Long> getNewVal(){
        return Pair.of(currId.getAndIncrement(), currMaxId.get());
    }



}
