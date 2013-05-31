
<%@ page import="Encryption.KeyManager.DataSet" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataSet.label', default: 'DataSet')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataSet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dataSet" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'dataSet.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'dataSet.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="eKey" title="${message(code: 'dataSet.eKey.label', default: 'EK ey')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataSetInstanceList}" status="i" var="dataSetInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dataSetInstance.id}">${fieldValue(bean: dataSetInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: dataSetInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: dataSetInstance, field: "eKey")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dataSetInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
