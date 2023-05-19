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
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.domain.OrderDetail;
import com.shuangyulin.domain.OrderState;
import com.shuangyulin.domain.OrderInfo;

public class OrderInfoDAO {

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

    /*��Ӷ�����Ϣ*/
    public void AddOrderInfo(OrderInfo orderInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(orderInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��Ӷ�����������ϸ*/
    public void AddOrderInfo(OrderInfo orderInfo, List<ProductCart> productCartList) { 
    	Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(orderInfo); //���涩����Ϣ
            for(int i=0;i<productCartList.size();i++) {
            	/*�����ﳵ��¼�ƶ���������ϸ��Ŀ��*/
            	ProductCart productCart = productCartList.get(i); 
            	
            	OrderDetail orderDetail = new OrderDetail();
            	orderDetail.setCount(productCart.getCount());
            	orderDetail.setProductObj(productCart.getProductObj());
            	orderDetail.setOrderObj(orderInfo);
            	orderDetail.setPrice(productCart.getPrice());
            	
            	ProductInfo productInfo = productCart.getProductObj();
            	productInfo.setHotNum(productInfo.getHotNum() + productCart.getCount());
            	
            	s.save(orderDetail); //���涩���嵥
            	s.delete(productCart);  //ɾ�����ﳵ��¼
            	s.save(productInfo); //������Ʒ����ֵ
            }
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
	}
    
    
    
    /*��ѯOrderInfo��Ϣ*/
    public ArrayList<OrderInfo> QueryOrderInfoInfo(String orderNo,MemberInfo memberObj,String orderTime,OrderState orderStateObj,String realName,String telphone,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderInfo orderInfo where 1=1";
            if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and orderInfo.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            if(!orderTime.equals("")) hql = hql + " and orderInfo.orderTime like '%" + orderTime + "%'";
            if(null != orderStateObj && orderStateObj.getStateId()!=0) hql += " and orderInfo.orderStateObj.stateId=" + orderStateObj.getStateId();
            if(!realName.equals("")) hql = hql + " and orderInfo.realName like '%" + realName + "%'";
            if(!telphone.equals("")) hql = hql + " and orderInfo.telphone like '%" + telphone + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderInfoList = q.list();
            return (ArrayList<OrderInfo>) orderInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*��ѯOrderInfo��Ϣ*/
    public ArrayList<OrderInfo> QueryOrderInfoInfo(String orderNo,MemberInfo memberObj,String orderStartTime,String orderEndTime,OrderState orderStateObj,String realName,String telphone,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderInfo orderInfo where 1=1";
            if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and orderInfo.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            if(!orderStartTime.equals("")) hql = hql + " and orderInfo.orderTime > '" + orderStartTime + "'";
            if(!orderEndTime.equals("")) hql = hql + " and orderInfo.orderTime < '" + orderEndTime + "'";
            if(null != orderStateObj && orderStateObj.getStateId()!=0) hql += " and orderInfo.orderStateObj.stateId=" + orderStateObj.getStateId();
            if(!realName.equals("")) hql = hql + " and orderInfo.realName like '%" + realName + "%'";
            if(!telphone.equals("")) hql = hql + " and orderInfo.telphone like '%" + telphone + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderInfoList = q.list();
            return (ArrayList<OrderInfo>) orderInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*�������ܣ���ѯ���е�OrderInfo��¼*/
    public ArrayList<OrderInfo> QueryAllOrderInfoInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderInfo";
            Query q = s.createQuery(hql);
            List orderInfoList = q.list();
            return (ArrayList<OrderInfo>) orderInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String orderNo,MemberInfo memberObj,String orderTime,OrderState orderStateObj,String realName,String telphone) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderInfo orderInfo where 1=1";
            if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and orderInfo.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            if(!orderTime.equals("")) hql = hql + " and orderInfo.orderTime like '%" + orderTime + "%'";
            if(null != orderStateObj && orderStateObj.getStateId()!=0) hql += " and orderInfo.orderStateObj.stateId=" + orderStateObj.getStateId();
            if(!realName.equals("")) hql = hql + " and orderInfo.realName like '%" + realName + "%'";
            if(!telphone.equals("")) hql = hql + " and orderInfo.telphone like '%" + telphone + "%'";
            Query q = s.createQuery(hql);
            List orderInfoList = q.list();
            recordNumber = orderInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String orderNo,MemberInfo memberObj,String orderStartTime, String orderEndTime,OrderState orderStateObj,String realName,String telphone) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From OrderInfo orderInfo where 1=1";
            if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and orderInfo.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            if(!orderStartTime.equals("")) hql = hql + " and orderInfo.orderTime > '" + orderStartTime + "'";
            if(!orderEndTime.equals("")) hql = hql + " and orderInfo.orderTime < '" + orderEndTime + "'";
            if(null != orderStateObj && orderStateObj.getStateId()!=0) hql += " and orderInfo.orderStateObj.stateId=" + orderStateObj.getStateId();
            if(!realName.equals("")) hql = hql + " and orderInfo.realName like '%" + realName + "%'";
            if(!telphone.equals("")) hql = hql + " and orderInfo.telphone like '%" + telphone + "%'";
            Query q = s.createQuery(hql);
            List orderInfoList = q.list();
            recordNumber = orderInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    

    /*����������ȡ����*/
    public OrderInfo GetOrderInfoByOrderNo(String orderNo) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            OrderInfo orderInfo = (OrderInfo)s.get(OrderInfo.class, orderNo);
            return orderInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����OrderInfo��Ϣ*/
    public void UpdateOrderInfo(OrderInfo orderInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(orderInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��OrderInfo��Ϣ*/
    public void DeleteOrderInfo (String orderNo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
        	OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailByOrderNo(orderNo);
            
            
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            
            /*ɾ���ö�������ϸ�嵥��Ϣ*/ 
            for(int i=0;i<orderDetailList.size();i++) {
            	OrderDetail orderDetail = orderDetailList.get(i);
            	s.delete(orderDetail);
            }
            
            /*Ȼ��ɾ��������Ϣ*/
            Object orderInfo = s.get(OrderInfo.class, orderNo);
            s.delete(orderInfo);
            
            
            
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
