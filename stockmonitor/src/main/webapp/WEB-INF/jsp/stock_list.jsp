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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Web Socket JavaScript Echo Client</title>
  <script src="https://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
  <script src="http://ajax.aspnetcdn.com/ajax/jquery/jquery-2.1.4.min.js"></script>
  <script type="text/javascript">
    var ws;

    function init() {
      output = document.getElementById("output");
    }

    function send_echo() {
      var wsUri = "ws://localhost:8080/stock";
      writeToScreen("Connecting to " + wsUri);
      ws = new WebSocket(wsUri);
      ws.onopen = function () {
//        setConnected(true);
        writeToScreen("Info: WebSocket connection opened");
//        log('Info: WebSocket connection opened.');
        ws.send("test");
        writeToScreen("send messgage : test");
      };
      ws.onmessage = function (event) {
//        log('Received: ' + event.data);
        //writeToScreen("Info: WebSocket connection Received:"+event.data);
          if (event.data!='') {
              var dataJson = eval(event.data);
              // alert("dataJson:"+dataJson);
              $("#stockList").html("");
              for (var i=0; i<dataJson.length; i++) {
                  //alert("event.data:"+dataJson[i]);
                  var obj = dataJson[i];
                  var stockList = stockToHtml(obj);
                  $("#stockList").append(stockList);
              }
          }
      };
      ws.onclose = function () {
//        setConnected(false);
//        log('Info: WebSocket connection closed.');
        writeToScreen("Info: WebSocket connection closed");
      };
    }
    function stockToHtml(d) {
        var stockhtml = '<tr id="stockTr">'+
                '<td width="10%">'+ d.stockCode+'</td>'+
                '<td width="10%">'+ d.stockName+'</td>'+
                '<td width="10%">'+ d.currentPrice+'</td>'+
                '<td width="10%">'+ d.openPrice+'</td>'+
                '<td width="10%">'+ d.bfclosePrice+'</td>'+
                '<td width="10%">'+ d.maxPrice+'</td>'+
                '<td width="10%">'+ d.minPrice+'</td>'+
                '<td width="10%">'+ d.biddingBuyPrice+'</td>'+
                '<td width="10%">'+ d.biddingSellPrice+'</td>'+
                '<td width="10%"><a href="">详细</a></td>'+
                '</tr>';
        return stockhtml;
    }

    // 报价列表
    function quoteToHtml(d) {
        var quotehtml = '<tr>'+
                '<td><a href="${base}user/stock_detail/1">股票详细</a></td>'+
        '<td>股票代码</td>'+
        '<td>价格</td>'+
        '<td>数量</td>'+
        '<td>账户</td>'+
        '</tr>';
        return quotehtml;
    }

    function doSend(message) {
      ws.send(message);
      writeToScreen("Sent message: " + message);
    }

    function writeToScreen(message) {
      var pre = document.createElement("p");
      pre.style.wordWrap = "break-word";
      pre.innerHTML = message;
      output.appendChild(pre);
    }

    window.addEventListener("load", init, false);

    function load_price() {
        var wsUri = "ws://localhost:8080/stock";
        writeToScreen("Connecting to " + wsUri);
        ws = new WebSocket(wsUri);
        ws.onopen = function () {
            writeToScreen("Info: WebSocket connection opened");
            ws.send("7");
            writeToScreen("send messgage : 7");
        };
        ws.onmessage = function (event) {
//        log('Received: ' + event.data);
            //writeToScreen("Info: WebSocket connection Received:"+event.data);
            if (event.data!='') {
                var dataJson = eval(event.data);
                // alert("dataJson:"+dataJson);
                $("#quoteList").html("");
                for (var i=0; i<dataJson.length; i++) {
                    //alert("event.data:"+dataJson[i]);
                    var obj = dataJson[i];
                    var stockList = quoteToHtml(obj);
                    $("#quoteList").append(stockList);
                }
            }
        };
        ws.onclose = function () {
//        setConnected(false);
//        log('Info: WebSocket connection closed.');
            writeToScreen("Info: WebSocket connection closed");
        };
    }

  </script>
</head>
<body>
<h1>监控主界面</h1>

<div style="text-align: left;">
  <form action="">
    <input onclick="send_echo()" value="开启" type="button">
      <input onclick="load_price()" value="开启2" type="button">
    <%--<input id="textID" name="message" value="Hello Web Sockets" type="text">--%><br>
  </form>
  <table border="1" width="1024">
    <tr id="stockTr">
      <td width="10%">股票代码</td>
      <td width="10%">股票名称</td>
      <td width="10%">当前价格</td>
      <td width="10%">今日开盘价</td>
      <td width="10%">昨日收盘价</td>
      <td width="10%">今日最高竟价</td>
      <td width="10%">今日最低竟价</td>
      <td width="10%">竞买价</td>
      <td width="10%">竞卖价</td>
      <td width="10%">详细</td>
    </tr>
  </table>
  <table border="1" id="stockList" width="1024">
  </table>

  <table border="1" width="1024">
    <tr>
      <td width="20%">股票名称</td>
      <td width="20%">股票代码</td>
      <td width="20%">价格</td>
      <td width="20%">数量</td>
      <td width="20%">账户</td>
    </tr>
  </table>
    <table border="1" id="quoteList" width="1024">

    </table>
</div>
<div id="output"></div>
</body>
</html>
