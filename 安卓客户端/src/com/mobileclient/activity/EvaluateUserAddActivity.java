package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.service.EvaluateService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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
public class EvaluateUserAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	 
	// 声明评价内容输入框
	private EditText ET_content;
	 
 
	/*要保存的商品评价信息*/
	Evaluate evaluate = new Evaluate();
	/*商品评价管理业务逻辑层*/
	private EvaluateService evaluateService = new EvaluateService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加商品评价");
		// 设置当前Activity界面布局
		setContentView(R.layout.evaluate_user_add); 
		 
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			evaluate.setProductObj(extras.getString("productObj")); 
		
		ET_content = (EditText) findViewById(R.id.ET_content);
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加商品评价按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取评价内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(EvaluateUserAddActivity.this, "评价内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					evaluate.setContent(ET_content.getText().toString());
					
					Declare declare = (Declare)EvaluateUserAddActivity.this.getApplication();
					evaluate.setMemberObj(declare.getUserName());
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					evaluate.setEvaluateTime(sdf.format(new java.util.Date()));
					
					/*调用业务逻辑层上传商品评价信息*/
					EvaluateUserAddActivity.this.setTitle("正在上传商品评价信息，稍等...");
					String result = evaluateService.AddEvaluate(evaluate);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					 
					EvaluateUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
