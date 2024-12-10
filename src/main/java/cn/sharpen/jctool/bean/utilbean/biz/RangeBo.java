package cn.sharpen.jctool.bean.utilbean.biz;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.consts.SymbolConst;
import cn.sharpen.jctool.util.MathTool;
import cn.sharpen.jctool.util.StrTool;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.text.StrPool.BRACKET_END;
import static cn.hutool.core.text.StrPool.BRACKET_START;
import static cn.sharpen.jctool.consts.SignConst.N;
import static cn.sharpen.jctool.consts.SignConst.Y;
import static cn.sharpen.jctool.consts.SymbolConst.CLOSE_PARENTHESES;
import static cn.sharpen.jctool.consts.SymbolConst.OPEN_PARENTHESES;


/**
 * 币种信息-视图对象
 * 关联PO:    com.ebct.biz.provider.domain.model.mysql.CoinInfoPo
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class RangeBo {
    private static String[] replaceSymbol = {OPEN_PARENTHESES, BRACKET_START, CLOSE_PARENTHESES, BRACKET_END};

    /**
     * 小数，左区间
     */
    private String small;
    /**
     * 左区间是否相等。Y为相等，包含([)。圆括号代表不相同
     */
    private String smallEqual;
    /**
     * 大数，右区间
     */
    private String big;
    /**
     * 右区间是否相等。Y为相等，包含(])
     */
    private String bigEqual;
    /**
     * 对应的值
     */
    private String val;
    /**
     * 原始范围字符串
     */
    private String scope;
    /**
     * 原始范围字符串
     */
    private String original;

    public static List<RangeBo> parseList(String range){
        if(StringUtils.isBlank(range)) {
            return null;
        }
        List<RangeBo> boList = new ArrayList<>();
        List<String> list = StrTool.splitManyGeneral(range, SymbolConst.SEMICOLON);
        for(String rule : list) {
            RangeBo bo = parse(rule);
            if(bo == null) {
                continue;
            }
            boList.add(bo);
        }
        return boList;
    }

    public static RangeBo parse(String range){
        if(StringUtils.isBlank(range)) {
            return null;
        }
        // range示例： [0,100):0.03
        List<String> listStr = StrTool.splitManyGeneral(range, StrPool.COLON);
        if(CollectionUtils.size(listStr)<2) {
            return null;
        }
        // [0和100)
        String scope = CollectionUtil.getFirst(listStr);
        List<String> rangeStr =StrTool.splitManyGeneral(scope, StrPool.COMMA);
        String start = CollectionUtil.getFirst(rangeStr).trim();
        String end = CollectionUtils.size(rangeStr)>1 ? rangeStr.get(SignConst.L1.intValue()).trim() : null ;
        String startNum = StrUtil.removeAny(start, replaceSymbol);
        String endNum = StrUtil.removeAny(end, replaceSymbol);

        RangeBo bo = new RangeBo();
        bo.setSmall(startNum);
        bo.setSmallEqual(start.startsWith(OPEN_PARENTHESES) ? N : Y);
        bo.setBig(endNum);
        if(StringUtils.isNotBlank(end)) {
            bo.setBigEqual(end.endsWith(CLOSE_PARENTHESES) ? N : Y);
        }
        bo.setVal(listStr.get(SignConst.L1.intValue()));
        bo.setScope(scope);
        bo.setOriginal(range);

        return bo;
    }

    public static RangeBo judgeRange(List<RangeBo> ranges, String judgeStr) {
        if(StringUtils.isBlank(judgeStr) || CollectionUtils.isEmpty(ranges)) {
            return null;
        }
        for(RangeBo elem : ranges) {
            String big = elem.getBig(), small = elem.getSmall(), se = elem.getSmallEqual(), be = elem.getBigEqual();
            if(StringUtils.equals(se, Y) && MathTool.less(judgeStr, small)) {
                continue;
            }
            if(!StringUtils.equals(se, Y) && MathTool.lessEqual(judgeStr, small)) {
                continue;
            }
            if(StringUtils.isNotBlank(big)) {
                if (StringUtils.equals(be, Y) && MathTool.greater(judgeStr, big)) {
                    continue;
                }
                if (!StringUtils.equals(be, Y) && MathTool.greaterEqual(judgeStr, big)) {
                    continue;
                }
            }
            return elem;
        }

        return null;
    }

    /**
     *
     * @param ranges
     * @param targetStr
     * @param scale 小数位
     * @return
     */
    public static RangeBo judgeTargetRange(List<RangeBo> ranges, String targetStr, int scale) {
        if(StringUtils.isBlank(targetStr) || CollectionUtils.isEmpty(ranges)) {
            return null;
        }
        for(RangeBo elem : ranges) {
            String big = elem.getBig(), small = elem.getSmall(), se = elem.getSmallEqual(), be = elem.getBigEqual();
            String pay = MathTool.strDivide(targetStr, MathTool.strSubtract(SignConst.STR_ONE, elem.getVal()), scale,
                    BigDecimal.ROUND_HALF_UP);

            if(StringUtils.equals(se, Y) && MathTool.less(pay, small)) {
                continue;
            }
            if(!StringUtils.equals(se, Y) && MathTool.lessEqual(pay, small)) {
                continue;
            }
            if(StringUtils.isNotBlank(big)) {
                if (StringUtils.equals(be, Y) && MathTool.greater(pay, big)) {
                    continue;
                }
                if (!StringUtils.equals(be, Y) && MathTool.greaterEqual(pay, big)) {
                    continue;
                }
            }
            return elem;
        }

        return null;
    }

    public static String getVal(RangeBo bo){
        return bo== null ? null : bo.getVal();
    }

    public static final long serialVersionUID = 673125L;
    @Override
    public String toString(){
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE);
        rtsb.setExcludeNullValues(true);
        return rtsb.setExcludeFieldNames("serialVersionUID").toString();
    }

}