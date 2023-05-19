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

    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*��Ӷ���������Ϣ*/
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

    /*��ѯOrderDetail��Ϣ*/
    public ArrayList<OrderDetail> QueryOrderDetailInfo(OrderInfo orderObj,ProductInfo productObj,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderDetail orderDetail where 1=1";
            if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderDetail.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and orderDetail.productObj.productNo='" + productObj.getProductNo() + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderDetailList = q.list();
            return (ArrayList<OrderDetail>) orderDetailList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*���ݶ�����Ų�ѯOrderDetail��Ϣ*/
    public ArrayList<OrderDetail> QueryOrderDetailInfo(String orderNo) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderDetail orderDetail where 1=1";
            hql += " and orderDetail.orderObj.orderNo='" + orderNo + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/ 
            List orderDetailList = q.list();
            return (ArrayList<OrderDetail>) orderDetailList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*�������ܣ���ѯ���е�OrderDetail��¼*/
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

    
    /*���ݶ�����Ż�ȡ��ϸ�嵥��Ϣ*/ 
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
    
    
    
    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
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

    /*����OrderDetail��Ϣ*/
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

    /*ɾ��OrderDetail��Ϣ*/
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
