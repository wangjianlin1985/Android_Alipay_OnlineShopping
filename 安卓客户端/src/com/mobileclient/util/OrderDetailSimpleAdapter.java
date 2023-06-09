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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
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
    /*第一次装载这个view时=null,就新建一个调用inflate宣誓一个view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.orderdetail_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_detailId = (TextView)convertView.findViewById(R.id.tv_detailId);
				holder.tv_orderObj = (TextView)convertView.findViewById(R.id.tv_orderObj);
				holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
				holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
				holder.tv_count = (TextView)convertView.findViewById(R.id.tv_count);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_detailId.setText("记录编号：" + mData.get(position).get("detailId").toString());
		holder.tv_orderObj.setText("定单编号：" + (new OrderInfoService()).GetOrderInfo(mData.get(position).get("orderObj").toString()).getOrderNo());
		holder.tv_productObj.setText("商品名称：" + (new ProductInfoService()).GetProductInfo(mData.get(position).get("productObj").toString()).getProductName());
		holder.tv_price.setText("商品单价：" + mData.get(position).get("price").toString());
		holder.tv_count.setText("订购数量：" + mData.get(position).get("count").toString());
		/*返回修改好的view*/
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
