package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.service.EvaluateService;
import com.mobileclient.util.EvaluateSimpleAdapter;
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

public class EvaluateListOfOneProductActivity extends Activity {
	EvaluateSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int evaluateId;
	/* 商品评价操作业务逻辑层对象 */
	EvaluateService evaluateService = new EvaluateService();
	/*保存查询参数条件的商品评价对象*/
	private Evaluate queryConditionEvaluate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluate_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--商品评价列表");
		} else {
			setTitle("您好：" + username + "   当前位置---商品评价列表");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionEvaluate = (Evaluate)extras.getSerializable("queryConditionEvaluate");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new EvaluateSimpleAdapter(this, list,
					R.layout.evaluate_list_item_one_product,
					new String[] { "evaluateId","productObj","memberObj","content","evaluateTime" },
					new int[] { R.id.tv_evaluateId,R.id.tv_productObj,R.id.tv_memberObj,R.id.tv_content,R.id.tv_evaluateTime,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 添加长按点击
		lv.setOnCreateContextMenuListener(evaluateListItemListener);
		/*
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int evaluateId = Integer.parseInt(list.get(arg2).get("evaluateId").toString());
            	Intent intent = new Intent();
            	intent.setClass(EvaluateListOfOneProductActivity.this, EvaluateDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("evaluateId", evaluateId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
        */
	}
	private OnCreateContextMenuListener evaluateListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "编辑商品评价信息"); 
			//menu.add(0, 1, 0, "删除商品评价信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑商品评价信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取评价编号
			evaluateId = Integer.parseInt(list.get(position).get("evaluateId").toString());
			Intent intent = new Intent();
			intent.setClass(EvaluateListOfOneProductActivity.this, EvaluateEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("evaluateId", evaluateId);
			intent.putExtras(bundle);
			startActivity(intent);
			EvaluateListOfOneProductActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除商品评价信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取评价编号
			evaluateId = Integer.parseInt(list.get(position).get("evaluateId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(EvaluateListOfOneProductActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = evaluateService.DeleteEvaluate(evaluateId);
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
			/* 查询商品评价信息 */
			List<Evaluate> evaluateList = evaluateService.QueryEvaluate(queryConditionEvaluate);
			for (int i = 0; i < evaluateList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("evaluateId", evaluateList.get(i).getEvaluateId());
				map.put("productObj", evaluateList.get(i).getProductObj());
				map.put("memberObj", evaluateList.get(i).getMemberObj());
				map.put("content", evaluateList.get(i).getContent());
				map.put("evaluateTime", evaluateList.get(i).getEvaluateTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "添加商品评价"); 
		menu.add(0, 2, 2, "返回主界面");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare)EvaluateListOfOneProductActivity.this.getApplication();
		
		if (item.getItemId() == 1) {
			// 添加商品评价信息
			Intent intent = new Intent();
			if(declare.getIdentify().equals("admin"))
				intent.setClass(EvaluateListOfOneProductActivity.this, EvaluateAddActivity.class);
			else
			{
				Bundle bundle = new Bundle();
				bundle.putString("productObj", queryConditionEvaluate.getProductObj());
				intent.putExtras(bundle);
				intent.setClass(EvaluateListOfOneProductActivity.this, EvaluateUserAddActivity.class);
				
			}
			
			startActivity(intent);
			EvaluateListOfOneProductActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*返回主界面*/
			Intent intent = new Intent();
			if(declare.getIdentify().equals("admin"))
				intent.setClass(EvaluateListOfOneProductActivity.this, MainMenuActivity.class);
			else
				intent.setClass(EvaluateListOfOneProductActivity.this, MainMenuUserActivity.class);
			startActivity(intent);
			EvaluateListOfOneProductActivity.this.finish();
		}
		return true; 
	}
}
