package cn.sharpen.jctool.consts;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 跟业务无关的标识常量
 *
 * @author Justin
 */
public class SignConst {

    public static final Integer[] EXPAND10 = {1, 10,100,1000,10000,100000, 1000000,1000000};
    public static final String[] COMMON_SPLITS = {",", "\\\n", "\\\t", ";", ":"};

    public static final String STR_LB = "\n";
    public static final String STR_LB2 = "\n\n";
    public static final String STR_NLB = "\r\n";
    public static final String STR_NLB2 = "\r\n\r\n";

    public static final String STR_ZERO = "0";
    public static final String STR_ONE = "1";
    public static final String STR_TWO = "2";
    public static final String STR_THREE = "3";
    public static final String STR_FOUR = "4";
    public static final String STR_FIVE = "5";
    public static final String STR_SIX = "6";
    public static final String STR_SEVEN = "7";
    public static final String STR_EIGHT = "8";
    public static final String STR_NINE = "9";
    public static final String STR_TEN = "10";
    public static final String STR_ELEVEN = "11";

    public static final String STR20 = "20";
    public static final String STR30 = "30";
    public static final String STR60 = "60";
    public static final String STR100 = "100";
    public static final String STR1800 = "1800";
    public static final String STR_SATOSHI = "100000000"; // 聪
    public static final String STR_NANOMETER = "1000000000"; // 纳米
    public static final String STR_ANGSTROMS = "10000000000"; // 埃米



    public static final Integer MINUS1 = -1;
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer FOUR = 4;
    public static final Integer FIVE = 5;
    public static final Integer SIX = 6;
    public static final Integer INT10 = 10;
    public static final Integer INT15= 15;
    public static final Integer INT20 = 20;
    public static final Integer INT50 = 50;
    public static final Integer INT100 = 100;
    public static final Integer INT200 = 200;
    public static final Integer INT_THOUSAND = 1000;
    public static final Integer INT4096 = 4096;
    public static final Integer INT10THOUSAND = 10000;
    public static final Integer INT10K = 10000;
    public static final Integer INT100K = 100000;
    public static final Double D_ZERO = 0D;

    public static final Integer YES = 0;
    public static final Integer NO = 1;
    public static final Integer IS_BIND_SUCCESS = 0;

    public static final String PRE_0X = "0x";
    public static final String SUF_SQL_LIKE = ",%";


    public static final BigDecimal BD_ONE_THOUSAND = new BigDecimal(1 / 1000);
    public static final BigDecimal BD_TWELVE_MILLI = new BigDecimal(12 / 1000000);
    public static final BigDecimal BD_THOUSAND = new BigDecimal(1000);
    public static final BigDecimal BD_TWELVE = new BigDecimal(12);
    public static final BigDecimal BD_TEN_THOUSAND = new BigDecimal(10000);
    public static final Integer INT_MILLI = 1000000;
    public static final String STR_MILLI = "1000000";
    public static final BigDecimal BD_MILLI = new BigDecimal(1000000);

    public static final Long L0 = 0L;
    public static final Long L1 = 1L;
    public static final Long L2 = 2L;
    public static final Long L3 = 3L;
    public static final Long L4 = 4L;
    public static final Long L5 = 5L;
    public static final Long L6 = 6L;
    public static final Long L7 = 7L;
    public static final Long L8 = 8L;
    public static final Long L9 = 9L;
    public static final Long L10 = 10L;
    public static final Long L11 = 11L;
    public static final Long L15 = 15L;
    public static final Long L20 = 20L;
    public static final Long L100 = 100L;
    public static final Long L150 = 150L;
    public static final Long L200 = 200L;
    public static final Long L500 = 500L;
    public static final Long LONG_MILLI = 1000L;
    public static final Long L1800 = 1800L;
    public static final Long L10000 = 10000L;
    /** 十万 */
    public static final Long L_LAKH = 100000L;
    public static final Long L_SATOSHI = 100000000L ; // 聪

    // 瑞波基准时间，单位：秒。参考： https://radarlab.org/dev-cn/radard-apis.html
    public static final Long RIPPLE_BASE_SECOND = 946684800L;


    public static final String METHOD_GET = "get";
    public static final String METHOD_SET = "set";

