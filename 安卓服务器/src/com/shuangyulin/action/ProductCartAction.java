package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.ProductCartDAO;
import com.shuangyulin.domain.ProductCart;
import com.shuangyulin.dao.MemberInfoDAO;
import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.dao.ProductInfoDAO;
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.test.TestUtil;

public class ProductCartAction extends ActionSupport {
	
	
	private String productNo; 
    public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/*�������Ҫ��ѯ������: �û���*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
    }

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int cartId;
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public int getCartId() {
        return cartId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    ProductCartDAO productCartDAO = new ProductCartDAO();

    /*��������ProductCart����*/
    private ProductCart productCart;
    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }
    public ProductCart getProductCart() {
        return this.productCart;
    }

    /*��ת�����ProductCart��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�MemberInfo��Ϣ*/
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*��ѯ���е�ProductInfo��Ϣ*/
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*���ProductCart��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
            }
            if(true) {
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            }
            productCartDAO.AddProductCart(productCart);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart���ʧ��!"));
            return "error";
        }
    }

    /*������Ʒ���ҵĹ��ﳵ*/
    public String AddToCart() {
    	ActionContext ctx = ActionContext.getContext();
    	String memberUserName = (String)ctx.getSession().get("memberUserName"); 
    	
    	if(memberUserName == null) {
    		 ctx.put("error",  java.net.URLEncoder.encode("���ȵ�½ϵͳ"));
             return "error";
    	}
    	
    	ProductInfoDAO productDAO = new ProductInfoDAO();
		ProductInfo productInfo = productDAO.GetProductInfoByProductNo(productNo);
		if(productInfo.getProductCount() == 0) {
			ctx.put("error",  java.net.URLEncoder.encode("��Ʒ��治���޷�����"));
            return "error";
		}
		
    	/*������ﳵ�д��ڸ���Ʒ ��ô����Ʒ����ֱ�Ӽ�1*/
    	ProductCart productCart = productCartDAO.QueryProductCartInfo(memberUserName, productNo);
    	if(productCart != null) {
    		productCart.setCount(productCart.getCount() + 1);
    		try {
				productCartDAO.UpdateProductCart(productCart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else {
    		/*������ﳵ�в����ڸ���Ʒ��ֱ�Ӵ����ü�¼*/
    		productCart = new ProductCart();
    		productCart.setCount(1); 
    		productCart.setProductObj(productInfo);
    		MemberInfoDAO memberDAO = new MemberInfoDAO();
    		MemberInfo memberInfo = memberDAO.GetMemberInfoByMemberUserName(memberUserName);
    		productCart.setMemberObj(memberInfo);
    		productCart.setPrice(productInfo.getProductPrice());  
    		try {
				productCartDAO.AddProductCart(productCart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	/*����Ʒ�Ŀ�����1*/  
		productInfo.setProductCount(productInfo.getProductCount() - 1);
		try {
			productDAO.UpdateProductInfo(productInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
    	ctx.put("productCartList",  productCartList);
    	return "myCartView";
    }
    
    /*��ѯ�ҵĹ��ﳵ*/
    public String MyCartView() {
    	ActionContext ctx = ActionContext.getContext();
    	
    	String memberUserName = (String)ctx.getSession().get("memberUserName");
    	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
    	ctx.put("productCartList",  productCartList);
    	return "myCartView";
    }
    
    
    
    /*��ѯProductCart��Ϣ*/
    public String QueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productCartDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productCartDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productCartList",  productCartList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberObj", memberObj);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("productObj", productObj);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "query_view";
    }

    /*ǰ̨��ѯProductCart��Ϣ*/
    public String FrontQueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productCartDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productCartDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productCartList",  productCartList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberObj", memberObj);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("productObj", productObj);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ProductCart��Ϣ*/
    public String ModifyProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡProductCart����*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "modify_view";
    }
    
    /*��Ա��ѯҪ�޸ĵ�ProductCart��Ϣ*/
    public String ModifyProductCartMemberQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡProductCart����*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "member_modify_view";
    }
    
    

    /*��ѯҪ�޸ĵ�ProductCart��Ϣ*/
    public String FrontShowProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡProductCart����*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "front_show_view";
    }

    /*�����޸�ProductCart��Ϣ*/
    public String ModifyProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
             
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
          
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            
            /*�ж���Ʒ����Ƿ�����*/
            ProductCart db_productCart = productCartDAO.GetProductCartByCartId(productCart.getCartId());
            if(productCart.getCount() - db_productCart.getCount() - productObj.getProductCount() > 0) {
            	ctx.put("error",  java.net.URLEncoder.encode("��Ʒ��治�㣡"));
                return "error";
            } 
            productCartDAO.UpdateProductCart(productCart); 
            
            ctx.put("message",  java.net.URLEncoder.encode("���ﳵ���³ɹ�!"));
            
            String memberUserName = (String)ctx.getSession().get("memberUserName");
        	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
        	ctx.put("productCartList",  productCartList);
        	
            return "myCartView";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ProductCart��Ϣ*/
    public String DeleteProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productCartDAO.DeleteProductCart(cartId);
            ctx.put("message",  java.net.URLEncoder.encode("��Ʒɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCartɾ��ʧ��!"));
            return "error";
        }
    }

}
