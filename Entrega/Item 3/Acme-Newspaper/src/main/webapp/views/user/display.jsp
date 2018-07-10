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
	<spring:message code="user.name" var="nameHeader"/>
	<jstl:out value="${nameHeader}"></jstl:out>
	:
	<jstl:out value="${user.name}"></jstl:out>
</p>

<p>
	<spring:message code="user.surname" var="surnameHeader"/>
	<jstl:out value="${surnameHeader}"></jstl:out>
	:
	<jstl:out value="${user.surname}"></jstl:out>
</p>

<p>
	<spring:message code="user.email" var="emailHeader" />
	<jstl:out value="${emailHeader}"></jstl:out>
	:	
	<jstl:out value="${user.email}"></jstl:out>
</p>

<p>
	<spring:message code="user.phone" var="phoneHeader" />
	<jstl:out value="${phoneHeader}"></jstl:out>
	:	
	<jstl:out value="${user.phone}"></jstl:out>
</p>

<p>
	<spring:message code="user.address" var="addressHeader" />
	<jstl:out value="${addressHeader}"></jstl:out>
	:	
	<jstl:out value="${user.address}"></jstl:out>
</p>




