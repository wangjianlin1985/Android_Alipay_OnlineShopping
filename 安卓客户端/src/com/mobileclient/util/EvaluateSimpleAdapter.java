package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ProductInfoService;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class EvaluateSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
    /*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 
    
    private int mResource;

    private LayoutInflater mInflater;
    Context context = null;

    public EvaluateSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
    ViewHolder holder = null; 
    /*��һ��װ�����viewʱ=null,���½�һ������inflate����һ��view*/
		if (convertView == null) {
			//convertView = mInflater.inflate(R.layout.evaluate_list_item, null);
			convertView = mInflater.inflate(mResource, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_evaluateId = (TextView)convertView.findViewById(R.id.tv_evaluateId);
				holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
				holder.tv_memberObj = (TextView)convertView.findViewById(R.id.tv_memberObj);
				holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
				holder.tv_evaluateTime = (TextView)convertView.findViewById(R.id.tv_evaluateTime);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_evaluateId.setText("���۱�ţ�" + mData.get(position).get("evaluateId").toString());
		holder.tv_productObj.setText("��Ʒ���ƣ�" + (new ProductInfoService()).GetProductInfo(mData.get(position).get("productObj").toString()).getProductName());
		holder.tv_memberObj.setText("�û�����" + (new MemberInfoService()).GetMemberInfo(mData.get(position).get("memberObj").toString()).getMemberUserName());
		holder.tv_content.setText("�������ݣ�" + mData.get(position).get("content").toString());
		holder.tv_evaluateTime.setText("����ʱ�䣺" + mData.get(position).get("evaluateTime").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_evaluateId;
    	TextView tv_productObj;
    	TextView tv_memberObj;
    	TextView tv_content;
    	TextView tv_evaluateTime;
    }
} 
