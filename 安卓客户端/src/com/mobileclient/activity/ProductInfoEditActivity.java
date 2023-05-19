package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductInfoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������Ʒ���TextView
	private TextView TV_productNo;
	// ������Ʒ���������
	private Spinner spinner_productClassObj;
	private ArrayAdapter<String> productClassObj_adapter;
	private static  String[] productClassObj_ShowText  = null;
	private List<ProductClass> productClassList = null;
	/*��Ʒ������ҵ���߼���*/
	private ProductClassService productClassService = new ProductClassService();
	// ������Ʒ���������
	private EditText ET_productName;
	// ������ƷͼƬͼƬ��ؼ�
	private ImageView iv_productPhoto;
	private Button btn_productPhoto;
	protected int REQ_CODE_SELECT_IMAGE_productPhoto = 1;
	private int REQ_CODE_CAMERA_productPhoto = 2;
	// ������Ʒ���������
	private EditText ET_productPrice;
	// ������Ʒ��������
	private EditText ET_productCount;
	// �����Ƿ��Ƽ�������
	private Spinner spinner_recommendFlag;
	private ArrayAdapter<String> recommendFlag_adapter;
	private static  String[] recommendFlag_ShowText  = null;
	private List<YesOrNo> yesOrNoList = null;
	/*�Ƿ��Ƽ�����ҵ���߼���*/
	private YesOrNoService yesOrNoService = new YesOrNoService();
	// ��������ֵ�����
	private EditText ET_hotNum;
	// �����ϼ����ڿؼ�
	private DatePicker dp_onlineDate;
	protected String carmera_path;
	/*Ҫ�������Ʒ��Ϣ��Ϣ*/
	ProductInfo productInfo = new ProductInfo();
	/*��Ʒ��Ϣ����ҵ���߼���*/
	private ProductInfoService productInfoService = new ProductInfoService();

	private String productNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸���Ʒ��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.productinfo_edit); 
		TV_productNo = (TextView) findViewById(R.id.TV_productNo);
		spinner_productClassObj = (Spinner) findViewById(R.id.Spinner_productClassObj);
		// ��ȡ���е���Ʒ���
		try {
			productClassList = productClassService.QueryProductClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int productClassCount = productClassList.size();
		productClassObj_ShowText = new String[productClassCount];
		for(int i=0;i<productClassCount;i++) { 
			productClassObj_ShowText[i] = productClassList.get(i).getClassName();
		}
		// ����ѡ������ArrayAdapter��������
		productClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productClassObj_ShowText);
		// ����ͼ����������б�ķ��
		productClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_productClassObj.setAdapter(productClassObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_productClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				productInfo.setProductClassObj(productClassList.get(arg2).getClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_productClassObj.setVisibility(View.VISIBLE);
		ET_productName = (EditText) findViewById(R.id.ET_productName);
		iv_productPhoto = (ImageView) findViewById(R.id.iv_productPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_productPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProductInfoEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_productPhoto);
			}
		});
		btn_productPhoto = (Button) findViewById(R.id.btn_productPhoto);
		btn_productPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_productPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_productPhoto);  
			}
		});
		ET_productPrice = (EditText) findViewById(R.id.ET_productPrice);
		ET_productCount = (EditText) findViewById(R.id.ET_productCount);
		spinner_recommendFlag = (Spinner) findViewById(R.id.Spinner_recommendFlag);
		// ��ȡ���е��Ƿ��Ƽ�
		try {
			yesOrNoList = yesOrNoService.QueryYesOrNo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int yesOrNoCount = yesOrNoList.size();
		recommendFlag_ShowText = new String[yesOrNoCount];
		for(int i=0;i<yesOrNoCount;i++) { 
			recommendFlag_ShowText[i] = yesOrNoList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		recommendFlag_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, recommendFlag_ShowText);
		// ����ͼ����������б�ķ��
		recommendFlag_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_recommendFlag.setAdapter(recommendFlag_adapter);
		// ����¼�Spinner�¼�����
		spinner_recommendFlag.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				productInfo.setRecommendFlag(yesOrNoList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_recommendFlag.setVisibility(View.VISIBLE);
		ET_hotNum = (EditText) findViewById(R.id.ET_hotNum);
		dp_onlineDate = (DatePicker)this.findViewById(R.id.dp_onlineDate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		productNo = extras.getString("productNo");
		initViewData();
		/*�����޸���Ʒ��Ϣ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ʒ����*/ 
					if(ET_productName.getText().toString().equals("")) {
						Toast.makeText(ProductInfoEditActivity.this, "��Ʒ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_productName.setFocusable(true);
						ET_productName.requestFocus();
						return;	
					}
					productInfo.setProductName(ET_productName.getText().toString());
					if (!productInfo.getProductPhoto().startsWith("upload/")) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						ProductInfoEditActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String productPhoto = HttpUtil.uploadFile(productInfo.getProductPhoto());
						ProductInfoEditActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						productInfo.setProductPhoto(productPhoto);
					} 
					/*��֤��ȡ��Ʒ����*/ 
					if(ET_productPrice.getText().toString().equals("")) {
						Toast.makeText(ProductInfoEditActivity.this, "��Ʒ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_productPrice.setFocusable(true);
						ET_productPrice.requestFocus();
						return;	
					}
					productInfo.setProductPrice(Float.parseFloat(ET_productPrice.getText().toString()));
					/*��֤��ȡ��Ʒ���*/ 
					if(ET_productCount.getText().toString().equals("")) {
						Toast.makeText(ProductInfoEditActivity.this, "��Ʒ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_productCount.setFocusable(true);
						ET_productCount.requestFocus();
						return;	
					}
					productInfo.setProductCount(Integer.parseInt(ET_productCount.getText().toString()));
					/*��֤��ȡ����ֵ*/ 
					if(ET_hotNum.getText().toString().equals("")) {
						Toast.makeText(ProductInfoEditActivity.this, "����ֵ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_hotNum.setFocusable(true);
						ET_hotNum.requestFocus();
						return;	
					}
					productInfo.setHotNum(Integer.parseInt(ET_hotNum.getText().toString()));
					/*��ȡ��������*/
					Date onlineDate = new Date(dp_onlineDate.getYear()-1900,dp_onlineDate.getMonth(),dp_onlineDate.getDayOfMonth());
					productInfo.setOnlineDate(new Timestamp(onlineDate.getTime()));
					/*����ҵ���߼����ϴ���Ʒ��Ϣ��Ϣ*/
					ProductInfoEditActivity.this.setTitle("���ڸ�����Ʒ��Ϣ��Ϣ���Ե�...");
					String result = productInfoService.UpdateProductInfo(productInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���Ʒ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(ProductInfoEditActivity.this, ProductInfoListActivity.class);
					startActivity(intent); 
					ProductInfoEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    productInfo = productInfoService.GetProductInfo(productNo);
		this.TV_productNo.setText(productNo);
		for (int i = 0; i < productClassList.size(); i++) {
			if (productInfo.getProductClassObj() == productClassList.get(i).getClassId()) {
				this.spinner_productClassObj.setSelection(i);
				break;
			}
		}
		this.ET_productName.setText(productInfo.getProductName());
		byte[] productPhoto_data = null;
		try {
			// ��ȡͼƬ����
			productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + productInfo.getProductPhoto());
			Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0, productPhoto_data.length);
			this.iv_productPhoto.setImageBitmap(productPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_productPrice.setText(productInfo.getProductPrice() + "");
		this.ET_productCount.setText(productInfo.getProductCount() + "");
		for (int i = 0; i < yesOrNoList.size(); i++) {
			if (productInfo.getRecommendFlag().equals(yesOrNoList.get(i).getId())) {
				this.spinner_recommendFlag.setSelection(i);
				break;
			}
		}
		this.ET_hotNum.setText(productInfo.getHotNum() + "");
		Date onlineDate = new Date(productInfo.getOnlineDate().getTime());
		this.dp_onlineDate.init(onlineDate.getYear() + 1900,onlineDate.getMonth(), onlineDate.getDate(), null);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_productPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_productPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_productPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_productPhoto.setImageBitmap(booImageBm);
				this.iv_productPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.productInfo.setProductPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_productPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_productPhoto.setImageBitmap(bm); 
				this.iv_productPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			productInfo.setProductPhoto(filename); 
		}
	}
}
