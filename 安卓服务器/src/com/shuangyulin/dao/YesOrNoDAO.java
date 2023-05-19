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

import com.shuangyulin.domain.YesOrNo;

public class YesOrNoDAO {

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

    /*添加图书信息*/
    public void AddYesOrNo(YesOrNo yesOrNo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(yesOrNo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询YesOrNo信息*/
    public ArrayList<YesOrNo> QueryYesOrNoInfo(int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From YesOrNo yesOrNo where 1=1";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List yesOrNoList = q.list();
            return (ArrayList<YesOrNo>) yesOrNoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的YesOrNo记录*/
    public ArrayList<YesOrNo> QueryAllYesOrNoInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From YesOrNo";
            Query q = s.createQuery(hql);
            List yesOrNoList = q.list();
            return (ArrayList<YesOrNo>) yesOrNoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber() {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From YesOrNo yesOrNo where 1=1";
            Query q = s.createQuery(hql);
            List yesOrNoList = q.list();
            recordNumber = yesOrNoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public YesOrNo GetYesOrNoById(int id) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            YesOrNo yesOrNo = (YesOrNo)s.get(YesOrNo.class, id);
            return yesOrNo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新YesOrNo信息*/
    public void UpdateYesOrNo(YesOrNo yesOrNo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(yesOrNo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除YesOrNo信息*/
    public void DeleteYesOrNo (int id) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object yesOrNo = s.get(YesOrNo.class, id);
            s.delete(yesOrNo);
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
