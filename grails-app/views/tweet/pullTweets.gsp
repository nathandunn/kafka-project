<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tweet.label', default: 'Tweet')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-tweet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="search" action="search">Search</g:link></li>
                <li><g:link class="search" action="searchTweets">Search Tweets</g:link></li>
			</ul>
		</div>
		<div id="create-tweet" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${tweetInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${tweetInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:tweetInstance, action:'doPullTweets']" >
                <div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'numTweets', 'error')} required">
                    <label for="numTweets">
                        Num Docs
                        <span class="required-indicator">*</span>
                    </label>
                    <g:field type="number" name="numTweets" required="" value="3"/>
                </div>
                <br/>

                %{--<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'pullSeconds', 'error')} required">--}%
                    %{--<label for="pullSeconds">--}%
                        %{--Pull Seconds--}%
                        %{--<span class="required-indicator">*</span>--}%
                    %{--</label>--}%
                    %{--<g:field type="number" name="pullSeconds" required="" value="3"/>--}%
                %{--</div>--}%

                %{--<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'tweetTags', 'error')} required">--}%
                    %{--<label for="tweetTags">--}%
                        %{--Tags--}%
                        %{--<span class="required-indicator">*</span>--}%
                    %{--</label>--}%
                    %{--<g:field type="number" name="tweetTags" required="" value="3"/>--}%
                %{--</div>--}%


                %{--<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'postDate', 'error')} required">--}%
                    %{--<label for="postDate">--}%
                        %{--<g:message code="tweet.postDate.label" default="Post Date" />--}%
                        %{--<span class="required-indicator">*</span>--}%
                    %{--</label>--}%
                    %{--<g:datePicker name="postDate" precision="day"  value="${tweetInstance?.postDate}"  />--}%

                %{--</div>--}%

                %{--<div class="fieldcontain ${hasErrors(bean: tweetInstance, field: 'user', 'error')} required">--}%
                    %{--<label for="user">--}%
                        %{--<g:message code="tweet.user.label" default="User" />--}%
                        %{--<span class="required-indicator">*</span>--}%
                    %{--</label>--}%
                    %{--<g:textField name="user" required="" value="${tweetInstance?.user}"/>--}%

                %{--</div>--}%
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
