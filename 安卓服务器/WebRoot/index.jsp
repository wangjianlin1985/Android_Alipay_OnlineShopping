<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>基于SSH2网上购物系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>ProductClass/ProductClass_FrontQueryProductClass.action" target="OfficeMain">商品类别</a></li> 
			<li><a href="<%=basePath %>ProductInfo/ProductInfo_FrontQueryProductInfo.action" target="OfficeMain">商品信息</a></li> 
			<!--   
			<li><a href="<%=basePath %>YesOrNo/YesOrNo_FrontQueryYesOrNo.action" target="OfficeMain">是否信息</a></li> 
			-->
			<li><a href="<%=basePath %>MemberInfo/MemberInfo_FrontQueryMemberInfo.action" target="OfficeMain">会员信息</a></li> 
			<!--  
			<li><a href="<%=basePath %>OrderInfo/OrderInfo_FrontQueryOrderInfo.action" target="OfficeMain">订单信息</a></li> 
			--> 
			<li><a href="<%=basePath %>MemberInfo/MemberInfo_RegisterView.action" target="OfficeMain">会员注册</a></li> 
			<li><a href="<%=basePath %>Evaluate/Evaluate_FrontQueryEvaluate.action" target="OfficeMain">商品评价</a></li> 
			<li><a href="<%=basePath %>Notice/Notice_FrontQueryNotice.action" target="OfficeMain">系统公告</a></li> 
		</ul>
		<br />
	</div>
	<div id="loginBar">
	  <%
	  
	  	String memberUserName = (String)session.getAttribute("memberUserName");
	    if(memberUserName==null){ 
	  %>
	  <form action="<%=basePath %>login/login_CheckMemberLogin.action">
		用户名：
		<input type=text name="member.memberUserName" size="12"/>&nbsp;&nbsp;
		密码：
		<input type=password name="member.password" size="12"/>&nbsp;&nbsp;
		<input type=submit value="登录" />
		</form>
	  
	  <%} else { %>
	    <ul>
	    	<li><a href="<%=basePath %>ProductCart/ProductCart_MyCartView.action" target="OfficeMain">【我的购物车】</a></li>
	    	<li><a href="<%=basePath %>OrderInfo/OrderInfo_MyOrderInfoQuery.action" target="OfficeMain">【我的订单】</a></li>
	    	<li><a href="<%=basePath %>MemberInfo/MemberInfo_ModifyMemberInfoQuery.action?memberUserName=<%=memberUserName %>" target="OfficeMain">【修改个人信息】</a></li>
	    	<li><a href="<%=basePath %>login/login_LoginOut.action">【退出登陆】</a></li>
	    </ul>
	     
	  
	 <% }%>
		
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>index/index_HomePage.action" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p> &copy;版权所有  <a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
