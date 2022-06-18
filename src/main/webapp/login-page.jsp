<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="login.title"/>
<fmt:message var="enter" key="login.enter"/>
<fmt:message var="login" key="login.button"/>

<u:locale/>

<html>
<head>
    <title>${title}</title>
</head>
<body>

<c:url var="titlePage" value="lodger-login"/>
    <form method="post" action="${titlePage}">
        <input type="text" id="lodgerName" name="lodgerName" value="${enter}">
        <input type="submit" value="${login}">
    </form>
</body>

<c:if test="${not empty param['message']}">
    <h1>${param['message']}</h1>
</c:if>
</html>
