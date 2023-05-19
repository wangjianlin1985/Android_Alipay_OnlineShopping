package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.alipay.AlipayConfig;
import com.alipay.OrderInfoUtil2_0;
import com.alipay.PayResult;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask; 
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class OrderInfoUserAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	 
	// �������ʽ�����
	private EditText ET_buyWay;
	// �����ջ������������
	private EditText ET_realName;
	// �����ջ��˵绰�����
	private EditText ET_telphone;
	// �����������������
	private EditText ET_postcode;
	// �����ջ���ַ�����
	private EditText ET_address;
	// ����������Ϣ�����
	private EditText ET_memo;
	 
	/*Ҫ����Ķ�����Ϣ��Ϣ*/
	OrderInfo orderInfo = new OrderInfo();
	/*������Ϣ����ҵ���߼���*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		
		// ���ñ���
		setTitle("�ֻ��ͻ���-��д������Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_user_add); 
		 
		Declare declare = (Declare)this.getApplication();
		MemberInfoService memberService = new MemberInfoService();
		MemberInfo memberInfo = memberService.GetMemberInfo(declare.getUserName());
		
		ET_buyWay = (EditText) findViewById(R.id.ET_buyWay); 
		
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_realName.setText(memberInfo.getRealName());
		
		ET_telphone = (EditText) findViewById(R.id.ET_telphone);
		ET_telphone.setText(memberInfo.getTelephone());
		
		ET_postcode = (EditText) findViewById(R.id.ET_postcode); 
		
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_address.setText(memberInfo.getAddress());
		
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӷ�����Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					
					/*���ɶ�����*/
					Declare declare = (Declare)OrderInfoUserAddActivity.this.getApplication();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
					orderInfo.setOrderNo(declare.getUserName() + sdf.format(new java.util.Date()));
					
					/*�����µ��û�*/
					orderInfo.setMemberObj(declare.getUserName());
					
					
					/*�����µ�ʱ��*/ 
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					orderInfo.setOrderTime(sdf.format(new java.util.Date()));
					
					
					//�����ܽ�� ������ȥ����
					orderInfo.setTotalMoney(1.02f);
					
					//���ʽ
					orderInfo.setBuyWay(ET_buyWay.getText().toString());
					
					//����״̬:δ����
					orderInfo.setOrderStateObj(1);
					 
					/*��֤��ȡ�ջ�������*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "�ջ����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					orderInfo.setRealName(ET_realName.getText().toString());
					/*��֤��ȡ�ջ��˵绰*/ 
					if(ET_telphone.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "�ջ��˵绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telphone.setFocusable(true);
						ET_telphone.requestFocus();
						return;	
					}
					orderInfo.setTelphone(ET_telphone.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_postcode.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_postcode.setFocusable(true);
						ET_postcode.requestFocus();
						return;	
					}
					orderInfo.setPostcode(ET_postcode.getText().toString());
					/*��֤��ȡ�ջ���ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "�ջ���ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					orderInfo.setAddress(ET_address.getText().toString());
					 
					//������Ϣ
					orderInfo.setMemo(ET_memo.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ��Ϣ*/
					OrderInfoUserAddActivity.this.setTitle("�����ύ������Ϣ��Ϣ���Ե�...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
					
					if(result.equals("�����ύ�ɹ�!")) {
						String orderNo = orderInfo.getOrderNo();
						orderInfo = orderInfoService.GetOrderInfo(orderNo);
						beginAlipay();
					} 
					
					//Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�������Ϣ�������*/ 
					//Intent intent = new Intent();
					//intent.setClass(OrderInfoUserAddActivity.this, MainMenuUserActivity.class);
					//startActivity(intent); 
					//OrderInfoUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
 
		});
	}

	/**
	 * ֧����֧��ҵ��
	 * 
	 * @param v
	 */ 
	protected void beginAlipay() {
		if (TextUtils.isEmpty(AlipayConfig.APPID) || (TextUtils.isEmpty(AlipayConfig.RSA2_PRIVATE) && TextUtils.isEmpty(AlipayConfig.RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("����").setMessage("��Ҫ����APPID | RSA_PRIVATE")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
		
		/**
		 * ����ֻ��Ϊ�˷���ֱ�����̻�չʾ֧����������֧�����̣�����Demo�м�ǩ����ֱ�ӷ��ڿͻ�����ɣ�
		 * ��ʵApp�privateKey�������Ͻ����ڿͻ��ˣ���ǩ�������Ҫ���ڷ������ɣ�
		 * ��ֹ�̻�˽������й¶����ɲ���Ҫ���ʽ���ʧ�������ٸ��ְ�ȫ���գ� 
		 * 
		 * orderInfo�Ļ�ȡ�������Է���ˣ�
		 */
		
		boolean rsa2 = (AlipayConfig.RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.APPID, rsa2,orderInfo.getOrderNo(),orderInfo.getTotalMoney(),orderInfo.getOrderNo(),"��һЩ��Ʒ");
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? AlipayConfig.RSA2_PRIVATE : AlipayConfig.RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfoStr = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(OrderInfoUserAddActivity.this);
				Map<String, String> result = alipay.payV2(orderInfoStr, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = AlipayConfig.SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
		
		
	}
	
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AlipayConfig.SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 ����֧����������̻���������˵��첽֪ͨ�����ͬ��֪ͨ���������Ϊ֧��������֪ͨ��
				 */
				String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ
				String resultStatus = payResult.getResultStatus();
				// �ж�resultStatus Ϊ9000�����֧���ɹ�
				if (TextUtils.equals(resultStatus, "9000")) {
					// �ñʶ����Ƿ���ʵ֧���ɹ�����Ҫ��������˵��첽֪ͨ��
					Toast.makeText(OrderInfoUserAddActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(OrderInfoUserAddActivity.this, MainMenuUserActivity.class);
					startActivity(intent); 
					OrderInfoUserAddActivity.this.finish();
				} else {
					// �ñʶ�����ʵ��֧���������Ҫ��������˵��첽֪ͨ��
					Toast.makeText(OrderInfoUserAddActivity.this, "֧��ʧ��"+resultStatus+resultInfo, Toast.LENGTH_LONG).show();
				}
				break;
			}
			 
			default:
				break;
			}
		};
	};
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
