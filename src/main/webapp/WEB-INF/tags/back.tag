<%@ tag pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="back" key="back.to.title"/>

<c:url var="titlePage" value="/title-page.jsp"/>
<a href="${titlePage}" style="align-content: end">${back}</a>