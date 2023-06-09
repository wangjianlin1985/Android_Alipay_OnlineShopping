package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
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
public class YesOrNoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明是否编号输入框
	private EditText ET_id;
	// 声明是否信息输入框
	private EditText ET_name;
	protected String carmera_path;
	/*要保存的是否信息信息*/
	YesOrNo yesOrNo = new YesOrNo();
	/*是否信息管理业务逻辑层*/
	private YesOrNoService yesOrNoService = new YesOrNoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加是否信息");
		// 设置当前Activity界面布局
		setContentView(R.layout.yesorno_add); 
		ET_id = (EditText) findViewById(R.id.ET_id);
		ET_name = (EditText) findViewById(R.id.ET_name);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加是否信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取是否编号*/
					if(ET_id.getText().toString().equals("")) {
						Toast.makeText(YesOrNoAddActivity.this, "是否编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_id.setFocusable(true);
						ET_id.requestFocus();
						return;
					}
					yesOrNo.setId(ET_id.getText().toString());
					/*验证获取是否信息*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(YesOrNoAddActivity.this, "是否信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					yesOrNo.setName(ET_name.getText().toString());
					/*调用业务逻辑层上传是否信息信息*/
					YesOrNoAddActivity.this.setTitle("正在上传是否信息信息，稍等...");
					String result = yesOrNoService.AddYesOrNo(yesOrNo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到是否信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(YesOrNoAddActivity.this, YesOrNoListActivity.class);
					startActivity(intent); 
					YesOrNoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
