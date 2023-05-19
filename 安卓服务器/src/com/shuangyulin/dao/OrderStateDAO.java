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

import com.shuangyulin.domain.OrderState;

public class OrderStateDAO {

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

    /*添加订单状态信息*/
    public void AddOrderState(OrderState orderState) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(orderState);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询OrderState信息*/
    public ArrayList<OrderState> QueryOrderStateInfo(int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderState orderState where 1=1";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderStateList = q.list();
            return (ArrayList<OrderState>) orderStateList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的OrderState记录*/
    public ArrayList<OrderState> QueryAllOrderStateInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderState";
            Query q = s.createQuery(hql);
            List orderStateList = q.list();
            return (ArrayList<OrderState>) orderStateList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber() {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderState orderState where 1=1";
            Query q = s.createQuery(hql);
            List orderStateList = q.list();
            recordNumber = orderStateList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public OrderState GetOrderStateByStateId(int stateId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            OrderState orderState = (OrderState)s.get(OrderState.class, stateId);
            return orderState;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新OrderState信息*/
    public void UpdateOrderState(OrderState orderState) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(orderState);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除OrderState信息*/
    public void DeleteOrderState (int stateId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object orderState = s.get(OrderState.class, stateId);
            s.delete(orderState);
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
