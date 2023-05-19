package com.alipay;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class AlipayConfig {
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "201609***635507"; 
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "208810****28577";
	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "*********";
	public static final String RSA_PRIVATE = "";
	
	public static final String Notify_URL = "http://55a2rb.nfafgroe.cc/AndroidShopping/NotifyUrlServlet";
	
	public static final int SDK_PAY_FLAG = 1;
	public static final int SDK_AUTH_FLAG = 2;
}
