package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.service.EvaluateService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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

public class EvaluateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明评价编号控件
	private TextView TV_evaluateId;
	// 声明商品名称控件
	private TextView TV_productObj;
	// 声明用户名控件
	private TextView TV_memberObj;
	// 声明评价内容控件
	private TextView TV_content;
	// 声明评价时间控件
	private TextView TV_evaluateTime;
	/* 要保存的商品评价信息 */
	Evaluate evaluate = new Evaluate(); 
	/* 商品评价管理业务逻辑层 */
	private EvaluateService evaluateService = new EvaluateService();
	private ProductInfoService productInfoService = new ProductInfoService();
	private MemberInfoService memberInfoService = new MemberInfoService();
	private int evaluateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看商品评价详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.evaluate_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_evaluateId = (TextView) findViewById(R.id.TV_evaluateId);
		TV_productObj = (TextView) findViewById(R.id.TV_productObj);
		TV_memberObj = (TextView) findViewById(R.id.TV_memberObj);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_evaluateTime = (TextView) findViewById(R.id.TV_evaluateTime);
		Bundle extras = this.getIntent().getExtras();
		evaluateId = extras.getInt("evaluateId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EvaluateDetailActivity.this.finish();
			}
		}); 
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    evaluate = evaluateService.GetEvaluate(evaluateId); 
		this.TV_evaluateId.setText(evaluate.getEvaluateId() + "");
		ProductInfo productInfo = productInfoService.GetProductInfo(evaluate.getProductObj());
		this.TV_productObj.setText(productInfo.getProductName());
		MemberInfo memberInfo = memberInfoService.GetMemberInfo(evaluate.getMemberObj());
		this.TV_memberObj.setText(memberInfo.getMemberUserName());
		this.TV_content.setText(evaluate.getContent());
		this.TV_evaluateTime.setText(evaluate.getEvaluateTime());
	} 
}
