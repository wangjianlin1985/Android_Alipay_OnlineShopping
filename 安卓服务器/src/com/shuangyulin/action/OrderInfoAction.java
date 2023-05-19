package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.ProductCartDAO;
import com.shuangyulin.dao.OrderInfoDAO;
import com.shuangyulin.domain.ProductCart;
import com.shuangyulin.domain.OrderInfo;
import com.shuangyulin.dao.MemberInfoDAO;
import com.shuangyulin.domain.MemberInfo;
import com.shuangyulin.dao.OrderStateDAO;
import com.shuangyulin.domain.OrderState;
import com.shuangyulin.test.TestUtil;

public class OrderInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 订单编号*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderNo() {
        return this.orderNo;
    }

    /*界面层需要查询的属性: 下单会员*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
    }

    /*界面层需要查询的属性: 下单开始时间*/
    private String orderStartTime;
    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }
    public String getOrderStartTime() {
        return this.orderStartTime;
    }
    
    /*界面层需要查询的属性: 下单结束时间*/
    private String orderEndTime;
    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }
    public String getOrderEndTime() {
        return this.orderEndTime;
    }

    
    /*界面层需要查询的属性: 下单时间*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }
    
    
    /*界面层需要查询的属性: 订单状态*/
    private OrderState orderStateObj;
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public OrderState getOrderStateObj() {
        return this.orderStateObj;
    }

    /*界面层需要查询的属性: 收货人姓名*/
    private String realName;
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return this.realName;
    }

    /*界面层需要查询的属性: 收货人电话*/
    private String telphone;
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getTelphone() {
        return this.telphone;
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

    /*待操作的OrderInfo对象*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*跳转到添加OrderInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的MemberInfo信息*/
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*查询所有的OrderState信息*/
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "add_view";
    }

    /*添加OrderInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        
        /*生成订单编号： 当前用户名  + 当前时间 */
        String memberUserName = (String)ctx.getSession().get("memberUserName"); //获取到当前的会员名称
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmss"); 
        String timeStr = sdf.format(new java.util.Date());
        String orderNo = memberUserName + timeStr; 
        /*验证订单编号是否已经存在*/ 
        OrderInfo db_orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);
        if(null != db_orderInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该订单编号已经存在!"));
            return "error";
        }
        orderInfo.setOrderNo(orderNo);
        
        //设置下单会员
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);
        orderInfo.setMemberObj(memberObj);
        
        /*获取到下单时间*/
        sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderInfo.setOrderTime(sdf.format(new java.util.Date()));
        
        
        /*设置订单状态: 未付款*/
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(1);
        orderInfo.setOrderStateObj(orderStateObj);
        
        /*遍历购物车计算订单总金额*/
        float totalMoney = 0.0f;
        ProductCartDAO productCartDAO = new ProductCartDAO();
        List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
        for(int i=0;i<productCartList.size();i++) {
        	ProductCart productCart = productCartList.get(i);
        	totalMoney += productCart.getPrice() * productCart.getCount();
        }
        orderInfo.setTotalMoney(totalMoney); 
        
        try {  
        	
            orderInfoDAO.AddOrderInfo(orderInfo,productCartList);
            ctx.put("message",  java.net.URLEncoder.encode("订单添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo添加失败!"));
            return "error";
        }
    }

    
    /*即将提交订单*/
    public String WillAddOrderInfo() { 
    	ActionContext ctx = ActionContext.getContext(); 
    	String memberUserName = (String)ctx.getSession().get("memberUserName");
    	ProductCartDAO productCartDAO = new ProductCartDAO();
    	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
    	MemberInfo memberInfo = (new MemberInfoDAO()).GetMemberInfoByMemberUserName(memberUserName);
    	if(productCartList.size() == 0) {
    		ctx.put("error",  java.net.URLEncoder.encode("请先选购商品！"));
            return "error";
    	} 
    	ctx.put("memberInfo", memberInfo);
    	ctx.put("productCartList",  productCartList);
    	return "myOrderView";
    }
    
    
    /*查询OrderInfo信息*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderStartTime == null) orderStartTime = "";
        if(orderEndTime == null) orderEndTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        
        /*销售总金额*/
        float totalMoney = 0.0f;
        for(int i=0;i<orderInfoList.size();i++) {
        	totalMoney += orderInfoList.get(i).getTotalMoney();
        }
        
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        ctx.put("memberObj", memberObj);
        ctx.put("totalMoney", String.format("%.2f", totalMoney));
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("orderTime", orderTime);
        ctx.put("orderStateObj", orderStateObj);
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("realName", realName);
        ctx.put("telphone", telphone);
        return "query_view";
    }

    /*查询OrderInfo信息*/
    public String MyOrderInfoQuery() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        
        ActionContext ctx = ActionContext.getContext();
        String memberUserName = (String)ctx.getSession().get("memberUserName");
        MemberInfo memberObj = new MemberInfo();
        memberObj.setMemberUserName(memberUserName);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderTime, orderStateObj, realName, telphone, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderTime, orderStateObj, realName, telphone);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
       
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        
        ctx.put("orderTime", orderTime);
        ctx.put("orderStateObj", orderStateObj);
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("realName", realName);
        ctx.put("telphone", telphone);
        return "member_query_view";
    }

    
    
    
    /*前台查询OrderInfo信息*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderStartTime == null) orderStartTime = "";
        if(orderEndTime == null) orderEndTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        ctx.put("memberObj", memberObj);
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("orderStartTime", orderStartTime);
        ctx.put("orderEndTime", orderEndTime);
        ctx.put("orderStateObj", orderStateObj);
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("realName", realName);
        ctx.put("telphone", telphone);
        return "front_query_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderNo获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderNo获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*更新修改OrderInfo信息*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(orderInfo.getMemberObj().getMemberUserName());
            orderInfo.setMemberObj(memberObj);
            }
            if(true) {
            OrderStateDAO orderStateDAO = new OrderStateDAO();
            OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getOrderStateObj().getStateId());
            orderInfo.setOrderStateObj(orderStateObj);
            }
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderInfo信息*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderNo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo删除失败!"));
            return "error";
        }
    }

}
