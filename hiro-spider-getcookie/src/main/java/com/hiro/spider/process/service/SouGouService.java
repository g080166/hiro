package com.hiro.spider.process.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hiro.spider.core.httpclient.Result;
import com.hiro.spider.core.httpclient.SendRequest;
import com.hiro.spider.data.dao.impl.PublicNumberDao;
import com.hiro.spider.data.entity.PublicNumber;
import com.hiro.spider.process.cache.CookieCache;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SouGouService implements ISouGouService{

	
	@Resource
	PublicNumberDao publicNumberDao;
	
	@Resource
	CookieCache cookieCache;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<PublicNumber> getOfficalAccount(){
//		String hql = "from PublicNumber where isnull('biz')";
//		List<PublicNumber> publicNumbers = publicNumberDao.query(hql);
		List<PublicNumber> publicNumbers = publicNumberDao.findAll();
		List<PublicNumber> _publicNumbers = new ArrayList<PublicNumber>();
		for(PublicNumber publicNumer:publicNumbers){
			if(StringUtils.isEmpty(publicNumer.getBiz())){
				_publicNumbers.add(publicNumer);
			}
		}
		
		logger.info("开始获取公众号："+_publicNumbers.size());
		return publicNumbers;
	}
	
	@Override
	public void saveOfficialAccount(PublicNumber publicNumber) throws UnsupportedEncodingException{
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("Host", "weixin.sogou.com");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
		
		String refererUrl = "http://weixin.sogou.com/weixin?type=1&query=%E4%B8%AD%E5%8C%BB%E5%85%BB%E7%94%9F&ie=utf8";
		
		Random r = new Random();
		int index = r.nextInt(10);
		String query = publicNumber.getWeixinName();
 		String url2 = "http://weixin.sogou.com/weixin?type=1&query="+URLEncoder.encode(query, "utf-8")+"&ie=utf-8";
 		String cookie = cookieCache.get(index+"");
// 		String cookie = "IPLOC=CN4403; SUID=3F419B0E1220920A0000000055FC038A; SUV=00027B620E9B413F55FC038CA2BCA485; _ga=GA1.2.1065184354.1442579354; ssuid=2265913808; ABTEST=0|1444826365|v1; weixinIndexVisited=1; SNUID=BB92C9BFC6C2E4EBB99CBE93C75AC7EB; sct=11; wapsogou_qq_nickname=";
 		String weixinAccount = publicNumber.getWeixinAccount();
 		
 		logger.info("搜索："+query+";cookie:"+cookie);
 		
 		headers.put("Cookie", cookie);
 		headers.put("Referer", refererUrl);
 		refererUrl = url2;
 		
 		Map<String,String> params = new HashMap<String, String>();
 		try{
	 		Result result2= SendRequest.sendGet(url2, headers, params, "utf-8", false);
	 		String response = EntityUtils.toString(result2.getHttpEntity(),"utf-8");
	 		System.out.println(response);
	 		Document doc = Jsoup.parse(response);
	 		Elements elts = doc.getElementsByClass("txt-box");
	 		for(int i =0;i<elts.size();i++){
	 			String name = "";
	 			String account = "";
	 			String url ="";
	 			String weixinCode = "";
	 			
	 			Element elt = elts.get(i);
	 			
	 			Elements h4Elts = elt.getElementsByTag("h4");
	 			if(!h4Elts.isEmpty()){
	 				account = h4Elts.get(0).text();
	 				account = account.substring(account.indexOf("：")+1, account.length()).trim();
	 			}
	 			
	 			if(!account.equals(publicNumber.getWeixinAccount()))
	 				continue;
	 			
	 			
	 			Elements h3Elts = elt.getElementsByTag("h3");
	 			if(!h3Elts.isEmpty()){
	 				name = h3Elts.get(0).text();
	 			}
	 			
	 			
	 			Elements aElts = elt.getElementsByTag("a");
	 			if(!aElts.isEmpty()){
	 				url = aElts.get(0).attr("href");
	 			}
	 			url = "http://weixin.sogou.com"+url;
	 			System.out.println(url);
	 			Result result = SendRequest.sendPost(url, headers, params, "utf-8", false);
	 			String location = result.getHeaders().get("Location").getValue();
	 			String biz = getBiz(location);
	 			if(!StringUtils.isEmpty(biz)){
	 				logger.info("biz："+biz);
	 				publicNumber.setBiz(biz);
	 				publicNumberDao.saveOrUpdate(publicNumber);
	 			}
	 			
	 			String xLogExt = result.getHeaders().get("x_log_ext").getValue();
	 			String openId =getOpenId(xLogExt);
	 			if(!StringUtils.isEmpty(openId)){
	 				logger.info("微信号："+openId);
	 				publicNumber.setOpenid(openId);
	 				publicNumberDao.saveOrUpdate(publicNumber);
	 			}
	 			
	 			logger.info("name:"+name+";account:"+account+";url:"+url);
	 			if(StringUtils.isEmpty(name)||StringUtils.isEmpty(account)||StringUtils.isEmpty(url)){
	 				logger.info("获取信息不全");
	 			}
	 		}
	 		
	 		Thread.sleep(20000);
 		}catch(Exception e){
 			e.printStackTrace();
 		}
	}
	
	private String getOpenId(String xLogExt){
		if(StringUtils.isEmpty(xLogExt))
			return null;
		Pattern p = Pattern.compile("openid=(.*?)&");
		Matcher m = p.matcher(xLogExt);
		return m.find()?m.group(1):null;
	}
	
	private String getBiz(String location){
		if(StringUtils.isEmpty(location))
			return null;
		Pattern p = Pattern.compile("__biz=(.*?)&");
		Matcher m = p.matcher(location);
		return m.find()?m.group(1):null;
	}
	
	private String getRedirectUrl(String url) throws ClientProtocolException, IOException{
		 HttpClient client = new DefaultHttpClient();
		 HttpPost post = new HttpPost(url);
		 HttpResponse response = client.execute(post);
		  Header locationHeader = response.getFirstHeader("Location");
		  return locationHeader.getValue();
	}
}
