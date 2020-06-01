<%--
  Created by IntelliJ IDEA.
  User: kabirsoneja
  Date: 1/27/20
  Time: 8:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%=request.getAttribute("doctype")%>
<html>
  <head>
    <title>Page 1</title>
  </head>
  <body>
    <h1>Migratory Birds</h1><br>
    <p>Created by Kabir Soneja</p><br>
    <form action="index" method="get">
      <p>Enter the name of a bird</p><br>
      <input type="text" name="inputstring" value=""><br><br>
      <input type="submit" value="submit"><br>
    </form>

  </body>
</html>
