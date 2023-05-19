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

import com.mobileclient.domain.Notice;
import com.mobileclient.handler.NoticeListHandler;
import com.mobileclient.util.HttpUtil;

/*ϵͳ�������ҵ���߼���*/
public class NoticeService {
	/* ���ϵͳ���� */
	public String AddNotice(Notice notice) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", notice.getNoticeId() + "");
		params.put("title", notice.getTitle());
		params.put("content", notice.getContent());
		params.put("publishDate", notice.getPublishDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯϵͳ���� */
	public List<Notice> QueryNotice(Notice queryConditionNotice) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NoticeServlet?action=query";
		if(queryConditionNotice != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionNotice.getTitle(), "UTF-8") + "";
			if(queryConditionNotice.getPublishDate() != null) {
				urlString += "&publishDate=" + URLEncoder.encode(queryConditionNotice.getPublishDate().toString(), "UTF-8");
			}
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NoticeListHandler noticeListHander = new NoticeListHandler();
		xr.setContentHandler(noticeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Notice> noticeList = noticeListHander.getNoticeList();
		return noticeList;
	}
	/* ����ϵͳ���� */
	public String UpdateNotice(Notice notice) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", notice.getNoticeId() + "");
		params.put("title", notice.getTitle());
		params.put("content", notice.getContent());
		params.put("publishDate", notice.getPublishDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ��ϵͳ���� */
	public String DeleteNotice(int noticeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", noticeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ϵͳ������Ϣɾ��ʧ��!";
		}
	}
	/* ���ݼ�¼��Ż�ȡϵͳ������� */
	public Notice GetNotice(int noticeId)  {
		List<Notice> noticeList = new ArrayList<Notice>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noticeId", noticeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoticeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Notice notice = new Notice();
				notice.setNoticeId(object.getInt("noticeId"));
				notice.setTitle(object.getString("title"));
				notice.setContent(object.getString("content"));
				notice.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = noticeList.size();
		if(size>0) return noticeList.get(0); 
		else return null; 
	}
}
