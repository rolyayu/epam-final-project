<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="title.title"/>
<fmt:message var="logout" key="logout.button"/>
<fmt:message var="lodgers" key="title.lodgers.view"/>
<fmt:message var="requests" key="title.requests.view"/>
<fmt:message var="brigades" key="title.brigades.view"/>
<fmt:message var="plans" key="title.plans.view"/>

<html>
<head>
    <title>
        ${title}
    </title>
</head>
<body>

<u:locale/>

<c:if test="${not empty sessionScope['currentLodger']}">
    <h4>
        <a href="logout" style="text-align: right">${logout}</a>
    </h4>
</c:if>

<h1>
    <a href="lodger-view">${lodgers}</a></h1>
<h1>
    <a href="request-view">${requests}</a></h1>
<h1>
    <a href="brigade-view">${brigades}</a></h1>
<h1>
    <a href="workplan-view">${plans}</a></h1>
</body>
</html>
