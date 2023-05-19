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
	// �������ذ�ť
	private Button btnReturn;
	// ����״̬��ſؼ�
	private TextView TV_stateId;
	// ��������״̬���ƿؼ�
	private TextView TV_stateName;
	/* Ҫ����Ķ���״̬��Ϣ��Ϣ */
	OrderState orderState = new OrderState(); 
	/* ����״̬��Ϣ����ҵ���߼��� */
	private OrderStateService orderStateService = new OrderStateService();
	private int stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴����״̬��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderstate_detail);
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    orderState = orderStateService.GetOrderState(stateId); 
		this.TV_stateId.setText(orderState.getStateId() + "");
		this.TV_stateName.setText(orderState.getStateName());
	} 
}
