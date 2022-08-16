package sale.ljw.clinicsystem.common.http;

import com.sun.corba.se.impl.encoding.CodeSetConversion;
import lombok.Data;

import java.util.Map;

@Data
public class ResponseResult<T> {
	private T result;
	private StatusCode statusCode;
	private String msgId;
	private Map<String,String> msgParams;
	private ResultType resultType;
	public ResponseResult() {
	}


	public ResponseResult(T result, String msgId, ResultType rt, StatusCode statusCode, Map<String,String> msgParams) {
		super();
		this.result = result;
		this.msgId = msgId;
		this.resultType = rt;
		this.statusCode = statusCode;
		this.msgParams = msgParams;
	}

	public static <T> ResponseResult<T> getSuccessResult(T res) {
		return new ResponseResult<T>(res, "C200", ResultType.SUCCESS, StatusCode.C200, null);
	}
	public static <T> ResponseResult<T> getSuccessResult(T res, String msgId, Map<String,String> msgParams) {
		return new ResponseResult<T>(res, msgId, ResultType.SUCCESS, StatusCode.C200, msgParams);
	}

	public static <T> ResponseResult<T> getMessageResult(T res, String msgId, StatusCode statusCode) {
		return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, statusCode, null);
	}
	
	public static <T> ResponseResult<T> getMessageResult(T res, String msgId) {
		return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, StatusCode.C200, null);
	}
	
	public static <T> ResponseResult<T> getMessageResult(T res, String msgId, Map<String,String> msgParams) {
		return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, StatusCode.C200, msgParams);
	}
	
	public static <T> ResponseResult<T> getMessageResult(T res, String msgId, Map<String,String> msgParams, StatusCode statusCode) {
		return new ResponseResult<T>(res, msgId, ResultType.MESSAGE, statusCode, msgParams);
	}
	
	public static <T> ResponseResult<T> getErrorResult(String msgId) {
		return new ResponseResult<T>(null, msgId, ResultType.ERROR, StatusCode.C500, null);
	}
	
	public static <T> ResponseResult<T> getNotFoundResult() {
		return new ResponseResult<T>(null, null, ResultType.NOT_FOUND, StatusCode.C500, null);
	}
}
