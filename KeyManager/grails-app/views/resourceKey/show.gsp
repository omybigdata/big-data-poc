
<%@ page import="Encryption.KeyManager.ResourceKey" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'resourceKey.label', default: 'ResourceKey')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-resourceKey" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-resourceKey" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list resourceKey">
			
				<g:if test="${resourceKeyInstance?.encryptionKey}">
				<li class="fieldcontain">
					<span id="encryptionKey-label" class="property-label"><g:message code="resourceKey.encryptionKey.label" default="Encryption Key" /></span>
					
						<span class="property-value" aria-labelledby="encryptionKey-label"><g:fieldValue bean="${resourceKeyInstance}" field="encryptionKey"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${resourceKeyInstance?.resource}">
				<li class="fieldcontain">
					<span id="resource-label" class="property-label"><g:message code="resourceKey.resource.label" default="Resource" /></span>
					
						<g:each in="${resourceKeyInstance.resource}" var="r">
						<span class="property-value" aria-labelledby="resource-label"><g:link controller="resource" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${resourceKeyInstance?.id}" />
					<g:link class="edit" action="edit" id="${resourceKeyInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
