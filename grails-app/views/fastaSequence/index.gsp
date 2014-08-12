
<%@ page import="kafka.project.FastaSequence" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'fastaSequence.label', default: 'FastaSequence')}" />
	<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>

<section id="index-fastaSequence" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="header" title="${message(code: 'fastaSequence.header.label', default: 'Header')}" />
			
				<g:sortableColumn property="sequence" title="${message(code: 'fastaSequence.sequence.label', default: 'Sequence')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${fastaSequenceInstanceList}" status="i" var="fastaSequenceInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${fastaSequenceInstance.id}">${fieldValue(bean: fastaSequenceInstance, field: "header")}</g:link></td>
			
				<td>${fieldValue(bean: fastaSequenceInstance, field: "sequence")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${fastaSequenceInstanceCount}" />
	</div>
</section>

</body>

</html>
