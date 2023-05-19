package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Notice;
import com.mobileclient.service.NoticeService;
import com.mobileclient.util.NoticeSimpleAdapter;
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

public class NoticeListActivity extends Activity {
	NoticeSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int noticeId;
	/* ϵͳ�������ҵ���߼������ */
	NoticeService noticeService = new NoticeService();
	/*�����ѯ����������ϵͳ�������*/
	private Notice queryConditionNotice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--ϵͳ�����б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---ϵͳ�����б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionNotice = (Notice)extras.getSerializable("queryConditionNotice");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new NoticeSimpleAdapter(this, list,
					R.layout.notice_list_item,
					new String[] { "noticeId","title","content","publishDate" },
					new int[] { R.id.tv_noticeId,R.id.tv_title,R.id.tv_content,R.id.tv_publishDate,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(noticeListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int noticeId = Integer.parseInt(list.get(arg2).get("noticeId").toString());
            	Intent intent = new Intent();
            	intent.setClass(NoticeListActivity.this, NoticeDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("noticeId", noticeId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener noticeListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) NoticeListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭ϵͳ������Ϣ"); 
				menu.add(0, 1, 0, "ɾ��ϵͳ������Ϣ");
			} 
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ϵͳ������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			noticeId = Integer.parseInt(list.get(position).get("noticeId").toString());
			Intent intent = new Intent();
			intent.setClass(NoticeListActivity.this, NoticeEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("noticeId", noticeId);
			intent.putExtras(bundle);
			startActivity(intent);
			NoticeListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ��ϵͳ������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			noticeId = Integer.parseInt(list.get(position).get("noticeId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(NoticeListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = noticeService.DeleteNotice(noticeId);
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
			/* ��ѯϵͳ������Ϣ */
			List<Notice> noticeList = noticeService.QueryNotice(queryConditionNotice);
			for (int i = 0; i < noticeList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("noticeId", noticeList.get(i).getNoticeId());
				map.put("title", noticeList.get(i).getTitle());
				map.put("content", noticeList.get(i).getContent());
				map.put("publishDate", noticeList.get(i).getPublishDate());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare) NoticeListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			menu.add(0, 1, 1, "���ϵͳ����");
			menu.add(0, 2, 2, "��ѯϵͳ����");
			menu.add(0, 3, 3, "����������");
		} else { 
			menu.add(0, 1, 1, "��ѯϵͳ����");
			menu.add(0, 2, 2, "����������");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare) NoticeListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// ���ϵͳ������Ϣ
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, NoticeAddActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*��ѯϵͳ������Ϣ*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, NoticeQueryActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			}
		} else {
			if (item.getItemId() == 1) {
				/*��ѯϵͳ������Ϣ*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, NoticeQueryActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
