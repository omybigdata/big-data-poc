<%@ page import="Encryption.KeyManager.DataSet" %>



<div class="fieldcontain ${hasErrors(bean: dataSetInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="dataSet.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${dataSetInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataSetInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="dataSet.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${dataSetInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataSetInstance, field: 'eKey', 'error')} ">
	<label for="eKey">
		<g:message code="dataSet.eKey.label" default="EK ey" />
		
	</label>
	<g:textField name="eKey" value="${dataSetInstance?.eKey}"/>
</div>

