<?php

require_once("../dependencias/DBCon.php");
$TOKEN = "f3cfbaae0d5bbdb106538d83edeb0faf4e9d5716a377890028cb4befe3191040";
if(isset($_GET["token"]) && isset($_GET["nombrePrueba"])){

        $token = $_GET["token"];
        $nombrePrueba = $_GET["nombrePrueba"];
        if (strlen($nombrePrueba)>15){$token="";}

        if ($token == $TOKEN){
                $con = conectarBD();

                $datosArchivo = array();

                $queryInsertar = "SELECT idRegistrosProveedor,NombreArchivo, NombreCiudad, NombrePrueba, Hash, HashFirmado FROM RegistrosProveedor WHERE NombrePrueba='$nombrePrueba'";
                $row=mysqli_fetch_array($queryInsertar);
                echo json_encode($row);
                desconectarBD($con);


        
        } else {
                echo "Accesso no permitido";
        }
        
} else {
        echo "No se recibieron todos los valores.";
}


?>

