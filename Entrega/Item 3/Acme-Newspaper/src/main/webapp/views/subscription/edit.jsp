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

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="subscription/edit.do" modelAttribute="subscription">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />		

<form:label path="creditCard.holderName"> 
		<spring:message code="subscription.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName"/>
	<form:errors cssClass="error" path="creditCard.holderName"  />
	<br />

<%-- 	<form:label path="creditCard.brandName">
		<spring:message code="subscription.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br /> --%>
	
	<spring:message code="subscription.creditCard.brandName" />
	<form:select path="creditCard.brandName">
		<form:option value="VISA" label="VISA" />
		<form:option value="MASTERCARD" label="MASTERCARD" />	
		<form:option value="DISCOVER" label="DISCOVER" />
		<form:option value="DINNERS" label="DINNERS" />	
		<form:option value="AMEX" label="AMEX" />
	</form:select>
	<br/>
	
	
	<form:label path="creditCard.number">
		<spring:message code="subscription.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="subscription.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="subscription.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />
	
	<form:label path="creditCard.CVV">
		<spring:message code="subscription.creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br /> 


<form:label path="newspaper">
		<spring:message code="subscription.newspaper"/>
	</form:label>
		<form:select path="newspaper">
		<form:options items="${newspaper}" itemLabel="title" itemValue="id"/>
	</form:select>
	<br />
	<br />
	
	<jstl:if test="${subscription.id == 0}">
	<input type="submit" name="save"
		value="<spring:message code="subscription.save" />" />&nbsp; 
	</jstl:if>
		
	<jstl:if test="${subscription.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="subscription.delete" />"
			onclick="return confirm('<spring:message code="subscription.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="subscription.cancel" />"
		onclick="javascript: window.location.replace('subscription/list.do');" />
	<br />


</form:form>

