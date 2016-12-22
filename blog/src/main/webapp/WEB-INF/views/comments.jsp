<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="comentarios">
<security:authorize access="hasAnyAuthority('ADIMIN','AUTOR','LEITOR')">
	<c:url var="save" value="/comentario/save"/>
	<form:form modelAttribute="comentario" action="${save}" method="post">
		<input type="hidden" value="${postagem.permalink}" name="permalink">
		<div>
			<form:label path="texto">Digite seu comentário</form:label>
			<form:textarea path="texto" rows="5" cols="80"/>
			<form:errors path="texto" cssClass="error"/>
		</div>
		<br>
		<div>
			<input type="submit" value="Salvar">
			<input type="reset" value="Limpar">
		</div>
	</form:form>
</security:authorize>	
	<hr>
	<c:forEach var="c" items="${postagem.comentarios}">
	<div class="comentarios">
		<img class="comentarios-avatar" src="<c:url value="/avatar/load/${c.usuario.avatar.id}"/>">
		<em>
			${c.usuario.nome} -
			<fmt:parseDate var="date" value="${c.dataComentario}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>
			<fmt:formatDate value="${date}" type="both"/>
		</em>
		<p>${c.texto}</p>
	</div>
	</c:forEach>
</div>
