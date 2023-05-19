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

    /*添加订单信息*/
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

    /*添加订单及订单明细*/
    public void AddOrderInfo(OrderInfo orderInfo, List<ProductCart> productCartList) { 
    	Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(orderInfo); //保存订单信息
            for(int i=0;i<productCartList.size();i++) {
            	/*将购物车记录移动到订单详细类目表*/
            	ProductCart productCart = productCartList.get(i); 
            	
            	OrderDetail orderDetail = new OrderDetail();
            	orderDetail.setCount(productCart.getCount());
            	orderDetail.setProductObj(productCart.getProductObj());
            	orderDetail.setOrderObj(orderInfo);
            	orderDetail.setPrice(productCart.getPrice());
            	
            	ProductInfo productInfo = productCart.getProductObj();
            	productInfo.setHotNum(productInfo.getHotNum() + productCart.getCount());
            	
            	s.save(orderDetail); //保存订单清单
            	s.delete(productCart);  //删除购物车记录
            	s.save(productInfo); //更新商品人气值
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
    
    
    
    /*查询OrderInfo信息*/
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
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderInfoList = q.list();
            return (ArrayList<OrderInfo>) orderInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*查询OrderInfo信息*/
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
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List orderInfoList = q.list();
            return (ArrayList<OrderInfo>) orderInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    

    /*函数功能：查询所有的OrderInfo记录*/
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

    /*计算总的页数和记录数*/
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
    
    
    /*计算总的页数和记录数*/
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
    
    

    /*根据主键获取对象*/
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

    /*更新OrderInfo信息*/
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

    /*删除OrderInfo信息*/
    public void DeleteOrderInfo (String orderNo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
        	OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailByOrderNo(orderNo);
            
            
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            
            /*删除该订单的详细清单信息*/ 
            for(int i=0;i<orderDetailList.size();i++) {
            	OrderDetail orderDetail = orderDetailList.get(i);
            	s.delete(orderDetail);
            }
            
            /*然后删除订单信息*/
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
