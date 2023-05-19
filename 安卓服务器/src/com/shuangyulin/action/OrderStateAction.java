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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    OrderStateDAO orderStateDAO = new OrderStateDAO();

    /*��������OrderState����*/
    private OrderState orderState;
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
    public OrderState getOrderState() {
        return this.orderState;
    }

    /*��ת�����OrderState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���OrderState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.AddOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderState��Ϣ*/
    public String QueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*ǰ̨��ѯOrderState��Ϣ*/
    public String FrontQueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderState��Ϣ*/
    public String ModifyOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡOrderState����*/
        OrderState orderState = orderStateDAO.GetOrderStateByStateId(stateId);

        ctx.put("orderState",  orderState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderState��Ϣ*/
    public String FrontShowOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡOrderState����*/
        OrderState orderState = orderStateDAO.GetOrderStateByStateId(stateId);

        ctx.put("orderState",  orderState);
        return "front_show_view";
    }

    /*�����޸�OrderState��Ϣ*/
    public String ModifyOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.UpdateOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderState��Ϣ*/
    public String DeleteOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderStateDAO.DeleteOrderState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderStateɾ��ʧ��!"));
            return "error";
        }
    }

}
