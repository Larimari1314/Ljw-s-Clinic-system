package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import java.io.Serializable;

public class RegistrationInformation implements Serializable {
    private String id;
    private Drug_information drug;
    private Integer number;

    @Override
    public String toString() {
        return "RegistrationInformation{" +
                "id='" + id + '\'' +
                ", drug=" + drug +
                ", number=" + number +
                '}';
    }

    public RegistrationInformation(String id, Drug_information drug, Integer number) {
        this.id = id;
        this.drug = drug;
        this.number = number;
    }

    public RegistrationInformation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Drug_information getDrug() {
        return drug;
    }

    public void setDrug(Drug_information drug) {
        this.drug = drug;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
