<%@ page import="kafka.project.FastaSequence" %>



			<div class="${hasErrors(bean: fastaSequenceInstance, field: 'header', 'error')} ">
				<label for="header" class="control-label"><g:message code="fastaSequence.header.label" default="Header" /></label>
				<div>
					<g:textField class="form-control" name="header" value="${fastaSequenceInstance?.header}"/>
					<span class="help-inline">${hasErrors(bean: fastaSequenceInstance, field: 'header', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: fastaSequenceInstance, field: 'sequence', 'error')} ">
				<label for="sequence" class="control-label"><g:message code="fastaSequence.sequence.label" default="Sequence" /></label>
				<div>
					<g:textField class="form-control" name="sequence" value="${fastaSequenceInstance?.sequence}"/>
					<span class="help-inline">${hasErrors(bean: fastaSequenceInstance, field: 'sequence', 'error')}</span>
				</div>
			</div>

