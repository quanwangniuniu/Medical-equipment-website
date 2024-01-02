<%--
  Created by IntelliJ IDEA.
  User: wangke
  Date: 2022/8/11
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        int x = 2;
    %>



    <%
        if(x == 1){

    %>
    <input type="submit" value="btn1">
    <%
        }
    %>

    <%
        if(x == 2){

    %>
    <input type="submit" value="btn2">
    <%
        }
    %>

</body>
</html>
