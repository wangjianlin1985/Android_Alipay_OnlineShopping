package com.alipay;

/**
 *  ��Ҫ˵��:
 *  
 *  ����ֻ��Ϊ�˷���ֱ�����̻�չʾ֧����������֧�����̣�����Demo�м�ǩ����ֱ�ӷ��ڿͻ�����ɣ�
 *  ��ʵApp�privateKey�������Ͻ����ڿͻ��ˣ���ǩ�������Ҫ���ڷ������ɣ�
 *  ��ֹ�̻�˽������й¶����ɲ���Ҫ���ʽ���ʧ�������ٸ��ְ�ȫ���գ� 
 */
public class AlipayConfig {
	/** ֧����֧��ҵ�����app_id */
	public static final String APPID = "201609***635507"; 
	/** ֧�����˻���¼��Ȩҵ�����pidֵ */
	public static final String PID = "208810****28577";
	/** �̻�˽Կ��pkcs8��ʽ */
	/** ����˽Կ��RSA2_PRIVATE ���� RSA_PRIVATE ֻ��Ҫ����һ�� */
	/** ����̻������������ˣ�����ʹ�� RSA2_PRIVATE */
	/** RSA2_PRIVATE ���Ա�֤�̻������ڸ��Ӱ�ȫ�Ļ����½��У�����ʹ�� RSA2_PRIVATE */
	/** ��ȡ RSA2_PRIVATE������ʹ��֧�����ṩ�Ĺ�˽Կ���ɹ������ɣ� */
	/** ���ߵ�ַ��https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "*********";
	public static final String RSA_PRIVATE = "";
	
	public static final String Notify_URL = "http://55a2rb.nfafgroe.cc/AndroidShopping/NotifyUrlServlet";
	
	public static final int SDK_PAY_FLAG = 1;
	public static final int SDK_AUTH_FLAG = 2;
}
