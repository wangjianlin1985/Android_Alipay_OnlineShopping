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
	/* 系统公告操作业务逻辑层对象 */
	NoticeService noticeService = new NoticeService();
	/*保存查询参数条件的系统公告对象*/
	private Notice queryConditionNotice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--系统公告列表");
		} else {
			setTitle("您好：" + username + "   当前位置---系统公告列表");
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
		// 添加长按点击
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
				menu.add(0, 0, 0, "编辑系统公告信息"); 
				menu.add(0, 1, 0, "删除系统公告信息");
			} 
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑系统公告信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			noticeId = Integer.parseInt(list.get(position).get("noticeId").toString());
			Intent intent = new Intent();
			intent.setClass(NoticeListActivity.this, NoticeEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("noticeId", noticeId);
			intent.putExtras(bundle);
			startActivity(intent);
			NoticeListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除系统公告信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			noticeId = Integer.parseInt(list.get(position).get("noticeId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(NoticeListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = noticeService.DeleteNotice(noticeId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询系统公告信息 */
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
			menu.add(0, 1, 1, "添加系统公告");
			menu.add(0, 2, 2, "查询系统公告");
			menu.add(0, 3, 3, "返回主界面");
		} else { 
			menu.add(0, 1, 1, "查询系统公告");
			menu.add(0, 2, 2, "返回主界面");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare) NoticeListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// 添加系统公告信息
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, NoticeAddActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*查询系统公告信息*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, NoticeQueryActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*返回主界面*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			}
		} else {
			if (item.getItemId() == 1) {
				/*查询系统公告信息*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, NoticeQueryActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*返回主界面*/
				Intent intent = new Intent();
				intent.setClass(NoticeListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				NoticeListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
