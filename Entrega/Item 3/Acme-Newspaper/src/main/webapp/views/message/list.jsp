<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="float: left">
	<spring:message code="folder.reserved" />
	<display:table name="reservFolders" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
		<spring:message code="folder.name" var="name"></spring:message>
		<display:column property="name" title="${name}" sortable="true"></display:column>

		<display:column>
			<a href="message/listByFolder.do?folderId=${row.id}"> <spring:message
					code="folder.see"></spring:message>
			</a>
		</display:column>
	</display:table>
	<input type="button" name="createFolder"
		value="<spring:message code="folder.create"/>"
		onClick="javascript: window.location.replace('${requestURI2}');" />
	<display:table name="folders" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
		<display:column>
			<a href="message/folder/edit.do?folderId=${row.id}"> <spring:message
					code="folder.edit"></spring:message>
			</a>

		</display:column>
		<spring:message code="folder.name" var="name"></spring:message>
		<display:column property="name" title="${name}" sortable="true"></display:column>
		<display:column>
			<a href="message/listByFolder.do?folderId=${row.id}"> <spring:message
					code="folder.see"></spring:message>
			</a>
		</display:column>
	</display:table>

</div>
<div style="float:middle;">
	<input type="button" name="create"
		value="<spring:message code="message.create"/>"
		onClick="javascript: window.location.replace('message/create.do');" />
	&nbsp;
	<display:table name="messages" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag" style="width: 40%;">
		<spring:message code="message.subject" var="subject"></spring:message>
		<display:column property="subject" title="${subject}" sortable="false"></display:column>

		<spring:message code="message.moment" var="moment"></spring:message>
		<display:column property="moment" title="${moment}" sortable="true"></display:column>

		<spring:message code="message.body" var="body"></spring:message>
		<display:column property="body" title="${body}" sortable="false"></display:column>

		<spring:message code="message.sender" var="send1"></spring:message>
		<display:column title="${send1}" value="${send[row_rowNum]}"></display:column>

		<spring:message code="message.recipient" var="rec1"></spring:message>
		<display:column title="${rec1}" value="${rec[row_rowNum]}"></display:column> 


		<display:column>
			<a href="message/move.do?messageId=${row.id}"> <spring:message
					code="message.move"></spring:message>
			</a>
		</display:column>

		<display:column>
			<a href="message/delete.do?mId=${row.id}"> <spring:message
					code="folder.delete"></spring:message>
			</a>
		</display:column>

	</display:table>
</div>