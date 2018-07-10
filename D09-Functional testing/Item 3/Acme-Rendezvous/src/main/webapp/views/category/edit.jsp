<%--
 * edit.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="childrens"/>	
	
	<form:label path="parent">
		<spring:message code="category.parent"/>
	</form:label>
	<form:select path="parent">

		<form:options items="${parent}" itemLabel="name" itemValue="id"/>
	</form:select>
	
		<br />
		<br />
	
	<form:label path="name">
		<spring:message code="category.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
		<br />
	
	<form:label path="description">
		<spring:message code="category.description" />
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
		<br />
	
	<form:label path="benefits">
		<spring:message code="category.benefits" />:
	</form:label>
	<br>
	<form:select mulitple="true" path="benefits">
		<form:options items="${benefits}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="benefits" />
	<br />
		<br />
			<br />
	
	
	<input type="submit" name="save"
		value="<spring:message code="category.save" />" />&nbsp; 
	<jstl:if test="${category.id != 0}">
	
	<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
		onclick="javascript: relativeRedir('category/list.do');" />
	<br />
	
	</form:form>