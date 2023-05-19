package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.MemberInfoService;
import com.mobileclient.service.OrderStateService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_orderNo = (TextView)convertView.findViewById(R.id.tv_orderNo);
				holder.tv_memberObj = (TextView)convertView.findViewById(R.id.tv_memberObj);
				holder.tv_orderTime = (TextView)convertView.findViewById(R.id.tv_orderTime);
				holder.tv_totalMoney = (TextView)convertView.findViewById(R.id.tv_totalMoney);
				holder.tv_orderStateObj = (TextView)convertView.findViewById(R.id.tv_orderStateObj);
				holder.tv_realName = (TextView)convertView.findViewById(R.id.tv_realName);
				holder.tv_telphone = (TextView)convertView.findViewById(R.id.tv_telphone);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_orderNo.setText("������ţ�" + mData.get(position).get("orderNo").toString());
		holder.tv_memberObj.setText("�µ���Ա��" + (new MemberInfoService()).GetMemberInfo(mData.get(position).get("memberObj").toString()).getMemberUserName());
		holder.tv_orderTime.setText("�µ�ʱ�䣺" + mData.get(position).get("orderTime").toString());
		holder.tv_totalMoney.setText("�����ܽ�" + mData.get(position).get("totalMoney").toString());
		holder.tv_orderStateObj.setText("����״̬��" + (new OrderStateService()).GetOrderState(Integer.parseInt(mData.get(position).get("orderStateObj").toString())).getStateName());
		holder.tv_realName.setText("�ջ���������" + mData.get(position).get("realName").toString());
		holder.tv_telphone.setText("�ջ��˵绰��" + mData.get(position).get("telphone").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_orderNo;
    	TextView tv_memberObj;
    	TextView tv_orderTime;
    	TextView tv_totalMoney;
    	TextView tv_orderStateObj;
    	TextView tv_realName;
    	TextView tv_telphone;
    }
} 
