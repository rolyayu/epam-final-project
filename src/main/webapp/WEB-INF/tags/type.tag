<%@ tag pageEncoding="UTF-8" language="java" %>
<%@attribute name="type" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="home" key="worktype.home"/>
<fmt:message var="traveling" key="worktype.traveling"/>
<fmt:message var="remote" key="worktype.remote"/>
<fmt:message var="part" key="worktype.parttime"/>
<fmt:message var="regular" key="worktype.regular"/>

<c:choose>
    <c:when test="${type=='HOME'}">${home}</c:when>
    <c:when test="${type=='PART_TIME'}">${part}</c:when>
    <c:when test="${type=='REGULAR'}">${regular}</c:when>
    <c:when test="${type=='REMOTE'}">${regular}</c:when>
    <c:otherwise>${traveling}</c:otherwise>
</c:choose>
