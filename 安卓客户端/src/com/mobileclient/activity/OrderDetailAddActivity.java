package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.OrderDetail;
import com.mobileclient.service.OrderDetailService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
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
public class OrderDetailAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����������������
	private Spinner spinner_orderObj;
	private ArrayAdapter<String> orderObj_adapter;
	private static  String[] orderObj_ShowText  = null;
	private List<OrderInfo> orderInfoList = null;
	/*������Ź���ҵ���߼���*/
	private OrderInfoService orderInfoService = new OrderInfoService();
	// ������Ʒ����������
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null;
	/*��Ʒ���ƹ���ҵ���߼���*/
	private ProductInfoService productInfoService = new ProductInfoService();
	// ������Ʒ���������
	private EditText ET_price;
	// �����������������
	private EditText ET_count;
	protected String carmera_path;
	/*Ҫ����Ķ�����ϸ��Ϣ��Ϣ*/
	OrderDetail orderDetail = new OrderDetail();
	/*������ϸ��Ϣ����ҵ���߼���*/
	private OrderDetailService orderDetailService = new OrderDetailService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��Ӷ�����ϸ��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderdetail_add); 
		spinner_orderObj = (Spinner) findViewById(R.id.Spinner_orderObj);
		// ��ȡ���еĶ������
		try {
			orderInfoList = orderInfoService.QueryOrderInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderInfoCount = orderInfoList.size();
		orderObj_ShowText = new String[orderInfoCount];
		for(int i=0;i<orderInfoCount;i++) { 
			orderObj_ShowText[i] = orderInfoList.get(i).getOrderNo();
		}
		// ����ѡ������ArrayAdapter��������
		orderObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderObj_ShowText);
		// ���������б�ķ��
		orderObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_orderObj.setAdapter(orderObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_orderObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderDetail.setOrderObj(orderInfoList.get(arg2).getOrderNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_orderObj.setVisibility(View.VISIBLE);
		spinner_productObj = (Spinner) findViewById(R.id.Spinner_productObj);
		// ��ȡ���е���Ʒ����
		try {
			productInfoList = productInfoService.QueryProductInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int productInfoCount = productInfoList.size();
		productObj_ShowText = new String[productInfoCount];
		for(int i=0;i<productInfoCount;i++) { 
			productObj_ShowText[i] = productInfoList.get(i).getProductName();
		}
		// ����ѡ������ArrayAdapter��������
		productObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productObj_ShowText);
		// ���������б�ķ��
		productObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_productObj.setAdapter(productObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_productObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderDetail.setProductObj(productInfoList.get(arg2).getProductNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_productObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_count = (EditText) findViewById(R.id.ET_count);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӷ�����ϸ��Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ʒ����*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(OrderDetailAddActivity.this, "��Ʒ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					orderDetail.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ��������*/ 
					if(ET_count.getText().toString().equals("")) {
						Toast.makeText(OrderDetailAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_count.setFocusable(true);
						ET_count.requestFocus();
						return;	
					}
					orderDetail.setCount(Integer.parseInt(ET_count.getText().toString()));
					/*����ҵ���߼����ϴ�������ϸ��Ϣ��Ϣ*/
					OrderDetailAddActivity.this.setTitle("�����ϴ�������ϸ��Ϣ��Ϣ���Ե�...");
					String result = orderDetailService.AddOrderDetail(orderDetail);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�������ϸ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(OrderDetailAddActivity.this, OrderDetailListActivity.class);
					startActivity(intent); 
					OrderDetailAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
