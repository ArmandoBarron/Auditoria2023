<?php
#$JSON_LOCATION="http://localhost/julio/tamps/AuditoriaWebServices/Jars/Proveedor/";
$JSON_LOCATION="http://127.0.0.1/2021Auditoria/AUDITORIA/generarConstancias/HuellasJson/";

# leer desde un json enviado por post
$jsondecodificado = json_decode(file_get_contents($_FILES['archivo']['tmp_name']) , true);
	date_default_timezone_set("America/Mexico_City");


$array_simulacros = array("S0.json", "S1.json");
#indice                     0           1       2       ... etc
$indice_simulacro = 0;
ob_start();
error_reporting(0);
//error_reporting(E_ALL);
//ini_set('display_errors', 1);
//ini_set('display_startup_errors', 1);
set_time_limit(100000);
ini_set('memory_limit', '-1');
date_default_timezone_set("America/Mexico_City");


$representanteINE = "Ing. Yuri Adrián González Robles";
$cargoINE = " Director de Seguridad y Control Informático Unidad Técnica de Servicios de Informática del Instituto Nacional Electoral";

$representanteCinvestav = "Dr. Arturo Díaz Pérez";
$cargoCinvestav = "Investigador Cinvestav 3C del Centro de Investigación y de Estudios Avanzados del Instituto Politécnico Nacional";


?>



<!DOCTYPE html>
<html>
 
<head>       
        <!--<link href="../dependencias/estilo.css" rel="stylesheet" type="text/css" />  -->
        <link href="template.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>    
<page backtop="28mm" backbottom="10mm" backleft="15mm" backright="10mm">
<body>
<?php

echo "<img src='../dependencias/LogoCin.png' width='200'>";
echo "<p align='right'>";

class CurlRequest {
    public function sendPost($URL){   

        //datos a enviar
        //$data = array("id" => "1");
        //url contra la que atacamos
        $ch = curl_init($URL);

        //a true, obtendremos una respuesta de la url, en otro caso, 
        //true si es correcto, false si no lo es
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_TIMEOUT_MS, 3000);
        //establecemos el verbo http que queremos utilizar para la petición
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "GET");
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Content-Type: application/json',
             'Accept: application/json'
        ));
        //enviamos el array data
        //curl_setopt($ch, CURLOPT_POSTFIELDS,http_build_query($data));
        //obtenemos la respuesta
        $response = curl_exec($ch);
        // var_dump(curl_getinfo($ch));
        // var_dump(curl_errno($ch));
        // Se cierra el recurso CURL y se liberan los recursos del sistema
        curl_close($ch);
        if(!$response) {
            return false;
        }else{
            return $response;
        }
    }

    public function sendGet(){
        //datos a enviar
        $data = array("id" => "1");
        //url contra la que atacamos
        $ch = curl_init("https://jsonplaceholder.typicode.com/users");
        //a true, obtendremos una respuesta de la url, en otro caso, 
        //true si es correcto, false si no lo es
        //curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        //establecemos el verbo http que queremos utilizar para la petición
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "GET");
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        //enviamos el array data
            
        //obtenemos la respuesta
        $response = curl_exec($ch);
        // Se cierra el recurso CURL y se liberan los recursos del sistema
        curl_close($ch);
        if(!$response) {          
            return false;
        }else{
            return var_dump($response);
        }
    }

    public function sendDelete()
    {
        //datos a enviar
        $data = array("a" => "a");
        //url contra la que atacamos
        $ch = curl_init("http://localhost/WebService/API_Rest/api.php");
        //a true, obtendremos una respuesta de la url, en otro caso, 
        //true si es correcto, false si no lo es
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        //establecemos el verbo http que queremos utilizar para la petición
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE");
        //enviamos el array data
        curl_setopt($ch, CURLOPT_POSTFIELDS,http_build_query($data));
        //obtenemos la respuesta
        $response = curl_exec($ch);
        // Se cierra el recurso CURL y se liberan los recursos del sistema
        curl_close($ch);
        if(!$response) {
            return false;
        }else{
            var_dump($response);
        }
    }
}
      
        
        
/*echo "Resultado:<br>";
print_r($resultado);
echo "<br><br>";
//pasear el json a un arreglo en php, una pasado el la estructura de json al arreglo se le puede aplicar un analis.
$json = json_decode($resultado, true);
for ($i=0; $i < sizeof($json); $i++) { 
    //print_r($json[i]['time_tamp']);
    print_r($json[$i]["name"]." ".$json[$i]["phone"]);
    echo "<br>";
}*/



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


