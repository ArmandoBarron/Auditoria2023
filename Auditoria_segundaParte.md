## Auditoria IETAM 2021 (Segunda parte)

Durante la segunda etapa de cada simulacro y de forma conjunta con la generación de las huellas criptográficas así como de la constancia de hechos.

A continuación se listan las actividades requeridas para conseguir completar la auditoria.

## Descargar la base de datos

Se debe descargar la base de datos cada 15 minutos. Es necesario que se este monitoreando el enlace para corregir cambios en el formato del nombre.

En la carpeta *ScriptDescargaDB* podrán encontrar el script llamado **ejecutable.sh** el cual fue usado para descargar la base de datos, se puede hacer de forma manual pero se recomienda usar chron para ejecutarla cada cierto tiempo. Es necesario actualizar el enlace de la url y el formato de nombre.

Ejemplos de archivos descargados durante la jornada pasada (incluidos en la carpeta *ScriptDescargaDB*):

* 20190512_1545_PREP.zip
* 20190512_1600_PREP.zip
* 20190512_1615_PREP.zip

## Insertar la última versión de la base de datos

Al finalizar la jornada electoral se genera la versión final de la base de datos, es necesario descargarla (por ejemplo el archivo *20190512_1615_PREP.zip* que esta en *ScriptDescargaDB*), descomprimirla e insertarla en la base de datos. La base de datos cuenta con la información del simulacro 3 y de la jornada electoral (buscar en Adaptivez).

Hacer un script que inserte estos registros en la base de datos, pueden ser insertados en la base de datos desde workbench.

## Manejo y conteo de las actas

Durante esta etapa se debe de recolectar las actas cargadas en el sistema, en las auditorias previas se tenían dos actas por cada casilla.

1. Descargar las actas (Se van cargando a lo largo de la jornada y no hay algún orden). Al final de la Jornada electoral se tiene un numero de actas registradas, se espera que ese número coincide con el número de actas descargadas. Usar los scripts de ejemplo que estan en *web/descargarActas/*
2. Calcular el resumen HASH de las actas descargadas.
   * Es necesario que el proveedor proporcione el algoritmo de HASH utilizado.
   * Hacer un script de php (u otro lenguaje) que calcule el resumen HASH de las actas descargadas.
   * Insertar los resúmenes en su registro correspondiente de la base de datos.
3. Comparar los resúmenes HASH de la base de datos con los obtenidos (hay veces que no coinciden) 
   * Ver el ejemplo del archivo web/descargarActas/validacion.php
4. Generar los documentos de verificación de las actas
   * Listado de actas descargadas y no descargadas: Contiene las actas que fue posible descargar las dos imágenes.
   * Análisis de las actas obtenidas: Actas en la base de datos con las esperadas, Actas contabilizadas y actas no contabilizadas así como las que tiene imágenes y cuales no.
     * Actualizar los scripts de la carpeta analizar actas
     * Ejecutar el comando *composer require mpdf/mpdf* en la carpeta web/analizarActas/

## Descargar el registro (Log) del sistema del proveedor

## Analizar el comportamiento de las actividades del Log

Utilizar el script *obtenerLog/consumir_web_service.php*. Es necesario actualizar la url de donde será descargado el log, este url es proporcionado por el proveedor.

Cuando este definido, dentro del Log existen registros de las acciones que se realizaron durante la jornada.

Es necesario que se actualicen las acciones así como la generación de las gráficas.

Un ejemplo puede encontrarse en la carpeta *web/analisisJE/*

* http://localhost/2021Auditoria/AnalisisJE/MostrarGraficaActasPorActividad.php
  * Genera un csv y muestra las actividades
* http://localhost/2021Auditoria/AnalisisJE/actasportiempos1.php
  * Puede tardar un tiempo