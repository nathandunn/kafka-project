<%@ page import="kafka.project.Tweet" %>



<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'message', 'error')} required">
	<label for="message">
		<g:message code="tweet.message.label" default="Message" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="message" required="" value="${tweetInstance?.message}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'postDate', 'error')} required">
	<label for="postDate">
		<g:message code="tweet.postDate.label" default="Post Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="postDate" precision="day"  value="${tweetInstance?.postDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="tweet.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="user" required="" value="${tweetInstance?.user}"/>

</div>

