# Aplicaciones requeridas para la generación de firmas, carga y validación.

En este repositorio se encuentran las aplicaciones utilizadas para la generación de firmas, carga y validación.

## Acerca de este repositorio

* *Proveedor*/: Aplicación que será utilizada por el proveedor del servicio. Contiene las siguientes operaciones:
  * *generarLlaves* Este proceso crea y almacena las llaves publica y privada en un directorio definido por el usuario
  * *firmar* Lee el contenido del directorio asignado como inventario, realiza el proceso de firma utilizando la llave privada (almacenada en el directorio definido por el usuario y almacena el resultado de las firmas en un archivo con formato JSON en el directorio de la aplicación.
* *Auditor*/: Aplicación encargada de trasmitir el contenido del JSON al servidor web. El servidor es el responsable de insertar los valores en la base de datos para que puedan ser utilizados por la siguiente aplicación.
* *Verificacion*/: Aplicación que será utilizada por el ente auditor en las instalaciones del IETAM encargada de verificar las firmas de dos eventos utilizando la llave publica. Esta aplicación genera un archivo csv que es utilizado para generar la constancia de hechos. 

Dentro de cada carpeta vienen los manuales para la utilización de las aplicaciones.
