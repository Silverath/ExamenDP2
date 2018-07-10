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
	name="articles" requestURI="article/listSearch.do" id="row">
	
	<spring:message code="article.title" var="title"/>
	<display:column property="title" title="${title}" sortable="true"/>
	
	<spring:message code="article.moment" var="moment"/>
	<display:column property="moment" title="${moment}" sortable="true"/>
	
	<spring:message code="article.summary" var="summary"/>
	<display:column property="summary" title="${summary}" sortable="true"/>
		
	<spring:message code="article.newspaper" var="newspaper" />	
	<spring:message code="article.newspaper" var="newspaperTitle"/>
		<display:column title="${newspaperTitle}">	
			${row.newspaper.title}"
		</display:column>
		
	<%-- <spring:message code="article.user" var="user" />	
	<spring:message code="article.user" var="userTitle"/>
		<display:column title="${userTitle}">	
			<a href="article/displayUser.do?articleId=${row.id}">
				<spring:message	code="article.user" />
			</a>
		</display:column> 
		 --%>
</display:table>
