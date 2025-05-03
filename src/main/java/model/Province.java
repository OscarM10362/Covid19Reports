package model;

public class Province {

    private String code; // Código de la provincia
    private String name; // Nombre de la provincia
    private String regionIso; // Código ISO de la región a la que pertenece

    // Constructor vacío
    public Province() {
    }

    // Constructor con parámetros
    public Province(String code, String name, String regionIso) {
        this.code = code;
        this.name = name;
        this.regionIso = regionIso;
    }

    // Getters y Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionIso() {
        return regionIso;
    }

    public void setRegionIso(String regionIso) {
        this.regionIso = regionIso;
    }

    @Override
    public String toString() {
        return "Province{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", regionIso='" + regionIso + '\'' +
                '}';
    }
}