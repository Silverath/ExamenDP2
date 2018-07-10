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
	name="newspapers" requestURI="newspaper/listPublish.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="newspaper.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />

	<spring:message code="newspaper.publication" var="publicationHeader" />
	<display:column property="publication" title="${publicationHeader}" sortable="true" format="{0,date,yyyy/MM/dd}" />

	<spring:message code="newspaper.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="newspaper.articleTitle" var="articleTitle"/>
	<display:column title ="${articleTitle}">
	<a href="newspaper/listArticles.do?newspaperId=${row.id}"><spring:message code="newspaper.article" />
	</a>
	</display:column>
	
	<spring:message code="newspaper.display" var="displayTitle"/>
	<display:column title ="${displayTitle}">
	<a href="newspaper/display.do?newspaperId=${row.id}"><spring:message code="newspaper.display" />
	</a>
	</display:column>
	
	<spring:message code="newspaper.public.private" var="publicTitle"/>
	<display:column title ="${publicTitle}">
	<jstl:choose>
		<jstl:when test="${row.isOpen == false}">
			<a href="newspaper/makePublic.do?newspaperId=${row.id}"><spring:message code="newspaper.make.public" />
			</a>
		</jstl:when>
		<jstl:when test="${row.isOpen == true}">
			<a href="newspaper/makePrivate.do?newspaperId=${row.id}"><spring:message code="newspaper.make.private" />
			</a>
		</jstl:when>
	</jstl:choose>
	</display:column>
	 
	<spring:message code="newspaper.publish" var="publishTitle"/>
	<display:column title ="${publishTitle}">
	<jstl:choose>
		<jstl:when test="${row.publish == false}">
			<a href="newspaper/publish.do?newspaperId=${row.id}"><spring:message code="newspaper.publish" />
			</a>
		</jstl:when>
		<jstl:when test="${row.publish == true}">
			<spring:message code="newspaper.ispublish"/>
		</jstl:when>
	</jstl:choose>
	</display:column>
	
	 <security:authorize access="hasRole('USER')">
	 	<spring:message code="article.create" var="articleCreateTitle"/>
	<display:column title ="${articleCreateTitle}">
	<a href="article/create.do?newspaperId=${row.id}"><spring:message code="article.create" />
	</a>
	</display:column>
	 </security:authorize>
	
	
	<security:authorize access="hasRole('USER')">
	<display:column>	
		<a href="newspaper/edit.do?newspaperId=${row.id}"><spring:message code="newspaper.edit" />	
		</a>
	</display:column>
	</security:authorize>
	</display:table>
	
<!-- Action links -->

	<security:authorize access="hasRole('USER')">
			<a href="newspaper/create.do">
				<spring:message	code="newspaper.create" />
			</a>
				
	</security:authorize>