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

<form:form action="audit/edit.do" modelAttribute="audit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="administrator"/>
	<form:hidden path="moment"/>
	<form:hidden path="ticker"/>
	<form:hidden path="title"/>
	<form:hidden path="gauge"/>
	<form:hidden path="description"/>
	
	<form:label path="newspaper">
		<spring:message code="audit.selectNewspaper" />:
	</form:label>
	<br>
	<form:select mulitple="false" path="newspaper">
		<form:options items="${select}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="newspaper" />
	
	<input type="submit" name="save"
		value="<spring:message code="audit.save" />" />&nbsp;
	
	<input type="button" name="cancel"
		value="<spring:message code="audit.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
	
</form:form>	
