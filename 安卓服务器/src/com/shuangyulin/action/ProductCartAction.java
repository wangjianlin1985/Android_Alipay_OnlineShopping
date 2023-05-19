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

	/*界面层需要查询的属性: 用户名*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
    }

    /*界面层需要查询的属性: 商品名称*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    ProductCartDAO productCartDAO = new ProductCartDAO();

    /*待操作的ProductCart对象*/
    private ProductCart productCart;
    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }
    public ProductCart getProductCart() {
        return this.productCart;
    }

    /*跳转到添加ProductCart视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的MemberInfo信息*/
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*查询所有的ProductInfo信息*/
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*添加ProductCart信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart添加失败!"));
            return "error";
        }
    }

    /*加入商品到我的购物车*/
    public String AddToCart() {
    	ActionContext ctx = ActionContext.getContext();
    	String memberUserName = (String)ctx.getSession().get("memberUserName"); 
    	
    	if(memberUserName == null) {
    		 ctx.put("error",  java.net.URLEncoder.encode("请先登陆系统"));
             return "error";
    	}
    	
    	ProductInfoDAO productDAO = new ProductInfoDAO();
		ProductInfo productInfo = productDAO.GetProductInfoByProductNo(productNo);
		if(productInfo.getProductCount() == 0) {
			ctx.put("error",  java.net.URLEncoder.encode("商品库存不足无法购买"));
            return "error";
		}
		
    	/*如果购物车中存在该商品 那么该商品数量直接加1*/
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
    		/*如果购物车中不存在该商品则直接创建该记录*/
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
    	
    	/*该商品的库存减少1*/  
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
    
    /*查询我的购物车*/
    public String MyCartView() {
    	ActionContext ctx = ActionContext.getContext();
    	
    	String memberUserName = (String)ctx.getSession().get("memberUserName");
    	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
    	ctx.put("productCartList",  productCartList);
    	return "myCartView";
    }
    
    
    
    /*查询ProductCart信息*/
    public String QueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*获取到总的页码数目*/
        totalPage = productCartDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*前台查询ProductCart信息*/
    public String FrontQueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*获取到总的页码数目*/
        totalPage = productCartDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的ProductCart信息*/
    public String ModifyProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cartId获取ProductCart对象*/
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
    
    /*会员查询要修改的ProductCart信息*/
    public String ModifyProductCartMemberQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cartId获取ProductCart对象*/
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
    
    

    /*查询要修改的ProductCart信息*/
    public String FrontShowProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cartId获取ProductCart对象*/
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

    /*更新修改ProductCart信息*/
    public String ModifyProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
             
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
          
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            
            /*判断商品库存是否满足*/
            ProductCart db_productCart = productCartDAO.GetProductCartByCartId(productCart.getCartId());
            if(productCart.getCount() - db_productCart.getCount() - productObj.getProductCount() > 0) {
            	ctx.put("error",  java.net.URLEncoder.encode("商品库存不足！"));
                return "error";
            } 
            productCartDAO.UpdateProductCart(productCart); 
            
            ctx.put("message",  java.net.URLEncoder.encode("购物车更新成功!"));
            
            String memberUserName = (String)ctx.getSession().get("memberUserName");
        	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
        	ctx.put("productCartList",  productCartList);
        	
            return "myCartView";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart信息更新失败!"));
            return "error";
       }
   }

    /*删除ProductCart信息*/
    public String DeleteProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productCartDAO.DeleteProductCart(cartId);
            ctx.put("message",  java.net.URLEncoder.encode("商品删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart删除失败!"));
            return "error";
        }
    }

}
