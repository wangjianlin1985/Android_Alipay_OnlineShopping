package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
public class OrderInfoAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����������������
	private EditText ET_orderNo;
	// �����µ���Ա������
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null;
	/*�µ���Ա����ҵ���߼���*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	// �����µ�ʱ�������
	private EditText ET_orderTime;
	// ���������ܽ�������
	private EditText ET_totalMoney;
	// ��������״̬������
	private Spinner spinner_orderStateObj;
	private ArrayAdapter<String> orderStateObj_adapter;
	private static  String[] orderStateObj_ShowText  = null;
	private List<OrderState> orderStateList = null;
	/*����״̬����ҵ���߼���*/
	private OrderStateService orderStateService = new OrderStateService();
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
	protected String carmera_path;
	/*Ҫ����Ķ�����Ϣ��Ϣ*/
	OrderInfo orderInfo = new OrderInfo();
	/*������Ϣ����ҵ���߼���*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��Ӷ�����Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_add); 
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_memberObj = (Spinner) findViewById(R.id.Spinner_memberObj);
		// ��ȡ���е��µ���Ա
		try {
			memberInfoList = memberInfoService.QueryMemberInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int memberInfoCount = memberInfoList.size();
		memberObj_ShowText = new String[memberInfoCount];
		for(int i=0;i<memberInfoCount;i++) { 
			memberObj_ShowText[i] = memberInfoList.get(i).getMemberUserName();
		}
		// ����ѡ������ArrayAdapter��������
		memberObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, memberObj_ShowText);
		// ���������б�ķ��
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_memberObj.setAdapter(memberObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setMemberObj(memberInfoList.get(arg2).getMemberUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_memberObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		spinner_orderStateObj = (Spinner) findViewById(R.id.Spinner_orderStateObj);
		// ��ȡ���еĶ���״̬
		try {
			orderStateList = orderStateService.QueryOrderState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderStateCount = orderStateList.size();
		orderStateObj_ShowText = new String[orderStateCount];
		for(int i=0;i<orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i).getStateName();
		}
		// ����ѡ������ArrayAdapter��������
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// ���������б�ķ��
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setOrderStateObj(orderStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		ET_buyWay = (EditText) findViewById(R.id.ET_buyWay);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_telphone = (EditText) findViewById(R.id.ET_telphone);
		ET_postcode = (EditText) findViewById(R.id.ET_postcode);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӷ�����Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�������*/
					if(ET_orderNo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_orderNo.setFocusable(true);
						ET_orderNo.requestFocus();
						return;
					}
					orderInfo.setOrderNo(ET_orderNo.getText().toString());
					/*��֤��ȡ�µ�ʱ��*/ 
					if(ET_orderTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "�µ�ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_orderTime.setFocusable(true);
						ET_orderTime.requestFocus();
						return;	
					}
					orderInfo.setOrderTime(ET_orderTime.getText().toString());
					/*��֤��ȡ�����ܽ��*/ 
					if(ET_totalMoney.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "�����ܽ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_totalMoney.setFocusable(true);
						ET_totalMoney.requestFocus();
						return;	
					}
					orderInfo.setTotalMoney(Float.parseFloat(ET_totalMoney.getText().toString()));
					/*��֤��ȡ���ʽ*/ 
					if(ET_buyWay.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "���ʽ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_buyWay.setFocusable(true);
						ET_buyWay.requestFocus();
						return;	
					}
					orderInfo.setBuyWay(ET_buyWay.getText().toString());
					/*��֤��ȡ�ջ�������*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "�ջ����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					orderInfo.setRealName(ET_realName.getText().toString());
					/*��֤��ȡ�ջ��˵绰*/ 
					if(ET_telphone.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "�ջ��˵绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telphone.setFocusable(true);
						ET_telphone.requestFocus();
						return;	
					}
					orderInfo.setTelphone(ET_telphone.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_postcode.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_postcode.setFocusable(true);
						ET_postcode.requestFocus();
						return;	
					}
					orderInfo.setPostcode(ET_postcode.getText().toString());
					/*��֤��ȡ�ջ���ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "�ջ���ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					orderInfo.setAddress(ET_address.getText().toString());
					/*��֤��ȡ������Ϣ*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "������Ϣ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					orderInfo.setMemo(ET_memo.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ��Ϣ*/
					OrderInfoAddActivity.this.setTitle("�����ϴ�������Ϣ��Ϣ���Ե�...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�������Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(OrderInfoAddActivity.this, OrderInfoListActivity.class);
					startActivity(intent); 
					OrderInfoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
