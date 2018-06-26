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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="advertisements" requestURI="newspaper/listAdvertisements" id="row">

	<!-- Attributes -->
	
	<%-- <spring:message code="advertisement.newspaper" var="newspaperTitle"/>
		<display:column title="${newspaperTitle}">	
		<a href="newspaper/display.do?newspaperId=${row.newspaper.id}">${row.newspaper.title}</a>
		</display:column> --%>
		
	<spring:message code="advertisement.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />

	<spring:message code="advertisement.banner" var="bannerHeader" />
	<display:column property="banner" title="${bannerHeader}" sortable="false" />

	<spring:message code="advertisement.targetPage" var="targetPageHeader" />
	<display:column property="targetPage" title="${targetPageHeader}" sortable="false" />
	
	
	
	
	</display:table>
	
<!-- Action links -->

