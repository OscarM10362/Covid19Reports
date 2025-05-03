package model;

public class Report {

    private String id; // Identificador único del reporte
    private String provinceCode; // Código de la provincia
    private String regionIso; // Código ISO de la región
    private String date; // Fecha del reporte
    private int confirmed; // Casos confirmados
    private int deaths; // Muertes
    private int recovered; // Recuperados

    // Constructor vacío
    public Report() {
    }

    // Constructor con parámetros
    public Report(String id, String provinceCode, String regionIso, String date, int confirmed, int deaths, int recovered) {
        this.id = id;
        this.provinceCode = provinceCode;
        this.regionIso = regionIso;
        this.date = date;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getRegionIso() {
        return regionIso;
    }

    public void setRegionIso(String regionIso) {
        this.regionIso = regionIso;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", regionIso='" + regionIso + '\'' +
                ", date='" + date + '\'' +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                '}';
    }
}