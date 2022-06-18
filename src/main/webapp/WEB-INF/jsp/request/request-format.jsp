<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<c:url var="sendRequest" value="lodger-send-request"/>
<form action="${sendRequest}" method="post">
  <select name="scale">
    <option value="SMALL">SMALL</option>
    <option value="MEDIUM">MEDIUM</option>
    <option value="LARGE">LARGE</option>
  </select>
  <select name="workType">
    <option value="TRAVELING">TRAVELING</option>
    <option value="REMOTE">REMOTE</option>
    <option value="PART_TIME">PART TIME</option>
    <option value="HOME">HOME</option>
    <option value="REGULAR">REGULAR</option>
  </select>
  <input type="text"  id="time" name="time" required size="10">
  <button type="submit">
    Send
  </button>
</form>
</body>
</html>
