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
	name="articles" requestURI="newspaper/listArticles.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="article.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />

	<spring:message code="article.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="article.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" sortable="false" />
	
	<spring:message code="article.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}" sortable="false" />

	<spring:message code="article.draftMode" var="draftModeHeader" />
	<display:column property="draftMode" title="${draftModeHeader}" sortable="false" />
	
	
	</display:table>
	
	
<!-- Action links -->

