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

/*ͼƬ�ֶ�photo��������*/
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
    /*�������Ҫ��ѯ������: ��Ա�û���*/
    private String memberUserName;
    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }
    public String getMemberUserName() {
        return this.memberUserName;
    }

    /*�������Ҫ��ѯ������: ��ʵ����*/
    private String realName;
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return this.realName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    MemberInfoDAO memberInfoDAO = new MemberInfoDAO();

    /*��������MemberInfo����*/
    private MemberInfo memberInfo;
    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }
    public MemberInfo getMemberInfo() {
        return this.memberInfo;
    }

    /*��ת�����MemberInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }
    
    
    public String RegisterView() {
    	ActionContext ctx = ActionContext.getContext();
        return "register_view";
    }

    /*���MemberInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ա�û����Ƿ��Ѿ�����*/
        String memberUserName = memberInfo.getMemberUserName();
        MemberInfo db_memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);
        if(null != db_memberInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�û�Ա�û����Ѿ�����!"));
            return "error";
        }
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String photoFileName = ""; 
       	 	if(photoFile != null) {
       	 		InputStream is = new FileInputStream(photoFile);
       			String fileContentType = this.getPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				photoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				photoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("��Ա��Ϣ��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯMemberInfo��Ϣ*/
    public String QueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(realName == null) realName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, realName, birthday, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, realName, birthday);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = memberInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*ǰ̨��ѯMemberInfo��Ϣ*/
    public String FrontQueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(realName == null) realName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, realName, birthday, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, realName, birthday);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = memberInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�MemberInfo��Ϣ*/
    public String ModifyMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������memberUserName��ȡMemberInfo����*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�MemberInfo��Ϣ*/
    public String FrontShowMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������memberUserName��ȡMemberInfo����*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "front_show_view";
    }

    /*�����޸�MemberInfo��Ϣ*/
    public String ModifyMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String photoFileName = ""; 
       	 	if(photoFile != null) {
       	 		InputStream is = new FileInputStream(photoFile);
       			String fileContentType = this.getPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				photoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				photoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("��Ա��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��MemberInfo��Ϣ*/
    public String DeleteMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            memberInfoDAO.DeleteMemberInfo(memberUserName);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
