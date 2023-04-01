 
<!DOCTYPE html>
<html>
 
<head>        
	<meta charset="UTF-8"> 
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        
       
        <link href="../dependencias/estilo.css" rel="stylesheet" type="text/css" />  
		

	<title>Huellas criptográficas</title>
 
 
 
</head>    
<body style ="background-color:#E6E6E6">
<div>


		<div >		
		<form id="form"  class="smart-green" action="GenerarConstanciaHuellasCripto.php" method="POST" enctype="multipart/form-data" target="_blank">
			<h1>Aplicación web para la generación de huellas criptográficas</h1>
				
			   	<p>
					<label>Archivo json de huellas:</label>
					<input type="file" class="text" name="archivo" required />
				</p>

				

				
				<p>
				<label>&nbsp;</label>
				<input type="submit" class="btn" value="Generar constancia de huellas criptográficas" name="insertar" required/>
				
			</p>
			<br>
		</form>
	</div>
 

</div>
</body>
</html>