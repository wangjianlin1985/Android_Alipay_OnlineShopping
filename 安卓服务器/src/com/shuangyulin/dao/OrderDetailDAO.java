package com.shuangyulin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mysql.jdbc.Statement;
import com.shuangyulin.utils.HibernateUtil;

import com.shuangyulin.domain.ProductCart;
import com.shuangyulin.domain.OrderInfo;
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.domain.OrderDetail;

public class OrderDetailDAO {

    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加订单明显信息*/
    public void AddOrderDetail(OrderDetail orderDetail) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(orderDetail);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询OrderDetail信息*/
    public ArrayList<OrderDetail> QueryOrderDetailInfo(OrderInfo orderObj,ProductInfo productObj,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderDetail orderDetail where 1=1";
            if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderDetail.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and orderDetail.productObj.productNo='" + productObj.getProductNo() + "'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderDetailList = q.list();
            return (ArrayList<OrderDetail>) orderDetailList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*根据订单编号查询OrderDetail信息*/
    public ArrayList<OrderDetail> QueryOrderDetailInfo(String orderNo) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderDetail orderDetail where 1=1";
            hql += " and orderDetail.orderObj.orderNo='" + orderNo + "'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/ 
            List orderDetailList = q.list();
            return (ArrayList<OrderDetail>) orderDetailList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*函数功能：查询所有的OrderDetail记录*/
    public ArrayList<OrderDetail> QueryAllOrderDetailInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderDetail";
            Query q = s.createQuery(hql);
            List orderDetailList = q.list();
            return (ArrayList<OrderDetail>) orderDetailList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    
    /*根据订单编号获取详细清单信息*/ 
    public ArrayList<OrderDetail> QueryOrderDetailByOrderNo(String orderNo) { 
        Session s = null; 
        try {  
        	s = HibernateUtil.getSession();
            String hql = "From OrderDetail orderDetail where orderDetail.orderObj.orderNo='" + orderNo + "'";
            Query q = s.createQuery(hql);
            List orderDetailList = q.list();
            return (ArrayList<OrderDetail>) orderDetailList;
        } finally {
        	HibernateUtil.closeSession();
        }
         
    }
    
    
    
    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(OrderInfo orderObj,ProductInfo productObj) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderDetail orderDetail where 1=1";
            if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderDetail.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and orderDetail.productObj.productNo='" + productObj.getProductNo() + "'";
            Query q = s.createQuery(hql);
            List orderDetailList = q.list();
            recordNumber = orderDetailList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public OrderDetail GetOrderDetailByDetailId(int detailId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            OrderDetail orderDetail = (OrderDetail)s.get(OrderDetail.class, detailId);
            return orderDetail;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新OrderDetail信息*/
    public void UpdateOrderDetail(OrderDetail orderDetail) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(orderDetail);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除OrderDetail信息*/
    public void DeleteOrderDetail (int detailId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object orderDetail = s.get(OrderDetail.class, detailId);
            s.delete(orderDetail);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

}
