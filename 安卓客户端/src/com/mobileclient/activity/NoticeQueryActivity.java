package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NoticeQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ����������������
	private EditText ET_title;
	// �������ڿؼ�
	private DatePicker dp_publishDate;
	private CheckBox cb_publishDate;
	/*��ѯ�����������浽���������*/
	private Notice queryConditionNotice = new Notice();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯϵͳ��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.notice_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		dp_publishDate = (DatePicker) findViewById(R.id.dp_publishDate);
		cb_publishDate = (CheckBox) findViewById(R.id.cb_publishDate);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionNotice.setTitle(ET_title.getText().toString());
					if(cb_publishDate.isChecked()) {
						/*��ȡ��������*/
						Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
						queryConditionNotice.setPublishDate(new Timestamp(publishDate.getTime()));
					} else {
						queryConditionNotice.setPublishDate(null);
					} 
					/*������ɺ󷵻ص�ϵͳ����������*/ 
					Intent intent = new Intent();
					intent.setClass(NoticeQueryActivity.this, NoticeListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionNotice", queryConditionNotice);
					intent.putExtras(bundle);
					startActivity(intent);  
					NoticeQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
