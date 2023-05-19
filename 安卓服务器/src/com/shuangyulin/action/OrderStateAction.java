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
import com.shuangyulin.dao.OrderStateDAO;
import com.shuangyulin.domain.OrderState;
import com.shuangyulin.test.TestUtil;

public class OrderStateAction extends ActionSupport {

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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    OrderStateDAO orderStateDAO = new OrderStateDAO();

    /*待操作的OrderState对象*/
    private OrderState orderState;
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
    public OrderState getOrderState() {
        return this.orderState;
    }

    /*跳转到添加OrderState视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加OrderState信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.AddOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState添加失败!"));
            return "error";
        }
    }

    /*查询OrderState信息*/
    public String QueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = orderStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*前台查询OrderState信息*/
    public String FrontQueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = orderStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的OrderState信息*/
    public String ModifyOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取OrderState对象*/
        OrderState orderState = orderStateDAO.GetOrderStateByStateId(stateId);

        ctx.put("orderState",  orderState);
        return "modify_view";
    }

    /*查询要修改的OrderState信息*/
    public String FrontShowOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取OrderState对象*/
        OrderState orderState = orderStateDAO.GetOrderStateByStateId(stateId);

        ctx.put("orderState",  orderState);
        return "front_show_view";
    }

    /*更新修改OrderState信息*/
    public String ModifyOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.UpdateOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderState信息*/
    public String DeleteOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderStateDAO.DeleteOrderState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState删除失败!"));
            return "error";
        }
    }

}
