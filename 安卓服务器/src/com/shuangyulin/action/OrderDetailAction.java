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

    /*界面层需要查询的属性: 定单编号*/
    private OrderInfo orderObj;
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }
    public OrderInfo getOrderObj() {
        return this.orderObj;
    }

  
    /*界面层需要查询的属性: 商品名称*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
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

    private int detailId;
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
    public int getDetailId() {
        return detailId;
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
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    /*待操作的OrderDetail对象*/
    private OrderDetail orderDetail;
    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
    public OrderDetail getOrderDetail() {
        return this.orderDetail;
    }
    
    
    /*订单编号*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
    	this.orderNo = orderNo;
    }
    public String getOrderNo() {
    	return this.orderNo;
    }

    /*跳转到添加OrderDetail视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的OrderInfo信息*/
        OrderInfoDAO orderInfoDAO = new OrderInfoDAO();
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        /*查询所有的ProductInfo信息*/
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*添加OrderDetail信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail添加失败!"));
            return "error";
        }
    }

    /*查询OrderDetail信息*/
    public String QueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*获取到总的页码数目*/
        totalPage = orderDetailDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*前台查询OrderDetail信息*/
    public String FrontQueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*获取到总的页码数目*/
        totalPage = orderDetailDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的OrderDetail信息*/
    public String ModifyOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键detailId获取OrderDetail对象*/
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

    /*查询要修改的OrderDetail信息*/
    public String FrontShowOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键detailId获取OrderDetail对象*/
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

    /*更新修改OrderDetail信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderDetail信息*/
    public String DeleteOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderDetailDAO.DeleteOrderDetail(detailId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail删除失败!"));
            return "error";
        }
    }

}
