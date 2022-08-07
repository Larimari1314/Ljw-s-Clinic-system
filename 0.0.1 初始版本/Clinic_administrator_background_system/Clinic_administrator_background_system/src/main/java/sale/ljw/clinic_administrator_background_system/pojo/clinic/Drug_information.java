package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import java.io.Serializable;

/**
 * 药品信息
 */
public class Drug_information implements Serializable {
    /**
     * 药品id
     */
    private String id;
    /**
     * 药品名称
     */
    private String drugName;
    /**
     * 药品功效
     */
    private String effect;
    /**
     * 药品原价
     */
    private Double originalPrice;
    /**
     * 药品封面
     */
    private String drugImgUrl;
    /**
     * 药品现价
     */
    private Double currentPrice;
    /**
     * 现存数量
     */
    private Integer quantity;

    /**
     * 是否对患者开放
     */
    private Integer exhibit;

    @Override
    public String toString() {
        return "Drug_information{" +
                "id='" + id + '\'' +
                ", drugName='" + drugName + '\'' +
                ", effect='" + effect + '\'' +
                ", originalPrice=" + originalPrice +
                ", drugImgUrl='" + drugImgUrl + '\'' +
                ", currentPrice=" + currentPrice +
                ", quantity=" + quantity +
                ", exhibit=" + exhibit +
                '}';
    }

    public Drug_information(String id, String drugName, String effect, Double originalPrice, String drugImgUrl, Double currentPrice, Integer quantity, Integer exhibit) {
        this.id = id;
        this.drugName = drugName;
        this.effect = effect;
        this.originalPrice = originalPrice;
        this.drugImgUrl = drugImgUrl;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
        this.exhibit = exhibit;
    }

    public Drug_information() {
    }

    public Integer getExhibit() {
        return exhibit;
    }

    public void setExhibit(Integer exhibit) {
        this.exhibit = exhibit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDrugImgUrl() {
        return drugImgUrl;
    }

    public void setDrugImgUrl(String drugImgUrl) {
        this.drugImgUrl = drugImgUrl;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
