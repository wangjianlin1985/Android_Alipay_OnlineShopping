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
	// 声明查询按钮
	private Button btnQuery;
	// 声明公告标题输入框
	private EditText ET_title;
	// 发布日期控件
	private DatePicker dp_publishDate;
	private CheckBox cb_publishDate;
	/*查询过滤条件保存到这个对象中*/
	private Notice queryConditionNotice = new Notice();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询系统公告条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		dp_publishDate = (DatePicker) findViewById(R.id.dp_publishDate);
		cb_publishDate = (CheckBox) findViewById(R.id.cb_publishDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionNotice.setTitle(ET_title.getText().toString());
					if(cb_publishDate.isChecked()) {
						/*获取发布日期*/
						Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
						queryConditionNotice.setPublishDate(new Timestamp(publishDate.getTime()));
					} else {
						queryConditionNotice.setPublishDate(null);
					} 
					/*操作完成后返回到系统公告管理界面*/ 
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
