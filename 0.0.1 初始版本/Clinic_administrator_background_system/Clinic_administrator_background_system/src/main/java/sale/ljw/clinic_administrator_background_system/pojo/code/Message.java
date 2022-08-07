package sale.ljw.clinic_administrator_background_system.pojo.code;

import java.io.Serializable;

public class Message implements Serializable {

    private String code;
    private String message;
    private String displayInformation;

    public Message() {
    }

    public Message(String code, String displayInformation) {
        this.code = code;
        this.message = "error";
        this.displayInformation = displayInformation;
    }

    public Message(String code, String message, String displayInformation) {
        this.code = code;
        this.message = message;
        this.displayInformation = displayInformation;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", displayInformation='" + displayInformation + '\'' +
                '}';
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

    public String getDisplayInformation() {
        return displayInformation;
    }

    public void setDisplayInformation(String displayInformation) {
        this.displayInformation = displayInformation;
    }
}
