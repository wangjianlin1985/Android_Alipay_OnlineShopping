package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.util.ProductClassSimpleAdapter;
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

public class ProductClassListActivity extends Activity {
	ProductClassSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int classId;
	/* ��Ʒ������ҵ���߼������ */
	ProductClassService productClassService = new ProductClassService();
	/*�����ѯ������������Ʒ������*/
	private ProductClass queryConditionProductClass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productclass_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--��Ʒ����б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---��Ʒ����б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionProductClass = (ProductClass)extras.getSerializable("queryConditionProductClass");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new ProductClassSimpleAdapter(this, list,
					R.layout.productclass_list_item,
					new String[] { "classId","className" },
					new int[] { R.id.tv_classId,R.id.tv_className,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(productClassListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int classId = Integer.parseInt(list.get(arg2).get("classId").toString());
            	Intent intent = new Intent();
            	intent.setClass(ProductClassListActivity.this, ProductClassDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("classId", classId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener productClassListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭��Ʒ�����Ϣ"); 
			menu.add(0, 1, 0, "ɾ����Ʒ�����Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭��Ʒ�����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�����
			classId = Integer.parseInt(list.get(position).get("classId").toString());
			Intent intent = new Intent();
			intent.setClass(ProductClassListActivity.this, ProductClassEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("classId", classId);
			intent.putExtras(bundle);
			startActivity(intent);
			ProductClassListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ����Ʒ�����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�����
			classId = Integer.parseInt(list.get(position).get("classId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(ProductClassListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = productClassService.DeleteProductClass(classId);
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
			/* ��ѯ��Ʒ�����Ϣ */
			List<ProductClass> productClassList = productClassService.QueryProductClass(queryConditionProductClass);
			for (int i = 0; i < productClassList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("classId", productClassList.get(i).getClassId());
				map.put("className", productClassList.get(i).getClassName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "�����Ʒ���");
		menu.add(0, 2, 2, "��ѯ��Ʒ���");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// �����Ʒ�����Ϣ
			Intent intent = new Intent();
			intent.setClass(ProductClassListActivity.this, ProductClassAddActivity.class);
			startActivity(intent);
			ProductClassListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ��Ʒ�����Ϣ*/
			Intent intent = new Intent();
			intent.setClass(ProductClassListActivity.this, ProductClassQueryActivity.class);
			startActivity(intent);
			ProductClassListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(ProductClassListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			ProductClassListActivity.this.finish();
		}
		return true; 
	}
}
