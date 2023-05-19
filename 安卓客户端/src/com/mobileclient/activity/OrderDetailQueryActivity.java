package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.OrderDetail;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class OrderDetailQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����������������
	private Spinner spinner_orderObj;
	private ArrayAdapter<String> orderObj_adapter;
	private static  String[] orderObj_ShowText  = null;
	private List<OrderInfo> orderInfoList = null; 
	/*������Ϣ����ҵ���߼���*/
	private OrderInfoService orderInfoService = new OrderInfoService();
	// ������Ʒ����������
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null; 
	/*��Ʒ��Ϣ����ҵ���߼���*/
	private ProductInfoService productInfoService = new ProductInfoService();
	/*��ѯ�����������浽���������*/
	private OrderDetail queryConditionOrderDetail = new OrderDetail();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ������ϸ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderdetail_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_orderObj = (Spinner) findViewById(R.id.Spinner_orderObj);
		// ��ȡ���еĶ�����Ϣ
		try {
			orderInfoList = orderInfoService.QueryOrderInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int orderInfoCount = orderInfoList.size();
		orderObj_ShowText = new String[orderInfoCount+1];
		orderObj_ShowText[0] = "������";
		for(int i=1;i<=orderInfoCount;i++) { 
			orderObj_ShowText[i] = orderInfoList.get(i-1).getOrderNo();
		} 
		// ����ѡ������ArrayAdapter��������
		orderObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderObj_ShowText);
		// ���ö�����������б�ķ��
		orderObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_orderObj.setAdapter(orderObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_orderObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderDetail.setOrderObj(orderInfoList.get(arg2-1).getOrderNo()); 
				else
					queryConditionOrderDetail.setOrderObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_orderObj.setVisibility(View.VISIBLE);
		spinner_productObj = (Spinner) findViewById(R.id.Spinner_productObj);
		// ��ȡ���е���Ʒ��Ϣ
		try {
			productInfoList = productInfoService.QueryProductInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int productInfoCount = productInfoList.size();
		productObj_ShowText = new String[productInfoCount+1];
		productObj_ShowText[0] = "������";
		for(int i=1;i<=productInfoCount;i++) { 
			productObj_ShowText[i] = productInfoList.get(i-1).getProductName();
		} 
		// ����ѡ������ArrayAdapter��������
		productObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productObj_ShowText);
		// ������Ʒ���������б�ķ��
		productObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_productObj.setAdapter(productObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_productObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderDetail.setProductObj(productInfoList.get(arg2-1).getProductNo()); 
				else
					queryConditionOrderDetail.setProductObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_productObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					/*������ɺ󷵻ص�������ϸ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(OrderDetailQueryActivity.this, OrderDetailListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionOrderDetail", queryConditionOrderDetail);
					intent.putExtras(bundle);
					startActivity(intent);  
					OrderDetailQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
