<?php

require_once("../dependencias/DBCon.php");
$TOKEN = "f3cfbaae0d5bbdb106538d83edeb0faf4e9d5716a377890028cb4befe3191040";

if(isset($_POST["json"])){

        $json = $_POST["json"];
        $accion = $_POST["accion"];
        $token = $_POST["token"];
        $entidad = $_POST["entidad"];

        if ($token == $TOKEN){
                if ($entidad == "proveedor" && $accion == "insertar") {
                        $con = conectarBD();
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
        
                                                $datetime2 = date_create()->format('Y-m-d H:i:s');
                                                $queryInsertar = "INSERT INTO RegistrosProveedor (NombreArchivo,Hash,HashFirmado,TimestampFirma,TimestampAlmacenaje, NombrePrueba, nombreCiudad) VALUES ('$datosArchivo[0]', '$datosArchivo[1]', '$datosArchivo[2]', '$datosArchivo[3]', '$datetime2', '$nombrePrueba', '$nombreCiudad');";
                                                if (mysqli_query($con, $queryInsertar)){
                                                        echo "\tEl registro $datosArchivo[0] fue agregado correctamente\n";
                                                } else {
                                                        echo "\tError: " . $queryInsertar . "\n" . mysqli_error($con);
                                                }
                                        }
        
                                desconectarBD($con);
                }  else {
                        echo "No se recibieron todos los valores.";
                }
        
        } else {
                echo "Accesso no permitido";
        }
        
} else {
        echo "No se recibieron todos los valores.";
}


?>

