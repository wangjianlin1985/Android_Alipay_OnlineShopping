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
	/* 是否信息操作业务逻辑层对象 */
	YesOrNoService yesOrNoService = new YesOrNoService();
	/*保存查询参数条件的是否信息对象*/
	private YesOrNo queryConditionYesOrNo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yesorno_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--是否信息列表");
		} else {
			setTitle("您好：" + username + "   当前位置---是否信息列表");
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
		// 添加长按点击
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
			menu.add(0, 0, 0, "编辑是否信息信息"); 
			menu.add(0, 1, 0, "删除是否信息信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑是否信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取是否编号
			id = list.get(position).get("id").toString();
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, YesOrNoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除是否信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取是否编号
			id = list.get(position).get("id").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(YesOrNoListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = yesOrNoService.DeleteYesOrNo(id);
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
			/* 查询是否信息信息 */
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
		menu.add(0, 1, 1, "添加是否信息");
		menu.add(0, 2, 2, "查询是否信息");
		menu.add(0, 3, 3, "返回主界面");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// 添加是否信息信息
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, YesOrNoAddActivity.class);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*查询是否信息信息*/
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, YesOrNoQueryActivity.class);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*返回主界面*/
			Intent intent = new Intent();
			intent.setClass(YesOrNoListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			YesOrNoListActivity.this.finish();
		}
		return true; 
	}
}
