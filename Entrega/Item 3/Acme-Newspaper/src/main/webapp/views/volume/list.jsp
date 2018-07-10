<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	
<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="volumes" requestURI="${requestURI }" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="volume.title" var="newspapersTitle"/>
		<display:column title="${newspapersTitle}">	
		<a href="volume/listNewspapers.do?volumeId=${row.id}">${row.title}</a>
		</display:column>
	
	<spring:message code="volume.description" var="description"/>
	<display:column property="description" title="${description}" sortable="true"/>
	
	<spring:message code="volume.year" var="year"/>
	<display:column property="year" title="${year}" sortable="true"/>
	
	<spring:message code="volume.publisher" var="publisher"/>
	<display:column title="${publisher}">	
		<a href="user/display.do?userId=${row.publisher.id}">${row.publisher.name}</a>
		</display:column>
		
		<security:authorize access="hasRole('USER')">
	<spring:message code="volume.newspapers" var="newspapersTitle"/>
	<spring:message code="volume.add" var="addTitle"/>
		<display:column title="${newspapersTitle}">	
		<a href="volume/add.do?volumeId=${row.id}">${addTitle}</a>
		</display:column>
		</security:authorize>
	
			<security:authorize access="hasRole('USER')">
	<spring:message code="volume.newspapers" var="newspapersTitle"/>	
	<spring:message code="volume.remove" var="removeTitle"/>
		<display:column title="${newspapersTitle}">	
		<a href="volume/remove.do?volumeId=${row.id}">${removeTitle}</a>
		</display:column>
						</security:authorize>
				
	<spring:message code="volume.create" var="createTitle"/>
	<a href="volume/create.do">${createTitle}</a>
	
	
	
		
		
		
</display:table>
