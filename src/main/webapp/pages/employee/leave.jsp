<%@ page import="java.util.List" %>
<%@ page import="com.example.llt.entity.Leave" %><%--
  Created by IntelliJ IDEA.
  User: wangke
  Date: 2022/8/11
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/employee/leave.css">
    <title>请假申请</title>
</head>

<body>
<div class="header">
    <blockquote>
        <h2>请假申请</h2>
    </blockquote>
</div>
<div class="container">
    <h2 class="title">请假申请单</h2>
    <form action="/leave" method="post">
        <span class="item-title">请假类型</span>
        <select name="type">
            <option value="0">事假</option>
            <option value="1">病假</option>
        </select>
        <br>
        <span class="item-title">开始日期</span>
        <input type="date" name="start_date" />
        <br>
        <span class="item-title">结束日期</span>
        <input type="date" name="end_date" />
        <br>
        <span class="item-title">请假原因</span>
        <input type="text" name="reason">
        <br>

        <div>
            <input class="submit-btn" type="submit" value="提交">
        </div>
    </form>
    <button class="exit"><a href="/pages/employee/index.html">退出</a></button>

</div>

<div class="header">
    <blockquote>
        <h2>历史请假</h2>
    </blockquote>
</div>

<table class="leaveRequest-table">
    <tr>
        <th>Id</th>
        <th>开始日期</th>
        <th>结束日期</th>
        <th>请假类型</th>
        <th>请假原因</th>
        <th>状态</th>
    </tr>

    <%
        List<Leave> leaveList = (List<Leave>) request.getAttribute("leaves");
        for (int i = leaveList.size() - 1; i >= 0; i--) {
            Leave leave = leaveList.get(i);
    %>


    <tr class="row<%=i % 2 + 1 %>">
        <td>
            <%=leave.getId() %>
        </td>
        <td>
            <%=leave.getStart_date() %>
        </td>
        <td>
            <%=leave.getEnd_date() %>
        </td>
        <td>

            <%
                String type = null;
                if(leave.getStatus() == 0){
                    type = "事假";
                }
                if(leave.getStatus() == 1){
                    type = "病假";
                }

            %>
            <%=type %>
        </td>
        <td>
            <%=leave.getReason() %>
        </td>
        <td>
            <%
                String status = null;
                if(leave.getStatus() == 0){
                    status = "待审批";
                }
                if(leave.getStatus() == 1){
                    status = "已通过";
                }
                if(leave.getStatus() == 2){
                    status = "拒绝申请";
                }

            %>
            <%=status %>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>

</html>