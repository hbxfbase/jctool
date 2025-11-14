package cn.sharpen.jctool.bean.utilbean;

/**
 * Google用户信息VO类
 * 用于承载解析Google登录凭证后得到的用户信息
 */
public class GoogleUserInfoVo {
    
    /**
     * 签发者
     */
    private String issuer;
    
    /**
     * 接收者
     */
    private String audience;
    
    /**
     * 用户唯一标识
     */
    private String subject;
    
    /**
     * 用户邮箱
     */
    private String email;
    
    /**
     * 邮箱是否已验证
     */
    private Boolean emailVerified;
    
    /**
     * 用户姓名
     */
    private String name;
    
    /**
     * 用户头像链接
     */
    private String picture;
    
    /**
     * 名字
     */
    private String givenName;
    
    /**
     * 姓氏
     */
    private String familyName;
    
    /**
     * 地区
     */
    private String locale;
    
    /**
     * 签发时间
     */
    private Long issuedAt;
    
    /**
     * 过期时间
     */
    private Long expiresAt;
    
    /**
     * Hosted Domain
     */
    private String hostedDomain;
    
    /**
     * Authorized party
     */
    private String authorizedParty;
    
    /**
     * Nonce
     */
    private String nonce;
    
    /**
     * Access token hash
     */
    private String accessTokenHash;
    
    /**
     * JWT ID
     */
    private String jwtId;
    
    /**
     * Type
     */
    private String type;
    
    // Getters and Setters
    
    public String getIssuer() {
        return issuer;
    }
    
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    
    public String getAudience() {
        return audience;
    }
    
    public void setAudience(String audience) {
        this.audience = audience;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getEmailVerified() {
        return emailVerified;
    }
    
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPicture() {
        return picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public String getGivenName() {
        return givenName;
    }
    
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    
    public String getFamilyName() {
        return familyName;
    }
    
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    public Long getIssuedAt() {
        return issuedAt;
    }
    
    public void setIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
    }
    
    public Long getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public String getHostedDomain() {
        return hostedDomain;
    }
    
    public void setHostedDomain(String hostedDomain) {
        this.hostedDomain = hostedDomain;
    }
    
    public String getAuthorizedParty() {
        return authorizedParty;
    }
    
    public void setAuthorizedParty(String authorizedParty) {
        this.authorizedParty = authorizedParty;
    }
    
    public String getNonce() {
        return nonce;
    }
    
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
    
    public String getAccessTokenHash() {
        return accessTokenHash;
    }
    
    public void setAccessTokenHash(String accessTokenHash) {
        this.accessTokenHash = accessTokenHash;
    }
    
    public String getJwtId() {
        return jwtId;
    }
    
    public void setJwtId(String jwtId) {
        this.jwtId = jwtId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GoogleUserInfoVo{" +
                "issuer='" + issuer + '\'' +
                ", audience='" + audience + '\'' +
                ", subject='" + subject + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", locale='" + locale + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                ", hostedDomain='" + hostedDomain + '\'' +
                ", authorizedParty='" + authorizedParty + '\'' +
                ", nonce='" + nonce + '\'' +
                ", accessTokenHash='" + accessTokenHash + '\'' +
                ", jwtId='" + jwtId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}