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

import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.domain.ProductCart;

public class ProductCartDAO {

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

    /*�����Ʒ���ﳵ��Ϣ*/
    public void AddProductCart(ProductCart productCart) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(productCart);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��ѯProductCart��Ϣ*/
    public ArrayList<ProductCart> QueryProductCartInfo(MemberInfo memberObj,ProductInfo productObj,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductCart productCart where 1=1";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and productCart.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and productCart.productObj.productNo='" + productObj.getProductNo() + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List productCartList = q.list();
            return (ArrayList<ProductCart>) productCartList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    /*���ݻ�Ա������Ʒ��Ų�ѯ���ﳵ��¼*/
    public ProductCart QueryProductCartInfo(String memberUserName,String productNo) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductCart productCart where 1=1";
            hql += " and productCart.memberObj.memberUserName='" + memberUserName + "'";
            hql += " and productCart.productObj.productNo='" + productNo + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/ 
            List productCartList = q.list();
            if(productCartList.size() > 0)
            	return  (ProductCart)productCartList.get(0);
            else 
            	return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    
    /*��ѯĳ���û����ﳵ*/ 
    public ArrayList<ProductCart> QueryMyProductCartInfo(String memberUserName) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductCart productCart where 1=1";
            hql += " and productCart.memberObj.memberUserName='" + memberUserName + "'"; 
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/ 
            List productCartList = q.list();
            return (ArrayList<ProductCart>) productCartList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�ProductCart��¼*/
    public ArrayList<ProductCart> QueryAllProductCartInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductCart";
            Query q = s.createQuery(hql);
            List productCartList = q.list();
            return (ArrayList<ProductCart>) productCartList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(MemberInfo memberObj,ProductInfo productObj) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From ProductCart productCart where 1=1";
            if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and productCart.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
            if(null != productObj && !productObj.getProductNo().equals("")) hql += " and productCart.productObj.productNo='" + productObj.getProductNo() + "'";
            Query q = s.createQuery(hql);
            List productCartList = q.list();
            recordNumber = productCartList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public ProductCart GetProductCartByCartId(int cartId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            ProductCart productCart = (ProductCart)s.get(ProductCart.class, cartId);
            return productCart;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����ProductCart��Ϣ*/
    public void UpdateProductCart(ProductCart productCart) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            ProductCart db_productCart = (ProductCart) s.get(ProductCart.class, productCart.getCartId());
            ProductInfo productInfo = (ProductInfo)s.get(ProductInfo.class, productCart.getProductObj().getProductNo());
            tx = s.beginTransaction();
            productInfo.setProductCount(productInfo.getProductCount() + (db_productCart.getCount() - productCart.getCount()));
            s.merge(productCart);
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

    /*ɾ��ProductCart��Ϣ*/
    public void DeleteProductCart (int cartId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            ProductCart productCart = (ProductCart)s.get(ProductCart.class, cartId);
            ProductInfo productInfo = productCart.getProductObj();
            productInfo.setProductCount(productInfo.getProductCount() + productCart.getCount());
            s.save(productInfo);
            s.delete(productCart);
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
