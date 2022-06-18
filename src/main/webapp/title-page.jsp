<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="title.title"/>
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
<h1><a href="lodger-view">${lodgers}</a></h1>
<h1><a href="request-view">${requests}</a></h1>
<h1><a href="brigade-view">${brigades}</a></h1>
<h1><a href="workplan-view">${plans}</a></h1>
<h1><a href="brigade-view.html">brigade</a></h1>
</body>
</html>
