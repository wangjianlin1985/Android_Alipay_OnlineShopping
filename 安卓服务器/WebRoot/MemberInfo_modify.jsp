<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.shuangyulin.domain.MemberInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");

    String username=(String)session.getAttribute("username");
    String memberUserName = (String)session.getAttribute("memberUserName");
    if(username==null && memberUserName == null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改会员信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var memberUserName = document.getElementById("memberInfo.memberUserName").value;
    if(memberUserName=="") {
        alert('请输入会员用户名!');
        return false;
    }
    var password = document.getElementById("memberInfo.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var realName = document.getElementById("memberInfo.realName").value;
    if(realName=="") {
        alert('请输入真实姓名!');
        return false;
    }
    var sex = document.getElementById("memberInfo.sex").value;
    if(sex=="") {
        alert('请输入性别!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="MemberInfo/MemberInfo_ModifyMemberInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>会员用户名:</td>
    <td width=70%><input id="memberInfo.memberUserName" name="memberInfo.memberUserName" type="text" value="<%=memberInfo.getMemberUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="memberInfo.password" name="memberInfo.password" type="text" size="20" value='<%=memberInfo.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>真实姓名:</td>
    <td width=70%><input id="memberInfo.realName" name="memberInfo.realName" type="text" size="20" value='<%=memberInfo.getRealName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="memberInfo.sex" name="memberInfo.sex" type="text" size="2" value='<%=memberInfo.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
    <td width=70%><input type="text" readonly  id="memberInfo.birthday"  name="memberInfo.birthday" onclick="setDay(this);" value='<%=memberInfo.getBirthday() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="memberInfo.telephone" name="memberInfo.telephone" type="text" size="20" value='<%=memberInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系邮箱:</td>
    <td width=70%><input id="memberInfo.email" name="memberInfo.email" type="text" size="40" value='<%=memberInfo.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系qq:</td>
    <td width=70%><input id="memberInfo.qq" name="memberInfo.qq" type="text" size="20" value='<%=memberInfo.getQq() %>'/></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><input id="memberInfo.address" name="memberInfo.address" type="text" size="60" value='<%=memberInfo.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>会员照片:</td>
    <td width=70%><img src="<%=basePath %><%=memberInfo.getPhoto() %>" width="200px" border="0px"/><br/>
    <input type="hidden" name="memberInfo.photo" value="<%=memberInfo.getPhoto() %>" />
    <input id="photoFile" name="photoFile" type="file" size="50" /></td>
  </tr>
  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='更新' >
        &nbsp;&nbsp;
         
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
