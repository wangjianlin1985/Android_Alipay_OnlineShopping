package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.util.MemberInfoSimpleAdapter;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MemberInfoListActivity extends Activity {
	MemberInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String memberUserName;
	/* ��Ա��Ϣ����ҵ���߼������ */
	MemberInfoService memberInfoService = new MemberInfoService();
	/*�����ѯ���������Ļ�Ա��Ϣ����*/
	private MemberInfo queryConditionMemberInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memberinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--��Ա��Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---��Ա��Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionMemberInfo = (MemberInfo)extras.getSerializable("queryConditionMemberInfo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new MemberInfoSimpleAdapter(this, list,
					R.layout.memberinfo_list_item,
					new String[] { "memberUserName","realName","sex","birthday","photo" },
					new int[] { R.id.tv_memberUserName,R.id.tv_realName,R.id.tv_sex,R.id.tv_birthday,R.id.iv_photo,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(memberInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String memberUserName = list.get(arg2).get("memberUserName").toString();
            	Intent intent = new Intent();
            	intent.setClass(MemberInfoListActivity.this, MemberInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("memberUserName", memberUserName);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener memberInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭��Ա��Ϣ��Ϣ"); 
			menu.add(0, 1, 0, "ɾ����Ա��Ϣ��Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭��Ա��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��Ա�û���
			memberUserName = list.get(position).get("memberUserName").toString();
			Intent intent = new Intent();
			intent.setClass(MemberInfoListActivity.this, MemberInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("memberUserName", memberUserName);
			intent.putExtras(bundle);
			startActivity(intent);
			MemberInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ����Ա��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��Ա�û���
			memberUserName = list.get(position).get("memberUserName").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(MemberInfoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = memberInfoService.DeleteMemberInfo(memberUserName);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯ��Ա��Ϣ��Ϣ */
			List<MemberInfo> memberInfoList = memberInfoService.QueryMemberInfo(queryConditionMemberInfo);
			for (int i = 0; i < memberInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("memberUserName", memberInfoList.get(i).getMemberUserName());
				map.put("realName", memberInfoList.get(i).getRealName());
				map.put("sex", memberInfoList.get(i).getSex());
				map.put("birthday", memberInfoList.get(i).getBirthday());
				byte[] photo_data = ImageService.getImage(HttpUtil.BASE_URL+ memberInfoList.get(i).getPhoto());// ��ȡͼƬ����
				BitmapFactory.Options photo_opts = new BitmapFactory.Options();  
				photo_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(photo_data, 0, photo_data.length, photo_opts); 
				photo_opts.inSampleSize = photoListActivity.computeSampleSize(photo_opts, -1, 100*100); 
				photo_opts.inJustDecodeBounds = false; 
				try {
					Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0, photo_data.length, photo_opts);
					map.put("photo", photo);
				} catch (OutOfMemoryError err) { }
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "��ӻ�Ա��Ϣ");
		menu.add(0, 2, 2, "��ѯ��Ա��Ϣ");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// ��ӻ�Ա��Ϣ��Ϣ
			Intent intent = new Intent();
			intent.setClass(MemberInfoListActivity.this, MemberInfoAddActivity.class);
			startActivity(intent);
			MemberInfoListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ��Ա��Ϣ��Ϣ*/
			Intent intent = new Intent();
			intent.setClass(MemberInfoListActivity.this, MemberInfoQueryActivity.class);
			startActivity(intent);
			MemberInfoListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(MemberInfoListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			MemberInfoListActivity.this.finish();
		}
		return true; 
	}
}
