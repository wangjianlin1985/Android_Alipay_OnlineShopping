<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>����SSH2���Ϲ���ϵͳ-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>ProductClass/ProductClass_FrontQueryProductClass.action" target="OfficeMain">��Ʒ���</a></li> 
			<li><a href="<%=basePath %>ProductInfo/ProductInfo_FrontQueryProductInfo.action" target="OfficeMain">��Ʒ��Ϣ</a></li> 
			<!--   
			<li><a href="<%=basePath %>YesOrNo/YesOrNo_FrontQueryYesOrNo.action" target="OfficeMain">�Ƿ���Ϣ</a></li> 
			-->
			<li><a href="<%=basePath %>MemberInfo/MemberInfo_FrontQueryMemberInfo.action" target="OfficeMain">��Ա��Ϣ</a></li> 
			<!--  
			<li><a href="<%=basePath %>OrderInfo/OrderInfo_FrontQueryOrderInfo.action" target="OfficeMain">������Ϣ</a></li> 
			--> 
			<li><a href="<%=basePath %>MemberInfo/MemberInfo_RegisterView.action" target="OfficeMain">��Աע��</a></li> 
			<li><a href="<%=basePath %>Evaluate/Evaluate_FrontQueryEvaluate.action" target="OfficeMain">��Ʒ����</a></li> 
			<li><a href="<%=basePath %>Notice/Notice_FrontQueryNotice.action" target="OfficeMain">ϵͳ����</a></li> 
		</ul>
		<br />
	</div>
	<div id="loginBar">
	  <%
	  
	  	String memberUserName = (String)session.getAttribute("memberUserName");
	    if(memberUserName==null){ 
	  %>
	  <form action="<%=basePath %>login/login_CheckMemberLogin.action">
		�û�����
		<input type=text name="member.memberUserName" size="12"/>&nbsp;&nbsp;
		���룺
		<input type=password name="member.password" size="12"/>&nbsp;&nbsp;
		<input type=submit value="��¼" />
		</form>
	  
	  <%} else { %>
	    <ul>
	    	<li><a href="<%=basePath %>ProductCart/ProductCart_MyCartView.action" target="OfficeMain">���ҵĹ��ﳵ��</a></li>
	    	<li><a href="<%=basePath %>OrderInfo/OrderInfo_MyOrderInfoQuery.action" target="OfficeMain">���ҵĶ�����</a></li>
	    	<li><a href="<%=basePath %>MemberInfo/MemberInfo_ModifyMemberInfoQuery.action?memberUserName=<%=memberUserName %>" target="OfficeMain">���޸ĸ�����Ϣ��</a></li>
	    	<li><a href="<%=basePath %>login/login_LoginOut.action">���˳���½��</a></li>
	    </ul>
	     
	  
	 <% }%>
		
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>index/index_HomePage.action" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p> &copy;��Ȩ����  <a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
