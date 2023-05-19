<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.shuangyulin.domain.ProductInfo" %>
<%@ page import="com.shuangyulin.domain.MemberInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的productObj信息
    //List<ProductInfo> productInfoList = (List<ProductInfo>)request.getAttribute("productInfoList");
    //获取所有的memberObj信息
    //List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    //String username=(String)session.getAttribute("username");
    //if(username==null){
    //    response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    //}
    ProductInfo productInfo = (ProductInfo) request.getAttribute("productInfo");
    String memberUserName = (String) session.getAttribute("memberUserName");
%>
<HTML><HEAD><TITLE>添加商品评价</TITLE> 
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
    var content = document.getElementById("evaluate.content").value;
    if(content=="") {
        alert('请输入评价内容!');
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
    <TD align="left" vAlign=top >
    <s:form action="Evaluate/Evaluate_AddEvaluate.action" method="post" id="evaluateAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%>
      <input type=hidden name="evaluate.productObj.productNo" value="<%=productInfo.getProductNo() %>">
      <%=productInfo.getProductName() %> 
    </td>
  </tr> 
  
      <input type=hidden name="evaluate.memberObj.memberUserName" value="<%=memberUserName %>"> 

  <tr>
    <td width=30%>评价内容:</td>
    <td width=70%><input id="evaluate.content" name="evaluate.content" type="text" size="50" /></td>
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
