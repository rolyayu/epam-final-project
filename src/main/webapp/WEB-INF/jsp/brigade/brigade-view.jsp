<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="brigade.title"/>
<fmt:message var="tableTitle" key="brigade.table.title"/>
<fmt:message var="workers" key="brigade.workers"/>
<fmt:message var="busyness" key="brigade.busy"/>
<fmt:message var="isBusy" key="brigade.busy.true"/>
<fmt:message var="available" key="brigade.busy.false"/>
<fmt:message var="dissolveButton" key="brigade.dissolve"/>
<fmt:message var="availableWorkersLable" key="brigade.available.workers"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<u:back/>
<table>
    <thead>
    <tr>
        <th colspan="3">${tableTitle}</th>

    </tr>
    <tr>
        <td>${workers}</td>
        <td>${busyness}</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="brigade" items="${brigades}">
        <tr>
            <td>
                <u:workers workers="${brigade.workersId}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${brigade.busy==true}">${isBusy}</c:when>
                    <c:otherwise>${available}</c:otherwise>
                </c:choose>
            </td>
            <c:if test="${brigade.busy==false && sessionScope['currentLodger']=='epam_reviewer'}">
                <td>
                    <c:url var="dissolve" value="brigade-dissolve">
                        <c:param name="brigadeId" value="${brigade.id}"/>
                    </c:url>
                    <form action="${dissolve}" method="post">
                        <button type="submit">${dissolveButton}</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h3>
    ${availableWorkersLable} ${availableWorkers}
</h3>
</body>
</html>