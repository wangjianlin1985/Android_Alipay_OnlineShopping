package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;

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

public class ProductInfoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ������Ʒ��������
	private EditText ET_productNo;
	// ������Ʒ���������
	private Spinner spinner_productClassObj;
	private ArrayAdapter<String> productClassObj_adapter;
	private static  String[] productClassObj_ShowText  = null;
	private List<ProductClass> productClassList = null; 
	/*��Ʒ������ҵ���߼���*/
	private ProductClassService productClassService = new ProductClassService();
	// ������Ʒ���������
	private EditText ET_productName;
	// �����Ƿ��Ƽ�������
	private Spinner spinner_recommendFlag;
	private ArrayAdapter<String> recommendFlag_adapter;
	private static  String[] recommendFlag_ShowText  = null;
	private List<YesOrNo> yesOrNoList = null; 
	/*�Ƿ���Ϣ����ҵ���߼���*/
	private YesOrNoService yesOrNoService = new YesOrNoService();
	// �ϼ����ڿؼ�
	private DatePicker dp_onlineDate;
	private CheckBox cb_onlineDate;
	/*��ѯ�����������浽���������*/
	private ProductInfo queryConditionProductInfo = new ProductInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ��Ʒ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_productNo = (EditText) findViewById(R.id.ET_productNo);
		spinner_productClassObj = (Spinner) findViewById(R.id.Spinner_productClassObj);
		// ��ȡ���е���Ʒ���
		try {
			productClassList = productClassService.QueryProductClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int productClassCount = productClassList.size();
		productClassObj_ShowText = new String[productClassCount+1];
		productClassObj_ShowText[0] = "������";
		for(int i=1;i<=productClassCount;i++) { 
			productClassObj_ShowText[i] = productClassList.get(i-1).getClassName();
		} 
		// ����ѡ������ArrayAdapter��������
		productClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productClassObj_ShowText);
		// ������Ʒ��������б�ķ��
		productClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_productClassObj.setAdapter(productClassObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_productClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionProductInfo.setProductClassObj(productClassList.get(arg2-1).getClassId()); 
				else
					queryConditionProductInfo.setProductClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_productClassObj.setVisibility(View.VISIBLE);
		ET_productName = (EditText) findViewById(R.id.ET_productName);
		spinner_recommendFlag = (Spinner) findViewById(R.id.Spinner_recommendFlag);
		// ��ȡ���е��Ƿ���Ϣ
		try {
			yesOrNoList = yesOrNoService.QueryYesOrNo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int yesOrNoCount = yesOrNoList.size();
		recommendFlag_ShowText = new String[yesOrNoCount+1];
		recommendFlag_ShowText[0] = "������";
		for(int i=1;i<=yesOrNoCount;i++) { 
			recommendFlag_ShowText[i] = yesOrNoList.get(i-1).getName();
		} 
		// ����ѡ������ArrayAdapter��������
		recommendFlag_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, recommendFlag_ShowText);
		// �����Ƿ��Ƽ������б�ķ��
		recommendFlag_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_recommendFlag.setAdapter(recommendFlag_adapter);
		// ����¼�Spinner�¼�����
		spinner_recommendFlag.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionProductInfo.setRecommendFlag(yesOrNoList.get(arg2-1).getId()); 
				else
					queryConditionProductInfo.setRecommendFlag("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_recommendFlag.setVisibility(View.VISIBLE);
		dp_onlineDate = (DatePicker) findViewById(R.id.dp_onlineDate);
		cb_onlineDate = (CheckBox) findViewById(R.id.cb_onlineDate);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionProductInfo.setProductNo(ET_productNo.getText().toString());
					queryConditionProductInfo.setProductName(ET_productName.getText().toString());
					if(cb_onlineDate.isChecked()) {
						/*��ȡ�ϼ�����*/
						Date onlineDate = new Date(dp_onlineDate.getYear()-1900,dp_onlineDate.getMonth(),dp_onlineDate.getDayOfMonth());
						queryConditionProductInfo.setOnlineDate(new Timestamp(onlineDate.getTime()));
					} else {
						queryConditionProductInfo.setOnlineDate(null);
					} 
					/*������ɺ󷵻ص���Ʒ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(ProductInfoQueryActivity.this, ProductInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionProductInfo", queryConditionProductInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					ProductInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
