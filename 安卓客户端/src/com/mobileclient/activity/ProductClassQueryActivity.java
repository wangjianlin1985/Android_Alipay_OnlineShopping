package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.ProductClass;

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

public class ProductClassQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	/*查询过滤条件保存到这个对象中*/
	private ProductClass queryConditionProductClass = new ProductClass();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询商品类别条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.productclass_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					/*操作完成后返回到商品类别管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(ProductClassQueryActivity.this, ProductClassListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionProductClass", queryConditionProductClass);
					intent.putExtras(bundle);
					startActivity(intent);  
					ProductClassQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
