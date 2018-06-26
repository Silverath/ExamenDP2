<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="article.advertisement" var="advertisementHeader" />
	<jstl:out value="${advertisementHeader}"></jstl:out>
	:	
</p>
<br>
<img src="${advertisement.banner}"/>
<br>
							
<p>
	<spring:message code="article.title" var="titleHeader"/>
	<jstl:out value="${titleHeader}"></jstl:out>
	:
	<jstl:out value="${article.title}"></jstl:out>
</p>

<p>
	<spring:message code="article.moment" var="momentHeader"/>
	<jstl:out value="${momentHeader}"></jstl:out>
	:
	<jstl:out value="${article.moment}"></jstl:out>
</p>

<p>
	<spring:message code="article.summary" var="summaryHeader"/>
	<jstl:out value="${summaryHeader}"></jstl:out>
	:
	<jstl:out value="${article.summary}"></jstl:out>
</p>

<p>
	<spring:message code="article.body" var="bodyHeader" />
	<jstl:out value="${bodyHeader}"></jstl:out>
:
<jstl:out value="${article.body}"></jstl:out>
</p>


<p>
	<spring:message code="article.user" var="userHeader"/>
	<jstl:out value="${userHeader}"></jstl:out>
	:
	<a href="user/display.do?userId=${article.user.id}">${article.user.name}
	</a>
</p>

<p>
	<spring:message code="article.newspaper" var="newspaperHeader"/>
	<jstl:out value="${newspaperHeader}"></jstl:out>
	:
	<a href="newspaper/display.do?newspaperId=${article.newspaper.id}">${article.newspaper.title}
	</a>
</p>

<h3><spring:message code="article.pictures" /></h3>
<display:table name="${article.pictures}" id="picture">

	<spring:message code="article.pictures" var="pictureTitle"/>
	<display:column title="${pictureTitle}" >
		<a href="${picture}">${picture}</a>
	</display:column>

</display:table>


		   			

