<%--
  Created by IntelliJ IDEA.
  User: THINK
  Date: 2016/4/20
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
     <title></title>
     <script type="text/javascript" src="/autotrade/js/jquery-1.11.1.min.js"></script>
     <script type="text/javascript">
       function start(){
         var url = 'ajax/saveTrading.htm';
         var accountNum = $("#accountNum").val();
         var stockCode = $("#stockCode").val();
         var dataText = {
           "accountNum":accountNum,
           "stockCode":stockCode
         };

         $.ajax({
           datatype: "json",
           type: 'get',
           url: url,
           data: dataText,
           success: function (data) {
             alert(data);
           }
         })
       }

       function stop(){
         var url = 'ajax/stopTrading.htm';
         $.ajax({
           datatype: "json",
           type: 'get',
           url: url,
           data: "",
           success: function (data) {
             alert(data);
           }
         })
       }
     </script>
</head>
<body>
    <div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="34" height="40"></td>
            <td height="15" colspan="5">自动化交易主界面</td>
          </tr>
          <tr>
            <td width="34" height="40"></td>
            <td width="80">帐号</td>
            <td width="180">
              <input type="text" id="accountNum" name="accountNum" value="">
            </td>
            <td width="80">股票代码</td>
            <td width="180">
              <input type="text" id="stockCode" name="stockCode" value="">
            </td>
            <td>
              <button type="button" id="startBtn" onclick="start()">开始</button>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <button type="button" id="endBtn" onclick="stop()">结束</button>
            </td>
          </tr>
        </table>
      </div>
</body>
</html>
