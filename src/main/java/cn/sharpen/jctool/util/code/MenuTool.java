package cn.sharpen.jctool.util.code;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sharpen.jctool.bean.utilbean.ExtMap;
import cn.sharpen.jctool.util.ObjTool;
import cn.sharpen.jctool.util.StrTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

import static cn.hutool.core.text.StrPool.COLON;
import static cn.hutool.core.text.StrPool.COMMA;
import static cn.sharpen.jctool.consts.SignConst.STR_ZERO;

/**
 * 菜单工具-包含扫描注释，转成bdfp insert sql
 * @Author: Justin
 * @Date: 2020/11/14 2:02 PM
 */
@Slf4j
public class MenuTool {

    public static Map<String, String> funcNameMapDef = StrTool.str2map("/add:新增,/nullifyBatch:作废,/modify:修改," +
            "/primaryQuery:详情,/listQuery:查询列表,/excelImport:excel批量导入,/downloadExcelTemplate:下载excel导入模板," +
            "/excelExport:excel文件导出,/primaryBatchQuery:主键批量查詢",
            COMMA, COLON);


    /**
     * 生成默认菜单
     * 包括：
     * @param projId 项目ID
     */
    public static List<String> createDefMenuSql(Long projId)  {
        List<String> authList = new ArrayList<>();
        // 系统操作ID前缀，例：20305102， 业务管理ID前缀，例： 20305103
        String sysPre = projId+ "02", bizPre = projId+ "03";
        //系统操作ID
        Long sysOperaId = ObjTool.str2long(sysPre),
                //系统全局ID
                sysGlobalId = ObjTool.str2long(sysPre+"02"),
                //混合查询ID
                hybridId = ObjTool.str2long(sysPre+"03"),
                //业务管理ID
                bizId = ObjTool.str2long(bizPre),
                //业务数据管理ID
                bizManageId = ObjTool.str2long(bizPre+"01");
        // 菜单包含 系统操作，系统全局， 混合查询，业务管理， 业务数据管理

        long np = 0L;
        String bs = "", osn = "系统操作", bm = "业务管理", foldIcon="el-icon-folder", setIcon="el-icon-setting",
                menuIcon="el-icon-menu", viewIcon = "el-icon-view",foldType= "catalogue_type", soi = sysOperaId+"",
                menuType="menu_type", sg = "系统全局", mq = "混合查询", bdm="业务数据管理", sgp="../admin/sysGlobal.html",
                hqp="../admin/hybridQuery.html";
        authList.add(makeMenuSql(sysOperaId, projId, np, bs,osn,bs, foldType, foldIcon, bs));
        authList.add(makeMenuSql(sysGlobalId, projId, sysOperaId, soi,sg,bs, menuType, menuIcon, sgp));
        authList.add(makeMenuSql(hybridId, projId, sysOperaId, soi,mq, bs,menuType,viewIcon, hqp));

        authList.add(makeMenuSql(bizId, projId, np, bs,bm, bs,foldType, foldIcon, bs));
        authList.add(makeMenuSql(bizManageId, projId, bizId, bizId+"",bdm, bs,foldType, bs));
        log.info("{}bizManageId={}", projId, bizManageId);

        return authList;
    }

    /**
     * 扫描注解转成bdfp的菜单的insert 语句。
     *
     * @param entityNameMap 实体标记-名称映射
     * @param packagePath 扫描的java包路径
     * @param projId 项目ID 例：3051
     * @param pathPid 菜单上级菜单，bizManageId，例：20305103
     * @param intfType 接口类型，按钮button_type，接口interface_type
     * @param mappingCls RequestMapping.class
     * @return
     */
    public static List<String> scanAnnotate2menuSql(
            Map<String, String> entityNameMap, String packagePath, Long projId, Long pathPid,
            String intfType, Class<? extends Annotation> mappingCls, Map<String, String> funcNameMap) {
        List<String> authList = new ArrayList<>();
        Set<Class<?>> clsSet = ClassUtil.scanPackage(packagePath);
        if(CollectionUtils.isEmpty(clsSet)) {
            return authList;
        }

        pathPid = ObjTool.str2long(StrTool.valNoBlank(pathPid+"", projId+"0301"));
        Set<String> pathUriSet = new TreeSet<>();
        Map<String, TreeSet<String>> funUriMap = new HashMap<>();
        for(Class cls : clsSet) {
            String[] clsUris = AnnotationUtil.getAnnotationValue(cls, mappingCls);
            if(ArrayUtil.isEmpty(clsUris)) {
                continue;
            }
            String clsUri = StrUtil.removeSuffix(clsUris[0], StrPool.SLASH);
            pathUriSet.add(clsUri);

            TreeSet<String> funUriSet = new TreeSet<>();
            funUriMap.put(clsUri, funUriSet);

            Method[] methods = ReflectUtil.getMethods(cls);
            if(ArrayUtil.isNotEmpty(methods)) {
                for(Method method : methods) {
                    String[] methodUris = AnnotationUtil.getAnnotationValue(method, mappingCls);
                    if(ArrayUtil.isEmpty(methodUris)) {
                        continue;
                    }
                    String methodUri = methodUris[0];
                    String uri = clsUri + StrUtil.prependIfMissing(methodUri, StrPool.SLASH);
                    funUriSet.add(uri);
                    log.debug("makeUri={}", uri);
                }
            }
        }

        log.debug("startCreateUri");
        Set<String> oldId = new HashSet<>();
        for(String pageUri : pathUriSet) {
            String entityMark = uriEnd(pageUri);
            String menuIdStr = pathPid + uri2int(oldId, pageUri, 4);
            long menuId = Long.valueOf(menuIdStr);
            // 菜单
            authList.add(makeMenuSql(menuId, projId, pathPid,pathPid+"", entityNameMap.get(entityMark.replaceAll("\\/","")),"", "menu_type",
                    "../manage"+entityMark+".html"));

            // 按钮，接口
            TreeSet<String> funUriSet = funUriMap.get(pageUri);
            if(CollectionUtil.isNotEmpty(funUriSet)) {
                Set<String> oldIdBtn = new HashSet<>();
                for(String func : funUriSet) {
                    String funMark = uriEnd(func);
                    String ordinalFuncId = uri2int(oldIdBtn, pageUri+funMark, 2);
                    String authId = menuId+ ordinalFuncId;
                    long funcId = Long.valueOf(authId);
                    String name = funcNameMap.get(funMark);
                    authList.add(makeMenuSql(funcId, projId, menuId,pathPid+","+menuId, name, func, intfType, func));
                }
            }
        }

        return authList;
    }

