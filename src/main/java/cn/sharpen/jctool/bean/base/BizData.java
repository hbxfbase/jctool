package cn.sharpen.jctool.bean.base;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.setting.dialect.Props;
import cn.sharpen.jctool.consts.SymbolConst;
import cn.sharpen.jctool.util.StrTool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hutool.core.text.StrPool.COLON;

/**
 * 业务数据
 */
public class BizData {

  public static String DEFAULT_TAG = "nftb";

  public static String projName = null;
  public static String projId = null;
  public static String uidProjId = null;
  public static String jwtKey = null;


  /**
   * 当前日期，格式：yyyyMMdd
   */
  public static int currDate = 0;
  /**
   * 在线用户的redis的key,系统初始化时，会替换
   */
  public static String onlineRedisKey = "online_redis_key";
  /**
   * 登录用户的redis的key,系统初始化时，会替换
   */
  public static String loginRedisKey = "login_redis_key";
  /**
   * 所有系统登录用户的redis的key,系统初始化时，会替换
   */
  public static String allLoginRedisKey = "all_login_redis_key";

  public static Snowflake defSnowflake = new Snowflake(1, 1);
  public static MailAccount defMailAccount = null;


  // 项目key初始化记录,key为projId, value为day(yyyyMMdd)
  public static final Map<String, Integer> projRedisInit = new ConcurrentHashMap();

  public static Props localBizConf = null;
  public static String localConfFile = "conf.properties";
  public static String WIN_CONF="D:\\datum\\data\\conf\\";
  public static String LINUX_CONF="/home/datum/conf/";
  public static String MAC_CONF="~/datum/conf/";


  public static final String DB_MYBATIS_TYPE_MAP = "varchar:VARCHAR;varchar2:VARCHAR;longtext:VARCHAR;" +
          "json:VARCHAR;text:VARCHAR;mediumtext:VARCHAR;timestamp:TIMESTAMP;datetime:TIMESTAMP;" +
          "tinyint:NUMERIC;smallint:NUMERIC;mediumint:NUMERIC;int:NUMERIC;integer:NUMERIC;bigint:NUMERIC;" +
          "decimal:DECIMAL;";
  public static Map<String, String> dbMybatisTypeMap = StrTool.str2map(DB_MYBATIS_TYPE_MAP, SymbolConst.SEMICOLON, COLON);

  public static final String DB_JAVA_TYPE_MAP = "VARCHAR:String;VARCHAR2:String;varchar:String;varchar2:String;" +
          "json:String;longtext:String;text:String;mediumtext:String;mediumtext:String;" +
          "timestamp:Date;datetime:Date;" +
          "tinyint:Integer;smallint:Integer;mediumint:Integer;int:Integer;integer:Integer;bigint:Long;" +
          "decimal:String;";
  public static Map<String, String> dbJavaTypeMap = StrTool.str2map(DB_JAVA_TYPE_MAP, SymbolConst.SEMICOLON, COLON);


  public static final String DB_GO_TYPE_MAP = "VARCHAR:string;VARCHAR2:string;varchar:string;varchar2:string;" +
          "json:string;longtext:string;text:string;mediumtext:string;mediumtext:string;text:string;" +
          "nchar:string;"+
          "timestamp:sql.NullTime;datetime:sql.NullTime;" +
          "tinyint:int32;smallint:int32;mediumint:int32;int:int32;integer:int32;bigint:int64;" +
          "decimal:decimal.Decimal;double:decimal.Decimal;";
  public static Map<String, String> dbGoTypeMap = StrTool.str2map(DB_GO_TYPE_MAP, SymbolConst.SEMICOLON, COLON);

  public static Set<String> dbDateTypes = new HashSet(Arrays.asList(new String[]{"timestamp", "datetime"}));
  /**
   * 忽略jwt的uri
   */
  public static Set<String> ignoreJwtUri = new HashSet();

}
