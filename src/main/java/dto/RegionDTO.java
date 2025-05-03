package dto;

public class RegionDTO {
    private String iso;
    private String name;

    // Constructor vacío
    public RegionDTO() {}

    // Constructor con parámetros
    public RegionDTO(String iso, String name) {
        this.iso = iso;
        this.name = name;
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

    // Método para representación formateada
    public String toFormattedString(int index) {
        return String.format("%-8d | %-6s | %s", index, iso, name);
    }

    @Override
    public String toString() {
        return "RegionDTO{" +
                "iso='" + iso + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}