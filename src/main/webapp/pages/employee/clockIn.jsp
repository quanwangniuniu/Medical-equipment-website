
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.example.llt.entity.ClockIn" %>
<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %><%--
  Created by IntelliJ IDEA.
  User: wangke
  Date: 2022/8/11
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="../../css/employee/clockIn.css">
</head>

<body>
<div class="header">
    <blockquote>
        <h2>今日打卡</h2>
    </blockquote>
</div>

<div class="container">
    <div class="clock_in"></div>
    <form action="/clockIn" method="post">
        <%
            Boolean hashClockIn = (Boolean) request.getAttribute("hasClockIn");
            Date date = new Date();
            int hour = date.getHours();
            if(hashClockIn){
        %>
        <input type="submit" class="submit-btn" value="今天已打卡" disabled="disabled">
        <%
            }else if(hour < 0 || hour > 24){
        %>
        <input type="submit" class="submit-btn" value="不在打卡时间内" disabled="disabled">
        <%
            }else{
        %>
        <input type="submit" class="submit-btn" value="提交">
        <%
            }
        %>
    </form>
    <form action="/pages/employee/index.html">
        <input type="submit" class="submit-btn" value="退出">
    </form>
</div>

<div class="header">
    <blockquote>
        <h2>历史打卡</h2>
    </blockquote>
</div>

<table class="clockIn-table">
    <tr>
        <th>Id</th>
        <th>打卡时间</th>
    </tr>
    <%
        List<ClockIn> clockInList = (List<ClockIn>) request.getAttribute("clockIn");
        for (int i = clockInList.size() - 1; i >= 0; i--) {
            ClockIn clockIn = clockInList.get(i);
    %>
    <tr>
        <td><%=clockIn.getId()%></td>
        <td><%=clockIn.getTime()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>