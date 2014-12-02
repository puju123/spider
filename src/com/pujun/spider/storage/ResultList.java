package com.pujun.spider.storage;

import java.util.ArrayList;
import java.util.List;
/**
 * 抓取最终结果缓存类
 * @Title: ResultList.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年12月2日 下午4:12:05
 */
public class ResultList {
    public List<SpiderDoc> resultDocs=new ArrayList<SpiderDoc>();
    public void addResult(SpiderDoc spiderDoc){
    	resultDocs.add(spiderDoc);
    	if (resultDocs.size()>=200) {
            insert();
		}
    }
	public void insert() {
		if (resultDocs.size()>0) {
			SpiderDocDao spiderDocDao = new SpiderDocDao();
			try {
				System.out.println("插入解析后记录："+ resultDocs.size());
				spiderDocDao.add(resultDocs);
				resultDocs.clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}