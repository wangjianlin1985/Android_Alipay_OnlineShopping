package com.mobileclient.activity;

import com.mobileclient.app.Declare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuUserActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// 上下文
        private Context mContext;
        
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"商品信息查询","我的购物车","我的订单","修改个人信息","系统公告查询"};

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				 
				case 0:
					// 商品信息管理监听器
					view.setOnClickListener(productInfoLinstener);
					break;
				case 1:
					// 商品购物车管理监听器
					view.setOnClickListener(productCartLinstener);
					break;
				case 2:
					// 订单信息管理监听器
					view.setOnClickListener(orderInfoLinstener);
					break;
				case 3:
					// 会员信息管理监听器
					view.setOnClickListener(memberInfoLinstener);
					break; 
				case 4:
					// 系统公告管理监听器
					view.setOnClickListener(noticeLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    
    OnClickListener productInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动商品信息管理Activity
			intent.setClass(MainMenuUserActivity.this, ProductInfoListActivity.class);
			startActivity(intent);
		}
	};
    
    OnClickListener memberInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Declare declare = (Declare)MainMenuUserActivity.this.getApplication();
			Intent intent = new Intent();
			// 启动修改会员信息管理Activity 
			intent.setClass(MainMenuUserActivity.this, MemberInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("memberUserName", declare.getUserName());
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
    OnClickListener orderInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动订单信息管理Activity
			intent.setClass(MainMenuUserActivity.this, OrderInfoUserListActivity.class);
			startActivity(intent);
		}
	};
   
    
    OnClickListener productCartLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动商品购物车管理Activity
			intent.setClass(MainMenuUserActivity.this, ProductCartUserListActivity.class);
			startActivity(intent);
		}
	};
    
    OnClickListener noticeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动系统公告管理Activity
			intent.setClass(MainMenuUserActivity.this, NoticeListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");  
		menu.add(0, 2, 2, "退出"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入 
			Intent intent = new Intent();
			intent.setClass(MainMenuUserActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true; 
	}
}
