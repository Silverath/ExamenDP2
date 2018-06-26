<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="actor/register.do" modelAttribute="actorAccountForm">


	<acme:textbox code="actor.username" path="username" />
	<acme:password code="actor.password" path="password" />
	<acme:password code="actor.password2" path="password2" />
	<form:label path="authorities">
		<spring:message code="actor.authorities" />:
	</form:label>
	<form:select id="authorities" path="authorities">
		<form:option value="0" label="----" />		
		<form:options items="${authorities}" itemLabel="authority" />
	</form:select>
	<form:errors cssClass="error" path="authorities" />
	<br />
	<br />
	<br />
	<acme:textbox code="actor.name" path="name"/>
	<acme:textbox code="actor.surname" path="surname"/>
	<acme:textbox code="actor.email" path="email"/><i>(<spring:message code="actor.example"/>: user@mail.com)</i>
	<acme:textbox code="actor.phone" path="phone"/><i>(<spring:message code="actor.example"/>: +34 678543662)</i>
	<acme:textbox code="actor.address" path="address"/>


	<br />
	<br />
	<form:label path="terms">
		<spring:message code="actorAccount.terms1" /> <a href="terms/terms.do"> <spring:message code="actorAccount.terms2"/></a><spring:message code="actorAccount.terms3"/>:
	</form:label>
	

<jstl:choose>
		<jstl:when test="${pageContext.response.locale.language=='en'}">
	<form:select path="terms">
		<form:option value="false" label="NO" />
		<form:option value="true" label="YES" />				
	</form:select>	
	</jstl:when>
	<jstl:otherwise>
	<form:select path="terms">
		<form:option value="false" label="NO" />
		<form:option value="true" label="SI" />				
	</form:select>
	</jstl:otherwise>
	</jstl:choose>
<br/>
<br/>
	<input type="submit" name="save"
		value="<spring:message code="actor.save" />" />&nbsp;
		
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: window.location.replace('welcome')" />
	<br />

</form:form>
