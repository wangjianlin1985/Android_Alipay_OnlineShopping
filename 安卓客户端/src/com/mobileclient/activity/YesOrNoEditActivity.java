package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class YesOrNoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����Ƿ���TextView
	private TextView TV_id;
	// �����Ƿ���Ϣ�����
	private EditText ET_name;
	protected String carmera_path;
	/*Ҫ������Ƿ���Ϣ��Ϣ*/
	YesOrNo yesOrNo = new YesOrNo();
	/*�Ƿ���Ϣ����ҵ���߼���*/
	private YesOrNoService yesOrNoService = new YesOrNoService();

	private String id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸��Ƿ���Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yesorno_edit); 
		TV_id = (TextView) findViewById(R.id.TV_id);
		ET_name = (EditText) findViewById(R.id.ET_name);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getString("id");
		initViewData();
		/*�����޸��Ƿ���Ϣ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�Ƿ���Ϣ*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(YesOrNoEditActivity.this, "�Ƿ���Ϣ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					yesOrNo.setName(ET_name.getText().toString());
					/*����ҵ���߼����ϴ��Ƿ���Ϣ��Ϣ*/
					YesOrNoEditActivity.this.setTitle("���ڸ����Ƿ���Ϣ��Ϣ���Ե�...");
					String result = yesOrNoService.UpdateYesOrNo(yesOrNo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص��Ƿ���Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(YesOrNoEditActivity.this, YesOrNoListActivity.class);
					startActivity(intent); 
					YesOrNoEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    yesOrNo = yesOrNoService.GetYesOrNo(id);
		this.TV_id.setText(id);
		this.ET_name.setText(yesOrNo.getName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
