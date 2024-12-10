package cn.sharpen.jctool.bean.msg;


import cn.hutool.core.text.StrPool;
import cn.sharpen.jctool.consts.SymbolConst;
import cn.sharpen.jctool.util.StrTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static cn.sharpen.jctool.consts.SignConst.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MsgOrder {
    private static final long serialVersionUID = 893495L;

    // 排序列
    private String sidx;
    // 排序顺序
    private String sord;

    public static MsgOrder of(String sidx, String sord) {
        return new MsgOrder(sidx, sord);
    }

    /**
     * 生成sql中的order by 子语句.
     *
     * @param prop2column
     * @param msgOrderList
     * @return
     */
    public static String toOrderBySql(Map<String, String> prop2column, List<MsgOrder> msgOrderList) {
        if (CollectionUtils.isEmpty(msgOrderList)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (MsgOrder msgOrder : msgOrderList) {
            if (StringUtils.isBlank(msgOrder.sidx)) {
                continue;
            }
            String prop = prop2column.get(msgOrder.getSidx());
            prop = StringUtils.isBlank(prop) ? msgOrder.getSidx() : prop;
            if (StringUtils.isNotBlank(sb)) {
                sb.append(StrPool.COMMA);
            }
            String sort = msgOrder.sord == null ? null : msgOrder.sord.toLowerCase();
            sort = StrTool.inAny(sort, ORDERBY_ASC, ORDERBY_VIEW_ASC) ? ORDERBY_ASC : ORDERBY_DESC;
            sb.append(prop).append(SymbolConst.BLANK_SPACE).append(sort);
        }
        return sb.toString();
    }

}
