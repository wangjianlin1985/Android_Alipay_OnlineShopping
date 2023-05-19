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

import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.domain.Evaluate;

public class EvaluateDAO {

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

    /*添加评价信息*/
    public void AddEvaluate(Evaluate evaluate) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(evaluate);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询Evaluate信息*/
    public ArrayList<Evaluate> QueryEvaluateInfo(ProductInfo productObj,MemberInfo memberObj,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Evaluate evaluate where 1=1";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and evaluate.productObj.productNo='" + productObj.getProductNo() + "'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and evaluate.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List evaluateList = q.list();
            return (ArrayList<Evaluate>) evaluateList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的Evaluate记录*/
    public ArrayList<Evaluate> QueryAllEvaluateInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Evaluate";
            Query q = s.createQuery(hql);
            List evaluateList = q.list();
            return (ArrayList<Evaluate>) evaluateList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(ProductInfo productObj,MemberInfo memberObj) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Evaluate evaluate where 1=1";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and evaluate.productObj.productNo='" + productObj.getProductNo() + "'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and evaluate.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            Query q = s.createQuery(hql);
            List evaluateList = q.list();
            recordNumber = evaluateList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public Evaluate GetEvaluateByEvaluateId(int evaluateId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Evaluate evaluate = (Evaluate)s.get(Evaluate.class, evaluateId);
            return evaluate;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新Evaluate信息*/
    public void UpdateEvaluate(Evaluate evaluate) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(evaluate);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除Evaluate信息*/
    public void DeleteEvaluate (int evaluateId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object evaluate = s.get(Evaluate.class, evaluateId);
            s.delete(evaluate);
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
