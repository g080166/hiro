package com.hiro.spider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.hiro.spider.core.crawler.CrawlerFactory;

public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	
	
	public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" }, true, null);
		
		//去西瓜获取公众号
//		CrawlerFactory crawler = (CrawlerFactory)context.getBean("xiGuaWaitingCrawler");
//		crawler.start();
		
		//去搜狗获取bizNo
//		CrawlerFactory souGouCrawler = (CrawlerFactory)context.getBean("souGouWaitingCrawler");
//		souGouCrawler.start();
	}
}
