package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class MemberInfoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public MemberInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
    ViewHolder holder = null; 
    /*第一次装载这个view时=null,就新建一个调用inflate宣誓一个view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.memberinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_memberUserName = (TextView)convertView.findViewById(R.id.tv_memberUserName);
				holder.tv_realName = (TextView)convertView.findViewById(R.id.tv_realName);
				holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
				holder.tv_birthday = (TextView)convertView.findViewById(R.id.tv_birthday);
				holder.iv_photo = (ImageView)convertView.findViewById(R.id.iv_photo);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_memberUserName.setText("会员用户名：" + mData.get(position).get("memberUserName").toString());
		holder.tv_realName.setText("真实姓名：" + mData.get(position).get("realName").toString());
		holder.tv_sex.setText("性别：" + mData.get(position).get("sex").toString());
		holder.tv_birthday.setText("出生日期：" + mData.get(position).get("birthday").toString().substring(0, 10));
		holder.iv_photo.setImageBitmap((Bitmap)mData.get(position).get("photo"));
		/*返回修改好的view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_memberUserName;
    	TextView tv_realName;
    	TextView tv_sex;
    	TextView tv_birthday;
    	ImageView iv_photo;
    }
} 
