package model;

import dto.RegionDTO;

public class Region {

    private String iso; // Código ISO de la región
    private String name; // Nombre de la región

    // Constructor vacío
    public Region() {
    }

    // Constructor con parámetros
    public Region(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    // Constructor que recibe un RegionDTO
    public Region(RegionDTO dto) {
        this.iso = dto.getIso();
        this.name = dto.getName();
    }

    // Getters y Setters
    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
