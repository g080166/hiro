package com.hiro.spider.process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hiro.spider.core.crawler.CrawlerFactory;
import com.hiro.spider.core.httpclient.Result;
import com.hiro.spider.core.httpclient.SendRequest;
import com.hiro.spider.process.cache.CookieCache;
import com.hiro.spider.process.cache.TestCache;

@Component
public class SouGouCookieJob implements CrawlerFactory{
	static final String ENCODE = "utf-8";
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	TestCache testCache;
	
	@Resource
	CookieCache cookieCache;

//	@Scheduled(fixedDelay=60*60*4*1000)
	public void start() throws UnsupportedEncodingException, ParseException, IOException, KeyManagementException,
			NoSuchAlgorithmException, InterruptedException {
		for(int i =0;i<10;i++){
			String cookie = getCookie();
			saveCookie(cookie,i);
			Thread.sleep(1000*60*2);
		}
	}
	
//	private String getCookies() throws ClientProtocolException, NoSuchAlgorithmException, IOException, KeyManagementException{
//		
//			String url = "http://weixin.sogou.com/";
//			Map<String,String> headers = new HashMap<String, String>();
//			headers.put("Host", "weixin.sogou.com");
//			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
//		
//			Map<String,String> params = new HashMap<String, String>();
//			
//	 		Result result = SendRequest.sendGet(url, headers, params, ENCODE, false);
//	 		String cookie = result.getCookie();
//	 		String tmp = URLEncoder.encode(((new Date(System.currentTimeMillis()).getTime())*1000+Math.round(Math.random()*1000))+"","utf-8");
//	 		cookie+= ";"+tmp;
//	 		
//	 		return cookie;
//	}
	
	
	private String getCookie() throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, IOException{
		String url = "http://weixin.sogou.com/";
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("Host", "weixin.sogou.com");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
		
		Map<String,String> params = new HashMap<String, String>();
		
 		Result result = SendRequest.sendGet(url, headers, params, ENCODE, false);
 		String cookie = result.getCookie();
 		String tmp = URLEncoder.encode(((new Date(System.currentTimeMillis()).getTime())*1000+Math.round(Math.random()*1000))+"","utf-8");
// 		cookie+= ";SUV="+tmp;
 		
 		String url2 = "http://weixin.sogou.com/weixin?type=1&query=%E8%8A%B1%E5%84%BF%E5%BE%AE%E7%AE%A1%E5%AE%B6";
 		headers.put("Cookie", cookie);
 		Result result2= SendRequest.sendGet(url2, headers, params, "utf-8", false);
 		cookie+= ";"+result2.getCookie();
 		
 		String url3 = "http://pb.sogou.com/cl.gif?uigs_productid=weixin&uigs_t=1446227533743853&uigs_uuid=1446227525421875&pagetype=main&type=weixinpc&wuid=undefined&uigs_version=v1.1&uigs_refer=&uigs_st=265&uigs_cl=close%3D265";
		Map<String,String> headers3 = new HashMap<String,String>();
		Result result3 = SendRequest.sendGet(url3, headers3, params, "utf-8", false);
 		cookie+= ";"+result3.getCookie();
		logger.info("cookie:"+cookie);
 		return cookie;
	}
	
	private void saveCookie(String cookie,int index){
		//先删除所有cookie
		//然后录入cookie
		cookieCache.set(String.valueOf(index), cookie);
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
