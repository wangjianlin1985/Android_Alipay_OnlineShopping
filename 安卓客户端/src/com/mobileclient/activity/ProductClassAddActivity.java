package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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
public class ProductClassAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明类别名称输入框
	private EditText ET_className;
	protected String carmera_path;
	/*要保存的商品类别信息*/
	ProductClass productClass = new ProductClass();
	/*商品类别管理业务逻辑层*/
	private ProductClassService productClassService = new ProductClassService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加商品类别");
		// 设置当前Activity界面布局
		setContentView(R.layout.productclass_add); 
		ET_className = (EditText) findViewById(R.id.ET_className);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加商品类别按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取类别名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ProductClassAddActivity.this, "类别名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					productClass.setClassName(ET_className.getText().toString());
					/*调用业务逻辑层上传商品类别信息*/
					ProductClassAddActivity.this.setTitle("正在上传商品类别信息，稍等...");
					String result = productClassService.AddProductClass(productClass);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到商品类别管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(ProductClassAddActivity.this, ProductClassListActivity.class);
					startActivity(intent); 
					ProductClassAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