    public static  String numStrNext(int size, String num){
        int numInt = Integer.valueOf(num)+1;
        String numStr = numInt+"";
        if(numStr.length()>size) {
            return new DecimalFormat(StrUtil.repeat(STR_ZERO, size)).format(1);
        }
        return numStr;
    }
    /**
     * URI转成数字
     * @param oldId 生成过的数字
     * @param str 要转换的uri
     * @param length 数字长度
     * @return
     */
    public static String uri2int(Set<String> oldId, String str, int length){
        int i = 1000;
        long num = Math.abs(StrTool.strHex2long(str));
        String intVal = new DecimalFormat(StrUtil.repeat(STR_ZERO, length)).format(num);
        if(intVal.length()>length){
            intVal = intVal.substring(intVal.length() - length);
        }

        do {
            --i;
            if(oldId.contains(intVal)) {
                intVal = numStrNext(length, intVal);
                continue;
            }else {
                oldId.add(intVal);
                break;
            }
        }while (i>0);
        return intVal+"";
    }

    /**
     * 获取uri的最后一部分
     * @param uri
     * @return
     */
    public static String uriEnd(String uri) {
        return StrUtil.subSuf(uri, StrUtil.lastIndexOf(uri, StrPool.SLASH, uri.length()-1, false));
    }

    public static Map<String, String> getEntityNameMap(String packagePath){
        ExtMap<String, String> map = ExtMap.inst();

        Set<Class<?>> clsSet = ClassUtil.scanPackage(packagePath);
        if(CollectionUtils.isEmpty(clsSet)) {
            return map.get();
        }

        for(Class cls : clsSet) {
            String entityMark = getStaticValStr(cls, "varName");
            String entityName = getStaticValStr(cls, "abbrName");
            map.putNoNull(entityMark, entityName);
        }
        return map.get();
    }

    public static String getStaticValStr(Class cls, String prop){
        Field field = ClassUtil.getDeclaredField(cls, prop);
        if(field!=null) {
            try {
                return ObjTool.obj2str(field.get(null));
            }catch (Exception e) {
                log.info("getStaticValStr={}", e.getMessage(), e);
            }
        }
        return null;
    }

    public static String makeMenuSql(Long id, Long projId,Long pid,String allIds, String name,String mark, String type,
                                     String uri) {
        return makeMenuSql(id, projId, pid, allIds, name, mark, type, "el-icon-menu", uri);
    }

    /**
     * 生成权限插入脚本SQL
     * @param id 菜单ID
     * @param projId 项目ID
     * @param name 菜单/按钮/接口名称
     * @param type 当前权限类型，依赖枚举authority_type。目录catalogue_type，菜单menu_type，按钮button_type，接口interface_type
     * @param uri 权限路径
     * @param iconStyle 权限图标， https://element.eleme.cn/#/en-US/component/icon
     * @return
     */
    public static String makeMenuSql(Long id, Long projId,Long pid,String allIds, String name,String mark, String type,
                                     String iconStyle, String uri) {
        String sql = "INSERT INTO function_authority(id, proj_id, pid,all_ids, name, " +
                "mark,authority_type, relative_path, allow_show, ordinal, icon_style, as_refresh, enable_flag) " +
                "VALUES ("+id+", "+projId+", "+pid+", '"+allIds+"','"+name+"','"+mark+"','"+type+"', '"+uri+"', 'Y', "+
                id+", '"+iconStyle+"', 'N', 'Y');\n";
        return sql;
    }

}
