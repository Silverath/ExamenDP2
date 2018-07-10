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
<display:table name="users" id="row" requestURI="follow/listFollower.do"
	pagesize="5" class="displaytag">

	<spring:message code="user.name" var="name"></spring:message>
	<display:column property="name" title="${name}" sortable="false"></display:column>
	
	<spring:message code="user.surname" var="surname"></spring:message>
	<display:column property="surname" title="${surname}" sortable="false"></display:column>
	
	<spring:message code="user.display" />
	<display:column>
			<a href="user/display.do?userId=${row.id}">
				<spring:message	code="user.display" />
			</a>
	</display:column>
	
	<%-- <security:authorize access="hasRole('USER')">
	
	<display:column>
		<jstl:if test="${loged!=row.id}">
			<jstl:choose>
				<jstl:when test="${followUser.contains(row)}">
				
					<a href="follow/listFollowers.do?actorId=${row.id}">
						<spring:message code="user.unfollow" />
					</a>					
					
				</jstl:when>
				<jstl:otherwise>
					<a href="follow/listFollowers.do?actorId=${row.id}">
						<spring:message code="user.follow" />
					</a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
	
	</security:authorize> --%>
	

</display:table>