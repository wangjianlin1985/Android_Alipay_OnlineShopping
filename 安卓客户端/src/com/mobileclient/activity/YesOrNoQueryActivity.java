package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.YesOrNo;

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

public class YesOrNoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	/*��ѯ�����������浽���������*/
	private YesOrNo queryConditionYesOrNo = new YesOrNo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ�Ƿ���Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yesorno_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					/*������ɺ󷵻ص��Ƿ���Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(YesOrNoQueryActivity.this, YesOrNoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionYesOrNo", queryConditionYesOrNo);
					intent.putExtras(bundle);
					startActivity(intent);  
					YesOrNoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
