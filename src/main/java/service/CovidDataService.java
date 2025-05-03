package service;

import dto.RegionDTO;
import dto.ProvinceDTO;
import dto.ReportDTO;
import model.Region;
import model.Province;
import model.Report;
import repository.DatabaseHelper;
import util.ApiClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CovidDataService {

    private final ApiClient apiClient = new ApiClient();
    private final DatabaseHelper databaseHelper = new DatabaseHelper();
    /**
     * Obtiene la lista de regiones desde la API.
     */
    public List<RegionDTO> fetchRegions() throws Exception {
        return apiClient.fetchRegions();
    }

    /**
     * Obtiene la lista de provincias para una región específica.
     */
    public List<ProvinceDTO> fetchProvinces(String iso) throws Exception {
        return apiClient.fetchProvinces(iso);
    }

    /**
     * Obtiene la lista de reportes para una provincia específica y fecha.
     */
    public List<ReportDTO> fetchReports(String provinceCode, String date) throws Exception {
        return apiClient.fetchReports(provinceCode, date);
    }
    
    
}