echo "<p align='right'> Toluca, Estado de México, $dia de $m de $anio</p>";
echo    '<h2 align="center">Constancia de hechos de la generación de las huellas criptográficas de los programas y de la base de datos del SIVEI.</h2>';

 echo "
        <p align='justify'>Siendo las $hora horas con $min minutos del día $dia del mes de $m del año $anio y, en cumplimiento 
        al numeral 38, del Anexo 21.2 \"LINEAMIENTOS DEL VOTO ELECTRÓNICO POR INTERNET PARA LAS MEXICANAS Y LOS MEXICANOS RESIDENTES EN EL EXTRANJERO\", el cual
        menciona que se deberá ejecutar el procedimiento de validación de los componentes del SIVEI, el cual consiste en generar los códigos de integridad de los componentes, con la finalidad de comprobar que estos se mantuvieron íntegros durante la operación del SIVEI en todas sus etapas; así como en el marco del convenio INE/DJ/5/2023 celebrado entre el Instituto Nacional Electoral (INE) y el Centro de Investigación y Estudios Avanzados del Instituto Politécnico Nacional (CINVESTAV); se procedió a realizar la validación de los módulos funcionales y bases de datos del SIVEI.</p>

        <p align='justify'>Para esta validación se contó con la presencia en su calidad de titular de la Dirección de Seguridad y Control Informático de la Unidad Técnica de Servicios de Informática el Ing. Yuri Adrián González Robles por parte del INE; por parte del CINVESTAV en su calidad de Ente Auditor el Investigador Titular Dr. Arturo Díaz Pérez.
        </p>

        <p align='justify'> A continuación, se muestran los componentes sujetos a este procedimiento, acompañados del nombre del archivo y la huella criptográfica original (SHA3-256 inicial).
        </p>
        <br>
        ";

$new = new CurlRequest();
// $json = $new ->sendPost('http://disys0.tamps.cinvestav.mx/Auditoria/EA/firmas/'.$array[0]);
#$json = $new ->sendPost($JSON_LOCATION.$array_simulacros[$indice_simulacro]);
#$jsondecodificado = json_decode($json, true);

//Se extraen los datos
$numeroPares = $jsondecodificado["numeroPares"];
$nombrePrueba = $jsondecodificado["nombrePrueba"];
$nombreCiudad = $jsondecodificado["nombreCiudad"];
                               
for ($i=0; $i < $numeroPares; $i++) { 
    $datosArchivo = array();
    foreach ($archivo = $jsondecodificado[$i] as $posicion => $valor) {
        $datosArchivo[$posicion] = $valor;
    }
    echo '<table id="central" autosize="2.4" border="1">';
    echo '<tr>';
    echo '<th style="width:34.3%;height:auto" align="center">Nombre del archivo</th>';
    echo '<td style="width:34.3%;height:auto" align="center">'.$datosArchivo[0].'</td>';
    echo '</tr>';
    echo '<tr>';
    echo '<th style="width:30.0%;height:auto" align="left">SHA3-256</th>';
    echo '<td style="width:70.0%;height:auto" align="left">'.$datosArchivo[1].'</td>';
    echo '</tr>';
    echo '<tr>';
    echo '<th style="width:20.0%;height:auto" align="left">Fecha en que fue firmado</th>';
    echo '<td style="width:70.0%;height:auto" align="left">'.$datosArchivo[3].'</td>';
    echo '</tr>';
    echo "</table> <br>";

    //$datetime2 = date_create()->format('Y-m-d H:i:s');
    //echo($datosArchivo[0]." ".$datosArchivo[1]." ".$datosArchivo[2]." ".$datosArchivo[3]."  ".$array[0]);
    //echo"<br>";
    //$queryInsertar = "INSERT INTO RegistrosProveedor (NombreArchivo,Hash,HashFirmado,TimestampFirma,TimestampAlmacenaje, NombrePrueba, nombreCiudad) VALUES ('$datosArchivo[0]', '$datosArchivo[1]', '$datosArchivo[2]', '$datosArchivo[3]', '$datetime2', '$nombrePrueba', '$nombreCiudad');";
    //if (mysqli_query($con, $queryInsertar)){
        //  echo "OK";
    //} else {
        //      echo "Error: " . $queryInsertar . "<br>" . mysqli_error($conexion);
    //}
}
                                 
