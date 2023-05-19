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

    /*���������Ϣ*/
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

    /*��ѯEvaluate��Ϣ*/
    public ArrayList<Evaluate> QueryEvaluateInfo(ProductInfo productObj,MemberInfo memberObj,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Evaluate evaluate where 1=1";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and evaluate.productObj.productNo='" + productObj.getProductNo() + "'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and evaluate.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List evaluateList = q.list();
            return (ArrayList<Evaluate>) evaluateList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�Evaluate��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
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

    /*����Evaluate��Ϣ*/
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

    /*ɾ��Evaluate��Ϣ*/
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
