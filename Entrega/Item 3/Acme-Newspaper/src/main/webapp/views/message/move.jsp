<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="message/move.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actorSender" />
	<form:hidden path="actorReceiver"/>
	<form:hidden path="moment" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
	<form:hidden path="priority" />
	
		<div>
			<form:select path="folder">
				<form:options items="${movefolders}" itemLabel="name" itemValue="id" />
				<spring:message code="message.folder" />:
			</form:select>
		</div>
	
	<div>
		<input type="submit" name="save"
			value="<spring:message code="message.move"/>" /> 
		&nbsp; 
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel"/>"
			onClick="javascript: window.location.replace('message/list.do');" 
		/>
	</div>
</form:form>