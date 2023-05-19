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

import com.mobileclient.domain.ProductInfo;
import com.mobileclient.handler.ProductInfoListHandler;
import com.mobileclient.util.HttpUtil;

/*��Ʒ��Ϣ����ҵ���߼���*/
public class ProductInfoService {
	/* �����Ʒ��Ϣ */
	public String AddProductInfo(ProductInfo productInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productInfo.getProductNo());
		params.put("productClassObj", productInfo.getProductClassObj() + "");
		params.put("productName", productInfo.getProductName());
		params.put("productPhoto", productInfo.getProductPhoto());
		params.put("productPrice", productInfo.getProductPrice() + "");
		params.put("productCount", productInfo.getProductCount() + "");
		params.put("recommendFlag", productInfo.getRecommendFlag());
		params.put("hotNum", productInfo.getHotNum() + "");
		params.put("onlineDate", productInfo.getOnlineDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ��Ʒ��Ϣ */
	public List<ProductInfo> QueryProductInfo(ProductInfo queryConditionProductInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductInfoServlet?action=query";
		if(queryConditionProductInfo != null) {
			urlString += "&productNo=" + URLEncoder.encode(queryConditionProductInfo.getProductNo(), "UTF-8") + "";
			urlString += "&productClassObj=" + queryConditionProductInfo.getProductClassObj();
			urlString += "&productName=" + URLEncoder.encode(queryConditionProductInfo.getProductName(), "UTF-8") + "";
			urlString += "&recommendFlag=" + URLEncoder.encode(queryConditionProductInfo.getRecommendFlag(), "UTF-8") + "";
			if(queryConditionProductInfo.getOnlineDate() != null) {
				urlString += "&onlineDate=" + URLEncoder.encode(queryConditionProductInfo.getOnlineDate().toString(), "UTF-8");
			}
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductInfoListHandler productInfoListHander = new ProductInfoListHandler();
		xr.setContentHandler(productInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ProductInfo> productInfoList = productInfoListHander.getProductInfoList();
		return productInfoList;
	}
	/* ������Ʒ��Ϣ */
	public String UpdateProductInfo(ProductInfo productInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productInfo.getProductNo());
		params.put("productClassObj", productInfo.getProductClassObj() + "");
		params.put("productName", productInfo.getProductName());
		params.put("productPhoto", productInfo.getProductPhoto());
		params.put("productPrice", productInfo.getProductPrice() + "");
		params.put("productCount", productInfo.getProductCount() + "");
		params.put("recommendFlag", productInfo.getRecommendFlag());
		params.put("hotNum", productInfo.getHotNum() + "");
		params.put("onlineDate", productInfo.getOnlineDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ����Ʒ��Ϣ */
	public String DeleteProductInfo(String productNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ��Ϣ��Ϣɾ��ʧ��!";
		}
	}
	/* ������Ʒ��Ż�ȡ��Ʒ��Ϣ���� */
	public ProductInfo GetProductInfo(String productNo)  {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductInfo productInfo = new ProductInfo();
				productInfo.setProductNo(object.getString("productNo"));
				productInfo.setProductClassObj(object.getInt("productClassObj"));
				productInfo.setProductName(object.getString("productName"));
				productInfo.setProductPhoto(object.getString("productPhoto"));
				productInfo.setProductPrice((float) object.getDouble("productPrice"));
				productInfo.setProductCount(object.getInt("productCount"));
				productInfo.setRecommendFlag(object.getString("recommendFlag"));
				productInfo.setHotNum(object.getInt("hotNum"));
				productInfo.setOnlineDate(Timestamp.valueOf(object.getString("onlineDate")));
				productInfoList.add(productInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productInfoList.size();
		if(size>0) return productInfoList.get(0); 
		else return null; 
	}
}
