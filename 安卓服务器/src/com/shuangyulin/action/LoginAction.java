package com.shuangyulin.action;

 

 

 

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.AdminDAO;
import com.shuangyulin.dao.MemberInfoDAO;
import com.shuangyulin.domain.Admin;
import com.shuangyulin.domain.MemberInfo;

public class LoginAction extends ActionSupport {
 
	
	private Admin admin;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	private MemberInfo member;
	

	public MemberInfo getMember() {
		return member;
	}

	public void setMember(MemberInfo member) {
		this.member = member;
	}

	/*直接跳转到登陆界面*/
	public String view() {
		
		return "login_view";
	}
	 
	
	/* 验证用户登录 */
	public String CheckLogin() {
		AdminDAO adminDAO = new AdminDAO();
		ActionContext ctx = ActionContext.getContext();
		if (!adminDAO.CheckLogin(admin)) {
			ctx.put("error",  java.net.URLEncoder.encode(adminDAO.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("username", admin.getUsername());
		return "main_view";

		/*
		 * ActionContext ctx = ActionContext.getContext();
		 * ctx.getApplication().put("app", "应用范围");//往ServletContext里放入app
		 * ctx.getSession().put("ses", "session范围");//往session里放入ses ctx.put("req",
		 * "request范围");//往request里放入req ctx.put("names", Arrays.asList("老张", "老黎",
		 * "老方")); HttpServletRequest request = ServletActionContext.getRequest();
		 * ServletContext servletContext = ServletActionContext.getServletContext();
		 * request.setAttribute("req", "请求范围属性");
		 * request.getSession().setAttribute("ses", "会话范围属性");
		 * servletContext.setAttribute("app", "应用范围属性"); // HttpServletResponse
		 * response = ServletActionContext.getResponse();
		 */
	}
	
	
	/*验证会员登陆*/
	public String CheckMemberLogin() {
		MemberInfoDAO memberDAO = new MemberInfoDAO();
		ActionContext ctx = ActionContext.getContext();
		if (!memberDAO.CheckLogin(member)) {
			ctx.put("error",  java.net.URLEncoder.encode(memberDAO.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("memberUserName", member.getMemberUserName());
		return "login_success";
	}

	/*退出登陆*/
	public String LoginOut() { 
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().remove("memberUserName"); 
		 
		return "loginout_success";
	}
	

}
