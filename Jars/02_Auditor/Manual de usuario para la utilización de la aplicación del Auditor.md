# Manual de usuario para la utilización de la aplicación del Auditor

Este manual contiene los comandos requerios para realizar la ejecución de la aplicación llamada 2103Auditor.

## Acerca de este repositorio:

* *lib/* Directorio que contiene las bibliotecas adicionales para el proyecto
* *2103Auditor.jar* Aplicación recibe la ruta del archivo JSON generado por el proveedor, realiza la lectura del archivo JSON, se conecta con el servidor web, transfiere el contenido del JSON para que sean insertados en la base de datos.

### Requerimientos para la utilización de este repositorio

* *Java*:  Es necesario contar con la versión 8 de Java para realizar la ejecución.
* *Permisos de lectura y escritura*: El proveedor debe de contar con permisos de escritura y lectura del sistema de archivos dentro del equipo donde será ejecutada la aplicación *2103Auditor*.

## Ejecución de la aplicación

1. El Auditor deberá ejecutar la aplicación transferir el JSON para que sea registrado en la base de datos.

   Para leer y trasmitir el archivo JSON el auditor deberá utilizar el siguiente comando:

   ```bash
   java -jar 2103Auditor.jar /home/user/auditoria/nombreArchivo.json
   ```

   En donde:

   * *java -jar 2103Auditor.jar*  Es el comando requerido para ejecutar la aplicación 
   * */home/user/auditoria/nombreArchivo.json* Es el parámetro que indica la ruta del directorio y nombre del archivo que será enviado al servidor web.

   A continuación se listan los ejemplos de comandos recomendados para cargar los archivos JSON de los tres simulacros y la Jornada Electoral:

   ```bash
   #Simulacro 1
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S1-Inicio
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar S1-Intermedio
   #Cargar JSON final
   java -jar 2103Auditor.jar S1-Final 
   
   #Simulacro 2
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S2-Inicio
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar S2-Intermedio 
   #Cargar JSON final
   java -jar 2103Auditor.jar S2-Final 
   
   #Simulacro 3
   #Cargar JSON inicial
   java -jar 2103Auditor.jar S3-Inicio
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar S3-Intermedio
   #Cargar JSON final
   java -jar 2103Auditor.jar S3-Final
   
   #Jornada Electoral
   #Cargar JSON inicial
   java -jar 2103Auditor.jar JE-Inicio 
   #Cargar JSON intermedio
   java -jar 2103Auditor.jar JE-Intermedio
   #Cargar JSON final
   java -jar 2103Auditor.jar JE-Final
   ```

   Después de realizar la carga de las firmas al servidor web el IETAM deberá realizar la validación de las firmas de cada evento con sus respectivas etapas.

