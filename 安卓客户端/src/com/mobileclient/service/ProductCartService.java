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

import com.mobileclient.domain.ProductCart;
import com.mobileclient.handler.ProductCartListHandler;
import com.mobileclient.util.HttpUtil;

/*��Ʒ���ﳵ����ҵ���߼���*/
public class ProductCartService {
	/* �����Ʒ���ﳵ */
	public String AddProductCart(ProductCart productCart) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", productCart.getCartId() + "");
		params.put("memberObj", productCart.getMemberObj());
		params.put("productObj", productCart.getProductObj());
		params.put("price", productCart.getPrice() + "");
		params.put("count", productCart.getCount() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ��Ʒ���ﳵ */
	public List<ProductCart> QueryProductCart(ProductCart queryConditionProductCart) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductCartServlet?action=query";
		if(queryConditionProductCart != null) {
			urlString += "&memberObj=" + URLEncoder.encode(queryConditionProductCart.getMemberObj(), "UTF-8") + "";
			urlString += "&productObj=" + URLEncoder.encode(queryConditionProductCart.getProductObj(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductCartListHandler productCartListHander = new ProductCartListHandler();
		xr.setContentHandler(productCartListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ProductCart> productCartList = productCartListHander.getProductCartList();
		return productCartList;
	}
	/* ������Ʒ���ﳵ */
	public String UpdateProductCart(ProductCart productCart) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", productCart.getCartId() + "");
		params.put("memberObj", productCart.getMemberObj());
		params.put("productObj", productCart.getProductObj());
		params.put("price", productCart.getPrice() + "");
		params.put("count", productCart.getCount() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ����Ʒ���ﳵ */
	public String DeleteProductCart(int cartId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", cartId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ���ﳵ��Ϣɾ��ʧ��!";
		}
	}
	/* ���ݼ�¼��Ż�ȡ��Ʒ���ﳵ���� */
	public ProductCart GetProductCart(int cartId)  {
		List<ProductCart> productCartList = new ArrayList<ProductCart>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", cartId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductCart productCart = new ProductCart();
				productCart.setCartId(object.getInt("cartId"));
				productCart.setMemberObj(object.getString("memberObj"));
				productCart.setProductObj(object.getString("productObj"));
				productCart.setPrice((float) object.getDouble("price"));
				productCart.setCount(object.getInt("count"));
				productCartList.add(productCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productCartList.size();
		if(size>0) return productCartList.get(0); 
		else return null; 
	}
}
