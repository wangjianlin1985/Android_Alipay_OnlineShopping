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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
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
    /*第一次装载这个view时=null,就新建一个调用inflate宣誓一个view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.productinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_productNo = (TextView)convertView.findViewById(R.id.tv_productNo);
				holder.tv_productClassObj = (TextView)convertView.findViewById(R.id.tv_productClassObj);
				holder.tv_productName = (TextView)convertView.findViewById(R.id.tv_productName);
				holder.iv_productPhoto = (ImageView)convertView.findViewById(R.id.iv_productPhoto);
				holder.tv_productPrice = (TextView)convertView.findViewById(R.id.tv_productPrice);
				holder.tv_productCount = (TextView)convertView.findViewById(R.id.tv_productCount);
				holder.tv_onlineDate = (TextView)convertView.findViewById(R.id.tv_onlineDate);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_productNo.setText("商品编号：" + mData.get(position).get("productNo").toString());
		holder.tv_productClassObj.setText("商品类别：" + (new ProductClassService()).GetProductClass(Integer.parseInt(mData.get(position).get("productClassObj").toString())).getClassName());
		holder.tv_productName.setText("商品名称：" + mData.get(position).get("productName").toString());
		holder.iv_productPhoto.setImageBitmap((Bitmap)mData.get(position).get("productPhoto"));
		holder.tv_productPrice.setText("商品单价：" + mData.get(position).get("productPrice").toString());
		holder.tv_productCount.setText("商品库存：" + mData.get(position).get("productCount").toString());
		holder.tv_onlineDate.setText("上架日期：" + mData.get(position).get("onlineDate").toString().substring(0, 10));
		/*返回修改好的view*/
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
