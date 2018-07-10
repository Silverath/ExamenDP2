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


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="categories" requestURI="category/list.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="category.name" var="name" />
		<display:column property="name" title="${name}" 
		sortable="true" />
	
	<spring:message code="category.description" var="description" />
		<display:column property="description" title="${description}" 
		sortable="true" />
	
	<spring:message code="category.parent" var="parent" />	
		
		<display:column title="${parent}">	
			${row.parent.name}
		</display:column>
		
	<spring:message code="category.childrens" var="childrens" />	
		<display:column title="${childrens}">	
			<a href="category/listChildren.do?categoryId=${row.id}">
				<spring:message	code="category.childrens" />
			</a>
		</display:column>
	
	<spring:message code="category.rendezvouses" var="rendezvousTitle" />	
		<display:column title="${rendezvousTitle}">	
			<a href="category/listRendezvousesByCategory.do?categoryId=${row.id}">
				<spring:message	code="category.rendezvouses" />
			</a>
		</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.edit" var="editTitle" />
		<display:column title="${editTitle}">
			<a href="category/administrator/edit.do?categoryId=${row.id}">
				<spring:message	code="category.edit" />
			</a>
	</display:column>
	
</security:authorize> 
</display:table>
<br>
<security:authorize access="hasRole('ADMIN')">	
	<a href="category/administrator/create.do">
			<spring:message code="category.create"/>
	</a>
</security:authorize>