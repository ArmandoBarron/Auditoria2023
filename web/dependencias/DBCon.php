<?php

	function conectarBD(){
    //Cambiar por la ip del servidor de base de datos
      $server = "127.0.0.1";
    //Cambiar por un usuario con acceso a la base de datos, se recomienda que no sea el root
      $usuario = "AuditPHP";
    //Colocar un password seguro
      $pass = "@udi20215963H";
    //Es el nombre de la base de datos
      $BD = "2023Auditoria";
	    //variable que guarda la conexión de la base de datos
	    $conexion = mysqli_connect($server, $usuario, $pass, $BD); 
	    //Comprobamos si la conexión ha tenido exito
	    if(!$conexion){ 
	        echo 'Ha sucedido un error inexperado en la conexion de la base de datos<br>'; 
	    } else {
	    	//echo "DB: $BD conexión OK<br>";
	    }
	    return $conexion;
	}

	/*Desconectar la conexion a la base de datos*/
    function desconectarBD($conexion){
            //Cierra la conexión y guarda el estado de la operación en una variable
            $close = mysqli_close($conexion); 
            //Comprobamos si se ha cerrado la conexión correctamente
            if(!$close){  
               echo 'Ha sucedido un error inexperado en la desconexion de la base de datos<br>'; 
            } else {
            	//echo "Conexíon cerrada";
            }   
            //devuelve el estado del cierre de conexión
            return $close;         
    }

    function sePuedeIngresarElEvento($con, $nombreEvento){
      $consulta = "SELECT count(*) as total FROM RegistrosProveedor WHERE NombrePrueba='$nombreEvento';";
      //$consulta = "SELECT count(*) as total FROM RegistrosProveedor WHERE NombrePrueba='Original';";
      $resultado = mysqli_query($con, $consulta);
      $respuesta = array(
              'esPosible' => false, 
              'mensaje' => "",
            );

      if($resultado) {

        $contenido = mysqli_fetch_array($resultado);

        if ($contenido['total'] == 0) {

          $respuesta['esPosible'] = true;
          $respuesta['mensaje'] = "Si es posible agragar los registros a la base de datos";

        } else {
          $respuesta['mensaje'] = "No se pueden insertar los datos. Los datos del Evento \"$nombreEvento\" ya se encuetran en el servidor.";
          //$respuesta['mensaje'] = "No se insertaron los datos. Los datos del Evento \"Original\" ya se encuetran en el servidor.";
        }

      } else {

        $respuesta['mensaje'] = "Error al conectarse a la base de datos. Contacte al administrador";
      }

      return $respuesta;

    }
    function obtener_estructura_directorios($ruta){
      // Se comprueba que realmente sea la ruta de un directorio
          $nombreArchivos= array();
          if (is_dir($ruta)){
              // Abre un gestor de directorios para la ruta indicada
              $gestor = opendir($ruta);
              echo "<ul>";
  
              // Recorre todos los elementos del directorio
              while (($archivo = readdir($gestor)) !== false)  {
                      
                  $ruta_completa = $ruta . "/" . $archivo;
  
                  // Se muestran todos los archivos y carpetas excepto "." y ".."
                  if ($archivo != "." && $archivo != "..") {
                      // Si es un directorio se recorre recursivamente
                      if (is_dir($ruta_completa)) {
                          //echo "<li>" . $archivo . "</li>";
                          $nombreArchivos[]=$archivo;
                          obtener_estructura_directorios($ruta_completa);
                      } else {
                          //echo "<li>" . $archivo . "</li>";
                          $nombreArchivos[]=$archivo;
                      }
                  }
              }
              
              // Cierra el gestor de directorios
              closedir($gestor);
              return $nombreArchivos;
              echo "</ul>";
          } else {
              echo "No es una ruta de directorio valida<br/>";
          }
      }

?>

