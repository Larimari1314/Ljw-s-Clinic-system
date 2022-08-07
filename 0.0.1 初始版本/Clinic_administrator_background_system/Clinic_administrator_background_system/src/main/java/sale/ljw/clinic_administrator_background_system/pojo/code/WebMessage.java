package sale.ljw.clinic_administrator_background_system.pojo.code;

import java.io.Serializable;

public class WebMessage implements Serializable {
    private String code;
    private String message;
    private Object data;

    @Override
    public String toString() {
        return "WebMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public WebMessage() {
    }

    public WebMessage(String message, Object data) {
        this.message = message;
        this.data = data;
        this.code=Code.server_error_code;
    }

    public WebMessage(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
