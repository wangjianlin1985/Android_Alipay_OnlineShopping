package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ProductClassService;
import com.mobileclient.service.YesOrNoService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ProductInfoSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public ProductInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.productinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_productNo = (TextView)convertView.findViewById(R.id.tv_productNo);
				holder.tv_productClassObj = (TextView)convertView.findViewById(R.id.tv_productClassObj);
				holder.tv_productName = (TextView)convertView.findViewById(R.id.tv_productName);
				holder.iv_productPhoto = (ImageView)convertView.findViewById(R.id.iv_productPhoto);
				holder.tv_productPrice = (TextView)convertView.findViewById(R.id.tv_productPrice);
				holder.tv_productCount = (TextView)convertView.findViewById(R.id.tv_productCount);
				holder.tv_onlineDate = (TextView)convertView.findViewById(R.id.tv_onlineDate);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_productNo.setText("��Ʒ��ţ�" + mData.get(position).get("productNo").toString());
		holder.tv_productClassObj.setText("��Ʒ���" + (new ProductClassService()).GetProductClass(Integer.parseInt(mData.get(position).get("productClassObj").toString())).getClassName());
		holder.tv_productName.setText("��Ʒ���ƣ�" + mData.get(position).get("productName").toString());
		holder.iv_productPhoto.setImageBitmap((Bitmap)mData.get(position).get("productPhoto"));
		holder.tv_productPrice.setText("��Ʒ���ۣ�" + mData.get(position).get("productPrice").toString());
		holder.tv_productCount.setText("��Ʒ��棺" + mData.get(position).get("productCount").toString());
		holder.tv_onlineDate.setText("�ϼ����ڣ�" + mData.get(position).get("onlineDate").toString().substring(0, 10));
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_productNo;
    	TextView tv_productClassObj;
    	TextView tv_productName;
    	ImageView iv_productPhoto;
    	TextView tv_productPrice;
    	TextView tv_productCount;
    	TextView tv_onlineDate;
    }
} 
