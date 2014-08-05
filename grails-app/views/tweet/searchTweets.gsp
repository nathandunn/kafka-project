<%@ page import="kafka.project.Tweet" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tweet.label', default: 'Tweet')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <g:javascript src="angular.js"/>
    <g:javascript src="search.js"/>
</head>

<body>
<a href="#show-tweet" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-tweet" class="content scaffold-show" role="main" ng-app="searchApp">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>


    <div ng-controller="SearchController">
        <label for="search">Search</label>
        <input name="search" type="search" ng-model="search" ng-change="performSearch();">
        <button type="button" value="Search" ng-click="performSearch();">Search</button>
        [{{tweetResults.length}}]


        <table>
            <tr ng-repeat="tweet in tweetResults">
                <td>{{tweet.message}}</td>
                <td>{{tweet.user}}</td>
                <td>{{tweet.tags}}</td>
            </tr>
        </table>
    </div>



    %{--<g:form url="[resource:tweetInstance, action:'delete']" method="DELETE">--}%
    %{--<fieldset class="buttons">--}%
    %{--<g:link class="edit" action="edit" resource="${tweetInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
    %{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
    %{--</fieldset>--}%
    %{--</g:form>--}%
</div>
</body>
</html>
