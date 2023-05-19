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

    /*界面层需要查询的属性: 商品名称*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
    }

    /*界面层需要查询的属性: 用户名*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
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

    private int evaluateId;
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }
    public int getEvaluateId() {
        return evaluateId;
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
    EvaluateDAO evaluateDAO = new EvaluateDAO();

    /*待操作的Evaluate对象*/
    private Evaluate evaluate;
    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }
    public Evaluate getEvaluate() {
        return this.evaluate;
    }

    /*跳转到添加Evaluate视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的ProductInfo信息*/
        //ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        //List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        //ctx.put("productInfoList", productInfoList);
        /*查询所有的MemberInfo信息*/
        //MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        //List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        //ctx.put("memberInfoList", memberInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        ProductInfo productInfo = productInfoDAO.GetProductInfoByProductNo(productObj.getProductNo());
        ctx.put("productInfo", productInfo);
        return "add_view";
    }

    /*添加Evaluate信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("评价成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate添加失败!"));
            return "error";
        }
    }

    /*查询Evaluate信息*/
    public String QueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj, memberObj, currentPage);
        /*计算总的页数和总的记录数*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(productObj, memberObj);
        /*获取到总的页码数目*/
        totalPage = evaluateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*前台查询Evaluate信息*/
    public String FrontQueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj, memberObj, currentPage);
        /*计算总的页数和总的记录数*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(productObj, memberObj);
        /*获取到总的页码数目*/
        totalPage = evaluateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Evaluate信息*/
    public String ModifyEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键evaluateId获取Evaluate对象*/
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

    /*查询要修改的Evaluate信息*/
    public String FrontShowEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键evaluateId获取Evaluate对象*/
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

    /*更新修改Evaluate信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate信息更新失败!"));
            return "error";
       }
   }

    /*删除Evaluate信息*/
    public String DeleteEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            evaluateDAO.DeleteEvaluate(evaluateId);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate删除失败!"));
            return "error";
        }
    }

}
