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

import com.mobileclient.domain.MemberInfo;
import com.mobileclient.handler.MemberInfoListHandler;
import com.mobileclient.util.HttpUtil;

/*��Ա��Ϣ����ҵ���߼���*/
public class MemberInfoService {
	/* ��ӻ�Ա��Ϣ */
	public String AddMemberInfo(MemberInfo memberInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberInfo.getMemberUserName());
		params.put("password", memberInfo.getPassword());
		params.put("realName", memberInfo.getRealName());
		params.put("sex", memberInfo.getSex());
		params.put("birthday", memberInfo.getBirthday().toString());
		params.put("telephone", memberInfo.getTelephone());
		params.put("email", memberInfo.getEmail());
		params.put("qq", memberInfo.getQq());
		params.put("address", memberInfo.getAddress());
		params.put("photo", memberInfo.getPhoto());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ��Ա��Ϣ */
	public List<MemberInfo> QueryMemberInfo(MemberInfo queryConditionMemberInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "MemberInfoServlet?action=query";
		if(queryConditionMemberInfo != null) {
			urlString += "&memberUserName=" + URLEncoder.encode(queryConditionMemberInfo.getMemberUserName(), "UTF-8") + "";
			if(queryConditionMemberInfo.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionMemberInfo.getBirthday().toString(), "UTF-8");
			}
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		MemberInfoListHandler memberInfoListHander = new MemberInfoListHandler();
		xr.setContentHandler(memberInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<MemberInfo> memberInfoList = memberInfoListHander.getMemberInfoList();
		return memberInfoList;
	}
	/* ���»�Ա��Ϣ */
	public String UpdateMemberInfo(MemberInfo memberInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberInfo.getMemberUserName());
		params.put("password", memberInfo.getPassword());
		params.put("realName", memberInfo.getRealName());
		params.put("sex", memberInfo.getSex());
		params.put("birthday", memberInfo.getBirthday().toString());
		params.put("telephone", memberInfo.getTelephone());
		params.put("email", memberInfo.getEmail());
		params.put("qq", memberInfo.getQq());
		params.put("address", memberInfo.getAddress());
		params.put("photo", memberInfo.getPhoto());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ����Ա��Ϣ */
	public String DeleteMemberInfo(String memberUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ա��Ϣ��Ϣɾ��ʧ��!";
		}
	}
	/* ���ݻ�Ա�û�����ȡ��Ա��Ϣ���� */
	public MemberInfo GetMemberInfo(String memberUserName)  {
		List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MemberInfo memberInfo = new MemberInfo();
				memberInfo.setMemberUserName(object.getString("memberUserName"));
				memberInfo.setPassword(object.getString("password"));
				memberInfo.setRealName(object.getString("realName"));
				memberInfo.setSex(object.getString("sex"));
				memberInfo.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				memberInfo.setTelephone(object.getString("telephone"));
				memberInfo.setEmail(object.getString("email"));
				memberInfo.setQq(object.getString("qq"));
				memberInfo.setAddress(object.getString("address"));
				memberInfo.setPhoto(object.getString("photo"));
				memberInfoList.add(memberInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = memberInfoList.size();
		if(size>0) return memberInfoList.get(0); 
		else return null; 
	}
}
