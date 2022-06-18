<%@ tag pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="eng" key="change.locale.en"/>
<fmt:message var="rus" key="change.locale.ru"/>

<c:url var="changeRu" value="change-locale?locale=ru"/>
<c:url var="changeEng" value="change-locale?locale=en"/>

<c:choose>
    <c:when test="${sessionScope['locale']=='ru'}">
        ${rus} /
        <form method="post" action="${changeEng}">
            <button type="submit">${eng}</button>
        </form>
    </c:when>
    <c:otherwise>
        <form method="post" action="${changeRu}">
            <button type="submit">${rus}</button>
        </form>
        / ${eng}
    </c:otherwise>
</c:choose>
