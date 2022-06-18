<%@ tag pageEncoding="UTF-8" language="java" %>
<%@attribute name="workers" required="true" rtexprvalue="true" type="java.lang.Long[]" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="workerId" items="${workers}">
    <c:out value="${workerId} "/>
</c:forEach>
