
<%@ page import="kafka.project.Fastq" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'fastq.label', default: 'Fastq')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>


<g:uploadForm action="upload2">
    %{--<g:hiddenField name="id" value="${erpAnalysisResultInstance.id}"/>--}%
    <input type="file" name="file"/>
    <input type="submit" value="Upload"/>
</g:uploadForm>



<section id="list-fastq" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="header" title="${message(code: 'fastq.header.label', default: 'Header')}" />
			
				<g:sortableColumn property="quality" title="${message(code: 'fastq.quality.label', default: 'Quality')}" />
			
				<g:sortableColumn property="sequence" title="${message(code: 'fastq.sequence.label', default: 'Sequence')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${fastqInstanceList}" status="i" var="fastqInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${fastqInstance.id}">${fieldValue(bean: fastqInstance, field: "header")}</g:link></td>
			
				<td>${fieldValue(bean: fastqInstance, field: "quality")}</td>
			
				<td>${fieldValue(bean: fastqInstance, field: "sequence")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${fastqInstanceCount}" />
	</div>
</section>

</body>

</html>
