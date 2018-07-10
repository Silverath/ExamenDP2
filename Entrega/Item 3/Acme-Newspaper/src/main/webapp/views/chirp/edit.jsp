<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="chirp/edit.do" modelAttribute="chirp">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="user"/>
	<form:hidden path="moment"/>
	
	
	
	<form:label path="title">
		<spring:message code="chirp.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	
	
	
	<br />
	<br/>
	<form:label path="description">
		<spring:message code="chirp.description"/>
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description" />
	
	<br />
	<br/>
	
		

	<jstl:if test="${chirp.id == 0}">
	<input type="submit" name="save"
		value="<spring:message code="chirp.save" />" />&nbsp; 
	</jstl:if>
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${chirp.id != 0}">
	
	<input type="submit" name="delete"
			value="<spring:message code="chirp.delete" />"
			onclick="return confirm('<spring:message code="chirp.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</security:authorize> 
	
		

	<input type="button" name="cancel"
		value="<spring:message code="chirp.cancel" />"
		onclick="javascript: relativeRedir('chirp/list.do');" />
	<br />
	
	</form:form>