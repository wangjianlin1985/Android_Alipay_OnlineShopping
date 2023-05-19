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

    /*��ӹ�����Ϣ*/
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

    /*��ѯNotice��Ϣ*/
    public ArrayList<Notice> QueryNoticeInfo(String title,String publishDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Notice notice where 1=1";
            if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
            if(!publishDate.equals("")) hql = hql + " and notice.publishDate like '%" + publishDate + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List noticeList = q.list();
            return (ArrayList<Notice>) noticeList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�Notice��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
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

    /*����Notice��Ϣ*/
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

    /*ɾ��Notice��Ϣ*/
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
