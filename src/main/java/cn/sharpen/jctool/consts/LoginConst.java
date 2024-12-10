package cn.sharpen.jctool.consts;

/**
 * 登录相关的常量
 * @author Justin
 */
public class LoginConst {

    // 请求流水号
    public static final String REQUEST_SEQUENCE = "requestId";
    // 服务流水号
    public static final String SERVICE_SEQUENCE = "serveSeq";
    public static final String CLIENT_IP = "clientIp";
    public static final String TOKEN = "token";

    public static final String PID = "pid";
    public static final String TID = "tid";
    public static final String UID = "uid";
    /**
     * 存活时间，单位：秒
     */
    public static final String ALIVE = "alive";
    public static final String ONLINE = "online";
    public static final String SESSION = "session";
    public static final String LOGIN = "login";
    /**
     * 用户登录账户
     */
    public static final String ACCOUNT = "account";
    /** 用户级别 */
    public static final String GRADE = "grade";

    // 下面是redis key前缀

    public static final String ONLINE_REDIS_PRE = "user_data:stat:online:uid:";
    public static final String LOGIN_REDIS_PRE = "user_data:stat:login:uid:";
    public static final String ALL_LOGIN_REDIS_PRE = "user_data:login:all:uid:";
    public static final String LOGIN_REDIS_KEY_PRE = "user_data:login:pre:";
    /** redis key前缀：登录过期时间（毫秒） */
    public static final String RKP_LOGIN_EXPIRE_MS = "user_data:login:expire_ms:";

    // 用于通过UID查token
    @Deprecated
    public static final String UID_LOGIN_REDIS_KEY_PRE = "user_data:uid:login:";
    // 用于通过UID查token(多个，放在set中)
    public static final String ULRK_TIDS_PRE_SET = "user_data:uid:login:tids:";

    /**
     * 登录类型
     */
    public static final String LOGIN_TYPE = "login_type";
    // 登录类型

    public static final String ACCOUNT_STR_ACCOUNT = "str_account";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_CELLPHONE = "cellphone";
    public static final String ACCOUNT_CELLPHONE_CODE = "cellphone_code";
    public static final String ACCOUNT_TELEGRAM = "telegram";
    public static final String ACCOUNT_GOOGLE = "google";
    /**
     * 第三方登录
     */
    public static final String ACCOUNT_WECHAT_OPEN = "wechat_open";
    public static final String ACCOUNT_WECHAT_MINI = "wechat_mini";;
    public static final String ACCOUNT_QQ = "qq";
    public static final String ACCOUNT_ALIPAY = "alipay";
    public static final String ACCOUNT_WEIBO = "weibo";
    public static final String ACCOUNT_FACEBOOK = "facebook";
    public static final String ACCOUNT_TWITTER = "twitter";
    public static final String ACCOUNT_GITHUB = "github";
    public static final String ACCOUNT_GITLIB = "gitlib";
    public static final String ACCOUNT_LINKIN = "linkin";


    // 用户类型

    public static final String USER_GRADE_ADMIN = "admin";
    public static final String USER_GRADE_MANAGER = "manager";
    public static final String USER_GRADE_MEMBER = "member";

    // 用户状态

    public static final String STATUS_NORMAL = "normal";
    public static final String STATUS_INACTIVE = "inactive";
    public static final String STATUS_FROZEN = "frozen";

    // 验证码

    public static final String REDIS_CODE_EMAIL = "verify_code:email:";
    public static final String REDIS_CODE_CELLPHONE = "verify_code:cellphone:";

    // 验证码类型

    public static final String CODE_REGISTER = "register";
    public static final String CODE_LOGIN = "login";
    public static final String CODE_RESETPWD = "resetPwd";
    public static final String CODE_LOGOUT = "hasLogout";


    public static final String CODE_DESC_MAIL = "邮箱验证码";


}
