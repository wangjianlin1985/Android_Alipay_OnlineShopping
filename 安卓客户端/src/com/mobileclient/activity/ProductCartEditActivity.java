package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.ProductCart;
import com.mobileclient.service.ProductCartService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductCartEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������¼���TextView
	private TextView TV_cartId;
	// ������Ʒ���� TextView
	private TextView TV_productObj;
	
	// �����û���������
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null;
	/*�û�������ҵ���߼���*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	// ������Ʒ����������
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null;
	/*��Ʒ���ƹ���ҵ���߼���*/
	private ProductInfoService productInfoService = new ProductInfoService();
	// ������Ʒ���������
	private EditText ET_price;
	// ������Ʒ���������
	private EditText ET_count;
	protected String carmera_path;
	/*Ҫ�������Ʒ���ﳵ��Ϣ*/
	ProductCart productCart = new ProductCart();
	/*��Ʒ���ﳵ����ҵ���߼���*/
	private ProductCartService productCartService = new ProductCartService();

	private int cartId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸���Ʒ���ﳵ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productcart_edit); 
		TV_cartId = (TextView) findViewById(R.id.TV_cartId);
		TV_productObj = (TextView)findViewById(R.id.TV_productObj);
		spinner_memberObj = (Spinner) findViewById(R.id.Spinner_memberObj);
		// ��ȡ���е��û���
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
		// ����ͼ����������б�ķ��
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_memberObj.setAdapter(memberObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				productCart.setMemberObj(memberInfoList.get(arg2).getMemberUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_memberObj.setVisibility(View.VISIBLE);
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
		// ����ͼ����������б�ķ��
		productObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_productObj.setAdapter(productObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_productObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				productCart.setProductObj(productInfoList.get(arg2).getProductNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_productObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_count = (EditText) findViewById(R.id.ET_count);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		cartId = extras.getInt("cartId");
		initViewData();
		/*�����޸���Ʒ���ﳵ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ʒ����*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(ProductCartEditActivity.this, "��Ʒ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					productCart.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ��Ʒ����*/ 
					if(ET_count.getText().toString().equals("")) {
						Toast.makeText(ProductCartEditActivity.this, "��Ʒ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_count.setFocusable(true);
						ET_count.requestFocus();
						return;	
					}
					productCart.setCount(Integer.parseInt(ET_count.getText().toString()));
					/*����ҵ���߼����ϴ���Ʒ���ﳵ��Ϣ*/
					ProductCartEditActivity.this.setTitle("���ڸ�����Ʒ���ﳵ��Ϣ���Ե�...");
					String result = productCartService.UpdateProductCart(productCart);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���Ʒ���ﳵ�������*/ 
					Intent intent = new Intent();
					Declare declare = (Declare) ProductCartEditActivity.this.getApplication();
					if(declare.getIdentify().equals("admin"))
						intent.setClass(ProductCartEditActivity.this, ProductCartListActivity.class);
					else
						intent.setClass(ProductCartEditActivity.this, ProductCartUserListActivity.class);
					startActivity(intent); 
					ProductCartEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    productCart = productCartService.GetProductCart(cartId);
		this.TV_cartId.setText(cartId+"");
		for (int i = 0; i < memberInfoList.size(); i++) {
			if (productCart.getMemberObj().equals(memberInfoList.get(i).getMemberUserName())) {
				this.spinner_memberObj.setSelection(i); 
				break;
			}
		}
		for (int i = 0; i < productInfoList.size(); i++) {
			if (productCart.getProductObj().equals(productInfoList.get(i).getProductNo())) {
				this.spinner_productObj.setSelection(i);
				TV_productObj.setText(productInfoList.get(i).getProductName());
				break;
			}
		}
		this.ET_price.setText(productCart.getPrice() + "");
		this.ET_count.setText(productCart.getCount() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
