<%@ page import="Encryption.KeyManager.ResourceKey" %>



<div class="fieldcontain ${hasErrors(bean: resourceKeyInstance, field: 'encryptionKey', 'error')} required">
	<label for="encryptionKey">
		<g:message code="resourceKey.encryptionKey.label" default="Encryption Key" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="encryptionKey" required="" value="${resourceKeyInstance?.encryptionKey}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resourceKeyInstance, field: 'resource', 'error')} ">
	<label for="resource">
		<g:message code="resourceKey.resource.label" default="Resource" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${resourceKeyInstance?.resource?}" var="r">
    <li><g:link controller="resource" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="resource" action="create" params="['resourceKey.id': resourceKeyInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'resource.label', default: 'Resource')])}</g:link>
</li>
</ul>

</div>

