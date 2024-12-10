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
public class Tuple4Param<F, S, T, Q> {
    private F first;
    private S second;
    private T third;
    private Q forth;

    public static <F, S, T, Q> Tuple4Param of(F first, S second, T third, Q forth) {
        Tuple4Param obj = new Tuple4Param(first, second, third, forth);
        return obj;
    }
    public Tuple4Param reset(F first, S second, T third, Q forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;

        return this;
    }

    public static final long serialVersionUID = 203L;
}
