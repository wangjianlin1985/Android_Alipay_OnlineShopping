package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.MemberInfo;

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

public class MemberInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明会员用户名输入框
	private EditText ET_memberUserName;
	// 出生日期控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	/*查询过滤条件保存到这个对象中*/
	private MemberInfo queryConditionMemberInfo = new MemberInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询会员信息条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.memberinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_memberUserName = (EditText) findViewById(R.id.ET_memberUserName);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionMemberInfo.setMemberUserName(ET_memberUserName.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取出生日期*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionMemberInfo.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionMemberInfo.setBirthday(null);
					} 
					/*操作完成后返回到会员信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(MemberInfoQueryActivity.this, MemberInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionMemberInfo", queryConditionMemberInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					MemberInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
