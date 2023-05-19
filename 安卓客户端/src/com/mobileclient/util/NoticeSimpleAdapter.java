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
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
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
    /*��һ��װ�����viewʱ=null,���½�һ������inflate����һ��view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.notice_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_noticeId = (TextView)convertView.findViewById(R.id.tv_noticeId);
				holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
				holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_noticeId.setText("��¼��ţ�" + mData.get(position).get("noticeId").toString());
		holder.tv_title.setText("������⣺" + mData.get(position).get("title").toString());
		holder.tv_content.setText("�������ݣ�" + mData.get(position).get("content").toString());
		holder.tv_publishDate.setText("�������ڣ�" + mData.get(position).get("publishDate").toString().substring(0, 10));
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_noticeId;
    	TextView tv_title;
    	TextView tv_content;
    	TextView tv_publishDate;
    }
} 
