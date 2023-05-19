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

import com.mobileclient.domain.Evaluate;
import com.mobileclient.handler.EvaluateListHandler;
import com.mobileclient.util.HttpUtil;

/*��Ʒ���۹���ҵ���߼���*/
public class EvaluateService {
	/* �����Ʒ���� */
	public String AddEvaluate(Evaluate evaluate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluate.getEvaluateId() + "");
		params.put("productObj", evaluate.getProductObj());
		params.put("memberObj", evaluate.getMemberObj());
		params.put("content", evaluate.getContent());
		params.put("evaluateTime", evaluate.getEvaluateTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ��Ʒ���� */
	public List<Evaluate> QueryEvaluate(Evaluate queryConditionEvaluate) throws Exception {
		String urlString = HttpUtil.BASE_URL + "EvaluateServlet?action=query";
		if(queryConditionEvaluate != null) {
			urlString += "&productObj=" + URLEncoder.encode(queryConditionEvaluate.getProductObj(), "UTF-8") + "";
			urlString += "&memberObj=" + URLEncoder.encode(queryConditionEvaluate.getMemberObj(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		EvaluateListHandler evaluateListHander = new EvaluateListHandler();
		xr.setContentHandler(evaluateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Evaluate> evaluateList = evaluateListHander.getEvaluateList();
		return evaluateList;
	}
	/* ������Ʒ���� */
	public String UpdateEvaluate(Evaluate evaluate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluate.getEvaluateId() + "");
		params.put("productObj", evaluate.getProductObj());
		params.put("memberObj", evaluate.getMemberObj());
		params.put("content", evaluate.getContent());
		params.put("evaluateTime", evaluate.getEvaluateTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ����Ʒ���� */
	public String DeleteEvaluate(int evaluateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ������Ϣɾ��ʧ��!";
		}
	}
	/* �������۱�Ż�ȡ��Ʒ���۶��� */
	public Evaluate GetEvaluate(int evaluateId)  {
		List<Evaluate> evaluateList = new ArrayList<Evaluate>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Evaluate evaluate = new Evaluate();
				evaluate.setEvaluateId(object.getInt("evaluateId"));
				evaluate.setProductObj(object.getString("productObj"));
				evaluate.setMemberObj(object.getString("memberObj"));
				evaluate.setContent(object.getString("content"));
				evaluate.setEvaluateTime(object.getString("evaluateTime"));
				evaluateList.add(evaluate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = evaluateList.size();
		if(size>0) return evaluateList.get(0); 
		else return null; 
	}
}
