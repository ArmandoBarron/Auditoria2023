# Manual de usuario para la utilización de la aplicación de huellas criptograficas

Este manual contiene los comandos requerios para realizar la ejecución de la aplicación llamada 2103Proveedor.

## Acerca de este repositorio:

* *Inventario/* Directorio que puede usarse para almacenar las aplicaciones, bases de datos, scripts y archivos que el Proveedor debe de firmar.
* *lib/* Directorio que contiene las bibliotecas adicionales para el proyecto
* *Llavero/* Directorio que contendrá las llaves publica y privadas del Proveedor.
* *2103Proveedor.jar* Aplicación que realiza las operaciones: 
  * *generarLlaves* Este proceso crea y almacena las llaves publica y privada en el directorio *Llavero/*,
  * *firmar* Lee el contenido del directorio llamado *InventarioI*, realiza el proceso de firma utilizando la llave privada (almacenada en el directorio *Llavero/* y almacena el resultado de las firmas en un archivo con formato JSON.

### Requerimientos para la utilización de este repositorio

* *Java*:  Es necesario contar con la versión 8 de Java para realizar la ejecución.
* *Permisos de lectura y escritura*: El Proveedor debe de contar con permisos de escritura y lectura del sistema de archivos dentro del equipo donde será ejecutada la aplicación *2103Proveedor* así como sobre las carpetas que serán utilizadas en el *Inventario* y el *Llavero*.
* *Requerimientos minimos*: 4 cores, 8 Gb RAM. 

### HASH en de carpetas
Aplicación del Proveedor modificada para generar un archivo con los sha3-256 de todos los archivos dentro del inventario incluyendo las carpetas.

*Nota: El hash de una carpeta se obtiene por el concatenamiento de su contendio, en este sentido EL ORDEN DENTRO DE LOS ARCHIVOS IMPORTA. El programa lee los archivos en orden alfabetico, por lo cual es aconsejable mantener un MISMO FORMATO DE NOMBRES*

El archivo csv contiene los siguientes datos:

* *NombreCarpeta* Describe la ruta completa de la carpeta del inventario que puede incluir las subcarpetas en el inventario
* *NombreArchivo* Es el nombre del archivo que se esta procesando
* *RequiereSegmentacion* Indica si el archivo cumple las condiciones de tamaño para ser segmentado y así mejorar el rendimiento de la aplicación. Los valores posibles son: si (si se requiere segmentación), no (cuando no requiere segmentación) y hashCarpeta (indica que es el hash completo de una carpeta y que dentro puede o no haber segmentos incluidos)
* *idTrabajador* Es un identificador para el hilo que realizó el calculo del hash. sus valores van de 0 a 3. En donde 0 representa al hilo principal y los demás números son hilos generados para producir concurrencia.
*  *SHA3-256* Es el valor del hash calculado por la aplicación.


## Ejecución de la aplicación

1. El Proveedor deberá ejecutar la aplicación para generar las llaves publica y privada.

   Para generar las llaves el Proveedor deberá utilizar el siguiente comando:

   ```bash
   java -jar 2103Proveedor.jar generarLlaves Llavero/ Original
   ```

   En donde:

   * *java -jar 2103Proveedor.jar Es el comando requerido para ejecutar la aplicación 
   * *generarLlaves* Es el parámetro de entrada que indica el nombre del proceso que se ve a ejecutar. En este ejemplo representa al proceso de generación de llaves publica y privada.
   * *Llavero/* Es el parámetro que indica la ruta del directorio que será utilizado para almacenar las llaves generadas por el proceso *generarLlaves*.
   * *Original* Es un valor que representa el evento en el que se están generando las llaves.

   **Nota**: El proceso *generarLlaves* detecta si en la ruta *Llavero* ya existen las llaves. Si es el caso, se muestra un mensaje que ya existen las llaves y no se generan, de lo contrario genera las llaves normalmente. Esto ocurre porque las llaves se deben de generar una sola vez para todos los procesos.

2. El Proveedor deberá de colocar todas las aplicaciones, bases de datos, scripts y archivos que debe firmar

3. El Proveedor deberá ejecutar el proceso encargado de realizar las firmas criptográficas iniciales. 

   Para ejecutar el proceso de firma el Proveedor deberá utilizar el siguiente comando

   ```bash
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Inicio CiudadVictoria
   ```

   En donde:

   * *java -jar 2103Proveedor.jar* Es el comando requerido para ejecutar la aplicación.
   * *firmarArchivos* Es el parámetro de entrada que indica el nombre del proceso que se ve a ejecutar. En este ejemplo representa al proceso para firmar los archivos.
   * *Inventario/* Es la ruta del directorio que contiene las aplicaciones, bases de datos, scripts y archivos que el Proveedor debe de firmar.
   * *Llavero/LlavePrivada* Es la ruta donde se encuentra la llave privada del Proveedor.
   * *S1-Inicio* Es el nombre que se le dará al evento de firmas iniciales
   * *CiudadVictoria* Es el nombre de la ciudad en la que fue realizada la generación de las firmas

   **Nota**: El proceso de firma debe de realizarse varias veces durante los simulacros: al inicio, a la mitad y al final. Es importante verificar que el Proveedor recolecte las aplicaciones justo antes de cada firma y revisar los metadatos para ver que se recolectaron a esa hora. 

   A continuación se listan los ejemplos de comandos recomendados para los tres simulacros y la Jornada Electoral:

   ```bash
   #Simulacro 1
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S1-Final CiudadVictoria
   
   #Simulacro 2
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S2-Final CiudadVictoria
   
   #Simulacro 3
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada S3-Final CiudadVictoria
   
   #Jornada Electoral
   #Firma inicial
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Inicio CiudadVictoria
   #Firma intermedia
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Intermedio CiudadVictoria
   #Firma final
   java -jar 2103Proveedor.jar firmarArchivos Inventario/ Llavero/LlavePrivada JE-Final CiudadVictoria
   ```

   Después de realizar la generación de las llaves y de las firmas de los archivos, el Proveedor debe de proporcionar al ente auditor y al INE la llave publica y los JSON generados por cada firma. De esta forma se puede continuar con el proceso.


## Validación de los hash con una herramienta

Si desean realizar una comparación de los hash SHA3-256 obtenidos por la aplicación pueden usar los siguientes enlaces:

* Para analizar el hash SHA3-256 de un texto

  * https://emn178.github.io/online-tools/sha3_256.html

  * Puede ser utilizado para obtener el hash de la concatenación de los 4 hash de un archivo segmentado y así obtener el hash producido por los trabajadores 0 a 3. Es importante copiar el texto sin espacios para que este coincida por ejemplo

    * El texto: 

      ```
      0963d1b240068eb6a7c1529c5370f03aed92db1601d488eb8eae03ce8d51c56688329974ea4c9833fc5df35daafb904940afd53962f5c1afd0547d9efd514ae7c7ceb60e1f227f4feafe199a49e4ccc3cf489f78c361bc31ea352450acf94f418617444d7f83d77421f79d613b861f5274a23288dcc3df26cb324ac98b7cb384
      ```

      que esta compuesto de los 4 hash

      ```
      0963d1b240068eb6a7c1529c5370f03aed92db1601d488eb8eae03ce8d51c566
      88329974ea4c9833fc5df35daafb904940afd53962f5c1afd0547d9efd514ae7
      c7ceb60e1f227f4feafe199a49e4ccc3cf489f78c361bc31ea352450acf94f41
      8617444d7f83d77421f79d613b861f5274a23288dcc3df26cb324ac98b7cb384
      ```

    * Da como resultado:

      ```
      5b456537c239e5f5529a46e624adc46cc9f17fd455bba1b8e5eef602b62ec2b7
      ```

      

* Para obtener el hash SHA3-256 de un archivo

  * https://emn178.github.io/online-tools/sha3_256_checksum.html