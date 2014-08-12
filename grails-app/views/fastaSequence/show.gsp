
<%@ page import="kafka.project.FastaSequence" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'fastaSequence.label', default: 'FastaSequence')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-fastaSequence" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="fastaSequence.header.label" default="Header" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: fastaSequenceInstance, field: "header")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="fastaSequence.sequence.label" default="Sequence" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: fastaSequenceInstance, field: "sequence")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
