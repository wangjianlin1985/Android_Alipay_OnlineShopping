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
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
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
    /*��һ��װ�����viewʱ=null,���½�һ������inflate����һ��view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.memberinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_memberUserName = (TextView)convertView.findViewById(R.id.tv_memberUserName);
				holder.tv_realName = (TextView)convertView.findViewById(R.id.tv_realName);
				holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
				holder.tv_birthday = (TextView)convertView.findViewById(R.id.tv_birthday);
				holder.iv_photo = (ImageView)convertView.findViewById(R.id.iv_photo);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_memberUserName.setText("��Ա�û�����" + mData.get(position).get("memberUserName").toString());
		holder.tv_realName.setText("��ʵ������" + mData.get(position).get("realName").toString());
		holder.tv_sex.setText("�Ա�" + mData.get(position).get("sex").toString());
		holder.tv_birthday.setText("�������ڣ�" + mData.get(position).get("birthday").toString().substring(0, 10));
		holder.iv_photo.setImageBitmap((Bitmap)mData.get(position).get("photo"));
		/*�����޸ĺõ�view*/
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
