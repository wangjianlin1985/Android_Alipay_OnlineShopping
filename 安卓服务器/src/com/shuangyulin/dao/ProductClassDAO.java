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

import com.shuangyulin.domain.ProductClass;

public class ProductClassDAO {

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

    /*添加商品类别信息*/
    public void AddProductClass(ProductClass productClass) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(productClass);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询ProductClass信息*/
    public ArrayList<ProductClass> QueryProductClassInfo(int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductClass productClass where 1=1";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List productClassList = q.list();
            return (ArrayList<ProductClass>) productClassList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的ProductClass记录*/
    public ArrayList<ProductClass> QueryAllProductClassInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductClass";
            Query q = s.createQuery(hql);
            List productClassList = q.list();
            return (ArrayList<ProductClass>) productClassList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber() {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductClass productClass where 1=1";
            Query q = s.createQuery(hql);
            List productClassList = q.list();
            recordNumber = productClassList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public ProductClass GetProductClassByClassId(int classId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            ProductClass productClass = (ProductClass)s.get(ProductClass.class, classId);
            return productClass;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新ProductClass信息*/
    public void UpdateProductClass(ProductClass productClass) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(productClass);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除ProductClass信息*/
    public void DeleteProductClass (int classId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object productClass = s.get(ProductClass.class, classId);
            s.delete(productClass);
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
