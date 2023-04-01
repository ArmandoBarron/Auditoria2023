 
<!DOCTYPE html>
<html>
 
<head>        
	<meta charset="UTF-8"> 
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        
       
        <link href="../dependencias/estilo.css" rel="stylesheet" type="text/css" />  
		

	<title>Validación</title>
 
 
 
</head>    
<body style ="background-color:#E6E6E6">
<div>


		<div >		
		<form id="form"  class="smart-green" action="ConstanciaHechosPDF.php" method="POST" enctype="multipart/form-data" target="_blank">
			<h1>Aplicación web para la validación de firmas y generación de la constancia de hechos</h1>
				
			   	<p>
					<label>Archivo de validación:</label>
					<input type="file" class="text" name="archivo" required />
				</p>

				

				
				<p>
				<label>&nbsp;</label>
				<input type="submit" class="btn" value="Generar constancia de hechos" name="insertar" required/>
				
			</p>
			<br>
		</form>
	</div>
 

</div>
</body>
</html>