package cn.sharpen.jctool.consts;

/**
 * 登录相关的常量
 * @author Justin
 */
public class WebConst {

    // 表示回调请求协议的常量,用于拼接回调URL
    public static final String PROTOCL_HTTP = "http://";
    public static final String PROTOCL_HTTPS = "https://";


    public static final String CONTENTTYPE_JSON = "application/json; charset=utf-8";
    public static final String CONTENTTYPE_FORM = "application/x-www-form-urlencoded";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String SESSIONID = "SESSIONID";
    public static final String HEAD_LANGUAGE = "req_language";
    public static final String HEAD_REQUEST_ID= "requestId";
    public static final String H_AUTHORIZATION = "Authorization";


    // http跨域请求参数及值
    public static final String CORS_ORIGIN = "Access-Control-Allow-Origin";
    public static final String CORS_METHODS = "Access-Control-Allow-Methods";
    public static final String CORS_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String CORS_HEADERS = "Access-Control-Allow-Headers";


    public static final String ORIGIN = "origin";
    public static final String HTTP_METHODS = "POST, GET, OPTIONS, PUT, DELETE";
    public static final String HTTP_HEADS = "Content-Type, Access-Control-Allow-Headers, language, Authorization, " +
            "X-Requested-With, otToken, requestId, otLanguage,otSource,otEquipId,X-Proj-Id, token,projid,proj_id,proj-id, Content-Disposition, req_language, from_terminal, appName,appVersion,appPlatform,ua";
    public static final String HTTP_EXPOSE_HEADS = "Content-Disposition";
    public static final String CORS_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    public static final String HTTP_HEAD_PROJ_ID = "proj-id";


    public static final String HH_FILEDOWNLOAD = "attachment;filename=";
    public static final String HH_CONTENTTYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String HH_CONTENTTYPE_XLS = "application/vnd.ms-excel";
    public static final String HH_CONTENT_FILE = "Content-Disposition";
    /**
     * 文件下载
     */
    public static final String CONTENT_DISPOSITION = "Content-disposition";
    public static final String CONTENT_FILE = "attachment; filename=";
    public static final String DOWNLOAD_COOKIE = "fileDownload=true; path=/";



}
