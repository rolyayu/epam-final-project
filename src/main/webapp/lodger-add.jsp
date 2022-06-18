
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['locale']=='ru'?'ru':'en'}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="lodger.create.title"/>
<fmt:message var="enter" key="lodger.create.input.title"/>
<fmt:message var="add" key="lodger.create.add.button"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<label for="name">${enter}:</label>
<form action="lodger-save" method="post">
    <input type="text" id="name" name="name" required size="10">
    <button type="submit">
        ${add}
    </button>
</form>

</body>
</html>
