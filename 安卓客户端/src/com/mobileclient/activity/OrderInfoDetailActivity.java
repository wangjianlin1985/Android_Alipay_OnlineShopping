package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.domain.OrderDetail;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������Ʒ��Ϣ��ť
	private Button btnProductDetail;
	
	// ����������ſؼ�
	private TextView TV_orderNo;
	// �����µ���Ա�ؼ�
	private TextView TV_memberObj;
	// �����µ�ʱ��ؼ�
	private TextView TV_orderTime;
	// ���������ܽ��ؼ�
	private TextView TV_totalMoney;
	// ��������״̬�ؼ�
	private TextView TV_orderStateObj;
	// �������ʽ�ؼ�
	private TextView TV_buyWay;
	// �����ջ��������ؼ�
	private TextView TV_realName;
	// �����ջ��˵绰�ؼ�
	private TextView TV_telphone;
	// ������������ؼ�
	private TextView TV_postcode;
	// �����ջ���ַ�ؼ�
	private TextView TV_address;
	// ����������Ϣ�ؼ�
	private TextView TV_memo;
	/* Ҫ����Ķ�����Ϣ��Ϣ */
	OrderInfo orderInfo = new OrderInfo(); 
	/* ������Ϣ����ҵ���߼��� */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private MemberInfoService memberInfoService = new MemberInfoService();
	private OrderStateService orderStateService = new OrderStateService();
	private String orderNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴������Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnProductDetail = (Button) findViewById(R.id.btnProductDetail);
		TV_orderNo = (TextView) findViewById(R.id.TV_orderNo);
		TV_memberObj = (TextView) findViewById(R.id.TV_memberObj);
		TV_orderTime = (TextView) findViewById(R.id.TV_orderTime);
		TV_totalMoney = (TextView) findViewById(R.id.TV_totalMoney);
		TV_orderStateObj = (TextView) findViewById(R.id.TV_orderStateObj);
		TV_buyWay = (TextView) findViewById(R.id.TV_buyWay);
		TV_realName = (TextView) findViewById(R.id.TV_realName);
		TV_telphone = (TextView) findViewById(R.id.TV_telphone);
		TV_postcode = (TextView) findViewById(R.id.TV_postcode);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		orderNo = extras.getString("orderNo");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
		
		btnProductDetail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//�鿴�����������ϸ��Ʒ��Ϣ
				Intent intent = new Intent();
				intent.setClass(OrderInfoDetailActivity.this, OrderDetailUserListActivity.class);
				Bundle bundle = new Bundle();
				OrderDetail queryConditionOrderDetail = new OrderDetail();
				queryConditionOrderDetail.setOrderObj(orderInfo.getOrderNo());
				queryConditionOrderDetail.setProductObj("");
				bundle.putSerializable("queryConditionOrderDetail", queryConditionOrderDetail);
				intent.putExtras(bundle);
				startActivity(intent);  
			}
		}); 
		
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderNo); 
		this.TV_orderNo.setText(orderInfo.getOrderNo());
		MemberInfo memberInfo = memberInfoService.GetMemberInfo(orderInfo.getMemberObj());
		this.TV_memberObj.setText(memberInfo.getMemberUserName());
		this.TV_orderTime.setText(orderInfo.getOrderTime());
		this.TV_totalMoney.setText(orderInfo.getTotalMoney() + "");
		OrderState orderState = orderStateService.GetOrderState(orderInfo.getOrderStateObj());
		this.TV_orderStateObj.setText(orderState.getStateName());
		this.TV_buyWay.setText(orderInfo.getBuyWay());
		this.TV_realName.setText(orderInfo.getRealName());
		this.TV_telphone.setText(orderInfo.getTelphone());
		this.TV_postcode.setText(orderInfo.getPostcode());
		this.TV_address.setText(orderInfo.getAddress());
		this.TV_memo.setText(orderInfo.getMemo());
	} 
}
