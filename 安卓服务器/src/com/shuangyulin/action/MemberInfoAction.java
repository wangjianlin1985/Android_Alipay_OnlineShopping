package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.MemberInfoDAO;
import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.test.TestUtil;

public class MemberInfoAction extends ActionSupport {

/*图片字段photo参数接收*/
	 private File photoFile;
	 private String photoFileFileName;
	 private String photoFileContentType;
	 public File getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
	public String getPhotoFileFileName() {
		return photoFileFileName;
	}
	public void setPhotoFileFileName(String photoFileFileName) {
		this.photoFileFileName = photoFileFileName;
	}
	public String getPhotoFileContentType() {
		return photoFileContentType;
	}
	public void setPhotoFileContentType(String photoFileContentType) {
		this.photoFileContentType = photoFileContentType;
	}
    /*界面层需要查询的属性: 会员用户名*/
    private String memberUserName;
    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }
    public String getMemberUserName() {
        return this.memberUserName;
    }

    /*界面层需要查询的属性: 真实姓名*/
    private String realName;
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return this.realName;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    MemberInfoDAO memberInfoDAO = new MemberInfoDAO();

    /*待操作的MemberInfo对象*/
    private MemberInfo memberInfo;
    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }
    public MemberInfo getMemberInfo() {
        return this.memberInfo;
    }

    /*跳转到添加MemberInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }
    
    
    public String RegisterView() {
    	ActionContext ctx = ActionContext.getContext();
        return "register_view";
    }

    /*添加MemberInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证会员用户名是否已经存在*/
        String memberUserName = memberInfo.getMemberUserName();
        MemberInfo db_memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);
        if(null != db_memberInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该会员用户名已经存在!"));
            return "error";
        }
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String photoFileName = ""; 
       	 	if(photoFile != null) {
       	 		InputStream is = new FileInputStream(photoFile);
       			String fileContentType = this.getPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				photoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				photoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("上传图片格式不正确!"));
       				return "error";
       			}
       			File file = new File(path, photoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(photoFile != null)
            	memberInfo.setPhoto("upload/" + photoFileName);
            else
            	memberInfo.setPhoto("upload/NoImage.jpg");
            memberInfoDAO.AddMemberInfo(memberInfo);
            ctx.put("message",  java.net.URLEncoder.encode("会员信息添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo添加失败!"));
            return "error";
        }
    }

    /*查询MemberInfo信息*/
    public String QueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(realName == null) realName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, realName, birthday, currentPage);
        /*计算总的页数和总的记录数*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, realName, birthday);
        /*获取到总的页码数目*/
        totalPage = memberInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = memberInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("memberInfoList",  memberInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberUserName", memberUserName);
        ctx.put("realName", realName);
        ctx.put("birthday", birthday);
        return "query_view";
    }

    /*前台查询MemberInfo信息*/
    public String FrontQueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(realName == null) realName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, realName, birthday, currentPage);
        /*计算总的页数和总的记录数*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, realName, birthday);
        /*获取到总的页码数目*/
        totalPage = memberInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = memberInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("memberInfoList",  memberInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberUserName", memberUserName);
        ctx.put("realName", realName);
        ctx.put("birthday", birthday);
        return "front_query_view";
    }

    /*查询要修改的MemberInfo信息*/
    public String ModifyMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键memberUserName获取MemberInfo对象*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "modify_view";
    }

    /*查询要修改的MemberInfo信息*/
    public String FrontShowMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键memberUserName获取MemberInfo对象*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "front_show_view";
    }

    /*更新修改MemberInfo信息*/
    public String ModifyMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String photoFileName = ""; 
       	 	if(photoFile != null) {
       	 		InputStream is = new FileInputStream(photoFile);
       			String fileContentType = this.getPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				photoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				photoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("上传图片格式不正确!"));
       				return "error";
       			}
       			File file = new File(path, photoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
            memberInfo.setPhoto("upload/" + photoFileName);
       	 	}
            memberInfoDAO.UpdateMemberInfo(memberInfo);
            ctx.put("message",  java.net.URLEncoder.encode("会员信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除MemberInfo信息*/
    public String DeleteMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            memberInfoDAO.DeleteMemberInfo(memberUserName);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo删除失败!"));
            return "error";
        }
    }

}
