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
	// 声明确定添加按钮
	private Button btnAdd;
	 
	// 声明付款方式输入框
	private EditText ET_buyWay;
	// 声明收货人姓名输入框
	private EditText ET_realName;
	// 声明收货人电话输入框
	private EditText ET_telphone;
	// 声明邮政编码输入框
	private EditText ET_postcode;
	// 声明收货地址输入框
	private EditText ET_address;
	// 声明附加信息输入框
	private EditText ET_memo;
	 
	/*要保存的订单信息信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*订单信息管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		
		// 设置标题
		setTitle("手机客户端-填写订单信息");
		// 设置当前Activity界面布局
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
		/*单击添加订单信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					
					/*生成订单号*/
					Declare declare = (Declare)OrderInfoUserAddActivity.this.getApplication();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
					orderInfo.setOrderNo(declare.getUserName() + sdf.format(new java.util.Date()));
					
					/*设置下单用户*/
					orderInfo.setMemberObj(declare.getUserName());
					
					
					/*设置下单时间*/ 
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					orderInfo.setOrderTime(sdf.format(new java.util.Date()));
					
					
					//订单总金额 服务器去计算
					orderInfo.setTotalMoney(1.02f);
					
					//付款方式
					orderInfo.setBuyWay(ET_buyWay.getText().toString());
					
					//订单状态:未付款
					orderInfo.setOrderStateObj(1);
					 
					/*验证获取收货人姓名*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "收货人姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					orderInfo.setRealName(ET_realName.getText().toString());
					/*验证获取收货人电话*/ 
					if(ET_telphone.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "收货人电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telphone.setFocusable(true);
						ET_telphone.requestFocus();
						return;	
					}
					orderInfo.setTelphone(ET_telphone.getText().toString());
					/*验证获取邮政编码*/ 
					if(ET_postcode.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "邮政编码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_postcode.setFocusable(true);
						ET_postcode.requestFocus();
						return;	
					}
					orderInfo.setPostcode(ET_postcode.getText().toString());
					/*验证获取收货地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(OrderInfoUserAddActivity.this, "收货地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					orderInfo.setAddress(ET_address.getText().toString());
					 
					//附加信息
					orderInfo.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传订单信息信息*/
					OrderInfoUserAddActivity.this.setTitle("正在提交订单信息信息，稍等...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
					
					if(result.equals("订单提交成功!")) {
						String orderNo = orderInfo.getOrderNo();
						orderInfo = orderInfoService.GetOrderInfo(orderNo);
						beginAlipay();
					} 
					
					//Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到订单信息管理界面*/ 
					//Intent intent = new Intent();
					//intent.setClass(OrderInfoUserAddActivity.this, MainMenuUserActivity.class);
					//startActivity(intent); 
					//OrderInfoUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
 
		});
	}

	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */ 
	protected void beginAlipay() {
		if (TextUtils.isEmpty(AlipayConfig.APPID) || (TextUtils.isEmpty(AlipayConfig.RSA2_PRIVATE) && TextUtils.isEmpty(AlipayConfig.RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
		
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
		
		boolean rsa2 = (AlipayConfig.RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.APPID, rsa2,orderInfo.getOrderNo(),orderInfo.getTotalMoney(),orderInfo.getOrderNo(),"买一些商品");
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
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(OrderInfoUserAddActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(OrderInfoUserAddActivity.this, MainMenuUserActivity.class);
					startActivity(intent); 
					OrderInfoUserAddActivity.this.finish();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(OrderInfoUserAddActivity.this, "支付失败"+resultStatus+resultInfo, Toast.LENGTH_LONG).show();
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
