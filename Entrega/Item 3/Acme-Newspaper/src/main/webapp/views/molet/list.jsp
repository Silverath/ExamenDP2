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
	name="molets" requestURI="molet/list.do" id="row">
	
	<jstl:if test="${row.gauge == 1}">
		<jstl:set var="style" value="background-color:LightSteelBlue" />
	</jstl:if>
	
	<jstl:if test="${row.gauge == 2}">
		<jstl:set var="style" value="background-color:DarkBlue" />
	</jstl:if>
	
	<jstl:if test="${row.gauge == 3}">
		<jstl:set var="style" value="background-color:Gray" />
	</jstl:if>

<security:authentication property="principal" var ="loggedactor"/>
	<!-- Attributes -->
	
	<spring:message code="molet.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" style="${style}"/>

	<spring:message code="molet.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="true" style="${style}"/>

	<spring:message code="molet.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" style="${style}"/>
	
	<spring:message code="molet.newspaper" var="newspaperHeader" />
	<display:column title="${newspaperHeader}" style="${style}">
	${row.newspaper.title}
	 </display:column>
	 
	 <spring:message code="molet.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true" style="${style}"/>
	 
	 <security:authorize access="hasRole('ADMIN')">
	 
	<spring:message code="molet.moment" var="momentHeader" />
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,yy/MM/dd HH:mm}" style="${style}"/>
			</jstl:when>
			
			<jstl:otherwise>
				<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd-MM-yy HH:mm}" style="${style}"/>
			</jstl:otherwise>
		</jstl:choose>
	<display:column style="${style}">	
		<jstl:if test="${row.administrator.userAccount.username==loggedactor.username and row.finalMode == false}">
		<a href="molet/edit.do?moletId=${row.id}"><spring:message code="molet.edit" />	
		</a>
		</jstl:if>
	</display:column>
	
	<display:column style="${style}">	
		<jstl:if test="${row.administrator.userAccount.username==loggedactor.username}">
		<a href="molet/delete.do?moletId=${row.id}"><spring:message code="molet.delete" />	
		</a>
		</jstl:if>
	</display:column>
	
	<display:column style="${style}">	
		<jstl:if test="${row.administrator.userAccount.username==loggedactor.username and row.finalMode == true and row.newspaper == null}">
		<a href="molet/selectNewspaper.do?moletId=${row.id}"><spring:message code="molet.selectNewspaper" />	
		</a>
		</jstl:if>
	</display:column>
	
	</security:authorize>
	
	</display:table>
	
	<br/>
	<security:authorize access="hasRole('ADMIN')">
		<a href="molet/create.do"><spring:message code="molet.create" />
		</a>
	</security:authorize>
	
<!-- Action links -->



	
