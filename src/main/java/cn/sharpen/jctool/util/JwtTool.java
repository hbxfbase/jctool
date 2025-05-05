package cn.sharpen.jctool.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON相关工具
 * @author justin
 */
@Slf4j
public class JwtTool {

    /**
     * 获取jwt的payload中的值Long类型
     * @param token 会话token
     * @param key claim的键
     * @return claim的键对应的值
     */
    public static Long getLongVal(String token, String key){
        String valStr = getStrVal(token, key);
        if(NumberUtil.isLong(valStr)) {
            return Long.parseLong(valStr);
        }
        return null;
    }

    /**
     * 获取jwt的payload中的值
     * @param token 会话token
     * @param key claim的键
     * @return claim的键对应的值
     */
    public static String getStrVal(String token, String key){
        // null,空白，返回空
        if(StringUtils.isBlank(token) && StrUtil.isNullOrUndefined(token)) {
            return null;
        }
        try {
            JWTPayload payload = JWT.of(token).getPayload();
            if(payload != null){
                return ObjTool.obj2str(payload.getClaim(key));
            }
        }catch (Exception e){
            log.info("getStrValFail={}", token);
        }
        return null;
    }

    /**
     * 获取jwt的payload中的clain的map
     * @param token 会话token
     * @return
     */
    public static Map<String, Object> getClaimMap(String token){
        if(StringUtils.isBlank(token) && StrUtil.isNullOrUndefined(token)) {
            return new HashMap<>();
        }
        try {
            JWTPayload payload = JWT.of(token).getPayload();
            if(payload != null){
                return payload.getClaimsJson();
            }
        }catch (Exception e){
            log.info("mistakeToken={}", token);
        }
        return new HashMap<>();
    }

}
