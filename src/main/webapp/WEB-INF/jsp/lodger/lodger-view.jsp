<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="lodger.view.title"/>
<fmt:message var="tableTitle" key="lodger.table.title"/>
<fmt:message var="delete" key="lodger.delete.button"/>
<fmt:message var="addButton" key="lodger.create.add.button"/>

<html>
<head>
    <title>${title} </title>
</head>
<body>
<u:back/>
<table>
    <thead>
    <tr>
        <th colspan="2">${tableTitle}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="lodger" items="${lodgers}">
        <tr>
            <td>${lodger.name}</td>
            <c:if test="${sessionScope['currentLodger']==lodger.name || sessionScope['currentLodger']=='epam_reviewer'}">
                <c:url value="/lodger-delete" var="deleteUrl">
                    <c:param name="lodgerId" value="${lodger.id}"/>
                </c:url>
                <td>
                    <form action="${deleteUrl}" method="post">
                        <input type="submit" value="${delete}"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${sessionScope['currentLodger']=='epam_reviewer'}">
    <form action="<c:url value="lodger-add"/>">
        <input type="submit" value="${addButton}"/>
    </form>
</c:if>
</body>
</html>
