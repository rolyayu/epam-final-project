<%@ tag pageEncoding="UTF-8" language="java" %>
<%@attribute name="scale" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="small" key="workscale.small"/>
<fmt:message var="medium" key="workscale.medium"/>
<fmt:message var="large" key="workscale.large"/>

<c:choose>
    <c:when test="${scale=='SMALL'}">${small}</c:when>
    <c:when test="${scale=='MEDIUM'}">${medium}</c:when>
    <c:otherwise>${large}</c:otherwise>
</c:choose>
