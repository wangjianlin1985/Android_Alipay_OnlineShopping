package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
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

public class OrderStateEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明状态编号TextView
	private TextView TV_stateId;
	// 声明订单状态名称输入框
	private EditText ET_stateName;
	protected String carmera_path;
	/*要保存的订单状态信息信息*/
	OrderState orderState = new OrderState();
	/*订单状态信息管理业务逻辑层*/
	private OrderStateService orderStateService = new OrderStateService();

	private int stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改订单状态信息");
		// 设置当前Activity界面布局
		setContentView(R.layout.orderstate_edit); 
		TV_stateId = (TextView) findViewById(R.id.TV_stateId);
		ET_stateName = (EditText) findViewById(R.id.ET_stateName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		stateId = extras.getInt("stateId");
		initViewData();
		/*单击修改订单状态信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取订单状态名称*/ 
					if(ET_stateName.getText().toString().equals("")) {
						Toast.makeText(OrderStateEditActivity.this, "订单状态名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stateName.setFocusable(true);
						ET_stateName.requestFocus();
						return;	
					}
					orderState.setStateName(ET_stateName.getText().toString());
					/*调用业务逻辑层上传订单状态信息信息*/
					OrderStateEditActivity.this.setTitle("正在更新订单状态信息信息，稍等...");
					String result = orderStateService.UpdateOrderState(orderState);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到订单状态信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(OrderStateEditActivity.this, OrderStateListActivity.class);
					startActivity(intent); 
					OrderStateEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    orderState = orderStateService.GetOrderState(stateId);
		this.TV_stateId.setText(stateId+"");
		this.ET_stateName.setText(orderState.getStateName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
