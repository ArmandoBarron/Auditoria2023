# Manual de usuario para la utilización de la aplicación de Verificacion

Este manual contiene los comandos requerios para realizar la ejecución de la aplicación llamada 2103INE.

## Acerca de este repositorio:

* *lib/* Directorio que contiene las bibliotecas adicionales para el proyecto
* *Llavero/* Directorio que contendrá la llave publica compartido por el proveedor.
* *2103INE.jar* Aplicación que realiza la operación de validación: 
  * *validar* Este proceso descarga las firmas de dos eventos almacenados en la base de datos y utilizando la llave publica (que se debe colocar en el directorio *Llavero/*) realiza la verificación de las firmas en los archivos realizando una comparación con los hash y las firmas.

### Requerimientos para la utilización de este repositorio

* *Java*:  Es necesario contar con la versión 8 de Java para realizar la ejecución.
* *Permisos de lectura y escritura*: El proveedor debe de contar con permisos de escritura y lectura del sistema de archivos dentro del equipo donde será ejecutada la aplicación *2103IETAM* así como sobre las carpetas que serán utilizadas en el *Inventario* y el *Llavero*.

## Ejecución de la aplicación

1. El ente auditor deberá ejecutar la aplicación para verificar las firmas generadas para los dos eventos seleccionados.

   Para verificar las firmas el ente auditor deberá utilizar el siguiente comando:

   ```bash
   java -jar 2103INE.jar validar Llavero/LlavePublica EventoInicial EventoPorComparar
   ```

   En donde:

   * *java -jar 2103INE.jar*  Es el comando requerido para ejecutar la aplicación 
   * *validar* Es el parámetro de entrada que indica el nombre del proceso que se ve a ejecutar. En este ejemplo representa al proceso de validación de firmas de dos eventos.
   * *Llavero/LlavePublica* Es el parámetro que indica la ruta del directorio de la llave publica compartida por el proveedor.
   * *EventoInicial* Es el valor que representa el nombre del evento en el que se generaron las firmas originales.
   * *EventoPorComparar* Es el valor que representa el nombre del evento que se desea comparar con las firmas originales.

   A continuación se listan los ejemplos de comandos recomendados para los tres simulacros y la Jornada Electoral:

   ```bash
   #Verificaciones del Simulacro 1
   #Firmas iniciales con firmas intermedias
   java -jar 2103INE.jar validar Llavero/LlavePublica S1-Inicio S1-Intermedio
   
   #Firmas iniciales con firmas finales
   java -jar 2103INE.jar validar Llavero/LlavePublica S1-Inicio S1-Final
   
   #Verificaciones del Simulacro 2
   #Firmas iniciales con firmas intermedias
   java -jar 2103INE.jar validar Llavero/LlavePublica S2-Inicio S2-Intermedio
   
   #Firmas iniciales con firmas finales
   java -jar 2103INE.jar validar Llavero/LlavePublica S2-Inicio S2-Final
   
   #Verificaciones del Simulacro 3
   #Firmas iniciales con firmas intermedias
   java -jar 2103INE.jar validar Llavero/LlavePublica S3-Inicio S3-Intermedio
   
   #Firmas iniciales con firmas finales
   java -jar 2103INE.jar validar Llavero/LlavePublica S3-Inicio S3-Final
   
   #Verificaciones de la Jornada electoral
   #Firmas iniciales con firmas intermedias
   java -jar 2103INE.jar validar Llavero/LlavePublica JE-Inicio JE-Intermedio
   
   #Firmas iniciales con firmas finales
   java -jar 2103INE.jar validar Llavero/LlavePublica JE-Inicio JE-Final
   ```

   Después de realizar la generación de las llaves y de las firmas de los archivos, el proveedor debe de proporcionar al ente auditor y al IETAM la llave publica y los JSON generados por cada firma. De esta forma se puede continuar con el proceso.

