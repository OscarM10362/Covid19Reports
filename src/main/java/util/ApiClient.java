package util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RegionDTO;
import dto.ProvinceDTO;
import dto.ReportDTO;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ApiClient {

    // Propiedades de la API cargadas desde application.properties
    private static final String RAPID_API_KEY;
    private static final String RAPID_API_HOST;

    // Bloque estático para cargar las propiedades al inicializar la clase
    static {
        Properties properties = new Properties();
        try (InputStream input = ApiClient.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar el archivo 'application.properties'");
            }
            properties.load(input);

            // Leer las propiedades de la API
            RAPID_API_KEY = properties.getProperty("rapidapi.key");
            RAPID_API_HOST = properties.getProperty("rapidapi.host");

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar las propiedades de la API", e);
        }
    }

    /**
     * Obtiene una lista de regiones desde la API.
     */

    public List<RegionDTO> fetchRegions() throws Exception {
        String url = "https://" + RAPID_API_HOST + "/regions";
        return executeGetRequest(url, RegionDTO.class);
    }
    /**
     * Obtiene una lista de provincias para una región específica (ISO).
     */
    public List<ProvinceDTO> fetchProvinces(String iso) throws Exception {
        String url = "https://" + RAPID_API_HOST + "/provinces?iso=" + iso;
        return executeGetRequest(url, ProvinceDTO.class);
    }

    /**
     * Obtiene una lista de reportes para una región específica (ISO) y fecha.
     */
    public List<ReportDTO> fetchReports(String provinceCode, String date) throws Exception {
        String url = "https://" + RAPID_API_HOST + "/reports?iso=" + provinceCode + "&date=2020-04-16";
        return executeGetRequest(url, ReportDTO.class);
    }

    /**
     * Método auxiliar para realizar una solicitud GET y mapear la respuesta JSON a una lista de objetos DTO.
     */
    private <T> List<T> executeGetRequest(String url, Class<T> clazz) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            // Agregar encabezados requeridos por RapidAPI
            request.addHeader("X-RapidAPI-Key", RAPID_API_KEY);
            request.addHeader("X-RapidAPI-Host", RAPID_API_HOST);

            // Ejecutar la solicitud HTTP
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Error al llamar a la API: " + response.getStatusLine().getReasonPhrase());
                }

                // Leer la respuesta como una cadena JSON
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // Crear un ObjectMapper para deserializar el JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Crear un tipo parametrizado para deserializar el objeto contenedor
                JavaType responseType = objectMapper.getTypeFactory()
                        .constructParametricType(ApiResponse.class, clazz);

                // Deserializar el JSON en un objeto ApiResponse<T>
                ApiResponse<T> apiResponse = objectMapper.readValue(jsonResponse, responseType);

                // Devolver la lista contenida en el campo "data"
                return apiResponse.getData();
            }
        }
    }
}