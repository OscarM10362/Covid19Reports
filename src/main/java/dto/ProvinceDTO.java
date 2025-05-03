package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvinceDTO {
    private String iso;
    private String code;
    private String province;
    private String lat;
    private String lon;

    // Constructor vacío
    public ProvinceDTO() {}

    // Constructor con parámetros
    public ProvinceDTO(String iso, String code, String province, String lat, String lon) {
        this.iso = iso;
        this.code = code;
        this.province = province;
        this.lat = lat;
        this.lon = lon;
    }

    // Getters y Setters
    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    // Método para representación formateada
    public String toFormattedString(int index) {
        return String.format("%-8d            | %-6s           | %-15s          | %-8s            | %-8s",
                index, iso, province, lat, lon);
    }

    @Override
    public String toString() {
        return "ProvinceDTO{" +
                "iso='" + iso + '\'' +
                ",code='" + code + '\'' +
                ",province='" + province + '\'' +
                ",lat='" + lat + '\'' +
                ",lon='" + lon + '\'' +
                '}';
    }
}