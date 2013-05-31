<%@ page import="Encryption.KeyManager.Resource" %>



<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="resource.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${resourceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="resource.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${resourceInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'rKey', 'error')} required">
	<label for="rKey">
		<g:message code="resource.rKey.label" default="RK ey" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="rKey" name="rKey.id" from="${Encryption.KeyManager.ResourceKey.list()}" optionKey="id" required="" value="${resourceInstance?.rKey?.id}" class="many-to-one"/>
</div>

