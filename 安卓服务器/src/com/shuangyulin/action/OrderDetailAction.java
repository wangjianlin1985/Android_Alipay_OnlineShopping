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
import com.shuangyulin.dao.OrderDetailDAO;
import com.shuangyulin.domain.OrderDetail;
import com.shuangyulin.dao.OrderInfoDAO;
import com.shuangyulin.domain.OrderInfo;
import com.shuangyulin.dao.ProductInfoDAO;
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.test.TestUtil;

public class OrderDetailAction extends ActionSupport {

    /*�������Ҫ��ѯ������: �������*/
    private OrderInfo orderObj;
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }
    public OrderInfo getOrderObj() {
        return this.orderObj;
    }

  
    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
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

    private int detailId;
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
    public int getDetailId() {
        return detailId;
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
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    /*��������OrderDetail����*/
    private OrderDetail orderDetail;
    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
    public OrderDetail getOrderDetail() {
        return this.orderDetail;
    }
    
    
    /*�������*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
    	this.orderNo = orderNo;
    }
    public String getOrderNo() {
    	return this.orderNo;
    }

    /*��ת�����OrderDetail��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�OrderInfo��Ϣ*/
        OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        /*��ѯ���е�ProductInfo��Ϣ*/
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*���OrderDetail��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderDetail.getOrderObj().getOrderNo());
            orderDetail.setOrderObj(orderObj);
            }
            if(true) {
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(orderDetail.getProductObj().getProductNo());
            orderDetail.setProductObj(productObj);
            }
            orderDetailDAO.AddOrderDetail(orderDetail);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderDetail��Ϣ*/
    public String QueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderDetailDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderDetailDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderDetailList",  orderDetailList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "query_view";
    }
    
    public String OrderDetailQuery() { 
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderNo);
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderDetailList",  orderDetailList); 
    	return "detail_query_view";
    }

    /*ǰ̨��ѯOrderDetail��Ϣ*/
    public String FrontQueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderDetailDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderDetailDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderDetailList",  orderDetailList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderDetail��Ϣ*/
    public String ModifyOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������detailId��ȡOrderDetail����*/
        OrderDetail orderDetail = orderDetailDAO.GetOrderDetailByDetailId(detailId);

        OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("orderDetail",  orderDetail);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderDetail��Ϣ*/
    public String FrontShowOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������detailId��ȡOrderDetail����*/
        OrderDetail orderDetail = orderDetailDAO.GetOrderDetailByDetailId(detailId);

        OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("orderDetail",  orderDetail);
        return "front_show_view";
    }

    /*�����޸�OrderDetail��Ϣ*/
    public String ModifyOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderDetail.getOrderObj().getOrderNo());
            orderDetail.setOrderObj(orderObj);
            }
            if(true) {
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(orderDetail.getProductObj().getProductNo());
            orderDetail.setProductObj(productObj);
            }
            orderDetailDAO.UpdateOrderDetail(orderDetail);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderDetail��Ϣ*/
    public String DeleteOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderDetailDAO.DeleteOrderDetail(detailId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetailɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetailɾ��ʧ��!"));
            return "error";
        }
    }

}
