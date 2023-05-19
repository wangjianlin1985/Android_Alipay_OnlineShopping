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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明公告标题输入框
	private EditText ET_title;
	// 声明公告内容输入框
	private EditText ET_content;
	// 出版发布日期控件
	private DatePicker dp_publishDate;
	protected String carmera_path;
	/*要保存的系统公告信息*/
	Notice notice = new Notice();
	/*系统公告管理业务逻辑层*/
	private NoticeService noticeService = new NoticeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加系统公告");
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_add); 
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		dp_publishDate = (DatePicker)this.findViewById(R.id.dp_publishDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加系统公告按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取公告标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "公告标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					notice.setTitle(ET_title.getText().toString());
					/*验证获取公告内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NoticeAddActivity.this, "公告内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					notice.setContent(ET_content.getText().toString());
					/*获取出版日期*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					notice.setPublishDate(new Timestamp(publishDate.getTime()));
					/*调用业务逻辑层上传系统公告信息*/
					NoticeAddActivity.this.setTitle("正在上传系统公告信息，稍等...");
					String result = noticeService.AddNotice(notice);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到系统公告管理界面*/ 
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
