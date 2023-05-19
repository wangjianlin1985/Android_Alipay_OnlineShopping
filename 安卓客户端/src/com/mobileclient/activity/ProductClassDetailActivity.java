package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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

public class ProductClassDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������ſؼ�
	private TextView TV_classId;
	// ����������ƿؼ�
	private TextView TV_className;
	/* Ҫ�������Ʒ�����Ϣ */
	ProductClass productClass = new ProductClass(); 
	/* ��Ʒ������ҵ���߼��� */
	private ProductClassService productClassService = new ProductClassService();
	private int classId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��Ʒ�������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productclass_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_classId = (TextView) findViewById(R.id.TV_classId);
		TV_className = (TextView) findViewById(R.id.TV_className);
		Bundle extras = this.getIntent().getExtras();
		classId = extras.getInt("classId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductClassDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    productClass = productClassService.GetProductClass(classId); 
		this.TV_classId.setText(productClass.getClassId() + "");
		this.TV_className.setText(productClass.getClassName());
	} 
}
