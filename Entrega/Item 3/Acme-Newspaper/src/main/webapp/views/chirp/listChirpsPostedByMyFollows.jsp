<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->
<display:table name="chirps" id="row" requestURI="chirp/listChirpsPostedByMyFollows.do"
	pagesize="5" class="displaytag">

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
	 

	</display:table>

