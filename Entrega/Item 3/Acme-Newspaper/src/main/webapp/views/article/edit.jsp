<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="article">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="newspaper" />
	<form:hidden path="followUps" />
	<form:hidden path="user" />


	<acme:textbox code="article.title" path="title" />

	<acme:textbox code="article.summary" path="summary" />

	<acme:textbox code="article.body" path="body"/>

	<acme:textbox code="article.pictures" path="pictures" />
	
	<spring:message code="article.draftMode" />:
	<input type="checkbox" name="draftMode"
		<jstl:if test="${article.draftMode==true}">
			checked
		</jstl:if> 
		value="true" />
	<br>
	
	<security:authorize access="hasRole('USER')">
	
	<input type="submit" name="save"
	value="<spring:message code="article.save" />" />&nbsp; 

	</security:authorize>
	
<%-- 	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${article.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="article.delete" />"
				onclick="return confirm('<spring:message code="article.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</security:authorize> --%>
	
	<input type="button" name="cancel"
		value="<spring:message code="article.cancel" />"
		onclick="javascript: relativeRedir('article/list.do');" />
	<br />
</form:form>