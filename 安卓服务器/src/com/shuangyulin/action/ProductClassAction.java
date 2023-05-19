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
import com.shuangyulin.dao.ProductClassDAO;
import com.shuangyulin.domain.ProductClass;
import com.shuangyulin.test.TestUtil;

public class ProductClassAction extends ActionSupport {

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

    private int classId;
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getClassId() {
        return classId;
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
    ProductClassDAO productClassDAO = new ProductClassDAO();

    /*��������ProductClass����*/
    private ProductClass productClass;
    public void setProductClass(ProductClass productClass) {
        this.productClass = productClass;
    }
    public ProductClass getProductClass() {
        return this.productClass;
    }

    /*��ת�����ProductClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ProductClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            productClassDAO.AddProductClass(productClass);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯProductClass��Ϣ*/
    public String QueryProductClass() {
        if(currentPage == 0) currentPage = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*ǰ̨��ѯProductClass��Ϣ*/
    public String FrontQueryProductClass() {
        if(currentPage == 0) currentPage = 1;
        List<ProductClass> productClassList = productClassDAO.QueryProductClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productClassList",  productClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ProductClass��Ϣ*/
    public String ModifyProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡProductClass����*/
        ProductClass productClass = productClassDAO.GetProductClassByClassId(classId);

        ctx.put("productClass",  productClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ProductClass��Ϣ*/
    public String FrontShowProductClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡProductClass����*/
        ProductClass productClass = productClassDAO.GetProductClassByClassId(classId);

        ctx.put("productClass",  productClass);
        return "front_show_view";
    }

    /*�����޸�ProductClass��Ϣ*/
    public String ModifyProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            productClassDAO.UpdateProductClass(productClass);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ProductClass��Ϣ*/
    public String DeleteProductClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productClassDAO.DeleteProductClass(classId);
            ctx.put("message",  java.net.URLEncoder.encode("ProductClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductClassɾ��ʧ��!"));
            return "error";
        }
    }

}
