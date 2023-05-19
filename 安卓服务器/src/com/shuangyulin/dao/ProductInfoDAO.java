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
import com.shuangyulin.domain.YesOrNo;
import com.shuangyulin.domain.ProductInfo;

public class ProductInfoDAO {

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

    /*添加商品信息*/
    public void AddProductInfo(ProductInfo productInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(productInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询ProductInfo信息*/
    public ArrayList<ProductInfo> QueryProductInfoInfo(String productNo,ProductClass productClassObj,String productName,YesOrNo recommendFlag,String onlineDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductInfo productInfo where 1=1";
            if(!productNo.equals("")) hql = hql + " and productInfo.productNo like '%" + productNo + "%'";
            if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and productInfo.productClassObj.classId=" + productClassObj.getClassId();
            if(!productName.equals("")) hql = hql + " and productInfo.productName like '%" + productName + "%'";
            if(null != recommendFlag && recommendFlag.getId()!=0) hql += " and productInfo.recommendFlag.id=" + recommendFlag.getId();
            if(!onlineDate.equals("")) hql = hql + " and productInfo.onlineDate like '%" + onlineDate + "%'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List productInfoList = q.list();
            return (ArrayList<ProductInfo>) productInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的ProductInfo记录*/
    public ArrayList<ProductInfo> QueryAllProductInfoInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductInfo";
            Query q = s.createQuery(hql);
            List productInfoList = q.list();
            return (ArrayList<ProductInfo>) productInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*查询推荐的商品*/
    public ArrayList<ProductInfo> QueryRecommendProductInfoInfo() { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductInfo productInfo where 1=1";   
            hql += " and productInfo.recommendFlag.id=1 order by productInfo.onlineDate ASC"; 
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = 0;
            q.setFirstResult(startIndex);
            q.setMaxResults(8);
            List productInfoList = q.list();
            return (ArrayList<ProductInfo>) productInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    /*查询热销的商品*/
    public ArrayList<ProductInfo> QueryHotProductInfoInfo() { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductInfo productInfo where 1=1";   
            hql += "order by productInfo.hotNum DESC,productInfo.onlineDate DESC"; 
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = 0;
            q.setFirstResult(startIndex);
            q.setMaxResults(8);
            List productInfoList = q.list();
            return (ArrayList<ProductInfo>) productInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    
    /*查询最新上架的商品*/
    public ArrayList<ProductInfo> QueryRecentProductInfoInfo() { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductInfo productInfo where 1=1";   
            hql += "order by productInfo.onlineDate DESC"; 
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = 0;
            q.setFirstResult(startIndex);
            q.setMaxResults(8);
            List productInfoList = q.list();
            return (ArrayList<ProductInfo>) productInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    
    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String productNo,ProductClass productClassObj,String productName,YesOrNo recommendFlag,String onlineDate) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductInfo productInfo where 1=1";
            if(!productNo.equals("")) hql = hql + " and productInfo.productNo like '%" + productNo + "%'";
            if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and productInfo.productClassObj.classId=" + productClassObj.getClassId();
            if(!productName.equals("")) hql = hql + " and productInfo.productName like '%" + productName + "%'";
            if(null != recommendFlag && recommendFlag.getId()!=0) hql += " and productInfo.recommendFlag.id=" + recommendFlag.getId();
            if(!onlineDate.equals("")) hql = hql + " and productInfo.onlineDate like '%" + onlineDate + "%'";
            Query q = s.createQuery(hql);
            List productInfoList = q.list();
            recordNumber = productInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public ProductInfo GetProductInfoByProductNo(String productNo) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            ProductInfo productInfo = (ProductInfo)s.get(ProductInfo.class, productNo);
            return productInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新ProductInfo信息*/
    public void UpdateProductInfo(ProductInfo productInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(productInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除ProductInfo信息*/
    public void DeleteProductInfo (String productNo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object productInfo = s.get(ProductInfo.class, productNo);
            s.delete(productInfo);
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
