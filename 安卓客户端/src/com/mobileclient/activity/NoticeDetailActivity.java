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
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_noticeId;
	// �����������ؼ�
	private TextView TV_title;
	// �����������ݿؼ�
	private TextView TV_content;
	// �����������ڿؼ�
	private TextView TV_publishDate;
	/* Ҫ�����ϵͳ������Ϣ */
	Notice notice = new Notice(); 
	/* ϵͳ�������ҵ���߼��� */
	private NoticeService noticeService = new NoticeService();
	private int noticeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴ϵͳ��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.notice_detail);
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
