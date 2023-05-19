package com.shuangyulin.action;

import java.util.ArrayList;

import com.shuangyulin.dao.ProductInfoDAO;
import com.shuangyulin.domain.ProductInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {

	public String HomePage(){
		
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		/*��ѯ�Ƽ���ʽ*/
		ArrayList<ProductInfo> recommentProductList = productInfoDAO.QueryRecommendProductInfoInfo();
		
		/*��ѯ������Ʒ*/
		ArrayList<ProductInfo> hotProductList = productInfoDAO.QueryHotProductInfoInfo();
		
		/*��ѯ�����ϼܵ���Ʒ*/
		ArrayList<ProductInfo> recentProductList = productInfoDAO.QueryRecentProductInfoInfo();
		
		ActionContext ctx = ActionContext.getContext();
		
		ctx.put("recommentProductList", recommentProductList);
		ctx.put("hotProductList", hotProductList);
		ctx.put("recentProductList", recentProductList);
		
		return "home_page_view";
	}
}
