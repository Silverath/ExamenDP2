<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="volume/edit.do" modelAttribute="volume">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publisher"/>
	
	<form:label path="title">
		<spring:message code="volume.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	
	<br />
	<br/>
	
	<form:label path="description">
		<spring:message code="volume.description"/>
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description" />
	
	<br />
	<br/>
	
	<form:label path="year">
		<spring:message code="volume.year"/>
	</form:label>
	<form:input path="year"/>
	<form:errors cssClass="error" path="year" />
	
	<br />
	<br/>
	
	<form:label path="newspapers">
		<spring:message code="volume.newspapersEdit" />:
	</form:label>
	<br>
	<form:select mulitple="true" path="newspapers">
		<form:options items="${news}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="newspapers" />
	
	
	<security:authorize access="hasRole('USER')">	
	<input type="submit" name="save"
		value="<spring:message code="volume.save" />" />&nbsp; 
	</security:authorize>	
	


	<input type="button" name="cancel"
		value="<spring:message code="volume.cancel" />"
		onclick="javascript: relativeRedir('volume/list.do');" />
	<br />
	
	</form:form>