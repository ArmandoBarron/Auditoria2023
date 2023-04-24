<?php
    ini_set("display_errors", "1");
    ob_start();
	date_default_timezone_set("America/Mexico_City");
?>

<!DOCTYPE html>
<html>
 
    <head>       
        <link href="template.css" rel="stylesheet" type="text/css" />
        <!--<link href="../dependencias/estilo.css" rel="stylesheet" type="text/css" />  -->

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    </head>    
        <page backtop="28mm" backbottom="10mm" backleft="15mm" backright="10mm">
    <body>
    <img src="../dependencias/LogoCin.png" width="200" >
        
       

<?php

    $fecha = date('Y-m-j H:i:s'); 
    $nuevafecha = strtotime ( '-7 hour' , strtotime ( $fecha ) ) ;
    $nuevafecha = date ( 'j/m/Y  H:i:s' , $nuevafecha );
    $dia = date("d"); 
    $mes = date("m"); 
    $anio = date("Y"); 
    $hora = date("H"); 
    $min = date("i"); 
    $m="";
    switch ($mes) {
        case 1:$m="Enero"; break;
        case 2:$m="Febrero"; break;
        case 3:$m="Marzo"; break;
        case 4:$m="Abril"; break;
        case 5:$m="Mayo"; break;
        case 6:$m="Junio"; break;
        case 7:$m="Julio"; break;
        case 8:$m="Agosto"; break;
        case 9:$m="Septiembre"; break;
        case 10:$m="Octubre"; break;
        case 11:$m="Noviembre"; break;
        case 12:$m="Diciembre"; break;
    }


    $representanteINE = "Ing. Yuri Adrián González Robles";
    $cargoINE = " Director de Seguridad y Control Informático Unidad Técnica de Servicios de Informática del Instituto Nacional Electoral";

    $representanteCinvestav = "Dr. Arturo Díaz Pérez";
    $cargoCinvestav = "Investigador Cinvestav 3C del Centro de Investigación y de Estudios Avanzados del Instituto Politécnico Nacional";


//casos especiales
$lista_BD = array("Base_de_datos.bak", "Base_de_datos.sql", "BaseDeDatos.bak", "BaseDeDatos.sql","Backup.bak","Backup.sql","basededatos.bak","script.sql", "script.bak");
$lista_posibles_cambios = array(" ContainerFile", " Database");
$lista_archivos_cambiantes = array("hashes_inventario.txt");
$show_details=False;
//$lista_BD_condatos = array("Base_de_datos_.bak", "Base_de_datos.sql", "BaseDeDatos.bak", "BaseDeDatos.sql","Backup.bak","Backup.sql","basededatos.bak","basededatos.txt");

