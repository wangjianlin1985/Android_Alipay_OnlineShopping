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
import com.shuangyulin.dao.YesOrNoDAO;
import com.shuangyulin.domain.YesOrNo;
import com.shuangyulin.test.TestUtil;

public class YesOrNoAction extends ActionSupport {

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

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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
    YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();

    /*待操作的YesOrNo对象*/
    private YesOrNo yesOrNo;
    public void setYesOrNo(YesOrNo yesOrNo) {
        this.yesOrNo = yesOrNo;
    }
    public YesOrNo getYesOrNo() {
        return this.yesOrNo;
    }

    /*跳转到添加YesOrNo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加YesOrNo信息*/
    @SuppressWarnings("deprecation")
    public String AddYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            yesOrNoDAO.AddYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo添加失败!"));
            return "error";
        }
    }

    /*查询YesOrNo信息*/
    public String QueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*前台查询YesOrNo信息*/
    public String FrontQueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的YesOrNo信息*/
    public String ModifyYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取YesOrNo对象*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "modify_view";
    }

    /*查询要修改的YesOrNo信息*/
    public String FrontShowYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取YesOrNo对象*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "front_show_view";
    }

    /*更新修改YesOrNo信息*/
    public String ModifyYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            yesOrNoDAO.UpdateYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo信息更新失败!"));
            return "error";
       }
   }

    /*删除YesOrNo信息*/
    public String DeleteYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            yesOrNoDAO.DeleteYesOrNo(id);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo删除失败!"));
            return "error";
        }
    }

}
