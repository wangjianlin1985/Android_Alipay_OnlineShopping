package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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

public class ProductClassDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明类别编号控件
	private TextView TV_classId;
	// 声明类别名称控件
	private TextView TV_className;
	/* 要保存的商品类别信息 */
	ProductClass productClass = new ProductClass(); 
	/* 商品类别管理业务逻辑层 */
	private ProductClassService productClassService = new ProductClassService();
	private int classId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看商品类别详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.productclass_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_classId = (TextView) findViewById(R.id.TV_classId);
		TV_className = (TextView) findViewById(R.id.TV_className);
		Bundle extras = this.getIntent().getExtras();
		classId = extras.getInt("classId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductClassDetailActivity.this.finish();
			}
		}); 
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    productClass = productClassService.GetProductClass(classId); 
		this.TV_classId.setText(productClass.getClassId() + "");
		this.TV_className.setText(productClass.getClassName());
	} 
}
