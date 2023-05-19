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

    /*界面层需要查询的属性: 公告标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 发布日期*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
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

    private int noticeId;
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }
    public int getNoticeId() {
        return noticeId;
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
    NoticeDAO noticeDAO = new NoticeDAO();

    /*待操作的Notice对象*/
    private Notice notice;
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
    public Notice getNotice() {
        return this.notice;
    }

    /*跳转到添加Notice视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Notice信息*/
    @SuppressWarnings("deprecation")
    public String AddNotice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            noticeDAO.AddNotice(notice);
            ctx.put("message",  java.net.URLEncoder.encode("Notice添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice添加失败!"));
            return "error";
        }
    }

    /*查询Notice信息*/
    public String QueryNotice() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, publishDate);
        /*获取到总的页码数目*/
        totalPage = noticeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*前台查询Notice信息*/
    public String FrontQueryNotice() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, publishDate);
        /*获取到总的页码数目*/
        totalPage = noticeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Notice信息*/
    public String ModifyNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键noticeId获取Notice对象*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "modify_view";
    }

    /*查询要修改的Notice信息*/
    public String FrontShowNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键noticeId获取Notice对象*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "front_show_view";
    }

    /*更新修改Notice信息*/
    public String ModifyNotice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            noticeDAO.UpdateNotice(notice);
            ctx.put("message",  java.net.URLEncoder.encode("Notice信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice信息更新失败!"));
            return "error";
       }
   }

    /*删除Notice信息*/
    public String DeleteNotice() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            noticeDAO.DeleteNotice(noticeId);
            ctx.put("message",  java.net.URLEncoder.encode("Notice删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice删除失败!"));
            return "error";
        }
    }

}
