<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.shuangyulin.domain.Evaluate" %>
<%@ page import="com.shuangyulin.domain.ProductInfo" %>
<%@ page import="com.shuangyulin.domain.MemberInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的productObj信息
    List<ProductInfo> productInfoList = (List<ProductInfo>)request.getAttribute("productInfoList");
    //获取所有的memberObj信息
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    Evaluate evaluate = (Evaluate)request.getAttribute("evaluate");

%>
<HTML><HEAD><TITLE>查看商品评价</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>评价编号:</td>
    <td width=70%><%=evaluate.getEvaluateId() %></td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%>
      <select name="evaluate.productObj.productNo" disabled>
      <%
        for(ProductInfo productInfo:productInfoList) {
          String selected = "";
          if(productInfo.getProductNo().equals(evaluate.getProductObj().getProductNo()))
            selected = "selected";
      %>
          <option value='<%=productInfo.getProductNo() %>' <%=selected %>><%=productInfo.getProductName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>用户名:</td>
    <td width=70%>
      <select name="evaluate.memberObj.memberUserName" disabled>
      <%
        for(MemberInfo memberInfo:memberInfoList) {
          String selected = "";
          if(memberInfo.getMemberUserName().equals(evaluate.getMemberObj().getMemberUserName()))
            selected = "selected";
      %>
          <option value='<%=memberInfo.getMemberUserName() %>' <%=selected %>><%=memberInfo.getMemberUserName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>评价内容:</td>
    <td width=70%><%=evaluate.getContent() %></td>
  </tr>

  <tr>
    <td width=30%>评价时间:</td>
    <td width=70%><%=evaluate.getEvaluateTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
