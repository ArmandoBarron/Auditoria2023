
# Original

- Firma (IETAM)  
    ```bash
    java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada Original CiudadVictoria
    ```
- Carga (Auditor)  
    ```bash
    java -jar 2103Auditor.jar Original.json
    ```
- Generacion de constancia (Auditor)  
    http://auditoria.tamps.cinvestav.mx/Auditoria2022/generarConstancias/Huellas.php


# inicio

- Firma (IETAM)  
    ```bash
    java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Inicio CiudadVictoria
    ```
- Carga (Auditor)  
    ```bash
    java -jar 2103Auditor.jar S3-inicio.json
    ```
- Validacion (Auditor)
    ```bash
    java -jar 2103IETAM.jar validar Llavero/LlavePublica Original S3-inicio
    ```
- Generacion de constancia (Auditor)  
       http://auditoria.tamps.cinvestav.mx/Auditoria2022/generarConstancias/Hechos.php


# Intermedio

- Firma (IETAM)  
    ```bash
    java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Intermedio CiudadVictoria
    ```
- Carga (Auditor)  
    ```bash
    java -jar 2103Auditor.jar S3-intermedio.json
    ```
- Validacion (Auditor)
    ```bash
    java -jar 2103IETAM.jar validar Llavero/LlavePublica Original S3-intermedio
    ```
- Generacion de constancia (Auditor)  
       http://auditoria.tamps.cinvestav.mx/Auditoria2022/generarConstancias/Hechos.php


# Final

- Firma (IETAM)  
    ```bash
    java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Final CiudadVictoria
    ```
- Carga (Auditor)  
    ```bash
    java -jar 2103Auditor.jar S3-final.json
    ```
- Validacion (Auditor)
    ```bash
    java -jar 2103IETAM.jar validar Llavero/LlavePublica Original S3-final
    ```
- Generacion de constancia (Auditor)  
       http://auditoria.tamps.cinvestav.mx/Auditoria2022/generarConstancias/Hechos.php
    




    #Firma inicial
    
    #Firma intermedia
    #Firma final


# inicio

    



# intermedio
    


