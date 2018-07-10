<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="message/folder/edit.do" modelAttribute="folder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="message" />
	<form:hidden path="actor" />
	<form:hidden path="modify" />
	



	<div>
		<form:label path="name">
			<spring:message code="folder.name" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
	<%-- 	<c:if test="${error eq 'folder.commit.error.reserved'}"><font color="red"><b>
		<spring:message code="${error}"/></b></font></c:if> --%>
	</div>
	
	<div>
		<input type="submit" name="save"
			value="<spring:message code="folder.save"/>" /> 
			&nbsp; 
		<jstl:if test="${create==false}" >
			<input type="submit" name="delete"
			value="<spring:message code="folder.delete"/>" /> 
			&nbsp; 
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="folder.cancel"/>"
			onClick="javascript: window.location.replace('message/list.do');" />
	</div>
</form:form>
