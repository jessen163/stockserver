<%--
  Created by IntelliJ IDEA.
  User: THINK
  Date: 2016/5/9
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>帐户</title>
    <script type="text/javascript">
      var currentMenuId,currentMenuPId,currentPageSize,currentPageIndex,currentStartTime,currentEndTime,
          currentRealName,currentAccountName,currentAccountNum,currentAccountLevel,currentAccountType,currentStatus;

      $(document).ready(function(){
        pagesearch(1,"","",null,null,null,null,null,null);
      });

      function fenYe(pageSize,startTime,endTime,realName,accountName,accountNum,accountLevel,accountType,status){
        currentRealName = realName;
        currentAccountName = accountName;
        currentAccountNum = accountNum;
        currentAccountLevel = accountLevel;
        currentStartTime = startTime;
        currentEndTime = endTime;
        currentAccountType = accountType;
        currentStatus = status;
        currentPageSize = pageSize;
        $("#span1").hide();
        $("#fa").hide();
        $("#demo2").paginate({
          count:pageSize,
          start: 1,
          display: 10,
          border: false,
          text_color: '#3083EB',
          background_color: '',
          text_hover_color: '#fff',
          background_hover_color: '#3093e7',
          onChange: function (page) {
            currentPageIndex = page;
            pagesearch(page,startTime,endTime,realName,accountName,accountNum,accountLevel,accountType,status);
          }
        });
      }

      function pagesearch(pageIndex,startTime,endTime,realName,accountName,accountNum,accountLevel,accountType,status){
        var url = '${base}account/ajax/searchAccount.htm';
        var dataText = {
          "realName":realName,
          "accountName":accountName,
          "accountNum":accountNum,
          "accountLevel":accountLevel,
          "accountType":accountType,
          "startTime":startTime,
          "endTime":endTime,
          "pageIndex":pageIndex,
          "status":status
        }

        $.ajax({
          datatype: "json",
          type: 'post',
          url: url,
          data: dataText,
          success: function (data) {
            cateShare(data);
          }
        })

      }


      function cateShare(data){
        if (data != null) {
          var html = "";
          html += " <table width='100%' border='0' cellpadding='0' cellspacing='0' class='list font2'>"
          + "<tr>"
          + "<td class='table_td04' width='3%' align='center'>序号</td>"
          + "<td class='table_td04' width='8%'>真实姓名</td>"
          + "<td class='table_td03' width='8%'>别名</td>"
          + "<td class='table_td03' width='12%'>帐号</td>"
          + "<td class='table_td03' width='8%'>总资产</td>"
          + "<td class='table_td03' width='8%'>可用资产</td>"
          + "<td class='table_td03' width='6%'>帐户级别</td>"
          + "<td class='table_td03' width='6%'>账户类型</td>"
          + "<td class='table_td03' width='6%'>性别</td>"
          + "<td class='table_td03' width='6%'>状态</td>"
          + "<td class='table_td03' width='18%'>创建时间</td>"
          + "<td class='table_td03' width='10%'>操作</td>"
          + "</tr>";
          var myData = data.list;
          for (var i = 0; i < myData.length; i++) {
            var id = myData[i].id;
            var realName = myData[i].realName;
            var accountName = myData[i].accountName;
            var accountNum = myData[i].accountNum;
            var accountLevel = myData[i].accountLevel;
            var accountType = myData[i].accountType;
            var status = myData[i].status;
            var totalAssets = myData[i].totalAssets;
            var useMoney = myData[i].useMoney;
            var sex=myData[i].sex;
            var createtime=myData[i].createTimeFormat;
            if (i % 2 == 0) {
              cssName = "table_td01";
            } else {
              cssName = "table_td02";
            }
            html += "<tr>";
            html+="<td class='" + cssName + "'align='center'>" + (i+1) + "</td>";
            html+="<td class='" + cssName + "'>" + realName + "</td>";
            html+="<td class='" + cssName + "'>" + accountName + "</td>";
            html+="<td class='" + cssName + "'>" + accountNum + "</td>";
            html+="<td class='" + cssName + "'>" + totalAssets + "</td>";
            html+="<td class='" + cssName + "'>" + useMoney + "</td>";
            html+="<td class='" + cssName + "'>";
            if(accountLevel==1){
              html+="普通帐户";
            }
            if(accountLevel==2){
              html+="VIP帐户";
            }
            html+="</td>";
            html+="<td class='" + cssName + "'>";
            if(accountType==1){
              html+="真实账户";
            }
            if(accountType==2){
              html+="马甲帐户";
            }
            html+="</td>";
            html+="<td class='" + cssName + "'>";
            if(sex==0){
              html+="女";
            }
            if(sex==1){
              html+="男";
            }
            html+="</td>";
            html+="<td class='" + cssName + "'>";
            if(status==1){
              html+="正常";
            }
            if(status==2){
              html+="禁用";
            }
            html+="</td>";
            html+="<td class='" + cssName + "'>" + createtime + "</td>";

            html+="<td class='" + cssName + "'>";
            html+=" <a href='${base}account/uptStAccountModel.htm?accountId=" + id + "' class='link4'>修改</a>";
            html+="</td>";
          }
          +"</td>";
          + " </tr>";
        }
        html += "</table></div>";
        $("#divTab").html(html);
      }
    </script>
</head>
<body>
<h1>帐户列表</h1>
<div id="divTab">

</div>
</body>
</html>
