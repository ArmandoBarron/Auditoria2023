# commandos para 01_proveedor

## Generar llave:
```bash
   java -jar 2103Proveedor.jar generarLlaves Llavero/ Original
```
## Firmar:
```bash
   #prueba
   #Firma original
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada P1-Original CiudadVictoria
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada P1-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada P1-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada P1-Final CiudadVictoria

   #Simulacro 1
   #Firma original
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Original CiudadVictoria
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Final CiudadVictoria
   
   #Simulacro 2
      #Firma original
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Original CiudadVictoria
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Final CiudadVictoria
   
   #Simulacro 3
      #Firma original
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Original CiudadVictoria
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Final CiudadVictoria
   
   #Jornada Electoral
      #Firma original
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Original CiudadVictoria
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Final CiudadVictoria
```

# commandos para 02_Auditor

  ```bash
   #Prueba
   #Cargar JSON inicial
   java -jar 2103Auditor.jar P1-Original.json
   #Cargar JSON inicial
   java -jar 2103Auditor.jar P1-Inicio.json
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar P1-Intermedio.json
   #Cargar JSON final
   java -jar 2103Auditor.jar P1-Final.json

   #Simulacro 1
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S1-Original.json
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S1-Inicio.json
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar S1-Intermedio.json
   #Cargar JSON final
   java -jar 2103Auditor.jar S1-Final.json
   
   #Simulacro 2
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S2-Original.json
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S2-Inicio.json
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar S2-Intermedio.json
   #Cargar JSON final
   java -jar 2103Auditor.jar S2-Final.json
   
   #Simulacro 3
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S3-Original.json
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S3-Inicio.json
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar S3-Intermedio.json
   #Cargar JSON final
   java -jar 2103Auditor.jar S3-Final.json
   
   #Jornada Electoral
   #Cargar JSON inicial
   java -jar 2103Auditor.jar JE-Original.json
   #Cargar JSON inicial
   java -jar 2103Auditor.jar JE-Inicio.json
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar JE-Intermedio.json
   #Cargar JSON final
   java -jar 2103Auditor.jar JE-Final.json
   ```


   # commandos para 03_Auditor

  ```bash
   #Verificaciones de la prueba 1
    #Firmas originales con firmas iniciales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica P1-Original P1-Inicio
    #Firmas originales con firmas intermedias
    java -jar 2103IETAM.jar validar Llavero/LlavePublica P1-Original P1-Intermedio
    #Firmas originales con firmas finales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica P1-Original P1-Final

   #Verificaciones del Simulacro 1
    #Firmas originales con firmas iniciales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S1-Original S1-Inicio
    #Firmas originales con firmas intermedias
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S1-Original S1-Intermedio
    #Firmas originales con firmas finales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S1-Original S1-Final
    
   #Verificaciones del Simulacro 2
    #Firmas originales con firmas iniciales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S2-Original S2-Inicio
    #Firmas originales con firmas intermedias
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S2-Original S2-Intermedio
    #Firmas originales con firmas finales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S2-Original S2-Final
   
   #Verificaciones del Simulacro 3
    #Firmas originales con firmas iniciales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S3-Original S3-Inicio
    #Firmas originales con firmas intermedias
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S3-Original S3-Intermedio
    #Firmas originales con firmas finales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica S3-Original S3-Final
   
   #Verificaciones de la Jornada electoral
    #Firmas originales con firmas iniciales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica JE-Original JE-Inicio
    #Firmas originales con firmas intermedias
    java -jar 2103IETAM.jar validar Llavero/LlavePublica JE-Original JE-Intermedio
    #Firmas originales con firmas finales
    java -jar 2103IETAM.jar validar Llavero/LlavePublica JE-Original JE-Final


   ```



   # link para generar constancias
   
     http://auditoria.tamps.cinvestav.mx/Auditoria2022/generarConstancias/Hechos.php

    http://auditoria.tamps.cinvestav.mx/Auditoria2022/generarConstancias/Huellas.php