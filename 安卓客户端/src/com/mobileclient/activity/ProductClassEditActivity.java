package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductClassEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明类别编号TextView
	private TextView TV_classId;
	// 声明类别名称输入框
	private EditText ET_className;
	protected String carmera_path;
	/*要保存的商品类别信息*/
	ProductClass productClass = new ProductClass();
	/*商品类别管理业务逻辑层*/
	private ProductClassService productClassService = new ProductClassService();

	private int classId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改商品类别");
		// 设置当前Activity界面布局
		setContentView(R.layout.productclass_edit); 
		TV_classId = (TextView) findViewById(R.id.TV_classId);
		ET_className = (EditText) findViewById(R.id.ET_className);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		classId = extras.getInt("classId");
		initViewData();
		/*单击修改商品类别按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取类别名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ProductClassEditActivity.this, "类别名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					productClass.setClassName(ET_className.getText().toString());
					/*调用业务逻辑层上传商品类别信息*/
					ProductClassEditActivity.this.setTitle("正在更新商品类别信息，稍等...");
					String result = productClassService.UpdateProductClass(productClass);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到商品类别管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(ProductClassEditActivity.this, ProductClassListActivity.class);
					startActivity(intent); 
					ProductClassEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    productClass = productClassService.GetProductClass(classId);
		this.TV_classId.setText(classId+"");
		this.ET_className.setText(productClass.getClassName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
