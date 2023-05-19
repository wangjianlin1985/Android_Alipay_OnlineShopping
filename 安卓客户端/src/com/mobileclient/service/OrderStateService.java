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

import com.mobileclient.domain.OrderState;
import com.mobileclient.handler.OrderStateListHandler;
import com.mobileclient.util.HttpUtil;

/*����״̬��Ϣ����ҵ���߼���*/
public class OrderStateService {
	/* ��Ӷ���״̬��Ϣ */
	public String AddOrderState(OrderState orderState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", orderState.getStateId() + "");
		params.put("stateName", orderState.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ����״̬��Ϣ */
	public List<OrderState> QueryOrderState(OrderState queryConditionOrderState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderStateServlet?action=query";
		if(queryConditionOrderState != null) {
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderStateListHandler orderStateListHander = new OrderStateListHandler();
		xr.setContentHandler(orderStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderState> orderStateList = orderStateListHander.getOrderStateList();
		return orderStateList;
	}
	/* ���¶���״̬��Ϣ */
	public String UpdateOrderState(OrderState orderState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", orderState.getStateId() + "");
		params.put("stateName", orderState.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ������״̬��Ϣ */
	public String DeleteOrderState(int stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "����״̬��Ϣ��Ϣɾ��ʧ��!";
		}
	}
	/* ����״̬��Ż�ȡ����״̬��Ϣ���� */
	public OrderState GetOrderState(int stateId)  {
		List<OrderState> orderStateList = new ArrayList<OrderState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderState orderState = new OrderState();
				orderState.setStateId(object.getInt("stateId"));
				orderState.setStateName(object.getString("stateName"));
				orderStateList.add(orderState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderStateList.size();
		if(size>0) return orderStateList.get(0); 
		else return null; 
	}
}
