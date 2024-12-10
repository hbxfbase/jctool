package cn.sharpen.jctool.bean.msg;


import cn.sharpen.jctool.enums.BizCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * HTTP结果封装
 * @author metatime
 * @date Oct 29, 2018
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class HttpResult {

	private String requestId;
	private String lastId;
	private String code;
	private String msg;
	private Object data;

	public static HttpResult error(BizCodeEnum bce) {
		if(bce!=null) {
			return error(bce.getCode(), bce.getMsg());
		}else {
			return error();
		}
	}

	public static HttpResult error() {
		return error(BizCodeEnum.SERVER_ABNORMAL.getCode(), BizCodeEnum.SERVER_ABNORMAL.getMsg());
	}

	public static HttpResult error4msg(String msg) {
		return error(BizCodeEnum.SERVER_ABNORMAL.getCode(), msg);
	}

	public static HttpResult error(String code, String msg) {
		HttpResult r = new HttpResult();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

  public static HttpResult error4code(String code) {
    HttpResult r = new HttpResult();
    r.setCode(code);
    return r;
  }

	public static HttpResult okMsg(String msg) {
		HttpResult r = new HttpResult();
		r.setMsg(msg);
		r.setCode("200");
		return r;
	}

	public static HttpResult okData(Object data) {
		HttpResult r = new HttpResult();
		r.setData(data);
		r.setCode("200");
		return r;
	}

	public static HttpResult ok() {
		HttpResult r = new HttpResult();
		r.setCode("200");
		return r;
	}

	public static HttpResult asOk(boolean asOkMark) {
		return asOkMark ? ok() : error();
	}



}
