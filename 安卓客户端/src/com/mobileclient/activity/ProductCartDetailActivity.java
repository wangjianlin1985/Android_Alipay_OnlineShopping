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
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_cartId;
	// 声明用户名控件
	private TextView TV_memberObj;
	// 声明商品名称控件
	private TextView TV_productObj;
	// 声明商品单价控件
	private TextView TV_price;
	// 声明商品数量控件
	private TextView TV_count;
	/* 要保存的商品购物车信息 */
	ProductCart productCart = new ProductCart(); 
	/* 商品购物车管理业务逻辑层 */
	private ProductCartService productCartService = new ProductCartService();
	private MemberInfoService memberInfoService = new MemberInfoService();
	private ProductInfoService productInfoService = new ProductInfoService();
	private int cartId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看商品购物车详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.productcart_detail);
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
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
