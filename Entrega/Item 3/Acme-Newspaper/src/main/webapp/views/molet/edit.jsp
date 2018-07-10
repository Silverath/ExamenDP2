<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="molet/edit.do" modelAttribute="molet">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="administrator"/>
	<form:hidden path="ticker"/>
	
	
	
	<form:label path="title">
		<spring:message code="molet.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	<br />
	<br/>
	<form:label path="gauge">
		<spring:message code="molet.gauge"/>
	</form:label>
	<form:input path="gauge"/>
	<form:errors cssClass="error" path="gauge" />
	
	<br />
	<br/>
	<form:label path="description">
		<spring:message code="molet.description"/>
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description" />
	
	<br />
	<br/>
	
	<form:label path="moment">
		<spring:message code="molet.moment"/>
	</form:label>
	<form:input path="moment" placeholder="yyyy/MM/dd HH:mm"/>
	<form:errors cssClass="error" path="moment" />
	
	<spring:message code="molet.finalMode" />:
	<input type="checkbox" name="finalMode"
		<jstl:if test="${molet.finalMode==true}">
			checked
		</jstl:if> 
		value="true" />
	<br>
	
	<input type="submit" name="save"
		value="<spring:message code="molet.save" />" />&nbsp; 
		
	<jstl:if test="${molet.id != 0}">
	
		<input type="submit" name="delete"
				value="<spring:message code="molet.delete" />"
				onclick="return confirm('<spring:message code="molet.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
		

	<input type="button" name="cancel"
		value="<spring:message code="molet.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
	
	</form:form>