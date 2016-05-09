<%--
  Created by IntelliJ IDEA.
  User: THINK
  Date: 2016/5/9
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title></title>
    <script>

      function uptPassword(){
        $("[name = uptpwd]:checkbox").each(function () {
          if ($(this).is(":checked")) {
            if($('#password').attr("readonly")=="readonly"){
              $('#password').attr("readonly",false);
              $('#password').val("");
              $('#isUptType').val(1);//0不修改密码，1修改密码
            }
          }else{
            $('#password').attr("readonly",true);
            var oldpwd = $('#oldPwd').val();
            $('#password').val(oldpwd);
            $('#isUptType').val(0);
          }
        });
      }
    </script>
</head>
<body>
<h1>修改帐户</h1>
<div id="divTab">
  <form id="defaultForm" method="post"
        <c:if test="${account.id!=null}">action="${base}account/updateStAccount.htm"</c:if> class="form-horizontal">
        <input type="hidden" id="accountId" name = "accountId" value="${account.id}">
    <div class="list_content">
      <div class="contentB">
        <div class="form_list skin-flat skin-section">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list font2">
                <tr>
                  <td height="15" colspan="5"></td>
                </tr>
                <tr>
                  <td height="40">&nbsp;</td>
                  <td>真实姓名</td>
                  <td>
                      <input name="realName" id="realName" value="${account.realName}" type="text" class="txt_01" size="34"/>
                  </td>
                  <td>&nbsp;</td>
                  <td class="font6">&nbsp;</td>
                </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>别名</td>
                <td>
                  <input name="accountName" id="accountName" value="${account.accountName}" type="text" class="txt_01" size="34"/>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>帐号</td>
                <td>
                  <input name="accountNum" id="accountNum" value="${account.accountNum}" type="text" readonly class="txt_01" size="34"/>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
                <tr>
                  <td height="40">&nbsp;</td>
                  <td>密码</td>
                  <td>
                    <input name="password" id="password"
                           value="${account.password}"
                           type="text" <c:if test="${account.id!=null}"> readonly="readonly" </c:if>
                           class="txt_01 form-control" size="34"/>
                    <input name="isUptType" id="isUptType" type="hidden" value="0"/>
                    <input type="hidden" id="oldPwd" name="oldPwd" value="${account.password}">
                  </td>
                  <td>
                    <span><input type="checkbox"  id="uptpwd" name="uptpwd" tabindex="3" onchange="uptPassword()"/></span>是否修改密码？
                  </td>
                  <td class="font6">&nbsp;</td>
                </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>总资产</td>
                <td>
                  <input name="totalAssets" id="totalAssets" value="${account.totalAssets}" type="text" class="txt_01" size="34"/>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>可用资产</td>
                <td>
                  <input name="useMoney" id="useMoney" value="${account.useMoney}" type="text" class="txt_01" size="34"/>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>帐户级别</td>
                <td width="400">
                  <select name="accountLevel" class="select">
                    <option value="1" <c:if test="${account.accountLevel==1}">selected</c:if>>普通帐户</option>
                    <option value="2" <c:if test="${account.accountLevel==2}">selected</c:if>>VIP帐户</option>
                  </select>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>帐户类型</td>
                <td width="400">
                  <select name="accountType" class="select">
                    <option value="1" <c:if test="${account.accountType==1}">selected</c:if>>真实帐户</option>
                    <option value="2" <c:if test="${account.accountType==2}">selected</c:if>>马甲帐户</option>
                  </select>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>性别</td>
                <td width="400">
                  <select name="sex" class="select">
                    <option value="0" <c:if test="${account.sex==0}">selected</c:if>>女</option>
                    <option value="1" <c:if test="${account.sex==1}">selected</c:if>>男</option>
                  </select>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>状态</td>
                <td width="400">
                  <select name="status" class="select">
                    <option value="1" <c:if test="${account.status==1}">selected</c:if>>正常</option>
                    <option value="2" <c:if test="${account.status==2}">selected</c:if>>禁用</option>
                  </select>
                </td>
                <td>&nbsp;</td>
                <td class="font6">&nbsp;</td>
              </tr>
              <tr>
                <td height="40">&nbsp;</td>
                <td>描述</td>
                <td colspan="3">
                  <textarea id="remark" name="remark" style="width: 500px;height: 100px">${account.remark}</textarea>
                </td>
              </tr>
              <tr>
                <td height="20"></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td height="30"></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td colspan="2">
                  <button type="submit" id="" class="button_01">提交</button>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  </button>
                  <button type="reset" class="button_01"
                          onclick="window.location.href='${base}account/accountList.htm'">
                    取消
                  </button>
                </td>
              </tr>
            </table>
        </div>
      </div>
    </div>
  </form>
</div>
</body>
</html>
