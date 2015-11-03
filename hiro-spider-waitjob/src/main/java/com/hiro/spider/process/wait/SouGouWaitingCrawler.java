package com.hiro.spider.process.wait;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiro.spider.core.crawler.CrawlerFactory;
import com.hiro.spider.core.httpclient.Proxy;
import com.hiro.spider.core.httpclient.Result;
import com.hiro.spider.core.httpclient.SendRequest;
import com.hiro.spider.data.dao.impl.PublicNumberDao;
import com.hiro.spider.data.entity.PublicNumber;

@Component("souGouWaitingCrawler")
@Transactional
public class SouGouWaitingCrawler implements CrawlerFactory{
	
	
	@Autowired
	PublicNumberDao publicNumberDao;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	static String TYPE_SEARCH_PUBLICNUMBER = "1";
	static String TYPE_SEARCH_ARTICLE = "0";
	List<String> Cookie = new ArrayList<String>();
	
	public void start() throws UnsupportedEncodingException, ParseException, IOException, KeyManagementException,
			NoSuchAlgorithmException {
		// TODO Auto-generated method stub
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

//	/***
//	 * 去数据库获取公众号
//	 * @throws IOException 
//	 * @throws ParseException 
//	 * @throws NoSuchAlgorithmException 
//	 * @throws KeyManagementException 
//	 */
//	public void start() throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException {
//		List<PublicNumber> publicNumberList = publicNumberDao.findAll();
//		
//		logger.info(JSON.toJSONString(publicNumberList));
//		
//		
//		for(int i =0;i<jsonArray.length();i++){
//			
//		}
//		
//		Map<String,String> ips = new HashMap<String, String>();
//		try{
//			for(int i =0;i<10;i++){
//				Random r = new Random();
//				int idx = r.nextInt(3);
//				Proxy proxy = new Proxy(jsonArray.getJSONObject(idx).getString("ip"),jsonArray.getJSONObject(idx).getInt("port"));
////				Cookie.add(getCookie(proxy));
//				ips.put(jsonArray.getJSONObject(idx).getString("ip"), getCookie(proxy));
//			}
//			
//			for(PublicNumber publicNumber:publicNumberList){
//				String name = publicNumber.getWeixinName();
//				Random random=new Random();
//				int idx = random.nextInt(10);
////				String cookie = Cookie.get(idx);
////				logger.info("采用cookie:"+cookie);
//				Random r = new Random();
//				int idx2 = r.nextInt(3);
//				Proxy proxy = new Proxy(jsonArray.getJSONObject(idx2).getString("ip"),jsonArray.getJSONObject(idx2).getInt("port"));
//				logger.info("代理ip为："+proxy.toString());
//				try{
//					
//					search(name, TYPE_SEARCH_PUBLICNUMBER, ips.get(jsonArray.getJSONObject(idx2).getString("ip")), proxy);
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//				
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//	}
//	
//	private String getCookie(Proxy proxy) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException{
//		String url = "http://weixin.sogou.com/";
//		Map<String,String> headers = new HashMap<String, String>();
//		
//		Map<String,String> params = new HashMap<String, String>();
//		logger.info(proxy.toString());
//		Result result = SendRequest.sendGet(url, headers, params, ENCODE, false,proxy);
//		logger.info(result.getCookie());
//		return result.getCookie();
//	}
//	
//	private void search(String word,String type,String cookie,Proxy proxy) throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, IOException{
//		String url = "http://weixin.sogou.com/weixin?query="+URLEncoder.encode(word, "utf-8")+"&type=1";
//		
//		Map<String,String> headers = new HashMap<String, String>();
//		headers.put("Cookie", cookie);
//		
//		Map<String,String> params = new HashMap<String, String>();
//		
//		Result result = SendRequest.sendGet(url, headers, params, ENCODE, false,proxy);
//		String response = EntityUtils.toString(result.getHttpEntity(),ENCODE);
//		logger.info(response);
//		
//		Document doc = Jsoup.parse(response);
//		Element elt = doc.getElementById("seccodeImage");
//		if(null != elt){
//			logger.info("cookie:"+cookie+","+proxy.toString()+":存在异常");
//		}
//	}
//
//	public void stop() {
//		// TODO Auto-generated method stub
//		
//	}

	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String tmp = "uigs_c=escape((new Date().getTime())*1000+Math.round(Math.random()*1000)";
		tmp = URLEncoder.encode(((new Date(System.currentTimeMillis()).getTime())*1000+Math.round(Math.random()*1000))+"","utf-8");
		System.out.println(tmp);
	}
}
