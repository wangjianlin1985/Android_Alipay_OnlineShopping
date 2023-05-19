package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.ProductCart;
import com.mobileclient.service.ProductCartService;
import com.mobileclient.util.ProductCartSimpleAdapter;
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

public class ProductCartUserListActivity extends Activity {
	ProductCartSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int cartId;
	/* ��Ʒ���ﳵ����ҵ���߼������ */
	ProductCartService productCartService = new ProductCartService();
	/*�����ѯ������������Ʒ���ﳵ����*/
	private ProductCart queryConditionProductCart;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productcart_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--��Ʒ���ﳵ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---�ҵĹ��ﳵ");
		}
		 
		queryConditionProductCart = new ProductCart();
		queryConditionProductCart.setProductObj("");
		queryConditionProductCart.setMemberObj(declare.getUserName());
		
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new ProductCartSimpleAdapter(this, list,
					R.layout.productcart_list_item,
					new String[] { "cartId","memberObj","productObj","price","count" },
					new int[] { R.id.tv_cartId,R.id.tv_memberObj,R.id.tv_productObj,R.id.tv_price,R.id.tv_count,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(productCartListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int cartId = Integer.parseInt(list.get(arg2).get("cartId").toString());
            	Intent intent = new Intent();
            	intent.setClass(ProductCartUserListActivity.this, ProductCartDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("cartId", cartId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener productCartListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�޸Ĺ�������"); 
			menu.add(0, 1, 0, "ɾ��");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭��Ʒ���ﳵ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			cartId = Integer.parseInt(list.get(position).get("cartId").toString());
			Intent intent = new Intent();
			intent.setClass(ProductCartUserListActivity.this, ProductCartEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("cartId", cartId);
			intent.putExtras(bundle);
			startActivity(intent);
			ProductCartUserListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ����Ʒ���ﳵ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			cartId = Integer.parseInt(list.get(position).get("cartId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(ProductCartUserListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = productCartService.DeleteProductCart(cartId);
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
			/* ��ѯ��Ʒ���ﳵ��Ϣ */
			List<ProductCart> productCartList = productCartService.QueryProductCart(queryConditionProductCart);
			for (int i = 0; i < productCartList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cartId", productCartList.get(i).getCartId());
				map.put("memberObj", productCartList.get(i).getMemberObj());
				map.put("productObj", productCartList.get(i).getProductObj());
				map.put("price", productCartList.get(i).getPrice());
				map.put("count", productCartList.get(i).getCount());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "�ύ����"); 
		menu.add(0, 2, 2, "����");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			
			if(list.size() > 0) {
				// �ύ������Ϣ
				Intent intent = new Intent();
				intent.setClass(ProductCartUserListActivity.this, OrderInfoUserAddActivity.class);
				startActivity(intent);
				ProductCartUserListActivity.this.finish();
			}
			
		}else if (item.getItemId() == 2) {
			/*����������*/ 
			ProductCartUserListActivity.this.finish();
		}
		return true; 
	}
}
