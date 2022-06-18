<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>Brigade list</title>
</head>
<body>
<u:back/>
<table>
    <thead>
    <tr>
        <th colspan="3">Brigades</th>
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
                    <c:when test="${brigade.busy==true}">Is busy yet</c:when>
                    <c:otherwise>Workers are available</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>