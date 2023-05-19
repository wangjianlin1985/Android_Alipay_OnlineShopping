package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
import com.mobileclient.util.YesOrNoSimpleAdapter;
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

public class YesOrNoListActivity extends Activity {
	YesOrNoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String id;
	/* �Ƿ���Ϣ����ҵ���߼������ */
	YesOrNoService yesOrNoService = new YesOrNoService();
	/*�����ѯ�����������Ƿ���Ϣ����*/
	private YesOrNo queryConditionYesOrNo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yesorno_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--�Ƿ���Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---�Ƿ���Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionYesOrNo = (YesOrNo)extras.getSerializable("queryConditionYesOrNo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new YesOrNoSimpleAdapter(this, list,
					R.layout.yesorno_list_item,
					new String[] { "id","name" },
					new int[] { R.id.tv_id,R.id.tv_name,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(yesOrNoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String id = list.get(arg2).get("id").toString();
            	Intent intent = new Intent();
            	intent.setClass(YesOrNoListActivity.this, YesOrNoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("id", id);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener yesOrNoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭�Ƿ���Ϣ��Ϣ"); 
			menu.add(0, 1, 0, "ɾ���Ƿ���Ϣ��Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭�Ƿ���Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�Ƿ���
			id = list.get(position).get("id").toString();
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, YesOrNoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ���Ƿ���Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�Ƿ���
			id = list.get(position).get("id").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(YesOrNoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = yesOrNoService.DeleteYesOrNo(id);
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
			/* ��ѯ�Ƿ���Ϣ��Ϣ */
			List<YesOrNo> yesOrNoList = yesOrNoService.QueryYesOrNo(queryConditionYesOrNo);
			for (int i = 0; i < yesOrNoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", yesOrNoList.get(i).getId());
				map.put("name", yesOrNoList.get(i).getName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "����Ƿ���Ϣ");
		menu.add(0, 2, 2, "��ѯ�Ƿ���Ϣ");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// ����Ƿ���Ϣ��Ϣ
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, YesOrNoAddActivity.class);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ�Ƿ���Ϣ��Ϣ*/
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, YesOrNoQueryActivity.class);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		}
		return true; 
	}
}
