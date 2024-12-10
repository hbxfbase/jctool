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
public class Tuple5Param<F, S, T, Q, E> {
    private F first;
    private S second;
    private T third;
    private Q forth;
    private E fifth;

    public static <F, S, T, Q, E> Tuple5Param of(F first, S second, T third, Q forth, E fifth) {
        Tuple5Param obj = new Tuple5Param(first, second, third, forth, fifth);
        return obj;
    }
    public Tuple5Param reset(F first, S second, T third, Q forth, E fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
        this.fifth = fifth;
        return this;
    }

    public static final long serialVersionUID = 203L;
}
