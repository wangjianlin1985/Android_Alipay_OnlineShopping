package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;

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

public class EvaluateQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ������Ʒ����������
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null; 
	/*��Ʒ��Ϣ����ҵ���߼���*/
	private ProductInfoService productInfoService = new ProductInfoService();
	// �����û���������
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null; 
	/*��Ա��Ϣ����ҵ���߼���*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	/*��ѯ�����������浽���������*/
	private Evaluate queryConditionEvaluate = new Evaluate();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ��Ʒ��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.evaluate_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
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
					queryConditionEvaluate.setProductObj(productInfoList.get(arg2-1).getProductNo()); 
				else
					queryConditionEvaluate.setProductObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_productObj.setVisibility(View.VISIBLE);
		spinner_memberObj = (Spinner) findViewById(R.id.Spinner_memberObj);
		// ��ȡ���еĻ�Ա��Ϣ
		try {
			memberInfoList = memberInfoService.QueryMemberInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int memberInfoCount = memberInfoList.size();
		memberObj_ShowText = new String[memberInfoCount+1];
		memberObj_ShowText[0] = "������";
		for(int i=1;i<=memberInfoCount;i++) { 
			memberObj_ShowText[i] = memberInfoList.get(i-1).getMemberUserName();
		} 
		// ����ѡ������ArrayAdapter��������
		memberObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, memberObj_ShowText);
		// �����û��������б�ķ��
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_memberObj.setAdapter(memberObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionEvaluate.setMemberObj(memberInfoList.get(arg2-1).getMemberUserName()); 
				else
					queryConditionEvaluate.setMemberObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_memberObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					/*������ɺ󷵻ص���Ʒ���۹������*/ 
					Intent intent = new Intent();
					intent.setClass(EvaluateQueryActivity.this, EvaluateListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionEvaluate", queryConditionEvaluate);
					intent.putExtras(bundle);
					startActivity(intent);  
					EvaluateQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
