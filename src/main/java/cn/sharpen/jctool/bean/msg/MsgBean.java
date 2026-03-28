package cn.sharpen.jctool.bean.msg;

import cn.sharpen.jctool.consts.SignConst;
import cn.sharpen.jctool.enums.BizCodeEnum;
import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.ObjTool;
import cn.sharpen.jctool.util.StrTool;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static cn.sharpen.jctool.consts.SignConst.Y;


/**
 * 通用请求对象
 *
 * @author sharpen-auto
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class MsgBean<T> implements Serializable {

    private static final long serialVersionUID = 8995L;
    private MsgHead msgHead;
    private MsgBody<T> msgBody;

    public MsgBean(MsgHead msgHead) {
        this.msgHead = msgHead;
    }

    public MsgBean(MsgBody<T> msgBody) {
        this.msgHead = new MsgHead();
        this.msgBody = msgBody;
    }

    public String toJson(){
        return JsonTool.obj2json(this);
    }

    public static void initReqPage(MsgBean msgBean){
        if(msgBean!=null) {
            MsgBody body = msgBean.getMsgBody();
            if(body == null) {
                body = new MsgBody();
                msgBean.setMsgBody(body);
            }
            if(body.getReqPageNum() == null) {
                body.setReqPageNum(SignConst.L1);
            }
            if(body.getReqRecordNum() == null) {
                body.setReqRecordNum(SignConst.INT15);
            }
        }
    }

    public static void initRespPage(MsgBean msgBean){
        if(msgBean!=null) {
            MsgBody body = msgBean.getMsgBody();
            if(body == null) {
                body = new MsgBody();
                msgBean.setMsgBody(body);
            }
            if(body.getRespPageCount() == null) {
                body.setRespPageCount(SignConst.L0);
            }
            if(body.getReqRecordNum() == null) {
                body.setRespRecordCount(SignConst.L0);
            }
        }
    }

    public static <T> MsgBean<T> setRespList(MsgBean msgBean, List<T> result, Integer pageCount, Long recordCount) {
        return setRespList(msgBean, result, ObjTool.int2long(pageCount), recordCount);
    }
    /**
     * 设置返回数据列表
     * @param msgBean 已有的返回对象
     * @param result 结果列表
     * @param pageCount 总共多少页
     * @param recordCount 总共多少条
     * @param <T> 结果列表对象类型
     */
    public static <T> MsgBean<T> setRespList(MsgBean msgBean, List<T> result, Long pageCount, Long recordCount){
        if(msgBean==null) {
            msgBean = MsgBean.successWithBody();
        }

        MsgBody body = msgBean.getMsgBody();
        if(body == null) {
            body = new MsgBody();
            msgBean.setMsgBody(body);
        }
        body.setEntityList(result);
        body.setRespPageCount(pageCount);
        body.setRespRecordCount(recordCount);
        return msgBean;
    }

    public static boolean isSuccess(MsgBean bean) {
        if (bean == null || bean.getMsgHead() == null || StringUtils.isBlank(bean.getMsgHead().getCode())) {
            return false;
        }
        return StringUtils.equals(bean.getMsgHead().getCode(), SignConst.STR_ZERO);
    }

    public static boolean noEntity(MsgBean msgBean) {
        return msgBean == null || msgBean.getMsgBody() == null || msgBean.getMsgBody().getEntity() == null;
    }

    public static MsgBean after(MsgBean old, MsgBean update) {
        if (update == null) {
            update = new MsgBean(new MsgHead(), new MsgBody());
        }
        if (update.getMsgHead() == null) {
            update.setMsgHead(new MsgHead());
        }
        if (old != null) {
            MsgHead oldHead = old.getMsgHead();
            if (oldHead != null) {
                MsgHead head = update.getMsgHead();
                head.setReqTime(old.getMsgHead().getReqTime());
                head.setRequestId(oldHead.getRequestId());
            }
        }
        update.getMsgHead().setRespTime(System.currentTimeMillis());
        update.getMsgBody().setBizSort(old.getMsgBody().getBizSort());
        return update;
    }

    // 实例化

    public static MsgBean inst(String code, String msg) {
        MsgHead msgHead = new MsgHead(code, msg);
        return new MsgBean(msgHead);
    }

    public static MsgBean inst(String code, String msg, String bizDesc) {
        MsgHead msgHead = new MsgHead(code, msg, bizDesc);
        return new MsgBean(msgHead);
    }

    public static MsgBean instWithBody(MsgBody msgBody) {
        return new MsgBean(msgBody);
    }

    public static MsgBean success() {
        MsgHead msgHead = new MsgHead("0", "操作成功");
        msgHead.setRespTime(System.currentTimeMillis());
        MsgBean msg = new MsgBean();
        msg.setMsgHead(msgHead);
        msg.setMsgBody(new MsgBody());
        return msg;
    }

    public static MsgBean result(Boolean result){
        return result? MsgBean.success() : MsgBean.fail();
    }

    public static boolean processResult(MsgBean msgBean) {
        if(msgBean==null || msgBean.getMsgBody() == null || msgBean.getMsgBody().getProcessResult() == null) {
            return false;
        }
        return msgBean.getMsgBody().getProcessResult();
    }

    /**
     * 建议使用instMsgStr
     * @param msg
     * @return
     */
    @Deprecated
    public static MsgBean instMsg(String msg) {
        MsgBody msgBody = new MsgBody();
        msgBody.setMsgStr(msg);
        return new MsgBean(msgBody);
    }
    public static MsgBean instMsgStr(String msg) {
        MsgBody msgBody = new MsgBody();
        msgBody.setMsgStr(msg);
        return new MsgBean(msgBody);
    }

    public static <T> MsgBean<T> instProcessResult(Boolean processResult) {
        MsgBody msgBody = new MsgBody();
        msgBody.setProcessResult(processResult);
        return new MsgBean(msgBody);
    }
    public static <T> MsgBean<T> instEntity(T t) {
        MsgBody msgBody = new MsgBody();
        msgBody.setEntity(t);
        return new MsgBean(msgBody);
    }

    public static <T> MsgBean<T> instEntityWithCode(String code,String msg,T t) {
        MsgHead msgHead = new MsgHead(code, msg, null);
        MsgBody msgBody = new MsgBody();
        msgBody.setEntity(t);
        return new MsgBean(msgHead, msgBody);
    }

    public static <T> MsgBean<T> instEntityAndReqCount(T t, int count) {
        MsgBody msgBody = new MsgBody();
        msgBody.setEntity(t);
        msgBody.setReqRecordNum(count);
        return new MsgBean(msgBody);
    }

    public static <T> MsgBean<T> instEntityList(List<T> t) {
        MsgBody msgBody = new MsgBody();
        msgBody.setEntityList(t);
        return new MsgBean(msgBody);
    }
    public static <T> MsgBean<T> nullPage(MsgBean msgBean) {
        if(msgBean!=null && msgBean.getMsgBody()!=null) {
            MsgBody msgBody = msgBean.getMsgBody();
            msgBody.setReqPageNum(null);
            msgBody.setReqRecordNum(null);
            msgBody.setRespPageCount(null);
            msgBody.setRespRecordCount(null);
        }
        return msgBean;
    }

    // 获取

    public static String operatorUid(MsgBean msgBean) {
        return msgBean == null || msgBean.getMsgBody() == null ? null : msgBean.getMsgBody().getOperatorUid();
    }
    public static String identify(MsgBean msgBean) {
        return msgBean == null || msgBean.getMsgBody() == null ? null : msgBean.getMsgBody().getIdentify();
    }
    public static Long identifyLong(MsgBean msgBean){
        String identify = identify(msgBean);
        return StringUtils.isBlank(identify)? null : Long.valueOf(identify);
    }
    public static Long getIdentifyLong(MsgBean msgBean){
        String identify = identify(msgBean);
        return StringUtils.isBlank(identify)? null : Long.valueOf(identify);
    }

    public static String msgStr(MsgBean msgBean) {
        return msgBean == null || msgBean.getMsgBody() == null ? null : msgBean.getMsgBody().getMsgStr();
    }

    public static <T> T entity(MsgBean<T> msgBean) {
        return msgBean == null || msgBean.getMsgBody() == null ? null : msgBean.getMsgBody().getEntity();
    }

    public static <T> List<T> entityList(MsgBean<T> msgBean) {
        return msgBean == null || msgBean.getMsgBody() == null ? null : msgBean.getMsgBody().getEntityList();
    }

    public static String getDeviceMark(MsgBean msgBean){
        return msgBean == null || msgBean.getMsgHead() == null ? null : msgBean.getMsgHead().getDeviceMark();
    }

    public static <T> String requestId(MsgBean msgBean) {
        return msgBean == null || msgBean.getMsgHead() == null ? null : msgBean.getMsgHead().getRequestId();
    }


    // 从MsgBean中获取asNext
    public static boolean getAsNext(MsgBean msgBean) {
        if(msgBean == null || msgBean.getMsgBody()== null) {
            return false;
        }
        String asNextStr = msgBean.getMsgBody().getAsNext();
        asNextStr = StrTool.valNoBlank(asNextStr, Y);
        return StrTool.asy(asNextStr);
    }

    // 从MsgBean中获取lastID
    public static Long getLastId(MsgBean msgBean) {
        if(msgBean == null || msgBean.getMsgBody()== null) {
            return null;
        }
        String lastId = msgBean.getMsgBody().getLastId();
        return StringUtils.isBlank(lastId) ? null : ObjTool.str2long(lastId);
    }

    // 从MsgBean中获取lastID
    public static Map<String, Object> getBodyExtMap(MsgBean msgBean) {
        if(msgBean == null || msgBean.getMsgBody()== null) {
            return null;
        }
        Map<String, Object> extMap = msgBean.getMsgBody().getExtMap();
        return extMap;
    }

    /// ///////////////////////

    // 实例化
    public static MsgBean instIdMsg(String identify, String msgStr) {
        MsgBody msgBody = new MsgBody();
        msgBody.setIdentify(identify);
        msgBody.setMsgStr(msgStr);
        return new MsgBean(msgBody);

    }
    public static MsgBean instIdentity(String identify) {
        MsgBody msgBody = new MsgBody();
        msgBody.setIdentify(identify);
        return new MsgBean(msgBody);

    }

    public static MsgBean successWithBody() {
        MsgHead msgHead = new MsgHead("0", "操作成功");
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead, new MsgBody());
    }

    public static MsgBean successWithBody(MsgHead reqHead) {
        MsgHead msgHead = new MsgHead("0", "操作成功");
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead, new MsgBody());
    }

    public static MsgBean fail() {
        MsgHead msgHead = new MsgHead("-1", "操作失败");
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead);
    }

    public static MsgBean fail(BizCodeEnum bizCodeEnum) {
        MsgHead msgHead = new MsgHead(bizCodeEnum.getCode(), bizCodeEnum.getMsg(), null);
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead);
    }

    public static MsgBean fail(String code, String msg) {
        MsgHead msgHead = new MsgHead(code, msg, null);
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead);
    }

    public static MsgBean fail(String code, String msg, String bizDesc) {
        MsgHead msgHead = new MsgHead(code, msg, bizDesc);
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead);
    }

    public static MsgBean failWithMsg(String msg, String bizDesc) {
        MsgHead msgHead = new MsgHead("-1", msg, bizDesc);
        msgHead.setRespTime(System.currentTimeMillis());
        return new MsgBean(msgHead);
    }

    public MsgBean failReplace(MsgBean result){
        if(!MsgBean.isSuccess(result)){
            this.setMsgHead(result.getMsgHead());
        }
        return this;
    }

}
