package com.pujun.spider.fetch;

import java.util.HashMap;
import java.util.List;
import com.pujun.spider.crawl.Crawlers;
import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;
import com.pujun.spider.tool.Const;
/**
 * url抓取队列伺服类
 * @Title: Feeder.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年12月2日 下午4:08:07
 */
public class Feeder implements Runnable {
	private static long startKey=0;
	private static final long size=1000;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
//			Crawlers.hightQueue.add("http://www.sina.com.cn/");
	        feedFromMySQL();
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
	/**
	 * 从数据库中取出需要抓取数据，每次取出1000条
	 * @Description: TODO
	 * @author xinhua
	 * @date 2014年12月2日 下午4:08:56 
	 * @update
	 */
	private void feedFromMySQL() {
		// TODO Auto-generated method stub
        List<SpiderDoc> list=getSpiderDocList();
        System.out.println("。。。。。。。。。进入队列：" + list.size());
        if (list.size()>0){
        	startKey=startKey+size-1;
        	for (int j = 0; j < list.size(); j++) {
            	SpiderDoc spiderDoc=list.get(j);
            	if (!Const.FETCHED.equals(spiderDoc.getStatus())) {
            		Crawlers.hightQueue.add(spiderDoc.getUrl());
				}
			}

        }
	}
	/**
	 * 查询数据库
	 * @Description: TODO
	 * @author xinhua
	 * @date 2014年12月2日 下午4:09:44 
	 * @update 
	 * @return
	 */
    private List<SpiderDoc> getSpiderDocList() {
        // TODO Auto-generated method stub
        SpiderDocDao spiderDocDao=new SpiderDocDao();
        HashMap<String, Long> params=new HashMap<String, Long>();
        params.put("startKey", startKey);
        params.put("size", size);
        return spiderDocDao.selectLimit(params);
    }

}
