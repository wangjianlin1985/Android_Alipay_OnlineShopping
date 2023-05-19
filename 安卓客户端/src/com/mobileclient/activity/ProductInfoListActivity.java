package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.util.ProductInfoSimpleAdapter;
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

public class ProductInfoListActivity extends Activity {
	ProductInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String productNo;
	/* ��Ʒ��Ϣ����ҵ���߼������ */
	ProductInfoService productInfoService = new ProductInfoService();
	/*�����ѯ������������Ʒ��Ϣ����*/
	private ProductInfo queryConditionProductInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--��Ʒ��Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---��Ʒ��Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionProductInfo = (ProductInfo)extras.getSerializable("queryConditionProductInfo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new ProductInfoSimpleAdapter(this, list,
					R.layout.productinfo_list_item,
					new String[] { "productNo","productClassObj","productName","productPhoto","productPrice","productCount","onlineDate" },
					new int[] { R.id.tv_productNo,R.id.tv_productClassObj,R.id.tv_productName,R.id.iv_productPhoto,R.id.tv_productPrice,R.id.tv_productCount,R.id.tv_onlineDate,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(productInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String productNo = list.get(arg2).get("productNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(ProductInfoListActivity.this, ProductInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("productNo", productNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener productInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) ProductInfoListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭��Ʒ��Ϣ��Ϣ"); 
				menu.add(0, 1, 0, "ɾ����Ʒ��Ϣ��Ϣ");
			}
			
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭��Ʒ��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��Ʒ���
			productNo = list.get(position).get("productNo").toString();
			Intent intent = new Intent();
			intent.setClass(ProductInfoListActivity.this, ProductInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("productNo", productNo);
			intent.putExtras(bundle);
			startActivity(intent);
			ProductInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ����Ʒ��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��Ʒ���
			productNo = list.get(position).get("productNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(ProductInfoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = productInfoService.DeleteProductInfo(productNo);
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
			/* ��ѯ��Ʒ��Ϣ��Ϣ */
			List<ProductInfo> productInfoList = productInfoService.QueryProductInfo(queryConditionProductInfo);
			for (int i = 0; i < productInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productNo", productInfoList.get(i).getProductNo());
				map.put("productClassObj", productInfoList.get(i).getProductClassObj());
				map.put("productName", productInfoList.get(i).getProductName());
				byte[] productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ productInfoList.get(i).getProductPhoto());// ��ȡͼƬ����
				BitmapFactory.Options productPhoto_opts = new BitmapFactory.Options();  
				productPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(productPhoto_data, 0, productPhoto_data.length, productPhoto_opts); 
				productPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(productPhoto_opts, -1, 100*100); 
				productPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0, productPhoto_data.length, productPhoto_opts);
					map.put("productPhoto", productPhoto);
				} catch (OutOfMemoryError err) { }
				map.put("productPrice", productInfoList.get(i).getProductPrice());
				map.put("productCount", productInfoList.get(i).getProductCount());
				map.put("onlineDate", productInfoList.get(i).getOnlineDate());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare) ProductInfoListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			menu.add(0, 1, 1, "�����Ʒ��Ϣ");
			menu.add(0, 2, 2, "��ѯ��Ʒ��Ϣ");
			menu.add(0, 3, 3, "����������");
		} else { 
			menu.add(0, 1, 1, "��ѯ��Ʒ��Ϣ");
			menu.add(0, 2, 2, "����������");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare) ProductInfoListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// �����Ʒ��Ϣ��Ϣ
				Intent intent = new Intent();
				intent.setClass(ProductInfoListActivity.this, ProductInfoAddActivity.class);
				startActivity(intent);
				ProductInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*��ѯ��Ʒ��Ϣ��Ϣ*/
				Intent intent = new Intent();
				intent.setClass(ProductInfoListActivity.this, ProductInfoQueryActivity.class);
				startActivity(intent);
				ProductInfoListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(ProductInfoListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				ProductInfoListActivity.this.finish();
			}	
		} else {
			if (item.getItemId() == 1) {
				/*��ѯ��Ʒ��Ϣ��Ϣ*/
				Intent intent = new Intent();
				intent.setClass(ProductInfoListActivity.this, ProductInfoQueryActivity.class);
				startActivity(intent);
				ProductInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(ProductInfoListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				ProductInfoListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
