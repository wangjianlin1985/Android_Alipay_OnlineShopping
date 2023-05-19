package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class NoticeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_noticeId;
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

	private int noticeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改系统公告");
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_edit); 
		TV_noticeId = (TextView) findViewById(R.id.TV_noticeId);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		dp_publishDate = (DatePicker)this.findViewById(R.id.dp_publishDate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		noticeId = extras.getInt("noticeId");
		initViewData();
		/*单击修改系统公告按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取公告标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NoticeEditActivity.this, "公告标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					notice.setTitle(ET_title.getText().toString());
					/*验证获取公告内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NoticeEditActivity.this, "公告内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					notice.setContent(ET_content.getText().toString());
					/*获取出版日期*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					notice.setPublishDate(new Timestamp(publishDate.getTime()));
					/*调用业务逻辑层上传系统公告信息*/
					NoticeEditActivity.this.setTitle("正在更新系统公告信息，稍等...");
					String result = noticeService.UpdateNotice(notice);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到系统公告管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(NoticeEditActivity.this, NoticeListActivity.class);
					startActivity(intent); 
					NoticeEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    notice = noticeService.GetNotice(noticeId);
		this.TV_noticeId.setText(noticeId+"");
		this.ET_title.setText(notice.getTitle());
		this.ET_content.setText(notice.getContent());
		Date publishDate = new Date(notice.getPublishDate().getTime());
		this.dp_publishDate.init(publishDate.getYear() + 1900,publishDate.getMonth(), publishDate.getDate(), null);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
