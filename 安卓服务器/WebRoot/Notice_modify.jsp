<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.shuangyulin.domain.Notice" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Notice notice = (Notice)request.getAttribute("notice");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改系统公告</TITLE>
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
    var title = document.getElementById("notice.title").value;
    if(title=="") {
        alert('请输入公告标题!');
        return false;
    }
    var content = document.getElementById("notice.content").value;
    if(content=="") {
        alert('请输入公告内容!');
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
    <TD align="left" vAlign=top ><s:form action="Notice/Notice_ModifyNotice.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="notice.noticeId" name="notice.noticeId" type="text" value="<%=notice.getNoticeId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>公告标题:</td>
    <td width=70%><input id="notice.title" name="notice.title" type="text" size="30" value='<%=notice.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>公告内容:</td>
    <td width=70%><input id="notice.content" name="notice.content" type="text" size="80" value='<%=notice.getContent() %>'/></td>
  </tr>

  <tr>
    <td width=30%>发布日期:</td>
    <td width=70%><input type="text" readonly  id="notice.publishDate"  name="notice.publishDate" onclick="setDay(this);" value='<%=notice.getPublishDate() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
