<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kabirsoneja
  Date: 1/27/20
  Time: 9:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%=request.getAttribute("doctype")%>
<html>
<head>
    <title>Page 2</title>
</head>
<body>
    <p> Choose a bird from the dropdown</p>
    <form action="next" method="get">
    <select name = "pc">
        <c:forEach items="${pictureURL}" var = "birdpage">
            <option value="${birdpage}">${birdpage}</option>
        </c:forEach>
    </select>
        <input type="submit" name="nextsubmit" value ="submit">
    </form>
</body>
</html>
