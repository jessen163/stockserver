<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Web Socket JavaScript Echo Client</title>
    <script type="text/javascript">
        var echo_websocket;

        function init() {
            output = document.getElementById("output");
        }

        function send_echo() {
            var wsUri = "ws://localhost:8080/echo";
            writeToScreen("Connecting to " + wsUri);
            echo_websocket = new WebSocket(wsUri);
            echo_websocket.onopen = function (evt) {
                writeToScreen("Connected !");
                doSend(textID.value);
            };
            echo_websocket.onmessage = function (evt) {
                writeToScreen("Received message: " + evt.data);
                echo_websocket.close();
            };
            echo_websocket.onerror = function (evt) {
                writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
                echo_websocket.close();
            };
        }

        function doSend(message) {
            echo_websocket.send(message);
            writeToScreen("Sent message: " + message);
        }

        function writeToScreen(message) {
            var pre = document.createElement("p");
            pre.style.wordWrap = "break-word";
            pre.innerHTML = message;
            output.appendChild(pre);
        }

        window.addEventListener("load", init, false);

    </script>
</head>
<body>
<h1>Echo Server</h1>

<div style="text-align: left;">
    <form action="">
        <input onclick="send_echo()" value="Press to send" type="button">
        <input id="textID" name="message" value="Hello Web Sockets" type="text"><br>
    </form>
    <table>
        <tr>
            <td>股票代码</td>
            <td>股票名称</td>
            <td>当前价格</td>
            <td>今日开盘价</td>
            <td>昨日收盘价</td>
            <td>今日最高竟价</td>
            <td>今日最低竟价</td>
            <td>竞买价</td>
            <td>竞卖价</td>
        </tr>
    </table>
</div>
<div id="output"></div>
</body>
</html>