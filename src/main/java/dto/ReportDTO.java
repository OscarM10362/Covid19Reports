package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDTO {
    private String id;
    private String date;
    private int ProvinceCode;
    private String NameProvince;
    private int confirmed;
    private int deaths;
    private int recovered;

    public ReportDTO() {}

    public ReportDTO(String id, String date, int confirmed, int deaths, int recovered,int ProvinceCode, String NameProvince) {
        this.id = id;
        this.date = date;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.ProvinceCode = ProvinceCode;
        this.NameProvince = NameProvince;
    }

    public String getNameProvince(String string) {
        return NameProvince;
    }

    public void setNameProvince(String NameProvince) {
        this.NameProvince = NameProvince;
    }

    // Getters y Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getConfirmed(int aInt) { return confirmed; }
    public void setConfirmed(int confirmed) { this.confirmed = confirmed; }

    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    public int getRecovered() { return recovered; }
    public void setRecovered(int recovered) { this.recovered = recovered; }

    public int getProvinceCode() {
        return ProvinceCode;
    }

    public void setProvinceCode(int ProvinceCode) {
        this.ProvinceCode = ProvinceCode;
    }

    @Override
    public String toString() {
        return String.format(
            "%-12s| %-8s| %-12s%n%-12d| %-8d| %-12d",
            "CONFIRMADO", "MUERTOS", "RECUPERADOS",
            confirmed, deaths, recovered
        );
    }

   
}
