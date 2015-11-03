package com.hiro.spider.process.wait;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hiro.spider.core.crawler.CrawlerFactory;
import com.hiro.spider.core.httpclient.Result;
import com.hiro.spider.core.httpclient.SendRequest;
import com.hiro.spider.data.dao.impl.PublicNumberDao;
import com.hiro.spider.data.entity.PublicNumber;

@Component("xiGuaWaitingCrawler")
@Transactional
public class XiGuaWaitingCrawler implements CrawlerFactory{
	final static String URL_BIZRANK = "http://www.xiguaji.com/BizRank";
	final static String XIGUA_DOMAIN = "http://www.xiguaji.com/";
	
	@Autowired
	PublicNumberDao publicNumberDao;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public void start() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		Map<String,PublicNumber> datas = getDataFromXigua();
		savePublicNumber(datas);
		
	}
	
	private Map<String,PublicNumber>  getDataFromXigua(){
		Map<String,String> headers = new HashMap<String, String>();
		Map<String,String> params = new HashMap<String, String>();
		try {
			Result result = SendRequest.sendGet(URL_BIZRANK, headers, params, "utf-8", false);
			String response = EntityUtils.toString(result.getHttpEntity());
			Document doc = Jsoup.parse(response);
			List<Element> elts = doc.getElementsByClass("rankNav");
			List<String> hrefs = new ArrayList<String>();
			if(null != elts&&elts.size()>0){
				Element elt = elts.get(0);
				List<Element> aTagElements = elt.getElementsByTag("a");
				for(Element aTag:aTagElements){
					hrefs.add(aTag.attr("href"));
				}
			}
			Map<String,PublicNumber> publicNumbers = new HashMap<String, PublicNumber>();
			for(String href:hrefs){
				String url = XIGUA_DOMAIN+href;
				System.out.println("开始获取："+url);
				Result rankResult = SendRequest.sendGet(url, headers, params, "utf-8", false);
				String rankResponse = EntityUtils.toString(rankResult.getHttpEntity());
				Document rankDocument = Jsoup.parse(rankResponse);
				Element elt = rankDocument.getElementById("articleRankingListBody");
			
				List<Element> rows = elt.getElementsByTag("tr");
			
				for(Element row:rows){
					String imgUrl = row.getElementsByTag("img").get(0).attr("src");
					String name = row.getElementsByClass("rankMpName").get(0).html();
					
					Document nameDocument = Jsoup.parse(name);
		
					String weixinName= name.substring(0,name.indexOf("<em>"));
					String account = nameDocument.getElementsByTag("em").text();
					System.out.println(imgUrl+","+weixinName.trim()+","+account);
					
					PublicNumber publicNumber = new PublicNumber(account, weixinName, imgUrl, null);
					publicNumbers.put(account, publicNumber);
				}
			}
			return publicNumbers;
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private void savePublicNumber(Map<String,PublicNumber> datas){
		if(null == datas){
			return ;
		}
		Set<String> accounts = datas.keySet();
		logger.info(accounts.size()+"");
		for(String account:accounts){
			if(StringUtils.isEmpty(account)){
				continue;
			}
			
			List<PublicNumber> publicNumbers = publicNumberDao.findByParams(new String[]{"weixinAccount"}, new Object[]{account});
			if(null != publicNumbers&&publicNumbers.size()>0){
				logger.info("数据库已经存在该账号："+account);
				continue;
			}
			
			PublicNumber publicNumber = datas.get(account);
			publicNumberDao.save(publicNumber);
		
		}
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
