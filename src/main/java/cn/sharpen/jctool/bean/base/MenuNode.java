package cn.sharpen.jctool.bean.base;

import cn.hutool.core.lang.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用于从bdfp获取ID的当前获取的值的状态
 * @author justin
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuNode {

    private String id;
    private String pid;
    private String label;
    private String page;
    private String bizMark;
    /** 页面是否定时刷新 */
    private String refreshPage;
    /** 图标-目前只支持element-ui */
    private String icon;


    private List<MenuNode> children;

}
