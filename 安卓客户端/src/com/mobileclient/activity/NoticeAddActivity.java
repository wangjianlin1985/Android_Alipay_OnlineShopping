package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Notice;
import com.mobileclient.service.NoticeService;
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
public class NoticeAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����������������
	private EditText ET_title;
	// �����������������
	private EditText ET_content;
	// ���淢�����ڿؼ�
	private DatePicker dp_publishDate;
	protected String carmera_path;
	/*Ҫ�����ϵͳ������Ϣ*/
	Notice notice = new Notice();
	/*ϵͳ�������ҵ���߼���*/
	private NoticeService noticeService = new NoticeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ϵͳ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.notice_add); 
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		dp_publishDate = (DatePicker)this.findViewById(R.id.dp_publishDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ϵͳ���水ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�������*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					notice.setTitle(ET_title.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					notice.setContent(ET_content.getText().toString());
					/*��ȡ��������*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					notice.setPublishDate(new Timestamp(publishDate.getTime()));
					/*����ҵ���߼����ϴ�ϵͳ������Ϣ*/
					NoticeAddActivity.this.setTitle("�����ϴ�ϵͳ������Ϣ���Ե�...");
					String result = noticeService.AddNotice(notice);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�ϵͳ����������*/ 
					Intent intent = new Intent();
					intent.setClass(NoticeAddActivity.this, NoticeListActivity.class);
					startActivity(intent); 
					NoticeAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
