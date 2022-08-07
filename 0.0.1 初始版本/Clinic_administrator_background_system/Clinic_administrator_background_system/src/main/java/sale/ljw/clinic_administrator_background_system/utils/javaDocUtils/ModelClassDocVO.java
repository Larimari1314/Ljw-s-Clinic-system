package sale.ljw.clinic_administrator_background_system.utils.javaDocUtils;

import java.util.List;

public class ModelClassDocVO {
    private String modelTableName;

    private String modelClassName;

    private String modelCommentText;

    private List<FildEntry> fildEntryList;


    public String getModelTableName() {
        return modelTableName;
    }

    public void setModelTableName(String modelTableName) {
        this.modelTableName = modelTableName;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getModelCommentText() {
        return modelCommentText;
    }

    public void setModelCommentText(String modelCommentText) {
        this.modelCommentText = modelCommentText;
    }

    public List<FildEntry> getFildEntryList() {
        return fildEntryList;
    }

    public void setFildEntryList(List<FildEntry> fildEntryList) {
        this.fildEntryList = fildEntryList;
    }

    @Override
    public String toString() {
        return "ModelClassDocVO{" +
                "modelClassName='" + modelClassName + '\'' +
                ", modelCommentText='" + modelCommentText + '\'' +
                ", fildEntryList=" + fildEntryList +
                '}';
    }
}
