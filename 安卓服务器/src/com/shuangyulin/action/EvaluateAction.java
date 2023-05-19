package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.EvaluateDAO;
import com.shuangyulin.domain.Evaluate;
import com.shuangyulin.dao.ProductInfoDAO;
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.dao.MemberInfoDAO;
import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.test.TestUtil;

public class EvaluateAction extends ActionSupport {

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
    }

    /*�������Ҫ��ѯ������: �û���*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
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

    private int evaluateId;
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }
    public int getEvaluateId() {
        return evaluateId;
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
    EvaluateDAO evaluateDAO = new EvaluateDAO();

    /*��������Evaluate����*/
    private Evaluate evaluate;
    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }
    public Evaluate getEvaluate() {
        return this.evaluate;
    }

    /*��ת�����Evaluate��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ProductInfo��Ϣ*/
        //ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        //List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        //ctx.put("productInfoList", productInfoList);
        /*��ѯ���е�MemberInfo��Ϣ*/
        //MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        //List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        //ctx.put("memberInfoList", memberInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        ProductInfo productInfo = productInfoDAO.GetProductInfoByProductNo(productObj.getProductNo());
        ctx.put("productInfo", productInfo);
        return "add_view";
    }

    /*���Evaluate��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(evaluate.getProductObj().getProductNo());
            evaluate.setProductObj(productObj);
            
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(evaluate.getMemberObj().getMemberUserName());
            evaluate.setMemberObj(memberObj);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            evaluate.setEvaluateTime(sdf.format(new java.util.Date())); 
            
            evaluateDAO.AddEvaluate(evaluate);
            ctx.put("message",  java.net.URLEncoder.encode("���۳ɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate���ʧ��!"));
            return "error";
        }
    }

    /*��ѯEvaluate��Ϣ*/
    public String QueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj, memberObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(productObj, memberObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = evaluateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = evaluateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("evaluateList",  evaluateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("memberObj", memberObj);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        return "query_view";
    }

    /*ǰ̨��ѯEvaluate��Ϣ*/
    public String FrontQueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj, memberObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(productObj, memberObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = evaluateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = evaluateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("evaluateList",  evaluateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("memberObj", memberObj);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Evaluate��Ϣ*/
    public String ModifyEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������evaluateId��ȡEvaluate����*/
        Evaluate evaluate = evaluateDAO.GetEvaluateByEvaluateId(evaluateId);

        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("evaluate",  evaluate);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Evaluate��Ϣ*/
    public String FrontShowEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������evaluateId��ȡEvaluate����*/
        Evaluate evaluate = evaluateDAO.GetEvaluateByEvaluateId(evaluateId);

        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("evaluate",  evaluate);
        return "front_show_view";
    }

    /*�����޸�Evaluate��Ϣ*/
    public String ModifyEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(evaluate.getProductObj().getProductNo());
            evaluate.setProductObj(productObj);
            }
            if(true) {
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(evaluate.getMemberObj().getMemberUserName());
            evaluate.setMemberObj(memberObj);
            }
            evaluateDAO.UpdateEvaluate(evaluate);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Evaluate��Ϣ*/
    public String DeleteEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            evaluateDAO.DeleteEvaluate(evaluateId);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluateɾ��ʧ��!"));
            return "error";
        }
    }

}
