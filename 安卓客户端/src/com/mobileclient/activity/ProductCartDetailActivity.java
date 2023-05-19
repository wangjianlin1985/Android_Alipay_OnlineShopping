package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ProductCart;
import com.mobileclient.service.ProductCartService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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

public class ProductCartDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_cartId;
	// �����û����ؼ�
	private TextView TV_memberObj;
	// ������Ʒ���ƿؼ�
	private TextView TV_productObj;
	// ������Ʒ���ۿؼ�
	private TextView TV_price;
	// ������Ʒ�����ؼ�
	private TextView TV_count;
	/* Ҫ�������Ʒ���ﳵ��Ϣ */
	ProductCart productCart = new ProductCart(); 
	/* ��Ʒ���ﳵ����ҵ���߼��� */
	private ProductCartService productCartService = new ProductCartService();
	private MemberInfoService memberInfoService = new MemberInfoService();
	private ProductInfoService productInfoService = new ProductInfoService();
	private int cartId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��Ʒ���ﳵ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productcart_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_cartId = (TextView) findViewById(R.id.TV_cartId);
		TV_memberObj = (TextView) findViewById(R.id.TV_memberObj);
		TV_productObj = (TextView) findViewById(R.id.TV_productObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_count = (TextView) findViewById(R.id.TV_count);
		Bundle extras = this.getIntent().getExtras();
		cartId = extras.getInt("cartId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductCartDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    productCart = productCartService.GetProductCart(cartId); 
		this.TV_cartId.setText(productCart.getCartId() + "");
		MemberInfo memberInfo = memberInfoService.GetMemberInfo(productCart.getMemberObj());
		this.TV_memberObj.setText(memberInfo.getMemberUserName());
		ProductInfo productInfo = productInfoService.GetProductInfo(productCart.getProductObj());
		this.TV_productObj.setText(productInfo.getProductName());
		this.TV_price.setText(productCart.getPrice() + "");
		this.TV_count.setText(productCart.getCount() + "");
	} 
}
