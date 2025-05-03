package com.umg.covid19;

import repository.DatabaseHelper;
import scheduler.CovidScheduler;
import service.CovidDataService;



public class Main {
        
       public static void main(String[] args) {
        System.out.println("Iniciando aplicación COVID-19...");
        
       
        DatabaseHelper dbHelper = new DatabaseHelper();
        CovidDataService dataService = new CovidDataService();
        CovidScheduler scheduler = new CovidScheduler(dbHelper, dataService);
        
        // Configuración
        String isoCode = "USA"; // Código ISO del país 
        int delaySeconds = 15; // Retardo en segundos
        
        // Ejecuta después de delay
        new Thread(() -> {
            try {
                System.out.println("Esperando " + delaySeconds + " segundos...");
                Thread.sleep(delaySeconds * 1000);
                scheduler.executeMainLogicWithVerification(isoCode);
                
            } catch (InterruptedException e) {
                
                System.err.println("Error en el retardo: " + e.getMessage());
            }
        }).start();
    }
}

   
