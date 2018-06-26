
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

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="subscription.list" /></p>

<display:table
	name="subscription"
	id="row"
	requestURI="subscription/list.do"
	pagesize="5"
	keepStatus="true"
	class="displaytag">
	
	<!-- Attributes -->


	 
	 <spring:message code="subscription.customer" var="customer"/>
	<display:column title="${customer}">
		${row.customer.name}
		${row.customer.surname}
	</display:column>
	
		<spring:message code="subscription.newspaper" var="newspaper"/>
	<display:column title="${newspaper}">
		<a href="newspaper/display.do?newspaperId=${row.newspaper.id}">${row.newspaper.title}
		</a>
	</display:column>
	
		<spring:message code="subscription.volume" var="volume"/>
	<display:column title="${volume}">
		<a href="volume/listNewspapers.do?volumeId=${row.volume.id}">${row.volume.title}
		</a>
	</display:column>
	

<%-- 	<security:authorize access="hasRole('MANAGER')">
		<display:column title ="Edit">
			<a href="application/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize> --%>
	
	



</display:table>