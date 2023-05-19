package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderDetail;
import com.mobileclient.service.OrderDetailService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
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

public class OrderDetailDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_detailId;
	// ����������ſؼ�
	private TextView TV_orderObj;
	// ������Ʒ���ƿؼ�
	private TextView TV_productObj;
	// ������Ʒ���ۿؼ�
	private TextView TV_price;
	// �������������ؼ�
	private TextView TV_count;
	/* Ҫ����Ķ�����ϸ��Ϣ��Ϣ */
	OrderDetail orderDetail = new OrderDetail(); 
	/* ������ϸ��Ϣ����ҵ���߼��� */
	private OrderDetailService orderDetailService = new OrderDetailService();
	private OrderInfoService orderInfoService = new OrderInfoService();
	private ProductInfoService productInfoService = new ProductInfoService();
	private int detailId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴������ϸ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderdetail_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_detailId = (TextView) findViewById(R.id.TV_detailId);
		TV_orderObj = (TextView) findViewById(R.id.TV_orderObj);
		TV_productObj = (TextView) findViewById(R.id.TV_productObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_count = (TextView) findViewById(R.id.TV_count);
		Bundle extras = this.getIntent().getExtras();
		detailId = extras.getInt("detailId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderDetailDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    orderDetail = orderDetailService.GetOrderDetail(detailId); 
		this.TV_detailId.setText(orderDetail.getDetailId() + "");
		OrderInfo orderInfo = orderInfoService.GetOrderInfo(orderDetail.getOrderObj());
		this.TV_orderObj.setText(orderInfo.getOrderNo());
		ProductInfo productInfo = productInfoService.GetProductInfo(orderDetail.getProductObj());
		this.TV_productObj.setText(productInfo.getProductName());
		this.TV_price.setText(orderDetail.getPrice() + "");
		this.TV_count.setText(orderDetail.getCount() + "");
	} 
}
