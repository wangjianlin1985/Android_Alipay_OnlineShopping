package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Notice;
import com.mobileclient.service.NoticeService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_noticeId;
	// 声明公告标题控件
	private TextView TV_title;
	// 声明公告内容控件
	private TextView TV_content;
	// 声明发布日期控件
	private TextView TV_publishDate;
	/* 要保存的系统公告信息 */
	Notice notice = new Notice(); 
	/* 系统公告管理业务逻辑层 */
	private NoticeService noticeService = new NoticeService();
	private int noticeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看系统公告详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_noticeId = (TextView) findViewById(R.id.TV_noticeId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_publishDate = (TextView) findViewById(R.id.TV_publishDate);
		Bundle extras = this.getIntent().getExtras();
		noticeId = extras.getInt("noticeId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NoticeDetailActivity.this.finish();
			}
		}); 
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    notice = noticeService.GetNotice(noticeId); 
		this.TV_noticeId.setText(notice.getNoticeId() + "");
		this.TV_title.setText(notice.getTitle());
		this.TV_content.setText(notice.getContent());
		Date publishDate = new Date(notice.getPublishDate().getTime());
		String publishDateStr = (publishDate.getYear() + 1900) + "-" + (publishDate.getMonth()+1) + "-" + publishDate.getDate();
		this.TV_publishDate.setText(publishDateStr);
	} 
}