?>




    <p align="right"> <?php echo "Toluca, Estado de México, $dia de $m de $anio"; ?></p>
    <h2 align="center">Constancia de hechos de la validación de los programas y de la bases de datos del SIVEI.</h2>

    <?php
        echo "
        <p align='justify'>Siendo las $hora horas con $min minutos del día $dia del mes de $m del año $anio y, en cumplimiento 
        al numeral 38, del Anexo 21.2 \"LINEAMIENTOS DEL VOTO ELECTRÓNICO POR INTERNET PARA LAS MEXICANAS Y LOS MEXICANOS RESIDENTES EN EL EXTRANJERO\", el cual 
        menciona que se deberá ejecutar el procedimiento de validación de los componentes del SIVEI, el cual consiste en generar los códigos de integridad de los componentes, con la finalidad de comprobar que estos se mantuvieron íntegros durante la operación del SIVEI en todas sus etapas; así como en el marco del convenio INE/DJ/5/2023 celebrado entre el Instituto Nacional Electoral (INE) y el Centro de Investigación y Estudios Avanzados del Instituto Politécnico Nacional (CINVESTAV); se procedió a realizar la validación de los módulos funcionales y bases de datos del SIVEI.</p>

        <p align='justify'>Dicha validación consistió en comparar las huellas criptográficas obtenidas a partir de la versión auditada del sistema respecto a las huellas criptográficas del mismo, minutos antes de iniciar la Jornada Electoral.</p>
        
        <p align='justify'>Para esta validación se contó con la presencia en su calidad de titular de la Dirección de Seguridad y Control Informático de la Unidad Técnica de Servicios de Informática el Ing. Yuri Adrián González Robles por parte del INE; por parte del CINVESTAV en su calidad de Ente Auditor el Investigador Titular Dr. Arturo Díaz Pérez.
        </p>";
    ?>
    
        <p align='justify'>A continuación, se describe brevemente cada uno de los archivos sujetos a validacion.</p>
    
        <table  autosize="2.4" border="1">
            <tr>
                <th style="width:34.3%;height:auto" align="center" >Nombre</th>
                <th style="width:34.3%;height:auto" align="center" >Descripción</th>
            </tr>
            <tr>
                <td style="width:34.3%;height:auto" >hashes.sh</td>
                <td style="width:34.3%;height:auto">Script de generacion de hash de los componentes que se encuentran en la infraestructura.</td>
            </tr>
            <tr>
                <td style="width:34.3%;height:auto" >hashes_inventario.txt</td>
                <td style="width:34.3%;height:auto">Lista de hashes generada por la aplicacion hashes.sh.</td>
            </tr>
        </table>
        
        <?php
        /* Se Genera la tabla*/
        if (($fichero = fopen($_FILES['archivo']['tmp_name'], "r")) !== FALSE) {
            echo "<p align='justify'>Los resultados de la comprobacion de las huellas criptograficas se muestran en la siguiente tabla, acompañados del nombre del archivo, 
            la huella criptográfica original (SHA3-256 inicial), 
            la huella criptográfica minutos antes de iniciar la jornada electoral (SHA3-256 inicial) y el resultado de la comparación, siendo estos clasificados como <b> Correctos </b> 
            cuando las huellas criptograficas son iguales, <span style='color:#F37D1B';> Con cambios </span> cuando las huellas criptograficas son distintas en un componente considerado como dinámico 
            (e.g. Base de datos), y <span style='color:#FF0000';> No válidado </span>
            cuando las huellas criptograficas son distintas y cuyo componente no se espera pueda presentar cambios.</p><br>";

            $i=1;
            $incorrectos=0;
            $total=0;

            while (($datos = fgetcsv($fichero,1000,"," )) !== FALSE) {
                //Se ignora el encabezado
                
                if($i!=1 & count($datos)<=9){

                    $path_file = explode("/", $datos[0]);
                    if (count($path_file)>4){
                        $namefile = $path_file[0]."/".$path_file[1]."/../".array_slice($path_file, -2, 1)[0]."/".array_slice($path_file, -1, 1)[0];
                    }
                    else{
                        $namefile = $datos[0];
                    }

                    echo "<table autosize='1' border='1'>
                        <tr>
                            <th style='width:30%;height:auto;' align='center' >Nombre del Archivo: $namefile </th>
                            <th style='width:70%;height:auto;' align='center' >Evento: Proceso Electoral 2023 Fecha validación $datos[8]</th>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>SHA3-256 Inicial</th>
                            <td style='width:70%;height:auto' align='left'>$datos[2]</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>SHA3-256 del evento</th>
                            <td style='width:70%;height:auto' align='left'>$datos[5]</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>Estado</th>
                            <td style='width:70%;height:auto' align='left'>";

                            if(trim($datos[7])=="SI"){
                                echo "Correcto";
                            }
                            elseif(trim($datos[7])==trim("NO")){
                                if (in_array($datos[0],$lista_archivos_cambiantes)){
                                    echo "<p style='color:#F37D1B';> Con cambios </p>";   
                                    $show_details=True;
                                }
                                else{
                                    $incorrectos= $incorrectos+1;
                                    echo "<p style='color:#FF0000';> No válidado </p>";   
                                }
                            }
                            
                    echo        "</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>Observaciones</th>
                            <td style='width:70%;height:auto' align='left'>";
                         
                            if (trim($datos[7])=="SI"){

                                if (strtoupper($datos[0])=="LOG.TXT") {
                                    echo "Se considera correcto porque las huellas criptográficas evaluadas para este archivo son distintas. El contenido de este archivo cambia cada que el proveedor realiza la recolección del inventario, por tal motivo deben ser distintas las huellas criptográficas.";
                                } else {
                                    echo "Se considera correcto porque las huellas criptográficas evaluadas para este archivo son iguales. Las aplicaciones no deben modificarse, por lo tanto se espera que las huellas criptográficas sean iguales.";
                                }
                            } else {

                                if (str_contains($datos[0], 'Line') ) {
                                        echo "La linea del archivo de inventario de hashes original es diferente a la del archivo a comparar. Se espera que los archivos sean exactamente iguales.";
                                }
                                elseif (trim($datos[2]) == "N/A" || trim($datos[5]) == "N/A" ){
                                        echo "Una de las huellas criptográficas no fue obtenida.";
                                } 
                                else{
                                    echo "Las huellas criptográficas son distintas. Se esperaba que fueran las mismas para este archivo. Las aplicaciones no deben modificarse, por lo tanto se espera que las huellas criptográficas sean iguales.";
                                }
                                
                                }

                            
                            echo "</td>
                        </tr>
                    </table>";
                    
                    $total= $total+1;
                }

                 
                $i=$i+1;                 

                
                    
            }

            if($incorrectos>0){
                    echo "<p align='justify'>Resumen de la validación:Se detectaron ".$incorrectos."  archivos incorrectos 
                    de un total de ".$total." Archivos. Para más detalles revisar el motivo de cada archivo con la validación 
                    'No validado' mismos que están resaltados en color rojo. Los detalles se muestran en el documento anexo.</p><br>";
            }
        } 
        ?>

        <p align='justify'>Firman la presente constancia los representantes de las entidades que intervienen, en su calidad de titular de la Dirección de Seguridad y Control Informático de la Unidad Técnica de Servicios de Informática el Ing. Yuri Adrián González Robles por parte del INE; por parte del CINVESTAV en su calidad de Ente Auditor el Investigador Titular Dr. Arturo Díaz Pérez
    </p>

