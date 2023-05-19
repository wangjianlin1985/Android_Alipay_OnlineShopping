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

import com.mobileclient.domain.YesOrNo;
import com.mobileclient.handler.YesOrNoListHandler;
import com.mobileclient.util.HttpUtil;

/*�Ƿ���Ϣ����ҵ���߼���*/
public class YesOrNoService {
	/* ����Ƿ���Ϣ */
	public String AddYesOrNo(YesOrNo yesOrNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", yesOrNo.getId());
		params.put("name", yesOrNo.getName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ�Ƿ���Ϣ */
	public List<YesOrNo> QueryYesOrNo(YesOrNo queryConditionYesOrNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "YesOrNoServlet?action=query";
		if(queryConditionYesOrNo != null) {
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		YesOrNoListHandler yesOrNoListHander = new YesOrNoListHandler();
		xr.setContentHandler(yesOrNoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<YesOrNo> yesOrNoList = yesOrNoListHander.getYesOrNoList();
		return yesOrNoList;
	}
	/* �����Ƿ���Ϣ */
	public String UpdateYesOrNo(YesOrNo yesOrNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", yesOrNo.getId());
		params.put("name", yesOrNo.getName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ���Ƿ���Ϣ */
	public String DeleteYesOrNo(String id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�Ƿ���Ϣ��Ϣɾ��ʧ��!";
		}
	}
	/* �����Ƿ��Ż�ȡ�Ƿ���Ϣ���� */
	public YesOrNo GetYesOrNo(String id)  {
		List<YesOrNo> yesOrNoList = new ArrayList<YesOrNo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				YesOrNo yesOrNo = new YesOrNo();
				yesOrNo.setId(object.getString("id"));
				yesOrNo.setName(object.getString("name"));
				yesOrNoList.add(yesOrNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = yesOrNoList.size();
		if(size>0) return yesOrNoList.get(0); 
		else return null; 
	}
}
