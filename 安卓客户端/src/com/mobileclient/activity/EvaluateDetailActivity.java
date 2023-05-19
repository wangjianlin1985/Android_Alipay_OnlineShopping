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
	// �������ذ�ť
	private Button btnReturn;
	// �������۱�ſؼ�
	private TextView TV_evaluateId;
	// ������Ʒ���ƿؼ�
	private TextView TV_productObj;
	// �����û����ؼ�
	private TextView TV_memberObj;
	// �����������ݿؼ�
	private TextView TV_content;
	// ��������ʱ��ؼ�
	private TextView TV_evaluateTime;
	/* Ҫ�������Ʒ������Ϣ */
	Evaluate evaluate = new Evaluate(); 
	/* ��Ʒ���۹���ҵ���߼��� */
	private EvaluateService evaluateService = new EvaluateService();
	private ProductInfoService productInfoService = new ProductInfoService();
	private MemberInfoService memberInfoService = new MemberInfoService();
	private int evaluateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��Ʒ��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.evaluate_detail);
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
