package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import com.mobileclient.util.OrderStateSimpleAdapter;
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

public class OrderStateListActivity extends Activity {
	OrderStateSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int stateId;
	/* ����״̬��Ϣ����ҵ���߼������ */
	OrderStateService orderStateService = new OrderStateService();
	/*�����ѯ���������Ķ���״̬��Ϣ����*/
	private OrderState queryConditionOrderState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderstate_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--����״̬��Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---����״̬��Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionOrderState = (OrderState)extras.getSerializable("queryConditionOrderState");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new OrderStateSimpleAdapter(this, list,
					R.layout.orderstate_list_item,
					new String[] { "stateId","stateName" },
					new int[] { R.id.tv_stateId,R.id.tv_stateName,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(orderStateListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int stateId = Integer.parseInt(list.get(arg2).get("stateId").toString());
            	Intent intent = new Intent();
            	intent.setClass(OrderStateListActivity.this, OrderStateDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("stateId", stateId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener orderStateListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭����״̬��Ϣ��Ϣ"); 
			menu.add(0, 1, 0, "ɾ������״̬��Ϣ��Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭����״̬��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ״̬���
			stateId = Integer.parseInt(list.get(position).get("stateId").toString());
			Intent intent = new Intent();
			intent.setClass(OrderStateListActivity.this, OrderStateEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("stateId", stateId);
			intent.putExtras(bundle);
			startActivity(intent);
			OrderStateListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ������״̬��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ״̬���
			stateId = Integer.parseInt(list.get(position).get("stateId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(OrderStateListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = orderStateService.DeleteOrderState(stateId);
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
			/* ��ѯ����״̬��Ϣ��Ϣ */
			List<OrderState> orderStateList = orderStateService.QueryOrderState(queryConditionOrderState);
			for (int i = 0; i < orderStateList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stateId", orderStateList.get(i).getStateId());
				map.put("stateName", orderStateList.get(i).getStateName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "��Ӷ���״̬��Ϣ");
		menu.add(0, 2, 2, "��ѯ����״̬��Ϣ");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// ��Ӷ���״̬��Ϣ��Ϣ
			Intent intent = new Intent();
			intent.setClass(OrderStateListActivity.this, OrderStateAddActivity.class);
			startActivity(intent);
			OrderStateListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ����״̬��Ϣ��Ϣ*/
			Intent intent = new Intent();
			intent.setClass(OrderStateListActivity.this, OrderStateQueryActivity.class);
			startActivity(intent);
			OrderStateListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(OrderStateListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			OrderStateListActivity.this.finish();
		}
		return true; 
	}
}
