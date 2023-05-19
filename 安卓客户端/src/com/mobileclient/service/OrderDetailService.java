package com.mobileclient.service;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.mobileclient.domain.OrderDetail;
import com.mobileclient.handler.OrderDetailListHandler;
import com.mobileclient.util.HttpUtil;

/*������ϸ��Ϣ����ҵ���߼���*/
public class OrderDetailService {
	/* ��Ӷ�����ϸ��Ϣ */
	public String AddOrderDetail(OrderDetail orderDetail) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", orderDetail.getDetailId() + "");
		params.put("orderObj", orderDetail.getOrderObj());
		params.put("productObj", orderDetail.getProductObj());
		params.put("price", orderDetail.getPrice() + "");
		params.put("count", orderDetail.getCount() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ������ϸ��Ϣ */
	public List<OrderDetail> QueryOrderDetail(OrderDetail queryConditionOrderDetail) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderDetailServlet?action=query";
		if(queryConditionOrderDetail != null) {
			urlString += "&orderObj=" + URLEncoder.encode(queryConditionOrderDetail.getOrderObj(), "UTF-8") + "";
			urlString += "&productObj=" + URLEncoder.encode(queryConditionOrderDetail.getProductObj(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderDetailListHandler orderDetailListHander = new OrderDetailListHandler();
		xr.setContentHandler(orderDetailListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderDetail> orderDetailList = orderDetailListHander.getOrderDetailList();
		return orderDetailList;
	}
	/* ���¶�����ϸ��Ϣ */
	public String UpdateOrderDetail(OrderDetail orderDetail) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", orderDetail.getDetailId() + "");
		params.put("orderObj", orderDetail.getOrderObj());
		params.put("productObj", orderDetail.getProductObj());
		params.put("price", orderDetail.getPrice() + "");
		params.put("count", orderDetail.getCount() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ��������ϸ��Ϣ */
	public String DeleteOrderDetail(int detailId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", detailId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "������ϸ��Ϣ��Ϣɾ��ʧ��!";
		}
	}
	/* ���ݼ�¼��Ż�ȡ������ϸ��Ϣ���� */
	public OrderDetail GetOrderDetail(int detailId)  {
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", detailId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setDetailId(object.getInt("detailId"));
				orderDetail.setOrderObj(object.getString("orderObj"));
				orderDetail.setProductObj(object.getString("productObj"));
				orderDetail.setPrice((float) object.getDouble("price"));
				orderDetail.setCount(object.getInt("count"));
				orderDetailList.add(orderDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderDetailList.size();
		if(size>0) return orderDetailList.get(0); 
		else return null; 
	}
}
