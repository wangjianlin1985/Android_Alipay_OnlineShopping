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

public class NoticeSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public NoticeSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.notice_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_noticeId = (TextView)convertView.findViewById(R.id.tv_noticeId);
				holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
				holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_noticeId.setText("记录编号：" + mData.get(position).get("noticeId").toString());
		holder.tv_title.setText("公告标题：" + mData.get(position).get("title").toString());
		holder.tv_content.setText("公告内容：" + mData.get(position).get("content").toString());
		holder.tv_publishDate.setText("发布日期：" + mData.get(position).get("publishDate").toString().substring(0, 10));
		/*返回修改好的view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_noticeId;
    	TextView tv_title;
    	TextView tv_content;
    	TextView tv_publishDate;
    }
} 
