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
	// ������ѯ��ť
	private Button btnQuery;
	// ������Ա�û��������
	private EditText ET_memberUserName;
	// �������ڿؼ�
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	/*��ѯ�����������浽���������*/
	private MemberInfo queryConditionMemberInfo = new MemberInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ��Ա��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.memberinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_memberUserName = (EditText) findViewById(R.id.ET_memberUserName);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionMemberInfo.setMemberUserName(ET_memberUserName.getText().toString());
					if(cb_birthday.isChecked()) {
						/*��ȡ��������*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionMemberInfo.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionMemberInfo.setBirthday(null);
					} 
					/*������ɺ󷵻ص���Ա��Ϣ�������*/ 
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
