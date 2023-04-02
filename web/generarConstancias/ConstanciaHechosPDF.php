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
    
    
        /* Se Genera la tabla*/
        if (($fichero = fopen($_FILES['archivo']['tmp_name'], "r")) !== FALSE) {
            echo"<p align='justify'>A continuación, se muestran los componentes sujetos a este procedimiento, acompañados del nombre del archivo, la huella criptográfica original (SHA3-256 inicial), la huella criptográfica minutos antes de iniciar la jornada electoral (SHA3-256 inicial) y el resultado de la comparación.</p><br>";
            $i=1;
            $incorrectos=0;
            $total=0;

            while (($datos = fgetcsv($fichero,1000,"," )) !== FALSE) {
                
                //Se ignora el encabezado
                if($i!=1){

                    echo "<table  autosize='2.4' border='1'>
                        <tr>
                            <th style='width:34.3%;height:auto' align='center' >Nombre del Archivo: $datos[0] </th>
                            <th style='width:34.3%;height:auto' align='center' >Evento: Proceso Electoral Tamaulipas 2022 Fecha validación $datos[8]</th>
                        </tr>
                        <tr>
                            <th style='width:30.0%;height:auto' align='left'>SHA3-256 Inicial</th>
                            <td style='width:70.0%;height:auto' align='left'>$datos[2]</td>
                        </tr>
                        <tr>
                            <th style='width:30.0%;height:auto' align='left'>SHA3-256 del evento</th>
                            <td style='width:70.0%;height:auto' align='left'>$datos[5]</td>
                        </tr>
                        <tr>
                            <th style='width:30.0%;height:auto' align='left'>Estado</th>
                            <td style='width:70.0%;height:auto' align='left'>";

                            if(trim($datos[7])=="SI"){
                                echo "Correcto";
                            }
                            elseif(trim($datos[7])==trim("NO")){
                                if (in_array($datos[0],$lista_BD)){
                                    echo "<p style='color:#F37D1B';> Con cambios </p>";   
                                }
                                else{
                                    $incorrectos= $incorrectos+1;
                                    echo "<p style='color:#FF0000';> No validado </p>";   
                                }
                            }
                            
                    echo        "</td>
                        </tr>
                        <tr>
                            <th style='width:30.0%;height:auto' align='left'>Observaciones</th>
                            <td style='width:70.0%;height:auto' align='left'>";
                         
                            if (trim($datos[7])=="SI"){

                                if (strtoupper($datos[0])=="LOG.TXT") {
                                    echo "Se considera correcto porque las huellas criptográficas evaluadas para este archivo son distintas. El contenido de este archivo cambia cada que el proveedor realiza la recolección del inventario, por tal motivo deben ser distintas las huellas criptográficas.";
                                } else {
                                    echo "Se considera correcto porque las huellas criptográficas evaluadas para este archivo son iguales. Las aplicaciones no deben modificarse, por lo tanto se espera que las huellas criptográficas sean iguales.";
                                }
                            } else {
                                if (strtoupper($datos[0])=="LOG.TXT"){
                                    if (($datos[5] == $datos[2])){
                                        echo "Las huellas criptográficas evaluadas para este archivo son iguales. Se esperaba que fueran diferentes. El contenido de este archivo cambia cada que el proveedor realiza la recolección del inventario, por tal motivo deben ser distintas.";
                                    } 
                                } else {
                                    if (trim($datos[2]) == "N/A" || trim($datos[5]) == "N/A" ){
                                        echo "Una de las huellas criptográficas no fue obtenida.";
                                    } 
                                    if ($datos[5] != $datos[2]){
                                        if (in_array($datos[0],$lista_BD)){ //si entra en las exepciones
                                            echo "Las huellas criptograficas son distintas, sin embargo, se espera que para este tipo de archivo pueda ocurrir. Se asume que el archivo original está vacío y el archivo del evento contiene datos. Se espera que el archivo del evento no contenga datos.";   
                                        }
                                        else{
                                            echo "Las huellas criptográficas son distintas. Se esperaba que fueran las mismas para este archivo. Las aplicaciones no deben modificarse, por lo tanto se espera que las huellas criptográficas sean iguales.";
                                        }
                                    } 
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
                    echo "<p align='justify'>Resumen de la validación: Se detectaron ".$incorrectos."  archivos incorrectos de un total de ".$total." Archivos. Para más detalles revisar el motivo de cada archivo con la validación 'No validado' mismos que están resaltados en color rojo.</p><br>";
            }
        } 

    ?>


    <p align='justify'>A continuación, se describe brevemente cada uno de los archivos validados.</p>
    
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

</body> 

</html>
<?php
    ini_set("display_errors", "1");
    require_once '../dependencias/mpdf/vendor/autoload.php';
    $mpdf = new \Mpdf\Mpdf();
    $html = ob_get_contents();
    ob_end_clean();
    $mpdf->WriteHTML($html);
    //$mpdf->Output();
    $nombrePdf = $anio.$mes.$dia."_".$hora."_".$min."_ConstanciaHechos.pdf";
    $mpdf->Output($nombrePdf,'I');
?>

