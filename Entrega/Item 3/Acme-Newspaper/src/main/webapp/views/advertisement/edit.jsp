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

<form:form action="advertisement/edit.do" modelAttribute="advertisement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="agent" />

<form:label path="title">
		<spring:message code="advertisement.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	
	<br />
	
<form:label path="banner">
		<spring:message code="advertisement.banner"/>
	</form:label>
	<form:input path="banner"/>
	<form:errors cssClass="error" path="banner" />
	<br />
	
<form:label path="targetPage">
		<spring:message code="advertisement.targetPage"/>
	</form:label>
	<form:input path="targetPage"/>
	<form:errors cssClass="error" path="banner" />
		<br />
<form:label path="creditCard.holderName"> 
		<spring:message code="advertisement.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName"/>
	<form:errors cssClass="error" path="creditCard.holderName"  />
	<br />

	<%-- <form:label path="creditCard.brandName">
		<spring:message code="advertisement.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br /> --%>
	
	<spring:message code="advertisement.creditCard.brandName" />
	<form:select path="creditCard.brandName">
		<form:option value="VISA" label="VISA" />
		<form:option value="MASTERCARD" label="MASTERCARD" />	
		<form:option value="DISCOVER" label="DISCOVER" />
		<form:option value="DINNERS" label="DINNERS" />	
		<form:option value="AMEX" label="AMEX" />
	</form:select>
	<br/>
	
	<form:label path="creditCard.number">
		<spring:message code="advertisement.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="advertisement.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="advertisement.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />
	
	<form:label path="creditCard.CVV">
		<spring:message code="advertisement.creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br /> 
	
	<form:label path="newspaper">
		<spring:message code="advertisement.newspaper"/>
	</form:label>
		<form:select path="newspaper">
		<form:options items="${newspapers}" itemLabel="title" itemValue="id"/>
	</form:select>
	<br />
	<br />
	
	<security:authorize access="hasRole('AGENT')">
	
	<input type="submit" name="save"
	value="<spring:message code="advertisement.save" />" />&nbsp; 

	</security:authorize>
	
	
	<input type="button" name="cancel"
		value="<spring:message code="advertisement.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
</form:form>