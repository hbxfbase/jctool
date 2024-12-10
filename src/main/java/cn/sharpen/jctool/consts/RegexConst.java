package cn.sharpen.jctool.consts;

/**
 * 正则表达式
 * 参考： https://www.jb51.net/article/83415.htm
 * @author justin
 */
public class RegexConst {

    public static final String DOT = "\\.";
    /**
     * 电话号码
     */
    public static final String REG_CELLPHONE = "^((\\+)?[0-9]{1,4}-)?[1-9]{1}[0-9]{1,10}$";
    /**
     * 邮箱
     */
    public static final String REG_EMAIL = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";
    /**
     * 用户名正则，4到16位（字母，数字，下划线，减号）
     */
    public static final String REG_ACCOUNT ="^[a-zA-Z0-9_-]{4,16}$";
    /**
     * 账户
     */
    public static final String REG_PASSWORD ="^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";
    /**
     * url
     */
    public static final String REG_URL = "^[a-zA-z]+://(/w+(-/w+)*)(/.(/w+(-/w+)*))*(/?/S*)?$";
    /**
     * 账户
     */
    public static final String REG_ID_NUMBER = "^/d{15}|/d{18}$";

//    public static final String VIRTUAL_CARD_APPLY_NAME = "^[A-Za-z0-9]+(?: [A-Za-z0-9 ]+)*$";
    public static final String VIRTUAL_CARD_APPLY_NAME = "[ 0-9A-Za-z]+";
    /**
     * IP地址
     */
    public static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

}
