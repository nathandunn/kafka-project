<%@ page import="kafka.project.Fastq" %>



			<div class="${hasErrors(bean: fastqInstance, field: 'header', 'error')} ">
				<label for="header" class="control-label"><g:message code="fastq.header.label" default="Header" /></label>
				<div>
					<g:textField class="form-control" name="header" value="${fastqInstance?.header}"/>
					<span class="help-inline">${hasErrors(bean: fastqInstance, field: 'header', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: fastqInstance, field: 'quality', 'error')} ">
				<label for="quality" class="control-label"><g:message code="fastq.quality.label" default="Quality" /></label>
				<div>
					<g:textField class="form-control" name="quality" value="${fastqInstance?.quality}"/>
					<span class="help-inline">${hasErrors(bean: fastqInstance, field: 'quality', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: fastqInstance, field: 'sequence', 'error')} ">
				<label for="sequence" class="control-label"><g:message code="fastq.sequence.label" default="Sequence" /></label>
				<div>
					<g:textField class="form-control" name="sequence" value="${fastqInstance?.sequence}"/>
					<span class="help-inline">${hasErrors(bean: fastqInstance, field: 'sequence', 'error')}</span>
				</div>
			</div>

