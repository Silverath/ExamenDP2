<%--
 * action-2.jsp
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

<form:form action="message/move.do" modelAttribute="messa">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actorSender" />
	<form:hidden path="moment" />
	<form:hidden path="actorReceiver" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
	<form:hidden path="priority" />
	<div>
		<form:select path="folder">
			<form:option label="----" value="0" />
			<form:options items="${avaiableFolders}" itemLabel="name" itemValue="id" />
			<spring:message code="message.folder" />:
		</form:select>
	</div>
	<br>
	<div>
	<input type="submit" name="save"
		value="<spring:message code="message.save"/>" /> 
	&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel"/>"
		onClick="javascript: window.location.replace('message/list.do');" 
	/>
	</div>
</form:form>