package com.mobileclient.activity;

import java.util.Date;
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

public class MemberInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������Ա�û����ؼ�
	private TextView TV_memberUserName;
	// ������¼����ؼ�
	private TextView TV_password;
	// ������ʵ�����ؼ�
	private TextView TV_realName;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����������ڿؼ�
	private TextView TV_birthday;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// ������ϵ����ؼ�
	private TextView TV_email;
	// ������ϵqq�ؼ�
	private TextView TV_qq;
	// ������ͥ��ַ�ؼ�
	private TextView TV_address;
	// ������Ա��ƬͼƬ��
	private ImageView iv_photo;
	/* Ҫ����Ļ�Ա��Ϣ��Ϣ */
	MemberInfo memberInfo = new MemberInfo(); 
	/* ��Ա��Ϣ����ҵ���߼��� */
	private MemberInfoService memberInfoService = new MemberInfoService();
	private String memberUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��Ա��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.memberinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_memberUserName = (TextView) findViewById(R.id.TV_memberUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_realName = (TextView) findViewById(R.id.TV_realName);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_qq = (TextView) findViewById(R.id.TV_qq);
		TV_address = (TextView) findViewById(R.id.TV_address);
		iv_photo = (ImageView) findViewById(R.id.iv_photo); 
		Bundle extras = this.getIntent().getExtras();
		memberUserName = extras.getString("memberUserName");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MemberInfoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    memberInfo = memberInfoService.GetMemberInfo(memberUserName); 
		this.TV_memberUserName.setText(memberInfo.getMemberUserName());
		this.TV_password.setText(memberInfo.getPassword());
		this.TV_realName.setText(memberInfo.getRealName());
		this.TV_sex.setText(memberInfo.getSex());
		Date birthday = new Date(memberInfo.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_telephone.setText(memberInfo.getTelephone());
		this.TV_email.setText(memberInfo.getEmail());
		this.TV_qq.setText(memberInfo.getQq());
		this.TV_address.setText(memberInfo.getAddress());
		byte[] photo_data = null;
		try {
			// ��ȡͼƬ����
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + memberInfo.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0,photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
