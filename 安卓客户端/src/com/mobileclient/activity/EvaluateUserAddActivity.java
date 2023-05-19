package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.service.EvaluateService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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
public class EvaluateUserAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	 
	// �����������������
	private EditText ET_content;
	 
 
	/*Ҫ�������Ʒ������Ϣ*/
	Evaluate evaluate = new Evaluate();
	/*��Ʒ���۹���ҵ���߼���*/
	private EvaluateService evaluateService = new EvaluateService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�����Ʒ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.evaluate_user_add); 
		 
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			evaluate.setProductObj(extras.getString("productObj")); 
		
		ET_content = (EditText) findViewById(R.id.ET_content);
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������Ʒ���۰�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(EvaluateUserAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					evaluate.setContent(ET_content.getText().toString());
					
					Declare declare = (Declare)EvaluateUserAddActivity.this.getApplication();
					evaluate.setMemberObj(declare.getUserName());
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					evaluate.setEvaluateTime(sdf.format(new java.util.Date()));
					
					/*����ҵ���߼����ϴ���Ʒ������Ϣ*/
					EvaluateUserAddActivity.this.setTitle("�����ϴ���Ʒ������Ϣ���Ե�...");
					String result = evaluateService.AddEvaluate(evaluate);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					 
					EvaluateUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
