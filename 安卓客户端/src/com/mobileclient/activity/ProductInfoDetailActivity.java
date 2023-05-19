package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.domain.ProductCart;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductCartService;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明查看商品评价按钮
	private Button btnViewEvaluate;
	// 声明加入到购物车按钮
	private Button btnAddToCart;
	// 声明商品编号控件
	private TextView TV_productNo;
	// 声明商品类别控件
	private TextView TV_productClassObj;
	// 声明商品名称控件
	private TextView TV_productName;
	// 声明商品图片图片框
	private ImageView iv_productPhoto;
	// 声明商品单价控件
	private TextView TV_productPrice;
	// 声明商品库存控件
	private TextView TV_productCount;
	// 声明是否推荐控件
	private TextView TV_recommendFlag;
	// 声明人气值控件
	private TextView TV_hotNum;
	// 声明上架日期控件
	private TextView TV_onlineDate;
	/* 要保存的商品信息信息 */
	ProductInfo productInfo = new ProductInfo(); 
	/* 商品信息管理业务逻辑层 */
	private ProductInfoService productInfoService = new ProductInfoService();
	private ProductClassService productClassService = new ProductClassService();
	private YesOrNoService yesOrNoService = new YesOrNoService();
	private String productNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看商品信息详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.productinfo_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnViewEvaluate = (Button) findViewById(R.id.btnViewEvaluate);
		btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
		TV_productNo = (TextView) findViewById(R.id.TV_productNo);
		TV_productClassObj = (TextView) findViewById(R.id.TV_productClassObj);
		TV_productName = (TextView) findViewById(R.id.TV_productName);
		iv_productPhoto = (ImageView) findViewById(R.id.iv_productPhoto); 
		TV_productPrice = (TextView) findViewById(R.id.TV_productPrice);
		TV_productCount = (TextView) findViewById(R.id.TV_productCount);
		TV_recommendFlag = (TextView) findViewById(R.id.TV_recommendFlag);
		TV_hotNum = (TextView) findViewById(R.id.TV_hotNum);
		TV_onlineDate = (TextView) findViewById(R.id.TV_onlineDate);
		Bundle extras = this.getIntent().getExtras();
		productNo = extras.getString("productNo");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductInfoDetailActivity.this.finish();
			}
		}); 
		
		btnViewEvaluate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*查询过滤条件保存到这个对象中*/
				Evaluate queryConditionEvaluate = new Evaluate();
				queryConditionEvaluate.setProductObj(productNo);
				queryConditionEvaluate.setMemberObj("");
				Intent intent = new Intent();
				intent.setClass(ProductInfoDetailActivity.this, EvaluateListOfOneProductActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("queryConditionEvaluate", queryConditionEvaluate);
				intent.putExtras(bundle);
				startActivity(intent);  
				
			}
		}); 
		
		//如果是管理员身份 或者 商品库存=0 隐藏加入到购物车按钮
		Declare declare = (Declare)ProductInfoDetailActivity.this.getApplication();
		if(declare.getIdentify().equals("admin") || productInfo.getProductCount()==0)
			btnAddToCart.setVisibility(View.GONE); 
	
		/*添加商品到购物车*/
		btnAddToCart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Declare declare = (Declare)ProductInfoDetailActivity.this.getApplication();
				
				ProductCart productCart = new ProductCart();
				productCart.setProductObj(productInfo.getProductNo());
				productCart.setMemberObj(declare.getUserName());
				productCart.setPrice(productInfo.getProductPrice());
				productCart.setCount(1);
				
				ProductCartService productCartService = new ProductCartService();
				/*调用业务逻辑层上传商品购物车信息*/
				ProductInfoDetailActivity.this.setTitle("正在加入到购物车，稍等...");
				String result = productCartService.AddProductCart(productCart);
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				/*操作完成后返回到商品购物车管理界面*/ 
				Intent intent = new Intent();
				intent.setClass(ProductInfoDetailActivity.this, ProductCartUserListActivity.class);
				startActivity(intent); 
				 
				
			}
		}); 
		
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    productInfo = productInfoService.GetProductInfo(productNo); 
		this.TV_productNo.setText(productInfo.getProductNo());
		ProductClass productClass = productClassService.GetProductClass(productInfo.getProductClassObj());
		this.TV_productClassObj.setText(productClass.getClassName());
		this.TV_productName.setText(productInfo.getProductName());
		byte[] productPhoto_data = null;
		try {
			// 获取图片数据
			productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + productInfo.getProductPhoto());
			Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0,productPhoto_data.length);
			this.iv_productPhoto.setImageBitmap(productPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_productPrice.setText(productInfo.getProductPrice() + "");
		this.TV_productCount.setText(productInfo.getProductCount() + "");
		YesOrNo yesOrNo = yesOrNoService.GetYesOrNo(productInfo.getRecommendFlag());
		this.TV_recommendFlag.setText(yesOrNo.getName());
		this.TV_hotNum.setText(productInfo.getHotNum() + "");
		Date onlineDate = new Date(productInfo.getOnlineDate().getTime());
		String onlineDateStr = (onlineDate.getYear() + 1900) + "-" + (onlineDate.getMonth()+1) + "-" + onlineDate.getDate();
		this.TV_onlineDate.setText(onlineDateStr);
	} 
}