    // 时间格式

    /**
     * 年年年年月月日日时时，例： 2020121430，2020121400
     */
    public static final String TIME_FORMAT_SHORT_HOUR = "yyyyMMddHH";
    /**
     * 年年年年月月，例： 202012，202013
     */
    public static final String TIME_FORMAT_SHORT_MONTH = "yyyyMM";
    /**
     * 年年年年，例： 2019，2020
     */
    public static final String TIME_FORMAT_SHORT_YEAR = "yyyy";
    /** 越南时间格式 */
    public static final String TF_VN = "HH:mm:ss, dd/MM/yyyy";

    // 日期时分秒
    public static final String DAYTIME = "ddHHmmss";
    public static final String ORDERBY_ASC = "asc";
    public static final String ORDERBY_VIEW_ASC = "ascending";
    public static final String ORDERBY_DESC = "desc";
    public static final String ORDERBY_VIEW_DESC = "desc";
    public static final String ST="startTime";
    public static final String ET="endTime";

    public static final String LOGIN_TOKEN_ERROR_PED_PREFIX = "TOKEN_ERROR_PWD_";

    public static final String CURRENT_LOGIN_CLIENT_IOS = "IOS";
    public static final String CURRENT_LOGIN_CLIENT_ANDROID = "ANDROID";
    public static final String CURRENT_LOGIN_CLIENT_WEB = "WEB";
    /**
     * redis 随机码KEY前缀
     */
    public static final String SESSION_RANDOM = "RANDOM_";
    /**
     * redis 谷歌验证码Key的前缀
     */
    public static final String GOOGLE_CODE_KEY_PREFIX = "GOOGLE_CODE_KEY_";


    public static final String LANG_ZH_CN = "zh-CN";
    public static final String LANG_EN = "en";
    public static final String LANG_TC = "tc";


    /**
     * 验证码key过期时间
     */
    public static final Long VALIDATE_CODE_EXPIRE = 10 * 60 * 1000L;

    public static final String LOGIN_USER_IP_TOKEN_PREFIX = "LOGIN_TOKEN_CURRENT_";

    public static final String STRING_NULL = "";
    public static final String STR_NULL_DESC = "\"\"";
    public static final String STR_2PERCENT = "%%";

    public static final String LOGIN_PWD_ERROR_COUNT_MSG_ZH = "您的账户或密码错误，您还可以尝试登录%d次";
    public static final String LOGIN_PWD_ERROR_COUNT_MSG_EN = "Incorrect password. You can try %d times";

    /**
     * 验证类型:短信,图片,邮箱,谷歌
     */
    public static final String CODE_TYPE_SMS = "SMS";
    public static final String CODE_TYPE_IMAGE = "IMAGE";
    public static final String CODE_TYPE_EMAIL = "EMAIL";

    /**
     * 短信,邮箱以及验证码的redis的key前缀
     */
    public static final String SMS_CODE_PREFIX = "SMS_CODE_";
    public static final String IMAGE_CODE_PREFIX = "IMAGE_CODE_";
    public static final String EMAIL_CODE_PREFIX = "EMAIL_CODE_";

    /**
     * 更新手机号不可提币时长
     */
    public static final long UPDATE_PHONE_FORBID_COIN_TIME = 24 * 60 * 60 * 1000;
    public static final String UPDATE_PHONE_FORBID_COIN_PREFIX = "UPDATE_PHONE_FORBID_COIN_";

    /**
     * 更新googlekey不可提币时长
     */
    public static final long UPDATE_GOOGLE_KEY_FORBID_COIN_TIME = 24 * 60 * 60 * 1000;
    public static final String UPDATE_GOOGLE_KEY_FORBID_COIN_PREFIX = "UPDATE_GOOGLE_KEY_FORBID_COIN_";



    public static final String Y = "Y";
    public static final String N = "N";
    public static final String f = "f";

    public static final String S_YES = "YES";

    public final static DateFormat UTC_FORMAT_SECOND = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static final String ID_CARD_MISTAKE = "id_card_mistake";
    public static final String CALL_STATUS_WAIT = "waiting";
    public static final String CALL_STATUS_SUCCESS = "success";

