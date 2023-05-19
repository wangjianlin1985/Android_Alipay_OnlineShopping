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

    /*�������Ҫ��ѯ������: �������*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderNo() {
        return this.orderNo;
    }

    /*�������Ҫ��ѯ������: �µ���Ա*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
    }

    /*�������Ҫ��ѯ������: �µ���ʼʱ��*/
    private String orderStartTime;
    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }
    public String getOrderStartTime() {
        return this.orderStartTime;
    }
    
    /*�������Ҫ��ѯ������: �µ�����ʱ��*/
    private String orderEndTime;
    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }
    public String getOrderEndTime() {
        return this.orderEndTime;
    }

    
    /*�������Ҫ��ѯ������: �µ�ʱ��*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }
    
    
    /*�������Ҫ��ѯ������: ����״̬*/
    private OrderState orderStateObj;
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public OrderState getOrderStateObj() {
        return this.orderStateObj;
    }

    /*�������Ҫ��ѯ������: �ջ�������*/
    private String realName;
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return this.realName;
    }

    /*�������Ҫ��ѯ������: �ջ��˵绰*/
    private String telphone;
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getTelphone() {
        return this.telphone;
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

    /*��������OrderInfo����*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*��ת�����OrderInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�MemberInfo��Ϣ*/
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*��ѯ���е�OrderState��Ϣ*/
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "add_view";
    }

    /*���OrderInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        
        /*���ɶ�����ţ� ��ǰ�û���  + ��ǰʱ�� */
        String memberUserName = (String)ctx.getSession().get("memberUserName"); //��ȡ����ǰ�Ļ�Ա����
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmss"); 
        String timeStr = sdf.format(new java.util.Date());
        String orderNo = memberUserName + timeStr; 
        /*��֤��������Ƿ��Ѿ�����*/ 
        OrderInfo db_orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);
        if(null != db_orderInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ö�������Ѿ�����!"));
            return "error";
        }
        orderInfo.setOrderNo(orderNo);
        
        //�����µ���Ա
        MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
        MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);
        orderInfo.setMemberObj(memberObj);
        
        /*��ȡ���µ�ʱ��*/
        sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderInfo.setOrderTime(sdf.format(new java.util.Date()));
        
        
        /*���ö���״̬: δ����*/
        OrderStateDAO orderStateDAO = new OrderStateDAO();
        OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(1);
        orderInfo.setOrderStateObj(orderStateObj);
        
        /*�������ﳵ���㶩���ܽ��*/
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
            ctx.put("message",  java.net.URLEncoder.encode("������ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo���ʧ��!"));
            return "error";
        }
    }

    
    /*�����ύ����*/
    public String WillAddOrderInfo() { 
    	ActionContext ctx = ActionContext.getContext(); 
    	String memberUserName = (String)ctx.getSession().get("memberUserName");
    	ProductCartDAO productCartDAO = new ProductCartDAO();
    	List<ProductCart> productCartList = productCartDAO.QueryMyProductCartInfo(memberUserName);
    	MemberInfo memberInfo = (new MemberInfoDAO()).GetMemberInfoByMemberUserName(memberUserName);
    	if(productCartList.size() == 0) {
    		ctx.put("error",  java.net.URLEncoder.encode("����ѡ����Ʒ��"));
            return "error";
    	} 
    	ctx.put("memberInfo", memberInfo);
    	ctx.put("productCartList",  productCartList);
    	return "myOrderView";
    }
    
    
    /*��ѯOrderInfo��Ϣ*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderStartTime == null) orderStartTime = "";
        if(orderEndTime == null) orderEndTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        
        /*�����ܽ��*/
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

    /*��ѯOrderInfo��Ϣ*/
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
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderTime, orderStateObj, realName, telphone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    
    
    
    /*ǰ̨��ѯOrderInfo��Ϣ*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderStartTime == null) orderStartTime = "";
        if(orderEndTime == null) orderEndTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderStartTime, orderEndTime, orderStateObj, realName, telphone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderNo��ȡOrderInfo����*/
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

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderNo��ȡOrderInfo����*/
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

    /*�����޸�OrderInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderInfo��Ϣ*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderNo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
