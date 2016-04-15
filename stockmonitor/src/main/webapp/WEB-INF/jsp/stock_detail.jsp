<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/14
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>股票详细</title>
</head>
<body>
<table border="1" id="quoteList">
  <tr>
    <td>股票名称</td>
    <td>股票代码</td>
    <td>价格</td>
    <td>数量</td>
    <td>账户</td>
  </tr>
  <tr>
    <td><a href="${base}user/stock_detail/1">股票详细</a></td>
    <td>股票代码</td>
    <td>价格</td>
    <td>数量</td>
    <td>账户</td>
  </tr>
</table>
</body>
</html>
