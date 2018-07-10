<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>

	<jstl:when test="${option == true }">
	
	<form:form action="volume/addNews.do" modelAttribute="volume">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publisher"/>
	<form:hidden path="title"/>
	<form:hidden path="year"/>
	<form:hidden path="description"/>
	
	<form:label path="newspapers">
		<spring:message code="volume.select" />:
	</form:label>
	<br>
	<form:select mulitple="true" path="newspapers">
		<form:options items="${news}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="newspapers" />
	
	<input type="submit" name="save"
		value="<spring:message code="volume.confirm" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="volume.cancel" />"
		onclick="javascript: relativeRedir('volume/list.do');" />
	<br />
	
	</form:form>
	</jstl:when>
	
	<jstl:when test="${option == false }">
	
	<form:form action="volume/removeNews.do" modelAttribute="volume">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publisher"/>
	<form:hidden path="title"/>
	<form:hidden path="year"/>
	<form:hidden path="description"/>
	
	<form:label path="newspapers">
		<spring:message code="volume.selectRemove" />:
	</form:label>
	<br>
	<form:select mulitple="true" path="newspapers">
		<form:options items="${news}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="newspapers" />
	
	<input type="submit" name="remove"
		value="<spring:message code="volume.remove" />" />&nbsp; ; 

	<input type="button" name="cancel"
		value="<spring:message code="volume.cancel" />"
		onclick="javascript: relativeRedir('volume/list.do');" />
	<br />
	
	</form:form>
	
	</jstl:when>
</jstl:choose>