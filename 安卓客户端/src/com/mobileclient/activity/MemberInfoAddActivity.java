package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class MemberInfoAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ������Ա�û��������
	private EditText ET_memberUserName;
	// ������¼���������
	private EditText ET_password;
	// ������ʵ���������
	private EditText ET_realName;
	// �����Ա������
	private EditText ET_sex;
	// ����������ڿؼ�
	private DatePicker dp_birthday;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ������ϵ���������
	private EditText ET_email;
	// ������ϵqq�����
	private EditText ET_qq;
	// ������ͥ��ַ�����
	private EditText ET_address;
	// ������Ա��ƬͼƬ��ؼ�
	private ImageView iv_photo;
	private Button btn_photo;
	protected int REQ_CODE_SELECT_IMAGE_photo = 1;
	private int REQ_CODE_CAMERA_photo = 2;
	protected String carmera_path;
	/*Ҫ����Ļ�Ա��Ϣ��Ϣ*/
	MemberInfo memberInfo = new MemberInfo();
	/*��Ա��Ϣ����ҵ���߼���*/
	private MemberInfoService memberInfoService = new MemberInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��ӻ�Ա��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.memberinfo_add); 
		ET_memberUserName = (EditText) findViewById(R.id.ET_memberUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_qq = (EditText) findViewById(R.id.ET_qq);
		ET_address = (EditText) findViewById(R.id.ET_address);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MemberInfoAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_photo);
			}
		});
		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn_photo.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_photo);  
			}
		});
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӻ�Ա��Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ա�û���*/
					if(ET_memberUserName.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��Ա�û������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_memberUserName.setFocusable(true);
						ET_memberUserName.requestFocus();
						return;
					}
					memberInfo.setMemberUserName(ET_memberUserName.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					memberInfo.setPassword(ET_password.getText().toString());
					/*��֤��ȡ��ʵ����*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��ʵ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					memberInfo.setRealName(ET_realName.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					memberInfo.setSex(ET_sex.getText().toString());
					/*��ȡ��������*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					memberInfo.setBirthday(new Timestamp(birthday.getTime()));
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					memberInfo.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ��ϵ����*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��ϵ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					memberInfo.setEmail(ET_email.getText().toString());
					/*��֤��ȡ��ϵqq*/ 
					if(ET_qq.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��ϵqq���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_qq.setFocusable(true);
						ET_qq.requestFocus();
						return;	
					}
					memberInfo.setQq(ET_qq.getText().toString());
					/*��֤��ȡ��ͥ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(MemberInfoAddActivity.this, "��ͥ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					memberInfo.setAddress(ET_address.getText().toString());
					if(memberInfo.getPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						MemberInfoAddActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String photo = HttpUtil.uploadFile(memberInfo.getPhoto());
						MemberInfoAddActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						memberInfo.setPhoto(photo);
					} else {
						memberInfo.setPhoto("upload/noimage.jpg");
					}
					/*����ҵ���߼����ϴ���Ա��Ϣ��Ϣ*/
					MemberInfoAddActivity.this.setTitle("�����ϴ���Ա��Ϣ��Ϣ���Ե�...");
					String result = memberInfoService.AddMemberInfo(memberInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���Ա��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(MemberInfoAddActivity.this, MemberInfoListActivity.class);
					startActivity(intent); 
					MemberInfoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_photo  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_photo.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_photo.setImageBitmap(booImageBm);
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER);
				this.memberInfo.setPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_photo && resultCode == Activity.RESULT_OK) {
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
				this.iv_photo.setImageBitmap(bm); 
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			memberInfo.setPhoto(filename); 
		}
	}
}
