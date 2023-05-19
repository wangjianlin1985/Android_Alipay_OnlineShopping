package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderDetail;
import com.mobileclient.service.OrderDetailService;
import com.mobileclient.util.OrderDetailSimpleAdapter;
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

public class OrderDetailListActivity extends Activity {
	OrderDetailSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int detailId;
	/* 订单明细信息操作业务逻辑层对象 */
	OrderDetailService orderDetailService = new OrderDetailService();
	/*保存查询参数条件的订单明细信息对象*/
	private OrderDetail queryConditionOrderDetail;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--订单明细信息列表");
		} else {
			setTitle("您好：" + username + "   当前位置---订单明细信息列表");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionOrderDetail = (OrderDetail)extras.getSerializable("queryConditionOrderDetail");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new OrderDetailSimpleAdapter(this, list,
					R.layout.orderdetail_list_item,
					new String[] { "detailId","orderObj","productObj","price","count" },
					new int[] { R.id.tv_detailId,R.id.tv_orderObj,R.id.tv_productObj,R.id.tv_price,R.id.tv_count,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 添加长按点击
		lv.setOnCreateContextMenuListener(orderDetailListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int detailId = Integer.parseInt(list.get(arg2).get("detailId").toString());
            	Intent intent = new Intent();
            	intent.setClass(OrderDetailListActivity.this, OrderDetailDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("detailId", detailId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener orderDetailListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑订单明细信息信息"); 
			menu.add(0, 1, 0, "删除订单明细信息信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑订单明细信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			detailId = Integer.parseInt(list.get(position).get("detailId").toString());
			Intent intent = new Intent();
			intent.setClass(OrderDetailListActivity.this, OrderDetailEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("detailId", detailId);
			intent.putExtras(bundle);
			startActivity(intent);
			OrderDetailListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除订单明细信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			detailId = Integer.parseInt(list.get(position).get("detailId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(OrderDetailListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = orderDetailService.DeleteOrderDetail(detailId);
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
			/* 查询订单明细信息信息 */
			List<OrderDetail> orderDetailList = orderDetailService.QueryOrderDetail(queryConditionOrderDetail);
			for (int i = 0; i < orderDetailList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("detailId", orderDetailList.get(i).getDetailId());
				map.put("orderObj", orderDetailList.get(i).getOrderObj());
				map.put("productObj", orderDetailList.get(i).getProductObj());
				map.put("price", orderDetailList.get(i).getPrice());
				map.put("count", orderDetailList.get(i).getCount());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "添加订单明细信息");
		menu.add(0, 2, 2, "查询订单明细信息");
		menu.add(0, 3, 3, "返回主界面");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// 添加订单明细信息信息
			Intent intent = new Intent();
			intent.setClass(OrderDetailListActivity.this, OrderDetailAddActivity.class);
			startActivity(intent);
			OrderDetailListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*查询订单明细信息信息*/
			Intent intent = new Intent();
			intent.setClass(OrderDetailListActivity.this, OrderDetailQueryActivity.class);
			startActivity(intent);
			OrderDetailListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*返回主界面*/
			Intent intent = new Intent();
			intent.setClass(OrderDetailListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			OrderDetailListActivity.this.finish();
		}
		return true; 
	}
}
