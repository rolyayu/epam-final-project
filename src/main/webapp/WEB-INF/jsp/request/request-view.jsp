<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="request.view.title"/>

<fmt:message var="tableTitle" key="request.table.title"/>
<fmt:message var="scale" key="request.table.scale"/>
<fmt:message var="time" key="request.table.time"/>
<fmt:message var="type" key="request.table.type"/>
<fmt:message var="lodger" key="request.table.lodger"/>
<fmt:message var="inProcess" key="request.table.inprocess"/>
<fmt:message var="inProcessTrue" key="request.table.inprocess.true"/>
<fmt:message var="inProcessFalse" key="request.table.inprocess.false"/>

<fmt:message var="decline" key="request.decline.request"/>
<fmt:message var="process" key="request.process.button"/>
<fmt:message var="send" key="request.send.button"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<u:back/>
<table>
    <thead>
    <tr>
        <th colspan="5">${tableTitle}</th>
    </tr>
    <tr>
        <td>${scale}</td>
        <td>${type}</td>
        <td>${time}</td>
        <td>${lodger}</td>
        <td>${inProcess}</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td><u:scale scale="${request.workScale}"/></td>
            <td><u:type type="${request.workType}"/></td>
            <td>${request.timeToDo}</td>
            <td>${request.lodger.name}</td>
            <td><c:choose>
                <c:when test="${request.inProcess==true}">${inProcessTrue}</c:when>
                <c:otherwise>${inProcessFalse}</c:otherwise>
            </c:choose></td>
            <c:if test="${request.inProcess==false
            && sessionScope['currentLodger']==request.lodger.name
            || sessionScope['currentLodger']=='epam_reviewer'}">
                <c:url value="request-delete" var="requestDelete">
                    <c:param name="requestId" value="${request.id}"/>
                </c:url>
                <td>
                    <form action="${requestDelete}" method="post">
                        <button type="submit">${decline}</button>
                    </form>
                </td>
            </c:if>
            <c:if test="${ sessionScope['currentLodger']=='epam_reviewer'
            && request.inProcess==false}">
                <c:url value="request-process" var="requestProcess">
                    <c:param name="requestId" value="${request.id}"/>
                </c:url>
                <td>
                    <form action="${requestProcess}" method="post">
                        <button type="submit">${process}</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${sessionScope['currentLodger']!='epam_reviewer'}">
    <c:url value="/lodger-select" var="lodgerSendRequest"/>
    <form action="${lodgerSendRequest}" method="post">
        <input type="submit" value="${send}"/>
    </form>
</c:if>

<c:if test="${not empty param['message']}">
    <h1>${param['message']}</h1>
</c:if>
</body>
</html>
