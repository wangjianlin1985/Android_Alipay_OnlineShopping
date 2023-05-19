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

import com.shuangyulin.domain.Notice;

public class NoticeDAO {

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

    /*添加公告信息*/
    public void AddNotice(Notice notice) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(notice);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询Notice信息*/
    public ArrayList<Notice> QueryNoticeInfo(String title,String publishDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notice notice where 1=1";
            if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
            if(!publishDate.equals("")) hql = hql + " and notice.publishDate like '%" + publishDate + "%'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List noticeList = q.list();
            return (ArrayList<Notice>) noticeList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的Notice记录*/
    public ArrayList<Notice> QueryAllNoticeInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notice";
            Query q = s.createQuery(hql);
            List noticeList = q.list();
            return (ArrayList<Notice>) noticeList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String title,String publishDate) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notice notice where 1=1";
            if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
            if(!publishDate.equals("")) hql = hql + " and notice.publishDate like '%" + publishDate + "%'";
            Query q = s.createQuery(hql);
            List noticeList = q.list();
            recordNumber = noticeList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public Notice GetNoticeByNoticeId(int noticeId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Notice notice = (Notice)s.get(Notice.class, noticeId);
            return notice;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新Notice信息*/
    public void UpdateNotice(Notice notice) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(notice);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除Notice信息*/
    public void DeleteNotice (int noticeId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object notice = s.get(Notice.class, noticeId);
            s.delete(notice);
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
