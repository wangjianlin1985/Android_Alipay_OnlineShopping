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

	/*ֱ����ת����½����*/
	public String view() {
		
		return "login_view";
	}
	 
	
	/* ��֤�û���¼ */
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
		 * ctx.getApplication().put("app", "Ӧ�÷�Χ");//��ServletContext�����app
		 * ctx.getSession().put("ses", "session��Χ");//��session�����ses ctx.put("req",
		 * "request��Χ");//��request�����req ctx.put("names", Arrays.asList("����", "����",
		 * "�Ϸ�")); HttpServletRequest request = ServletActionContext.getRequest();
		 * ServletContext servletContext = ServletActionContext.getServletContext();
		 * request.setAttribute("req", "����Χ����");
		 * request.getSession().setAttribute("ses", "�Ự��Χ����");
		 * servletContext.setAttribute("app", "Ӧ�÷�Χ����"); // HttpServletResponse
		 * response = ServletActionContext.getResponse();
		 */
	}
	
	
	/*��֤��Ա��½*/
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

	/*�˳���½*/
	public String LoginOut() { 
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().remove("memberUserName"); 
		 
		return "loginout_success";
	}
	

}
