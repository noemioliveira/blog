<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Usuário</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />">
</head>
<body>
	<c:url var="save" value="/usuario/update"/>
	<form:form modelAttribute="usuario" action="${save}" method="post">
		<form:hidden path="id"/>												 
		<fieldset>
			<legend> Editar Usuário</legend>
			<div class="campo">
				<form:label path="nome">Nome do Usuário</form:label><br>
				<form:input path="nome" type="text" required="true"/>
			</div>
			<div class="campo">
				<form:label path="email">E-mail</form:label><br>
				<form:input path="email" type="email" required="true"/>
			</div>
			<div>
				<input type="submit" value="Salvar">
				<input type="reset" value="Limpar">
			</div>
		</fieldset>	
	</form:form>
</body>
</html>