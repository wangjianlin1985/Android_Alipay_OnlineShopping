package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
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

public class YesOrNoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����Ƿ��ſؼ�
	private TextView TV_id;
	// �����Ƿ���Ϣ�ؼ�
	private TextView TV_name;
	/* Ҫ������Ƿ���Ϣ��Ϣ */
	YesOrNo yesOrNo = new YesOrNo(); 
	/* �Ƿ���Ϣ����ҵ���߼��� */
	private YesOrNoService yesOrNoService = new YesOrNoService();
	private String id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴�Ƿ���Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yesorno_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_name = (TextView) findViewById(R.id.TV_name);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getString("id");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				YesOrNoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    yesOrNo = yesOrNoService.GetYesOrNo(id); 
		this.TV_id.setText(yesOrNo.getId());
		this.TV_name.setText(yesOrNo.getName());
	} 
}
