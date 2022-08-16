<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.JavabBeans" %>
<%@page import="java.util.ArrayList"%>
<%
	ArrayList<JavabBeans> lista = (ArrayList<JavabBeans>) 
request.getAttribute("jbContato");
%>
<!DOCTYPE html>
<html lang = "pt-br">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="IMAGEM/1.png">
<link  rel="stylesheet" href="st.css">
</head>
<body>
	<h1>Agenda de contatos</h1>
	<a href="novo.html" class="bt">Novo contato</a>
	<a href="report" class="bt2">Gerar Relátorio</a>
	<table id="tabela">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Fone</th>
				<th>E-mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		<tbody>
			<%for (int i = 0; i < lista.size(); i++) { %>
			<tr>
				<td><%=lista.get(i).getIdcon()%> </td>
				<td><%=lista.get(i).getNome()%></td>
				<td><%=lista.get(i).getFone()%></td>
				<td><%=lista.get(i).getEmail()%></td>
				<td><a href="select?idcon=<%=lista.get(i).getIdcon()%>" class="bt">Editar</a>
				    <a href="javascript: confirmar(<%=lista.get(i).getIdcon()%>)" class="bt2" >Excluir</a>
			</tr>
			<%} %>
		</tbody>
	</table>
	<script src="Scripts/confirmador.js"></script>
</body>
</html>