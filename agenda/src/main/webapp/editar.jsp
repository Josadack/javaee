<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="IMAGEM/1.png">
<link rel="stylesheet" href="st.css">
</head>
<body>
	<h1>Editar Contato</h1>
	<form name="frmContato" action="update">
	
	<table>
		<tr>
			<td><input type="text" name="idcon" id="cx3" readonly="readonly" value="<%out.print(request.getAttribute("idcon"));%>"></td>
		</tr>
		
		<tr>
			<td><input type="text" name="nome" class="cx" value="<%out.print(request.getAttribute("nome"));%>"></td>
		</tr>
		
		<tr>
			<td><input type="tel" name="fone"  class="cx2" value="<%out.print(request.getAttribute("fone"));%>"></td>
		</tr>
		
		<tr>
			<td><input type="text" name="email"  class="cx" value="<%out.print(request.getAttribute("email"));%>"></td>
		</tr>
	</table>
		<input type="button" value="Salvar" class="bt" onclick="validar()">
	</form>
	<script src="Scripts/validador.js"></script>
</body>
</html>