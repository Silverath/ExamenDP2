<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="users" requestURI="user/list.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="user.name" var="name" />
		<display:column property="name" title="${name}" 
		sortable="true" />
	
		
		<spring:message code="user.articles" var="articlesHeader" />	
		<display:column title="${articlesHeader}">	
			<a href="user/listArticles.do?userId=${row.id}">
				<spring:message	code="user.articles" />
			</a>
		</display:column>
		
	<display:column>
		<a href="user/display.do?userId=${row.id}">
			<spring:message	code="user.display" />
		</a>
	</display:column>
		<security:authorize access="hasRole('USER')">
	
	<display:column>
		<jstl:if test="${loged!=row.id}">
			<jstl:choose>
				<jstl:when test="${followUser.contains(row)}">
				
					<a href="user/follow.do?userId=${row.id}">
						<spring:message code="user.unfollow" />
					</a>					
					
				</jstl:when>
				<jstl:otherwise>
					<a href="user/follow.do?userId=${row.id}">
						<spring:message code="user.follow" />
					</a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
	
	</security:authorize>
	
</display:table>
