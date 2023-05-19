package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.MemberInfoService;
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

public class ProductCartSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public ProductCartSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.productcart_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_cartId = (TextView)convertView.findViewById(R.id.tv_cartId);
				holder.tv_memberObj = (TextView)convertView.findViewById(R.id.tv_memberObj);
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
		holder.tv_cartId.setText("��¼��ţ�" + mData.get(position).get("cartId").toString());
		holder.tv_memberObj.setText("�û�����" + (new MemberInfoService()).GetMemberInfo(mData.get(position).get("memberObj").toString()).getMemberUserName());
		holder.tv_productObj.setText("��Ʒ���ƣ�" + (new ProductInfoService()).GetProductInfo(mData.get(position).get("productObj").toString()).getProductName());
		holder.tv_price.setText("��Ʒ���ۣ�" + mData.get(position).get("price").toString());
		holder.tv_count.setText("��Ʒ������" + mData.get(position).get("count").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_cartId;
    	TextView tv_memberObj;
    	TextView tv_productObj;
    	TextView tv_price;
    	TextView tv_count;
    }
} 
