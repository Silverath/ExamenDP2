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

<form:form action="message/editBroadcast.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actorSender" />
	<form:hidden path="moment" />
	<form:hidden path="folder" />
	
	<div>
	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<form:select id="priority" path="priority">		
		<form:options items="${priorityList}"/>
	</form:select>
	<form:errors cssClass="error" path="priority" />
	</div>
	
	
	<div>
		<form:label path="subject">
			<spring:message code="message.subject" />:
			</form:label>
		<form:input path="subject" />
		<form:errors cssClass="error" path="subject" />
	</div>

	<div>
		<form:label path="body">
			<spring:message code="message.body" />:
		</form:label>
		<form:textarea path="body" />
		<form:errors cssClass="error" path="body" />
	</div>

	<div>
		<input type="submit" name="save"
			value="<spring:message code="message.send"/>" /> &nbsp; <input
			type="button" name="cancel"
			value="<spring:message code="message.cancel"/>"
			onClick="javascript: window.location.replace('message/list.do');" />
	</div>
</form:form>