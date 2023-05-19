package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class ProductClassAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����������������
	private EditText ET_className;
	protected String carmera_path;
	/*Ҫ�������Ʒ�����Ϣ*/
	ProductClass productClass = new ProductClass();
	/*��Ʒ������ҵ���߼���*/
	private ProductClassService productClassService = new ProductClassService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�����Ʒ���");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productclass_add); 
		ET_className = (EditText) findViewById(R.id.ET_className);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������Ʒ���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�������*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ProductClassAddActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					productClass.setClassName(ET_className.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ�����Ϣ*/
					ProductClassAddActivity.this.setTitle("�����ϴ���Ʒ�����Ϣ���Ե�...");
					String result = productClassService.AddProductClass(productClass);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���Ʒ���������*/ 
					Intent intent = new Intent();
					intent.setClass(ProductClassAddActivity.this, ProductClassListActivity.class);
					startActivity(intent); 
					ProductClassAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
