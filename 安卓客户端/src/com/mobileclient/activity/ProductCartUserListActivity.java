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
	/* 商品购物车操作业务逻辑层对象 */
	ProductCartService productCartService = new ProductCartService();
	/*保存查询参数条件的商品购物车对象*/
	private ProductCart queryConditionProductCart;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productcart_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--商品购物车列表");
		} else {
			setTitle("您好：" + username + "   当前位置---我的购物车");
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
		// 添加长按点击
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
			menu.add(0, 0, 0, "修改购买数量"); 
			menu.add(0, 1, 0, "删除");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑商品购物车信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			cartId = Integer.parseInt(list.get(position).get("cartId").toString());
			Intent intent = new Intent();
			intent.setClass(ProductCartUserListActivity.this, ProductCartEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("cartId", cartId);
			intent.putExtras(bundle);
			startActivity(intent);
			ProductCartUserListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除商品购物车信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			cartId = Integer.parseInt(list.get(position).get("cartId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(ProductCartUserListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = productCartService.DeleteProductCart(cartId);
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
			/* 查询商品购物车信息 */
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
		menu.add(0, 1, 1, "提交订单"); 
		menu.add(0, 2, 2, "返回");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			
			if(list.size() > 0) {
				// 提交订单信息
				Intent intent = new Intent();
				intent.setClass(ProductCartUserListActivity.this, OrderInfoUserAddActivity.class);
				startActivity(intent);
				ProductCartUserListActivity.this.finish();
			}
			
		}else if (item.getItemId() == 2) {
			/*返回主界面*/ 
			ProductCartUserListActivity.this.finish();
		}
		return true; 
	}
}
