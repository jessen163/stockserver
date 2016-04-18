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
    <script src="https://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
    <script src="http://ajax.aspnetcdn.com/ajax/jquery/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
    var ws;

    function init() {
//      output = document.getElementById("output");
      // 加载报价队列\
      loadQuoteListRecord();
    }

    window.addEventListener("load", init, false);

    // 加载报价队列
    function loadQuoteListRecord() {
      var stockId = '${stockId}';
      var wsUri = "ws://localhost:8080/stock";
      writeToScreen("Connecting to " + wsUri);
      ws = new WebSocket(wsUri);
      ws.onopen = function () {
//      var stockId = $("#stockId").val();
//        setConnected(true);
        writeToScreen("Info: WebSocket connection opened");
//        log('Info: WebSocket connection opened.');
        ws.send("8@"+stockId);
        writeToScreen("send messgage : test");
      };
      ws.onmessage = function (event) {
//        log('Received: ' + event.data);
        //writeToScreen("Info: WebSocket connection Received:" + event.data);
        if (event.data!='') {
          var dataJson = eval("("+event.data+")");
          var buyQueue = dataJson.buyqueue;
          var sellQueue = dataJson.sellqueue;
          var traderecodeList = dataJson.traderecodeList;
          // alert("dataJson:"+dataJson);
          $("#buyQuoteList").html("");
          for (var i=0; i<buyQueue.length; i++) {
            //alert("event.data:"+dataJson[i]);
            var obj = buyQueue[i];
            var stockList = quoteToHtml(obj);
            $("#buyQuoteList").append(stockList);
          }
          $("#sellQuoteList").html("");
          for (var i=0; i<sellQueue.length; i++) {
            //alert("event.data:"+dataJson[i]);
            var obj = sellQueue[i];
            var stockList = quoteToHtml(obj);
            $("#sellQuoteList").append(stockList);
          }
          $("#traderecordList").html("");
          for (var i=0; i<traderecodeList.length; i++) {
            //alert("event.data:"+dataJson[i]);
            var obj = traderecodeList[i];
            var stockList = traderecordToHtml(obj);
            $("#traderecordList").append(stockList);
          }
        }
      };
      ws.onclose = function () {
//        setConnected(false);
//        log('Info: WebSocket connection closed.');
        writeToScreen("Info: WebSocket connection closed");
      };
    }


    function writeToScreen(message) {
//      var pre = document.createElement("p");
//      pre.style.wordWrap = "break-word";
//      pre.innerHTML = message;
//      output.appendChild(pre);
    }

    function traderecordToHtml(d) {
      var traderecordhtml = '<tr>'+
      '<td width="5%">'+ d.sellerAccountId + '</td>'+
      '<td width="10%">'+ d.sellerAccountNumber + '</td>'+
      '<td width="10%">'+ d.buyerAccountNumber + '</td>'+
      '<td width="10%">'+ d.stockCode + '</td>'+
      '<td width="10%">'+ d.amount + '</td>'+
      '<td width="10%">'+ d.quotePrice + '</td>'+
      '<td width="10%">'+ d.dealMoney + '</td>'+
      '<td width="15%">'+ d.dateTime + '</td>'+
      '<td width="10%">'+ d.dealFee + '</td>'+
      '<td width="10%">'+ d.dealTax + '</td>'+
      '</tr>';
      return traderecordhtml;
    }

    // 报价列表
    function quoteToHtml(d) {
      var quotehtml = '<tr>'+
              '<td width="20%">'+ d.stockId +'</td>'+
              '<td width="20%">'+ d.stockId +'</td>'+
              '<td width="20%">'+ d.quotePrice +'</td>';

      quotehtml += '<td width="20%">'+ d.amount +'</td>'+
      '<td width="20%">' + d.accountId + '</td>'+
      '</tr>';
      return quotehtml;
    }

    function doSend(message) {
      ws.send(message);
      writeToScreen("Sent message: " + message);
    }
    </script>
</head>
<body>
<input type="hidden" id="stockId" name="stockId" value="${stockId}" />
<div>买队列</div>
<div>
  <table border="1" width="512">
    <tr>
      <td width="20%">股票名称</td>
      <td width="20%">股票代码</td>
      <td width="20%">价格</td>
      <td width="20%">数量</td>
      <td width="20%">账户</td>
    </tr>
  </table>
    <table border="1" id="buyQuoteList" width="512">
    </table>
</div>
<div>卖队列</div>
<div>
    <table border="1" width="512">
        <tr>
            <td width="20%">股票名称</td>
            <td width="20%">股票代码</td>
            <td width="20%">价格</td>
            <td width="20%">数量</td>
            <td width="20%">账户</td>
        </tr>
    </table>
    <table border="1" id="sellQuoteList" width="512">
    </table>
</div>

<div>交易记录</div>
<div>
  <table border="1" width="1024">
    <tr>
      <td width="5%">序号</td>
      <td width="10%">卖家</td>
      <td width="10%">买家</td>
      <td width="10%">股票代码</td>
      <td width="10%">交易价格</td>
      <td width="10%">股票数量</td>
      <td width="10%">交易总额</td>
      <td width="15%">交易时间</td>
      <td width="10%">交易费用（佣金）</td>
      <td width="10%">交易税(印花税)</td>
    </tr>
  </table>
  <table border="1" id="traderecordList" width="1024">
  </table>
</div>
</body>
</html>