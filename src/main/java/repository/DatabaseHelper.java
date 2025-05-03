package repository;



import dto.ReportDTO;
import model.Region;
import model.Province;
import model.Report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseHelper {

    // Propiedades de la base de datos cargadas desde application.properties
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String EXECUTION_TABLE = "executed_reports";
    private static String covidReportDate;

    
       static {
        loadProperties();
    }
    
 
     private static void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DatabaseHelper.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input != null) {
                props.load(input);
                covidReportDate = props.getProperty("covid.report.date");
                System.out.println("Fecha configurada: " + covidReportDate);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar propiedades: " + e.getMessage());
            covidReportDate = java.time.LocalDate.now().toString(); // Fecha actual por defecto
        }
    }
     
      public static String getCovidReportDate() {
        return covidReportDate;
    }
      
      
    // Bloque estático para cargar las propiedades al inicializar la clase
    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseHelper.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar el archivo 'application.properties'");
            }
            properties.load(input);

            // Leer las propiedades de la base de datos
            DB_URL = properties.getProperty("spring.datasource.url");
            DB_USER = properties.getProperty("spring.datasource.username");
            DB_PASSWORD = properties.getProperty("spring.datasource.password");

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar las propiedades de la base de datos", e);
        }
    }

    /**
     * Guarda una región en la base de datos.
     */
    public void saveRegion(String iso, String name) {
        String checkSql = "SELECT COUNT(*) FROM regions WHERE iso = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setString(1, iso);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);  

            if (count > 0) { 
            } else {
                // Si no existe, proceder con la inserción
                String sql = "INSERT INTO regions (iso, name) VALUES (?, ?)";
                executeUpdate(sql, iso, name);
            }

        } catch (Exception e) {
            //System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            //e.printStackTrace();
        }
    }
    
  

 
    public void saveProvince(String iso, String name, String province) {
        String checkSql = "SELECT COUNT(*) FROM provinces WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
             
            checkStatement.setString(1, name);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
              
            } else {
                String sql = "INSERT INTO provinces (iso, name, province) VALUES (?, ?, ?)";
                executeUpdate(sql, iso, province, province);
                
            }

        } catch (Exception e) {
            //System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            //e.printStackTrace();
        }
    }
    
    public void saveReport(ReportDTO report) {
    String checkSql = "SELECT COUNT(*) FROM reports WHERE province_code = ? AND report_date = ?";
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
        
        // Verificar existencia (usando INT para province_code)
        checkStatement.setInt(1, report.getProvinceCode());
        checkStatement.setString(2, report.getDate());
        
        ResultSet resultSet = checkStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        if (count == 0) {
            String insertSql = "INSERT INTO reports (province_code, report_date, confirmed_cases, deaths, recovered) " +
                             "VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setInt(1, report.getProvinceCode());  // Ahora es setInt
                insertStatement.setString(2, report.getDate());
               
                insertStatement.setInt(4, report.getDeaths());
                insertStatement.setInt(5, report.getRecovered());
                
                int rowsInserted = insertStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("✅ Reporte guardado para provincia " + report.getProvinceCode() + 
                                    " en fecha " + report.getDate());
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al guardar reporte: " + e.getMessage());
    }
}
    
    

    /**
     * Método auxiliar para ejecutar consultas SQL de tipo INSERT/UPDATE.
     */
    private void executeUpdate(String sql, Object... params) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
            	  //System.out.println("Consulta ejecutada correctamente.");
            } else {
            	 //System.out.println("No se afectaron filas con la consulta.");
            }
        } catch (Exception e) {
        	// System.err.println("❌ Error al ejecutar el SQL: " + e.getMessage());
            //.printStackTrace(); 
        }
    }
           
    //Marca un reporte como ejecutado en la tabla de control
    public void markAsExecuted(String tableName, String date, String isoCode) {
    String sql = "INSERT INTO " + tableName + " (report_date, iso_code, execution_time) VALUES (?, ?, CURRENT_TIMESTAMP)";
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(sql)) {
        
        statement.setString(1, date);
        statement.setString(2, isoCode);
        
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("✅ Reporte marcado como ejecutado para " + isoCode + " en fecha " + date);
        } else {
            System.out.println("⚠️ No se pudo marcar el reporte como ejecutado");
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al marcar reporte como ejecutado: " + e.getMessage());
    }
}
    public boolean checkExecution(String tableName, String date, String isoCode) {
    String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE report_date = ? AND iso_code = ?";
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(sql)) {
        
        statement.setString(1, date);
        statement.setString(2, isoCode);
        
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al verificar ejecución: " + e.getMessage());
    }
    return false;
}
    
     public  List<ReportDTO> consultarReportesEnDB(String iso, String fecha) {
    List<ReportDTO> reportes = new ArrayList<>();
    String sql = "SELECT r.*, p.name as province_name FROM reports r " +
                 "JOIN provinces p ON r.province_code = p.code " +
                 "WHERE p.iso = ? AND r.report_date = ?";
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        
        stmt.setString(1, iso);
        stmt.setString(2, fecha);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ReportDTO reporte = new ReportDTO();
                reporte.setProvinceCode(rs.getInt("province_code"));
                reporte.setDate(rs.getString("report_date"));
                reporte.getConfirmed(rs.getInt("confirmed_cases"));
                reporte.setDeaths(rs.getInt("deaths"));
                reporte.setRecovered(rs.getInt("recovered"));
                reporte.getNameProvince(rs.getString("province_name"));
                
                reportes.add(reporte);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al consultar reportes: " + e.getMessage());
    }
    
    return reportes;
    
   
}
}