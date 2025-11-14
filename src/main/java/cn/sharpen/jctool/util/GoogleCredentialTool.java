package cn.sharpen.jctool.util;

import cn.sharpen.jctool.bean.utilbean.GoogleUserInfoVo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Google登录凭证解析工具类
 * 用于解析Google登录返回的JWT格式credential
 */
@Slf4j
public class GoogleCredentialTool {

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final GsonFactory gsonFactory = GsonFactory.getDefaultInstance();

    /**
     * 验证并解析谷歌返回的 credential（JWT）
     * @param credential 前端传递的 JWT 字符串
     * @param clientId 客户端ID
     * @return 解析后的用户信息
     */
    public static GoogleUserInfoVo verifyAndParse(String credential, String clientId) {
        // 创建验证器
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, gsonFactory)
                // 验证受众（必须是你的 client-id）
                .setAudience(Collections.singletonList(clientId))
                // 可选：验证签发者（谷歌的签发者固定为这两个）
                .setIssuers(Collections.singletonList("https://accounts.google.com"))
                .build();

        // 验证 JWT 并获取解析结果
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(credential);
        } catch (Exception e) {
            return null;
        }
        if (idToken == null) {
            return null;
        }
        
        // 将 GoogleIdToken.Payload 转换为 GoogleUserInfoVo
        GoogleIdToken.Payload payload = idToken.getPayload();
        GoogleUserInfoVo userInfo = new GoogleUserInfoVo();
        
        // 设置基本用户信息
        userInfo.setEmail(payload.getEmail());
        userInfo.setEmailVerified(payload.getEmailVerified());
        userInfo.setSubject(payload.getSubject());
        userInfo.setIssuer(payload.getIssuer());
        userInfo.setIssuedAt(payload.getIssuedAtTimeSeconds());
        userInfo.setExpiresAt(payload.getExpirationTimeSeconds());
        
        // 设置名称相关信息
        userInfo.setName((String) payload.get("name"));
        userInfo.setPicture((String) payload.get("picture"));
        userInfo.setGivenName((String) payload.get("given_name"));
        userInfo.setFamilyName((String) payload.get("family_name"));
        userInfo.setLocale((String) payload.get("locale"));
        userInfo.setHostedDomain(payload.getHostedDomain());

        // 设置其他标准 JWT 信息
        userInfo.setAuthorizedParty(payload.getAuthorizedParty());
        userInfo.setNonce(payload.getNonce());
        userInfo.setAccessTokenHash(payload.getAccessTokenHash());
        userInfo.setJwtId(payload.getJwtId());
        userInfo.setType(payload.getType());
        
        log.info("UserInfo: {}", userInfo);
        return userInfo;
    }

}