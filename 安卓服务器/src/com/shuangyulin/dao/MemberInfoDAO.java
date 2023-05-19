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

import com.shuangyulin.domain.Admin;
import com.shuangyulin.domain.MemberInfo;

public class MemberInfoDAO {

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

	private String errMessage;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddMemberInfo(MemberInfo memberInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(memberInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��ѯMemberInfo��Ϣ*/
    public ArrayList<MemberInfo> QueryMemberInfoInfo(String memberUserName,String realName,String birthday,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From MemberInfo memberInfo where 1=1";
            if(!memberUserName.equals("")) hql = hql + " and memberInfo.memberUserName like '%" + memberUserName + "%'";
            if(!realName.equals("")) hql = hql + " and memberInfo.realName like '%" + realName + "%'";
            if(!birthday.equals("")) hql = hql + " and memberInfo.birthday like '%" + birthday + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List memberInfoList = q.list();
            return (ArrayList<MemberInfo>) memberInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�MemberInfo��¼*/
    public ArrayList<MemberInfo> QueryAllMemberInfoInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From MemberInfo";
            Query q = s.createQuery(hql);
            List memberInfoList = q.list();
            return (ArrayList<MemberInfo>) memberInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String memberUserName,String realName,String birthday) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From MemberInfo memberInfo where 1=1";
            if(!memberUserName.equals("")) hql = hql + " and memberInfo.memberUserName like '%" + memberUserName + "%'";
            if(!realName.equals("")) hql = hql + " and memberInfo.realName like '%" + realName + "%'";
            if(!birthday.equals("")) hql = hql + " and memberInfo.birthday like '%" + birthday + "%'";
            Query q = s.createQuery(hql);
            List memberInfoList = q.list();
            recordNumber = memberInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public MemberInfo GetMemberInfoByMemberUserName(String memberUserName) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            MemberInfo memberInfo = (MemberInfo)s.get(MemberInfo.class, memberUserName);
            return memberInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����MemberInfo��Ϣ*/
    public void UpdateMemberInfo(MemberInfo memberInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(memberInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��MemberInfo��Ϣ*/
    public void DeleteMemberInfo (String memberUserName) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object memberInfo = s.get(MemberInfo.class, memberUserName);
            s.delete(memberInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
	public boolean CheckLogin(MemberInfo member) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			MemberInfo db_member = (MemberInfo)s.get(MemberInfo.class, member.getMemberUserName());
			if(db_member == null) { 
				this.errMessage = " �˺Ų����� ";
				System.out.print(this.errMessage);
				return false;
			} else if( !db_member.getPassword().equals(member.getPassword())) {
				this.errMessage = " ���벻��ȷ! ";
				System.out.print(this.errMessage);
				return false;
			}
		} finally {
			HibernateUtil.closeSession();
		} 
		return true;
	}
	public String getErrMessage() {
		// TODO Auto-generated method stub
		return this.errMessage;
	}

}
