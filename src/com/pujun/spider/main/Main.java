package com.pujun.spider.main;

import java.util.List;

import com.pujun.spider.crawl.Crawl;
import com.pujun.spider.fetch.Feeder;
import com.pujun.spider.fetch.HtmlFetcher;
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
       feeder.hightQueue.add(1, "http://auguslee.iteye.com/blog/1292335");
       feeder.hightQueue.add(2, "http://blog.csdn.net/mars5337/article/details/6576417");
       feeder.hightQueue.add(3, "http://os.51cto.com/art/201306/398363_6.htm");
       feeder.hightQueue.add(4, "http://www-01.ibm.com/software/data/infosphere/biginsights/quick-start/downloads.html");
       feeder.hightQueue.add(5, "http://blog.csdn.net/ghsau/article/details/7451464");
       feeder.middleQueue.add(1, "http://blog.csdn.net/ghsau/article/details/7451464");
       Thread thread=new Thread(feeder);
       thread.start();
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