    public static final String STR_PROCESS = "process";
    public static final String STR_WAIT = "wait";
    public static final String STR_SUCCESS = "success";
    public static final String STR_FINISH = "finish";
    public static final String STR_FINISHED = "finished";
    public static final String CALL_STATUS_FAIL = "fail";

    public static final String STR_ZH_SUCCESS = "成功";
    public static final String STR_ZH_FAIL = "失败";
    public static final String STR_ZH_WAIT = "等待";

    public static final String STR_ZH_ADD = "添加";
    public static final String STR_ZH_DEL = "删除";
    public static final String STR_ZH_UPDATE = "修改";


    public static final String STATUS_UNTREATED= "untreated";
    public static final String STATUS_ACTIVE= "active";
    public static final String STATUS_INACTIVE= "inactive";

    // 跟踪序号
    public static final String TRACE_NO = "traceNo";
    // 当前登录用户IP
    public static final String CURRENT_LOGIN_IP = "CURRENT_LOGIN_IP";

    public static final String REQUESTMAPPING = "RequestMapping";
    public static final String RESOURCEPAGEANNOTION = "ResourcePageAnnotion";
    public static final String RESOURCEANNOTION = "ResourceAnnotion";
    public static final String ANNOTIONMAPMETHODKEY = "method"; // 方法上的注解
    public static final String ANNOTIONMAPCLASSKEY = "class"; // 类上的注解

    public static final String DBT_DATE = "datetime";
    public static final String DBT_TIMESTAMP = "timestamp";
    public static final String DBT_INT = "int";
    public static final String DBT_BIGINT = "bigint";
    public static final String DBT_VARCHAR = "varchar";
    public static final String DBT_VARCHAR2 = "varchar2";
    public static final String DBT_TEXT = "text";
    public static final String DBT_MEDIUMTEXT = "mediumtext";
    public static final String DBT_JSON = "json";
    public static final String CREATE_TIME = "createTime";
    public static final String ENABLE_FLAG = "enableFlag";
    public static final String ID = "id";
    public static final String START = "start";
    public static final String END = "end";
    public static final String TOTAL = "total";
    public static final String PAGE_QTY = "pageQty";
    public static final String vec2str = "vec2str";
    public static final String EMPTY_JSON = "{}";


    public static final String CHAR_CODE = StandardCharsets.UTF_8.name();

    public static String FE_XLS = ".xls";
    public static String FE_XLSX = ".xlsx";

    //短信配置category
    public static String SMS_CONFIG = "smsConfig";

    //阿里云短信 const
    public static String ALIYUN_KEY = "aliyun_access_key";
    public static String ALIYUN_SECRET = "aliyun_access_secret";
    public static String ALIYUN_TEMPLATE_CODE = "aliyun_template_code";
    public static String ALIYUN_SIGN_NAME = "aliyun_sign_name";


    public static final String REQUEST_ID = "requestId";
    public static final String CLASSIFY_USERTYPE = "userType";
    @Deprecated
    public static final String USERTYPE_ADMIN = "admin";
    @Deprecated
    public static final String USERTYPE_MANAGER = "manager";
    @Deprecated
    public static final String USERTYPE_MEMBER = "member";

    /** 用户分类，会员，管理员，操作员，会计，出纳 */
    public static final String UC_MEMBER = "member";
    public static final String UC_MANAGER = "manager";
    public static final String UC_OPERATOR = "operator";
    public static final String UC_ACCOUNTANT = "accountant";
    public static final String UC_CASHIER = "cashier";
    public static final String PROP_CATEGORY = "category";
    public static final String PROP_PUB_LEVEL = "pubLevel";
    // 公开程度
    public static final String LEVEL_PUB = "pub";
    public static final String LEVEL_SYS = "sys";
    public static final String LEVEL_USER = "user";

    public static final String QUERY_NUM = "num";
    public static final String QUERY_EQ = "eq";
    public static final String QUERY_LIKE = "like";

    public static final String SELECT_VAL = "select_val";
    public static final String HYBRID_QUERY = "hybrid_query";



    public static final String UPPER_HUMP ="upperHump";
    public static final String HUMP ="hump";
    public static final String SRC ="src";
    public static final String MODULE ="module";
    public static final String TEMPLATE ="template";

}
