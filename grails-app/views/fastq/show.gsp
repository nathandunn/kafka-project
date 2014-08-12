
<%@ page import="kafka.project.Fastq" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'fastq.label', default: 'Fastq')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-fastq" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="fastq.header.label" default="Header" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: fastqInstance, field: "header")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="fastq.quality.label" default="Quality" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: fastqInstance, field: "quality")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="fastq.sequence.label" default="Sequence" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: fastqInstance, field: "sequence")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
