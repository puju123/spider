package com.pujun.spider.storage;

import java.util.ArrayList;
import java.util.List;

import com.pujun.spider.crawl.Crawlers;
/**
 * 抓取最终结果缓存类
 * @Title: ResultList.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年12月2日 下午4:12:05
 */
public class ResultList {
    public static List<SpiderDoc> resultDocs=new ArrayList<SpiderDoc>();
    public synchronized static void addResult(SpiderDoc spiderDoc){
    	resultDocs.add(spiderDoc);
    	if (resultDocs.size()>=500) {
            insert();
		}
    }
	public synchronized static void insert() {
		if (resultDocs.size()>0) {
			SpiderDocDao spiderDocDao = new SpiderDocDao();
			try {
//				System.out.println("插入解析后记录："+ resultDocs.size());
				spiderDocDao.save(resultDocs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				resultDocs.clear();
			}
		}
	}
}
