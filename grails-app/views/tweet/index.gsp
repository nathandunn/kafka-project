<%@ page import="kafka.project.Tweet" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tweet.label', default: 'Tweet')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-tweet" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-tweet" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    Total tweets: ${tweetInstanceCount}
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="message" title="${message(code: 'tweet.message.label', default: 'Message')}"/>

            <g:sortableColumn property="postDate"
                              title="${message(code: 'tweet.postDate.label', default: 'Post Date')}"/>

            <g:sortableColumn property="user" title="${message(code: 'tweet.user.label', default: 'User')}"/>
            <th>Tags</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${tweetInstanceList}" status="i" var="tweetInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td>
                    <g:if test="${tweetInstance.twitterId}">
                        <g:link url="https://twitter.com/SOMEUSER/status/${tweetInstance.twitterId}">Original</g:link>
                    </g:if>
                    ${tweetInstance.messageStart}
                    %{--<g:link action="show"--}%
                            %{--id="${tweetInstance.id}">${fieldValue(bean: tweetInstance, field: "message")}</g:link>--}%
                </td>

                <td><g:formatDate date="${tweetInstance.postDate}"/></td>

                <td>
                    <g:link url="https://twitter.com/@${tweetInstance.userName}">${tweetInstance.userName}</g:link>
                    %{--${fieldValue(bean: tweetInstance, field: "userName")}--}%
                </td>
                <td>${tweetInstance.tags.split("\\|\\|").join(" ")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${tweetInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
