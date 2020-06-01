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
    <title>Migratory Birds</title>
</head>
<body>
<form action="birds" method="get">
<h1>Bird Chosen : ${birdchosen}</h1>

<img action = "/res" method="get">
    <img src = "<%=request.getAttribute("abc")%>"> </img>
    <p>Credits : ${link} </p>

<p>Photographer : ${credit} </p>

    <input type="submit" name="continue" value="continue">
</form>
</body>
</html>
