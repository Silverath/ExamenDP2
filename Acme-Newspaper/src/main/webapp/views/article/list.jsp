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
	name="articles" requestURI="article/list.do" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="article.title" var="title"/>
	<display:column property="title" title="${title}" sortable="true" />
	
	<spring:message code="article.moment" var="moment"/>
	<display:column property="moment" title="${moment}" sortable="true"/>
	
	<spring:message code="article.pictures" var="pictures"/>
	<display:column property="pictures" title="${pictures}" sortable="true"/>
	
	<spring:message code="article.draftMode" var="draftMode"/>
	<display:column property="draftMode" title="${draftMode}" sortable="true"/>
		
	<spring:message code="article.newspaper" var="newspaperTitle"/>
		<display:column title="${newspaperTitle}">	
		<a href="newspaper/display.do?newspaperId=${row.newspaper.id}">${row.newspaper.title}</a>
		</display:column>	
		
	<spring:message code="article.user" var="userHeader" />
	<display:column title="${userHeader}">
	${row.user.name}
	 </display:column>
	
	<spring:message code="article.display" var="displayTitle"/>
		<display:column title="${displayTitle}">	
			<a href="article/display.do?articleId=${row.id}">
				<spring:message	code="article.display" />
			</a>
		</display:column> 
	<security:authorize access="hasRole('USER')">
		<spring:message code="article.edit" var="editTitle"/>
		<display:column title="${editTitle}">	
		<jstl:if test="${loggedactor == row.user.userAccount and row.draftMode}">
			<a href="article/edit.do?articleId=${row.id}"><spring:message code="article.edit" />	
			</a>
		</jstl:if>
		</display:column>
	</security:authorize>
	
	 <security:authorize access="hasRole('ADMIN')">
	<display:column>	
		<a href="article/delete.do?articleId=${row.id}"><spring:message code="article.delete" />			
		</a>
	</display:column>
	</security:authorize>
</display:table>
