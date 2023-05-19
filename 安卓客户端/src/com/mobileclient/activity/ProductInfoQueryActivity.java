package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;

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

public class ProductInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明商品编号输入框
	private EditText ET_productNo;
	// 声明商品类别下拉框
	private Spinner spinner_productClassObj;
	private ArrayAdapter<String> productClassObj_adapter;
	private static  String[] productClassObj_ShowText  = null;
	private List<ProductClass> productClassList = null; 
	/*商品类别管理业务逻辑层*/
	private ProductClassService productClassService = new ProductClassService();
	// 声明商品名称输入框
	private EditText ET_productName;
	// 声明是否推荐下拉框
	private Spinner spinner_recommendFlag;
	private ArrayAdapter<String> recommendFlag_adapter;
	private static  String[] recommendFlag_ShowText  = null;
	private List<YesOrNo> yesOrNoList = null; 
	/*是否信息管理业务逻辑层*/
	private YesOrNoService yesOrNoService = new YesOrNoService();
	// 上架日期控件
	private DatePicker dp_onlineDate;
	private CheckBox cb_onlineDate;
	/*查询过滤条件保存到这个对象中*/
	private ProductInfo queryConditionProductInfo = new ProductInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询商品信息条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.productinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_productNo = (EditText) findViewById(R.id.ET_productNo);
		spinner_productClassObj = (Spinner) findViewById(R.id.Spinner_productClassObj);
		// 获取所有的商品类别
		try {
			productClassList = productClassService.QueryProductClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int productClassCount = productClassList.size();
		productClassObj_ShowText = new String[productClassCount+1];
		productClassObj_ShowText[0] = "不限制";
		for(int i=1;i<=productClassCount;i++) { 
			productClassObj_ShowText[i] = productClassList.get(i-1).getClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		productClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productClassObj_ShowText);
		// 设置商品类别下拉列表的风格
		productClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_productClassObj.setAdapter(productClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_productClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionProductInfo.setProductClassObj(productClassList.get(arg2-1).getClassId()); 
				else
					queryConditionProductInfo.setProductClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productClassObj.setVisibility(View.VISIBLE);
		ET_productName = (EditText) findViewById(R.id.ET_productName);
		spinner_recommendFlag = (Spinner) findViewById(R.id.Spinner_recommendFlag);
		// 获取所有的是否信息
		try {
			yesOrNoList = yesOrNoService.QueryYesOrNo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int yesOrNoCount = yesOrNoList.size();
		recommendFlag_ShowText = new String[yesOrNoCount+1];
		recommendFlag_ShowText[0] = "不限制";
		for(int i=1;i<=yesOrNoCount;i++) { 
			recommendFlag_ShowText[i] = yesOrNoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		recommendFlag_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, recommendFlag_ShowText);
		// 设置是否推荐下拉列表的风格
		recommendFlag_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_recommendFlag.setAdapter(recommendFlag_adapter);
		// 添加事件Spinner事件监听
		spinner_recommendFlag.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionProductInfo.setRecommendFlag(yesOrNoList.get(arg2-1).getId()); 
				else
					queryConditionProductInfo.setRecommendFlag("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_recommendFlag.setVisibility(View.VISIBLE);
		dp_onlineDate = (DatePicker) findViewById(R.id.dp_onlineDate);
		cb_onlineDate = (CheckBox) findViewById(R.id.cb_onlineDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionProductInfo.setProductNo(ET_productNo.getText().toString());
					queryConditionProductInfo.setProductName(ET_productName.getText().toString());
					if(cb_onlineDate.isChecked()) {
						/*获取上架日期*/
						Date onlineDate = new Date(dp_onlineDate.getYear()-1900,dp_onlineDate.getMonth(),dp_onlineDate.getDayOfMonth());
						queryConditionProductInfo.setOnlineDate(new Timestamp(onlineDate.getTime()));
					} else {
						queryConditionProductInfo.setOnlineDate(null);
					} 
					/*操作完成后返回到商品信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(ProductInfoQueryActivity.this, ProductInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionProductInfo", queryConditionProductInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					ProductInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
