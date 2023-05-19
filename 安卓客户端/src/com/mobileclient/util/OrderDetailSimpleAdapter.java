package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.OrderInfoService;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class OrderDetailSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public OrderDetailSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.orderdetail_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_detailId = (TextView)convertView.findViewById(R.id.tv_detailId);
				holder.tv_orderObj = (TextView)convertView.findViewById(R.id.tv_orderObj);
				holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
				holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
				holder.tv_count = (TextView)convertView.findViewById(R.id.tv_count);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_detailId.setText("��¼��ţ�" + mData.get(position).get("detailId").toString());
		holder.tv_orderObj.setText("������ţ�" + (new OrderInfoService()).GetOrderInfo(mData.get(position).get("orderObj").toString()).getOrderNo());
		holder.tv_productObj.setText("��Ʒ���ƣ�" + (new ProductInfoService()).GetProductInfo(mData.get(position).get("productObj").toString()).getProductName());
		holder.tv_price.setText("��Ʒ���ۣ�" + mData.get(position).get("price").toString());
		holder.tv_count.setText("����������" + mData.get(position).get("count").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_detailId;
    	TextView tv_orderObj;
    	TextView tv_productObj;
    	TextView tv_price;
    	TextView tv_count;
    }
} 
