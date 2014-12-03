package com.pujun.spider.fetch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.pujun.spider.crawl.Crawler;
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
		feedFromFile();
		while (true) {
			if (Crawlers.queueSize<1000) {
		        feedFromMySQL();
			}
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
	private void feedFromFile() {
		// TODO Auto-generated method stub
		try {
			FileReader fileReader=new FileReader("urls/seeds.txt");
			BufferedReader bReader=new BufferedReader(fileReader);
			String fileUrl=null;
			while ((fileUrl=bReader.readLine())!=null) {
				Crawlers.hightQueue.add(fileUrl);
				Crawlers.queueSize++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
//        	startKey=startKey+size-1;
        	for (int j = 0; j < list.size(); j++) {
            	SpiderDoc spiderDoc=list.get(j);
            	if (shouldAdd(spiderDoc)) {
            		Crawlers.hightQueue.add(spiderDoc.getUrl());
            		Crawlers.queueSize++;
				}
			}

        }
	}
	private boolean shouldAdd(SpiderDoc spiderDoc) {
		// TODO Auto-generated method stub
		return (!Const.PARSED.equals(spiderDoc.getStatus()))&&((spiderDoc.getFetchcode()==200)||(spiderDoc.getFetchcode()==0));
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
        HashMap<String, Object> params=new HashMap<String, Object>();
        params.put("startKey", startKey);
        params.put("size", size);
        params.put("status", Const.PARSED);
        return spiderDocDao.selectLimit(params);
    }

}
