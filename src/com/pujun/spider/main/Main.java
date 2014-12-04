package com.pujun.spider.main;

import java.util.List;

import com.pujun.spider.crawl.Crawlers;
import com.pujun.spider.fetch.Feeder;
import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//        insert();
//        select();
        crawl();
	}
	private static void crawl() {
		// TODO Auto-generated method stub
		Feeder feeder=new Feeder();
		Thread thread=new Thread(feeder);
		thread.start();
        Crawlers crawlers=new Crawlers();
//        for (int i = 0; i < 100; i++) {
//			Thread thread1=new Thread();
//			thread1.setName(String.valueOf(i));
//			crawlers.pool.
//		}
        crawlers.crawl();
	}
	private static void insert() {
		// TODO Auto-generated method stub
        SpiderDoc spiderDoc=new SpiderDoc();
        spiderDoc.setUrl("https://github.com/loiane/ibatis-helloworld/blob/master/src/com/loiane/data/Contact.xml");
        spiderDoc.setAuthor("pujun");
        spiderDoc.setContent("测试content");
        spiderDoc.setFetchtime("2014 11 19 14:43:00");
        spiderDoc.setHtml("测试html");
        spiderDoc.setOriginal("test");
        spiderDoc.setPubdate("2014 11 19 14:43:00");
        spiderDoc.setTitle("测试title");
        SpiderDocDao spiderDocDao=new SpiderDocDao();
        int result=spiderDocDao.add(spiderDoc);
        System.out.println(result);
	}
	public static void select() {
		SpiderDocDao spiderDocDao=new SpiderDocDao();
		List<SpiderDoc> result=spiderDocDao.selectAll();
		if (result!=null&&result.size()>0) {
			SpiderDoc spiderDoc;
			for (int i = 0; i < result.size(); i++) {
				spiderDoc=result.get(i);
				System.out.println(spiderDoc.getId());
			}
		}
		
	}

}
