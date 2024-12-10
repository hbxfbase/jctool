package cn.sharpen.jctool.bean.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 两属性参数
 *
 * @author Justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tuple2Param<F, S> {
    private F first;
    private S second;

    public static <F, S> Tuple2Param of(F first, S second) {
        Tuple2Param obj = new Tuple2Param(first, second);
        return obj;
    }
    public static <F, S> Tuple2Param empty() {
        Tuple2Param obj = new Tuple2Param(null, null);
        return obj;
    }
    public Tuple2Param reset(F first, S second) {
        this.first = first;
        this.second = second;
        return this;
    }

    public static final long serialVersionUID = 203L;
}
