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

public class OrderDetailUserListActivity extends Activity {
	OrderDetailSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int detailId;
	/* ������ϸ��Ϣ����ҵ���߼������ */
	OrderDetailService orderDetailService = new OrderDetailService();
	/*�����ѯ���������Ķ�����ϸ��Ϣ����*/
	private OrderDetail queryConditionOrderDetail;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--������ϸ��Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---������ϸ��Ϣ�б�");
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
		 
		 
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int detailId = Integer.parseInt(list.get(arg2).get("detailId").toString());
            	Intent intent = new Intent();
            	intent.setClass(OrderDetailUserListActivity.this, OrderDetailDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("detailId", detailId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
 

	 
	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯ������ϸ��Ϣ��Ϣ */
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
 
	 
}
