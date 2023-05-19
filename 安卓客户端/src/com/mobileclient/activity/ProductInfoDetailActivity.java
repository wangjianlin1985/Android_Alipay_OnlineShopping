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
	// �������ذ�ť
	private Button btnReturn;
	// �����鿴��Ʒ���۰�ť
	private Button btnViewEvaluate;
	// �������뵽���ﳵ��ť
	private Button btnAddToCart;
	// ������Ʒ��ſؼ�
	private TextView TV_productNo;
	// ������Ʒ���ؼ�
	private TextView TV_productClassObj;
	// ������Ʒ���ƿؼ�
	private TextView TV_productName;
	// ������ƷͼƬͼƬ��
	private ImageView iv_productPhoto;
	// ������Ʒ���ۿؼ�
	private TextView TV_productPrice;
	// ������Ʒ���ؼ�
	private TextView TV_productCount;
	// �����Ƿ��Ƽ��ؼ�
	private TextView TV_recommendFlag;
	// ��������ֵ�ؼ�
	private TextView TV_hotNum;
	// �����ϼ����ڿؼ�
	private TextView TV_onlineDate;
	/* Ҫ�������Ʒ��Ϣ��Ϣ */
	ProductInfo productInfo = new ProductInfo(); 
	/* ��Ʒ��Ϣ����ҵ���߼��� */
	private ProductInfoService productInfoService = new ProductInfoService();
	private ProductClassService productClassService = new ProductClassService();
	private YesOrNoService yesOrNoService = new YesOrNoService();
	private String productNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��Ʒ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productinfo_detail);
		// ͨ��findViewById����ʵ�������
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
				/*��ѯ�����������浽���������*/
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
		
		//����ǹ���Ա��� ���� ��Ʒ���=0 ���ؼ��뵽���ﳵ��ť
		Declare declare = (Declare)ProductInfoDetailActivity.this.getApplication();
		if(declare.getIdentify().equals("admin") || productInfo.getProductCount()==0)
			btnAddToCart.setVisibility(View.GONE); 
	
		/*�����Ʒ�����ﳵ*/
		btnAddToCart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Declare declare = (Declare)ProductInfoDetailActivity.this.getApplication();
				
				ProductCart productCart = new ProductCart();
				productCart.setProductObj(productInfo.getProductNo());
				productCart.setMemberObj(declare.getUserName());
				productCart.setPrice(productInfo.getProductPrice());
				productCart.setCount(1);
				
				ProductCartService productCartService = new ProductCartService();
				/*����ҵ���߼����ϴ���Ʒ���ﳵ��Ϣ*/
				ProductInfoDetailActivity.this.setTitle("���ڼ��뵽���ﳵ���Ե�...");
				String result = productCartService.AddProductCart(productCart);
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				/*������ɺ󷵻ص���Ʒ���ﳵ�������*/ 
				Intent intent = new Intent();
				intent.setClass(ProductInfoDetailActivity.this, ProductCartUserListActivity.class);
				startActivity(intent); 
				 
				
			}
		}); 
		
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    productInfo = productInfoService.GetProductInfo(productNo); 
		this.TV_productNo.setText(productInfo.getProductNo());
		ProductClass productClass = productClassService.GetProductClass(productInfo.getProductClassObj());
		this.TV_productClassObj.setText(productClass.getClassName());
		this.TV_productName.setText(productInfo.getProductName());
		byte[] productPhoto_data = null;
		try {
			// ��ȡͼƬ����
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
