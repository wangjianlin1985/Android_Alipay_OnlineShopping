package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
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
public class YesOrNoAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����Ƿ��������
	private EditText ET_id;
	// �����Ƿ���Ϣ�����
	private EditText ET_name;
	protected String carmera_path;
	/*Ҫ������Ƿ���Ϣ��Ϣ*/
	YesOrNo yesOrNo = new YesOrNo();
	/*�Ƿ���Ϣ����ҵ���߼���*/
	private YesOrNoService yesOrNoService = new YesOrNoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-����Ƿ���Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yesorno_add); 
		ET_id = (EditText) findViewById(R.id.ET_id);
		ET_name = (EditText) findViewById(R.id.ET_name);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*��������Ƿ���Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�Ƿ���*/
					if(ET_id.getText().toString().equals("")) {
						Toast.makeText(YesOrNoAddActivity.this, "�Ƿ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_id.setFocusable(true);
						ET_id.requestFocus();
						return;
					}
					yesOrNo.setId(ET_id.getText().toString());
					/*��֤��ȡ�Ƿ���Ϣ*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(YesOrNoAddActivity.this, "�Ƿ���Ϣ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					yesOrNo.setName(ET_name.getText().toString());
					/*����ҵ���߼����ϴ��Ƿ���Ϣ��Ϣ*/
					YesOrNoAddActivity.this.setTitle("�����ϴ��Ƿ���Ϣ��Ϣ���Ե�...");
					String result = yesOrNoService.AddYesOrNo(yesOrNo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص��Ƿ���Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(YesOrNoAddActivity.this, YesOrNoListActivity.class);
					startActivity(intent); 
					YesOrNoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
