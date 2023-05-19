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

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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
    YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();

    /*��������YesOrNo����*/
    private YesOrNo yesOrNo;
    public void setYesOrNo(YesOrNo yesOrNo) {
        this.yesOrNo = yesOrNo;
    }
    public YesOrNo getYesOrNo() {
        return this.yesOrNo;
    }

    /*��ת�����YesOrNo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���YesOrNo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            yesOrNoDAO.AddYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯYesOrNo��Ϣ*/
    public String QueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*ǰ̨��ѯYesOrNo��Ϣ*/
    public String FrontQueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�YesOrNo��Ϣ*/
    public String ModifyYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡYesOrNo����*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�YesOrNo��Ϣ*/
    public String FrontShowYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡYesOrNo����*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "front_show_view";
    }

    /*�����޸�YesOrNo��Ϣ*/
    public String ModifyYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            yesOrNoDAO.UpdateYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��YesOrNo��Ϣ*/
    public String DeleteYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            yesOrNoDAO.DeleteYesOrNo(id);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNoɾ��ʧ��!"));
            return "error";
        }
    }

}
