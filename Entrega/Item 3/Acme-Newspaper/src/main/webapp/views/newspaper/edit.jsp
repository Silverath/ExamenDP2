<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="newspaper/edit.do" modelAttribute="newspaper">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author"/>
	<form:hidden path="subscriptions"/>
	<form:hidden path="articles"/>
	<form:hidden path="publish"/>
	<form:hidden path="volumes"/>
	
	<form:label path="title">
		<spring:message code="newspaper.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	
	<br />
	<br/>
	
	<form:label path="publication">
		<spring:message code="newspaper.publication"/>
	</form:label>
	<form:input path="publication" placeholder="yyyy/MM/dd"/>
	<form:errors cssClass="error" path="publication" />
	
	<br />
	<br/>
	<form:label path="description">
		<spring:message code="newspaper.description"/>
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description" />
	
	<br />
	<br/>
	
	<form:label path="picture">
		<spring:message code="newspaper.picture"/>
	</form:label>
	<form:input path="picture"/>
	<form:errors cssClass="error" path="picture" />
	
	<br />
	<br/>
	
	
	
	<form:label path="isOpen">
		<spring:message code="newspaper.isOpen"/>
	</form:label>
	<form:radiobutton path="isOpen" value="true"/><spring:message code="newspaper.true"/>
	<form:radiobutton path="isOpen" value="false"/><spring:message code="newspaper.false"/>
	
	<br/>
	<br/>
	
	<security:authorize access="hasRole('USER')">	
	<input type="submit" name="save"
		value="<spring:message code="newspaper.save" />" />&nbsp; 
	</security:authorize>	
	


	<input type="button" name="cancel"
		value="<spring:message code="newspaper.cancel" />"
		onclick="javascript: relativeRedir('newspaper/list.do');" />
	<br />
	
	</form:form>