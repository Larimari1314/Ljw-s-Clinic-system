package sale.ljw.clinic_administrator_background_system.pojo.page;

public class LandingAds {
    private String id;
    private String imgUrl;
    private String ads;

    @Override
    public String toString() {
        return "LandingAds{" +
                "id='" + id + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", ads='" + ads + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public LandingAds() {
    }

    public LandingAds(String id, String imgUrl, String ads) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.ads = ads;
    }
}
