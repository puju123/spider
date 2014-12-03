package com.pujun.spider.crawl;

import com.pujun.spider.fetch.HtmlFetcher;
import com.pujun.spider.parse.ParsePage;
import com.pujun.spider.storage.ResultList;
import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;
import com.pujun.spider.tool.Const;
/**
 * url抓取类，负责抓取、解析和数据库写入流程
 * @Title: Crawler.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年12月2日 下午4:06:00
 */
public class Crawler implements Runnable{
	//需要抓取的url
    private String url;
    //抓取结果队列
    public static ResultList resultList=new ResultList();
    //已经抓取数量
    private long recordCount=0;
	public Crawler(String url, long recordCount) {
		// TODO Auto-generated constructor stub
		this.url=url;
		this.recordCount=recordCount;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SpiderDoc spiderDoc=new SpiderDoc();
		spiderDoc.setUrl(url);
		//抓取
		HtmlFetcher htmlFetcher = new HtmlFetcher();
		htmlFetcher.fetch(spiderDoc);
		if (Const.FETCHED.equals(spiderDoc.getStatus())) {
			//解析
			ParsePage parsePage = new ParsePage();
			parsePage.parse(spiderDoc);
		}
			//结果写入数据库
		synchronized(resultList) {
			resultList.addResult(spiderDoc);
		}
	}
}
