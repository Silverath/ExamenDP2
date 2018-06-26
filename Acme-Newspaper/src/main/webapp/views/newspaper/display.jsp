<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<script>
	function displaySumm(arg){
		window.alert(arg);
	}
</script>							
<p>
	<spring:message code="newspaper.title" var="titleHeader"/>
	<jstl:out value="${titleHeader}"></jstl:out>
	:
	<jstl:out value="${newspaper.title}"></jstl:out>
</p>

<p>
	<spring:message code="newspaper.publication" var="publicationHeader"/>
	<jstl:out value="${publicationHeader}"></jstl:out>
	:
	<jstl:out value="${newspaper.publication}"></jstl:out>
</p>

<p>
	<spring:message code="newspaper.description" var="descriptionHeader"/>
	<jstl:out value="${descriptionHeader}"></jstl:out>
	:
	<jstl:out value="${newspaper.description}"></jstl:out>
</p>

<jstl:if test="${newspaper.picture != null }">
<p>
	<spring:message code="newspaper.picture" var="pictureHeader" />
	<jstl:out value="${pictureHeader}"></jstl:out>
</p>
:
<jstl:set var="enlace" value="${newspaper.picture}"/>
<img src="${enlace}" style="width:300px;height:180px;"/>
</jstl:if>

<p>
	<spring:message code="newspaper.isOpen" var="isOpenHeader"/>
	<jstl:out value="${isOpenHeader}"></jstl:out>
	:
	<jstl:out value="${newspaper.isOpen}"></jstl:out>
</p>

<h3><spring:message code="newspaper.article" /></h3>
<display:table name="${articles}" id="article" requestURI="newspaper/display.do">

	<spring:message code="article.title" var="titleTitle"/>
	<display:column title="${titleTitle}" sortable="true">
		<a href="article/display.do?articleId=${article.id}">${article.title}</a>
	</display:column>
	
	<spring:message code="article.author" var="authorTitle"/>
	<display:column title ="${authorTitle}">
	<a href="user/display.do?userId=${article.user.id}">${article.user.name}
	</a>
	</display:column>
	
	<spring:message code="article.summary" var="summaryTitle"/>
	<display:column title ="${summaryTitle}" maxLength="10"><a href="javascript:window.onclick = displaySumm('${article.summary}')">${article.summary}</a>
	</display:column>

</display:table>


		   			

