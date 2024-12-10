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
public class Tuple3Param<F, S, T> {
    private F first;
    private S second;
    private T third;

    public static <F, S, T> Tuple3Param of(F first, S second, T third) {
        Tuple3Param obj = new Tuple3Param(first, second, third);
        return obj;
    }
    public Tuple3Param reset(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
        return this;
    }

    public static final long serialVersionUID = 203L;
}