echo("<br>");
if ($indice_simulacro>100){
    //echo "<h3>Firmas generadas por el PROVEEDOR del simulacro 2 con 30 actas capturadas.</h3>";
    echo("A continuación se muestran las firmas generadas por el PROVEEDOR con su llave privada a los documentos de cada uno de los simualacros. Las firmas generadas durante estos eventos serán validadas con las firmas generadas inicialmente por el PROVEEDOR.");
    for ($j=1; $j < sizeof($array_simulacros); $j++) { 
        $new = new CurlRequest();
        $json = $new ->sendPost($JSON_LOCATION.$array_simulacros[$j-1]);
    
        $jsondecodificado = json_decode($json, true);
        //Se extraen los datos
        $numeroPares = $jsondecodificado["numeroPares"];
        $nombrePrueba = $jsondecodificado["nombrePrueba"];
        $nombreCiudad = $jsondecodificado["nombreCiudad"];
        for ($i=0; $i < $numeroPares; $i++) { 
            $datosArchivo = array();
            foreach ($archivo = $jsondecodificado[$i] as $posicion => $valor) {
                $datosArchivo[$posicion] = $valor;
            }
            echo '<table id="central" style="width: 100%; border-collapse: collapse;" border="1" >';
            echo '<tr>';
            echo '<th style="width:20.0%;height:auto" align="left">Nombre del archivo</th>';
            echo '<td style="width:80.0%;height:auto" align="left">'.$datosArchivo[0].'</td>';
            echo '</tr>';
            
            echo '<tr>';
            echo '<th style="width:20.0%;height:auto" align="left">SHA3-256</th>';
            echo '<td style="width:80.0%;height:auto" align="left">'.$datosArchivo[1].'</td>';
            echo '</tr>';
    
            echo '<tr>';
            echo '<th style="width:20.0%;height:auto" align="left">Fecha en que fueron firmados</th>';
            echo '<td style="width:80.0%;height:auto" align="left">'.$datosArchivo[3].'</td>';
            echo '</tr>';
                                               
            echo '<tr>';
            echo '<th style="width:20.0%;height:auto" align="left">Evento</th>';
            if($nombrePrueba=="S1"){
                echo '<td style="width:80.0%;height:auto" align="left">Simulacro 1</td>';
            }elseif ($nombrePrueba=="S2") {
                echo '<td style="width:80.0%;height:auto" align="left">Simulacro 2</td>';
            }elseif ($nombrePrueba=="S3") {
                echo '<td style="width:80.0%;height:auto" align="left">Simulacro 3</td>';
            }
                                                
            echo '</tr>';
            echo "</table> <br>";
    
            //$datetime2 = date_create()->format('Y-m-d H:i:s');
            //echo($datosArchivo[0]." ".$datosArchivo[1]." ".$datosArchivo[2]." ".$datosArchivo[3]."  ".$array[$j]);
            //echo"<br>";
            //$queryInsertar = "INSERT INTO RegistrosProveedor (NombreArchivo,Hash,HashFirmado,TimestampFirma,TimestampAlmacenaje, NombrePrueba, nombreCiudad) VALUES ('$datosArchivo[0]', '$datosArchivo[1]', '$datosArchivo[2]', '$datosArchivo[3]', '$datetime2', '$nombrePrueba', '$nombreCiudad');";
            //if (mysqli_query($con, $queryInsertar)){
                    //  echo "OK";
            //} else {
                //      echo "Error: " . $queryInsertar . "<br>" . mysqli_error($conexion);
            //}
        }
        //echo "<a href='generarReporteConstancia.php?json= $resultado & json1=$resultado1'>PRESIONE AQUI</a>";
        //echo "<br>";
        //$json = json_decode($resultado, true);
        //echo $resultado1;
    
        echo("<br>");
    }
}

echo("<br>");
echo("<br>");


// if(isset($_POST["json"]) ){
//     $json = $_POST["json"];
//     $accion = $_POST["accion"];
//     $entidad = $_POST["entidad"];

//     if ($entidad == "proveedor" && $accion == "insertar") {
//         $con = conectarBD();
//         $jsondecodificado = json_decode($json, true);
//         //Se extraen los datos
//         $numeroPares = $jsondecodificado["numeroPares"];
//         $nombrePrueba = $jsondecodificado["nombrePrueba"];
//         $nombreCiudad = $jsondecodificado["nombreCiudad"];
//         for ($i=0; $i < $numeroPares; $i++) { 
//             $datosArchivo = array();
//             foreach ($archivo = $jsondecodificado[$i] as $posicion => $valor) {
//                 $datosArchivo[$posicion] = $valor;
//             }
//             $datetime2 = date_create()->format('Y-m-d H:i:s');
//             $queryInsertar = "INSERT INTO RegistrosProveedor (NombreArchivo,Hash,HashFirmado,TimestampFirma,TimestampAlmacenaje, NombrePrueba, nombreCiudad) VALUES ('$datosArchivo[0]', '$datosArchivo[1]', '$datosArchivo[2]', '$datosArchivo[3]', '$datetime2', '$nombrePrueba', '$nombreCiudad');";
//             if (mysqli_query($con, $queryInsertar)){
//                 echo "OK";
//             } else {
//                 echo "Error: " . $queryInsertar . "<br>" . mysqli_error($conexion);
//             }
//         }
//         desconectarBD($con);
//     }
// } else {
//     echo "No se recibieron todos los valores";
// }
             
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



    <p align='justify'>Firman la presente constancia los representantes de las entidades que intervienen, en su calidad de titular de la Dirección de Seguridad y Control Informático de la Unidad Técnica de Servicios de Informática el Ing. Yuri Adrián González Robles por parte del INE; por parte del CINVESTAV en su calidad de Ente Auditor el Investigador Titular Dr. Arturo Díaz Pérez</p>

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
    require_once '../dependencias/mpdf/vendor/autoload.php';
    $mpdf = new \Mpdf\Mpdf();
    // $mpdf=new \Mpdf\Mpdf(['c','A4','','',5,5,10,10,10,10]); 
    $html = ob_get_contents();
    ob_end_clean();
    $mpdf->WriteHTML($html);
    $nombrePdf = $anio.$mes.$dia."_".$hora."_".$min."_ConstanciaHuellasCripto.pdf";
    $mpdf->Output($nombrePdf,'I');

    // LOAD a stylesheet
    // $stylesheet = file_get_contents('mpdfstyletables.css');

?>
