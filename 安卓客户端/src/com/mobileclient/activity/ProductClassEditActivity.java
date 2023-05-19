package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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

public class ProductClassEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ���������TextView
	private TextView TV_classId;
	// ����������������
	private EditText ET_className;
	protected String carmera_path;
	/*Ҫ�������Ʒ�����Ϣ*/
	ProductClass productClass = new ProductClass();
	/*��Ʒ������ҵ���߼���*/
	private ProductClassService productClassService = new ProductClassService();

	private int classId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸���Ʒ���");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productclass_edit); 
		TV_classId = (TextView) findViewById(R.id.TV_classId);
		ET_className = (EditText) findViewById(R.id.ET_className);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		classId = extras.getInt("classId");
		initViewData();
		/*�����޸���Ʒ���ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�������*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ProductClassEditActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					productClass.setClassName(ET_className.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ�����Ϣ*/
					ProductClassEditActivity.this.setTitle("���ڸ�����Ʒ�����Ϣ���Ե�...");
					String result = productClassService.UpdateProductClass(productClass);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���Ʒ���������*/ 
					Intent intent = new Intent();
					intent.setClass(ProductClassEditActivity.this, ProductClassListActivity.class);
					startActivity(intent); 
					ProductClassEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    productClass = productClassService.GetProductClass(classId);
		this.TV_classId.setText(classId+"");
		this.ET_className.setText(productClass.getClassName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
