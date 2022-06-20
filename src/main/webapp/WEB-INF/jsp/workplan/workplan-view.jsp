<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="workplan.title"/>
<fmt:message var="tableTitle" key="workplan.table.title"/>

<fmt:message var="num" key="workplan.number"/>
<fmt:message var="scale" key="workplan.scale"/>
<fmt:message var="type" key="workplan.type"/>
<fmt:message var="time" key="workplan.time"/>
<fmt:message var="sender" key="workplan.lodger"/>
<fmt:message var="workers" key="workplan.workers.number"/>
<fmt:message var="done" key="workplan.done"/>
<fmt:message var="doneTrue" key="workplan.done.true"/>
<fmt:message var="doneFalse" key="workplan.done.false"/>
<fmt:message var="process" key="workplan.process"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<u:back/>
<table>
    <thead>
    <tr>
        <th colspan="6">${tableTitle}</th>
    </tr>
    <tr>
        <td>${num}</td>
        <td>${scale}</td>
        <td>${type}</td>
        <td>${time}</td>
        <td>${sender}</td>
        <td>${workers}</td>
        <td>${done}</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="workplan" items="${workplans}">
        <tr>
            <td>${workplan.id}</td>
            <td><u:scale scale="${workplan.request.workScale}"/></td>
            <td><u:type type="${workplan.request.workType}"/></td>
            <td>${workplan.request.timeToDo}</td>
            <td>${workplan.request.lodger.name}</td>
            <td>
                <u:workers workers="${workplan.brigade.workersId}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${workplan.done==true}">${doneTrue}</c:when>
                    <c:otherwise>${doneFalse}</c:otherwise>
                </c:choose>
            </td>
            <c:if test="${workplan.done==false && sessionScope['currentLodger']=='epam_reviewer'}">
                <c:url var="processed" value="/workplan-processed">
                    <c:param name="id" value="${workplan.id}"/>
                </c:url>
                <td>
                    <form action="${processed}" method="post">
                        <input type="submit" value="${process}"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
