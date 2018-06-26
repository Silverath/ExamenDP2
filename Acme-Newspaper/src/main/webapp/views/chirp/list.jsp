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

<p><spring:message code="chirp.list" /></p>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chirps" requestURI="chirp/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="chirp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />

	<spring:message code="chirp.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,yyyy/MM/dd}" />

	<spring:message code="chirp.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="chirp.user" var="chirpUser" />
	<display:column title="${chirpUser}">
	${row.user.name}
	 </display:column>
	 
	 <security:authorize access="hasRole('ADMIN')">
	<display:column>	
		<a href="chirp/edit.do?chirpId=${row.id}"><spring:message code="chirp.delete" />	
		</a>
	</display:column>
	</security:authorize>
	
	</display:table>
	
	
	
<!-- Action links -->



	<security:authorize access="hasRole('USER')">
			<a href="chirp/create.do">
				<spring:message	code="chirp.create" />
			</a>
				
	</security:authorize>