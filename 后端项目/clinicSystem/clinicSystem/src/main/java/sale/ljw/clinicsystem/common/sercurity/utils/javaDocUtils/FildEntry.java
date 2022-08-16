package sale.ljw.clinicsystem.common.sercurity.utils.javaDocUtils;


public class FildEntry {

    /**
     * 参数名
     */
    private String fName;
    /**
     * 类型
     */
    private String fType;
    /**
     * 说明
     */
    private String fExplain;

    public FildEntry(String fName, String fType, String fExplain) {
        super();
        this.fName = fName;
        this.fType = fType;
        this.fExplain = fExplain;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "fName='" + fName + '\'' +
                ", fType='" + fType + '\'' +
                ", fExplain='" + fExplain + '\'' +
                '}';
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getfExplain() {
        return fExplain;
    }

    public void setfExplain(String fExplain) {
        this.fExplain = fExplain;
    }
}