<br>
<br>
<br>

    <table  autosize="2.4">
        <tr>
            <td style="width:50%;height:auto" align="center">_________________________________</td>
            <td style="width:50%;height:auto" align="center">_________________________________</td>
        </tr>
        <tr>
            <td style="width:50%;height:auto;font-size: 11px;" align="center"> <?php echo "$representanteINE"; ?> </td>
            <td style="width:50%;height:auto;font-size: 11px;vertical-align: top;" align="center"> <?php echo "$representanteCinvestav"; ?> </td>
        </tr>
        <tr>
            <td style="width:50%;height:auto;font-size: 11px;vertical-align: top;" align="center"> <?php echo "$cargoINE"; ?> </td>
            <td style="width:50%;height:auto;font-size: 11px;vertical-align: top;" align="center"> <?php echo "$cargoCinvestav"; ?> </td>
        </tr>
    </table>

    <div style='page-break-after:always'></div>


        <?php
        $html_table = "";
        if ($show_details){
            $incorrectos=0;
            $contenedores=0;
            $bases_de_datos=0;
            $i=1;
            $total= 0;
            $total_archivos=0;
            $porcentaje_Archivos_faltantes=0;
            $conteos_erroneos=0;
            $fichero = fopen($_FILES['archivo']['tmp_name'], "r");
            $index=1;
            while (($datos = fgetcsv($fichero,5000,"," )) !== FALSE) {

                if($i!=1 & count($datos)>=10){//Se ignora el encabezado
                    $path_file = explode("/", $datos[0]);
                    if (count($path_file)>4){
                        $namefile = $path_file[0]."/".$path_file[1]."/../".array_slice($path_file, -2, 1)[0]."/".array_slice($path_file, -1, 1)[0];
                    }
                    else{
                        $namefile = $datos[0];
                    }

                    if ($datos[13]!=""){
                        $total_archivos = $datos[13];
                    }

                    $html_table = $html_table. "<table autosize='1' border='1'>
                        <tr>
                            <th style='width:30%;height:auto;' align='center' >".$index.".- Nombre del Archivo: </th>
                            <th style='width:70%;height:auto;' align='center' > $namefile</th>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>SHA3-256 Inicial</th>
                            <td style='width:70%;height:auto' align='left'>$datos[2]</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>SHA3-256 del evento</th>
                            <td style='width:70%;height:auto' align='left'>$datos[5]</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>Estado</th>
                            <td style='width:70%;height:auto' align='left'>";

                            if(trim($datos[7])=="SI"){
                                $html_table = $html_table. "Correcto";
                            }
                            elseif(trim($datos[7])==trim("NO")){
                                if (in_array($datos[9],$lista_posibles_cambios)){
                                    $html_table = $html_table. "<p style='color:#F37D1B';> Con cambios </p>";   
                                    if($datos[9]==" Database"){
                                        $bases_de_datos = $bases_de_datos+1;
                                    }
                                    else{
                                        $contenedores=$contenedores+1;
                                    }
                                }
                                else{
                                    if (str_contains($datos[10], 'conteo')){
                                        $html_table = $html_table. "<p style='color:#F37D1B';> Con cambios </p>"; 
                                        $conteos_erroneos = $conteos_erroneos +1;
                                    }
                                    else{
                                        $incorrectos= $incorrectos+1;
                                        $html_table = $html_table. "<p style='color:#FF0000';> No validado </p>";  
                                    }
                                }
                            }
                            
                            $html_table = $html_table. "</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>Observaciones</th>
                            <td style='width:70%;height:auto' align='left'>";
                    
                            $html_table = $html_table. $datos[10];
                            
                            $html_table = $html_table. "</td>
                        </tr>
                        <tr>
                            <th style='width:30%;height:auto' align='left'>Fecha: </th>
                            <td style='width:70%;height:auto' align='left'> $datos[8]</td>
                        </tr>
                        
                        </table>";
                    $total= $total+1;
                    $index = $index+1;
                }

                $i=$i+1;   

            }

            $porcentaje_Archivos_faltantes = ($total*100) / $total_archivos;
            $porcentaje_incorrectos = ($incorrectos*100) / $total_archivos;

            echo "<h3 align='center'>Anexo 1. Cambios en Hashes_inventario.txt</h3>";
            echo "<p align='justify'>Se realizó un proceso de validacion de huellas criptograficas a grano fino de los hashes contenidos dentro del archivo Hases_inventario.txt.
            Dicha validación consistió en la extracción de los hashes de los archivos Hashes_inventario.txt de cada uno de los eventos, posteriormente se realizó la construccion de 2 tablas 
            de hash (donde cada registro corresponde a la ruta de un archivo y su huella criptografica listadas dentro de los archivos Hashes_inventario.txt), y finalmente se 
            realizó la comparacion entre los contenidos de las tablas.  
            </p>";
            if($contenedores+$bases_de_datos+$conteos_erroneos >0 ){
                echo "<p align='justify'>De un total de ".$total." rutas de archivos con cambios (aproximadamente el ".round($porcentaje_Archivos_faltantes)    ."% del total de ".$total_archivos." archivos unicos) detectados dentro de Hashes_inventario.txt, 
                se encontraron ".$contenedores." rutas de archivos que pertenecen a contenedores virtuales, ".$bases_de_datos." que pertenecen a bases de datos y ".$conteos_erroneos." que
                presentan inconsistencias en la cantidad de archivos con el mismo hash, dando un total de ".$bases_de_datos+$contenedores+$conteos_erroneos." archivos de los 
                cuales se espera que puedan presentar cambios, por lo cual fueron clasificados como <span style='color:#F37D1B';> Con cambios </span>. </p>";
            }

            if($incorrectos>0){
                echo "<p align='justify'>No obstante, se detactaron un total de ".$incorrectos." rutas de archivos (".round($porcentaje_incorrectos)."% del total de archivos unicos) cuyo estatus fue denominado como 'No validado' 
                debido a que las huellas criptograficas no son similares. A continuación se muestra la tabla de 
                hallazgos encontrados en la cual se resaltan los componentes marcados como <span style='color:#FF0000';> No válidado </span> </p><br>";
                echo $html_table;
            }


        }
        
    

    ?>


   


</body> 
</html>
<?php
    ini_set("display_errors", "1");
    require_once '../dependencias/mpdf/vendor/autoload.php';
    $mpdf = new \Mpdf\Mpdf();
    $html = ob_get_contents();
    ob_end_clean();
    $mpdf->shrink_tables_to_fit=0;
    $mpdf->WriteHTML($html);
    //$mpdf->Output();
    $nombrePdf = $anio.$mes.$dia."_".$hora."_".$min."_ConstanciaHechos.pdf";

    $mpdf->Output($nombrePdf,'I');
?>

