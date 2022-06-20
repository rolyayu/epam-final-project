<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="messages"/>

<fmt:message var="title" key="request.send.title"/>
<fmt:message var="small" key="workscale.small"/>
<fmt:message var="medium" key="workscale.medium"/>
<fmt:message var="large" key="workscale.large"/>

<fmt:message var="home" key="worktype.home"/>
<fmt:message var="traveling" key="worktype.traveling"/>
<fmt:message var="remote" key="worktype.remote"/>
<fmt:message var="part" key="worktype.parttime"/>
<fmt:message var="regular" key="worktype.regular"/>

<fmt:message var="send" key="request.send.button"/>
<fmt:message var="sender" key="request.sender.name"/>

<html>
<head>
  <title>${title}</title>
</head>
<body>
<p>${sender} ${sessionScope['currentLodger']}</p>
<c:url var="sendRequest" value="request-send"/>
<form action="${sendRequest}" method="post">
  <select name="scale">
    <option value="SMALL" >${small}</option>
    <option value="MEDIUM">${medium}</option>
    <option value="LARGE">${large}</option>
  </select>
  <select name="workType">
    <option value="TRAVELING">${traveling}</option>
    <option value="REMOTE">${remote}</option>
    <option value="PART_TIME">${part}</option>
    <option value="HOME">${home}</option>
    <option value="REGULAR">${regular}</option>
  </select>
  <input type="text"  id="time" name="time" required size="10">
  <button type="submit">
    ${send}
  </button>
</form>
</body>
</html>
