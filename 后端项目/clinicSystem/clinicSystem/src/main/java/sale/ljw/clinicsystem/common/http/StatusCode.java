package sale.ljw.clinicsystem.common.http;

public enum StatusCode {
	C200, // 正常
	C401, // 认证不通过
	C404, // 未找到资源
	C405, // 权限不足
	C500; // 服务异常
}
