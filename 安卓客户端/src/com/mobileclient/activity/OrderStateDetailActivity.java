package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
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

public class OrderStateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明状态编号控件
	private TextView TV_stateId;
	// 声明订单状态名称控件
	private TextView TV_stateName;
	/* 要保存的订单状态信息信息 */
	OrderState orderState = new OrderState(); 
	/* 订单状态信息管理业务逻辑层 */
	private OrderStateService orderStateService = new OrderStateService();
	private int stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看订单状态信息详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.orderstate_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_stateId = (TextView) findViewById(R.id.TV_stateId);
		TV_stateName = (TextView) findViewById(R.id.TV_stateName);
		Bundle extras = this.getIntent().getExtras();
		stateId = extras.getInt("stateId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderStateDetailActivity.this.finish();
			}
		}); 
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderState = orderStateService.GetOrderState(stateId); 
		this.TV_stateId.setText(orderState.getStateId() + "");
		this.TV_stateName.setText(orderState.getStateName());
	} 
}
