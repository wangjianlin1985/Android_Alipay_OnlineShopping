<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.shuangyulin.domain.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//�Ƽ�����Ʒ
ArrayList<ProductInfo> recommentProductList = (ArrayList<ProductInfo>)request.getAttribute("recommentProductList");

//��������Ʒ
ArrayList<ProductInfo> hotProductList = (ArrayList<ProductInfo>)request.getAttribute("hotProductList");

//�����ϼܵ���Ʒ
ArrayList<ProductInfo> recentProductList = (ArrayList<ProductInfo>)request.getAttribute("recentProductList");



%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ϣ����ϵͳ - ��ҳ</title>
<link href="<%=basePath %>css/desk.css" rel="stylesheet" type="text/css"> 
</head>

<body> 
<table align=center width="100%" border="0" cellspacing="0" cellpadding="0"  >
      <tr>
        <td valign="top" width="30%">
        	<table width="98%" cellpadding="5"   border="1" borderColor="red">
        		<tr><td colspan=3 align=center>�Ƽ���Ʒ</td></tr>
        		<tr><td>��ƷͼƬ</td><td>��Ʒ����</td><td>�ϼ�����</td></tr>
        		<%
        			for(int i= 0;i<recommentProductList.size();i++) {
        				ProductInfo productInfo = recommentProductList.get(i);
        		%>
        		<tr>
        			<td><a href="<%=basePath  %>ProductInfo/ProductInfo_FrontShowProductInfoQuery.action?productNo=<%=productInfo.getProductNo() %>"><img src="<%=basePath %><%=productInfo.getProductPhoto() %>" width="60" height="60"/></a></td>
        			<td><a href="<%=basePath  %>ProductInfo/ProductInfo_FrontShowProductInfoQuery.action?productNo=<%=productInfo.getProductNo() %>"><%=productInfo.getProductName() %></a></td>
        			<td><%=productInfo.getOnlineDate() %></td>
        		</tr>
        		<%
        			} 
        		%>
        		
        	</table>
		</td>
		
		<td valign="top" width="30%">
         	<table width="98%" cellpadding="5"   border="1" borderColor="red">
        		<tr><td colspan=4 align=center>������Ʒ</td></tr>
        		<tr><td>��ƷͼƬ</td><td>��Ʒ����</td><td>����<td>�ϼ�����</td></tr>
        		<%
        			for(int i= 0;i<hotProductList.size();i++) {
        				ProductInfo productInfo = hotProductList.get(i);
        		%>
        		<tr>
        			<td><a href="<%=basePath  %>ProductInfo/ProductInfo_FrontShowProductInfoQuery.action?productNo=<%=productInfo.getProductNo() %>"><img src="<%=basePath %><%=productInfo.getProductPhoto() %>" width="60" height="60"/></a></td>
        			<td><a href="<%=basePath  %>ProductInfo/ProductInfo_FrontShowProductInfoQuery.action?productNo=<%=productInfo.getProductNo() %>"><%=productInfo.getProductName() %></a></td>
        			<td><%=productInfo.getHotNum() %></td>
        			<td><%=productInfo.getOnlineDate() %></td>
        		</tr>
        		<%
        			} 
        		%>
        	</table>
		</td>
		
		<td valign="top" width="30%">
        	<table width="98%" cellpadding="5"   border="1" borderColor="red">
        		<tr><td colspan=3 align=center>������Ʒ</td></tr>
        		<tr><td>��ƷͼƬ</td><td>��Ʒ����</td><td>�ϼ�����</td></tr>
        		<%
        			for(int i= 0;i<recentProductList.size();i++) {
        				ProductInfo productInfo = recentProductList.get(i);
        		%>
        		<tr>
        			<td><a href="<%=basePath  %>ProductInfo/ProductInfo_FrontShowProductInfoQuery.action?productNo=<%=productInfo.getProductNo() %>"><img src="<%=basePath %><%=productInfo.getProductPhoto() %>" width="60" height="60"/></a></td>
        			<td><a href="<%=basePath  %>ProductInfo/ProductInfo_FrontShowProductInfoQuery.action?productNo=<%=productInfo.getProductNo() %>"><%=productInfo.getProductName() %></a></td>
        			<td><%=productInfo.getOnlineDate() %></td>
        		</tr>
        		<%
        			} 
        		%>
        	</table>
		</td>
		
      </tr>
 </table>
</body>
</html>

