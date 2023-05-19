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
import com.shuangyulin.dao.NoticeDAO;
import com.shuangyulin.domain.Notice;
import com.shuangyulin.test.TestUtil;

public class NoticeAction extends ActionSupport {

    /*�������Ҫ��ѯ������: �������*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
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

    private int noticeId;
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }
    public int getNoticeId() {
        return noticeId;
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
    NoticeDAO noticeDAO = new NoticeDAO();

    /*��������Notice����*/
    private Notice notice;
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
    public Notice getNotice() {
        return this.notice;
    }

    /*��ת�����Notice��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Notice��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNotice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            noticeDAO.AddNotice(notice);
            ctx.put("message",  java.net.URLEncoder.encode("Notice��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNotice��Ϣ*/
    public String QueryNotice() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = noticeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = noticeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noticeList",  noticeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "query_view";
    }

    /*ǰ̨��ѯNotice��Ϣ*/
    public String FrontQueryNotice() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = noticeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = noticeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noticeList",  noticeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Notice��Ϣ*/
    public String ModifyNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������noticeId��ȡNotice����*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Notice��Ϣ*/
    public String FrontShowNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������noticeId��ȡNotice����*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "front_show_view";
    }

    /*�����޸�Notice��Ϣ*/
    public String ModifyNotice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            noticeDAO.UpdateNotice(notice);
            ctx.put("message",  java.net.URLEncoder.encode("Notice��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Notice��Ϣ*/
    public String DeleteNotice() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            noticeDAO.DeleteNotice(noticeId);
            ctx.put("message",  java.net.URLEncoder.encode("Noticeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Noticeɾ��ʧ��!"));
            return "error";
        }
    }

}
