package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class OrderStateAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ��������״̬���������
	private EditText ET_stateName;
	protected String carmera_path;
	/*Ҫ����Ķ���״̬��Ϣ��Ϣ*/
	OrderState orderState = new OrderState();
	/*����״̬��Ϣ����ҵ���߼���*/
	private OrderStateService orderStateService = new OrderStateService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��Ӷ���״̬��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderstate_add); 
		ET_stateName = (EditText) findViewById(R.id.ET_stateName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӷ���״̬��Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����״̬����*/ 
					if(ET_stateName.getText().toString().equals("")) {
						Toast.makeText(OrderStateAddActivity.this, "����״̬�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_stateName.setFocusable(true);
						ET_stateName.requestFocus();
						return;	
					}
					orderState.setStateName(ET_stateName.getText().toString());
					/*����ҵ���߼����ϴ�����״̬��Ϣ��Ϣ*/
					OrderStateAddActivity.this.setTitle("�����ϴ�����״̬��Ϣ��Ϣ���Ե�...");
					String result = orderStateService.AddOrderState(orderState);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�����״̬��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(OrderStateAddActivity.this, OrderStateListActivity.class);
					startActivity(intent); 
					OrderStateAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
