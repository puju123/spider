package com.pujun.spider.crawl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pujun.spider.fetch.FetchQueue;
import com.pujun.spider.storage.ResultList;
/**
 * 抓取队列的调度类，负责调度加入队列的url
 * @Title: Crawlers.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年12月2日 下午4:04:22
 */
public class Crawlers{
	// 高优先级队列
	public static FetchQueue hightQueue = new FetchQueue(1000);
	// 高优先级队列
	public static FetchQueue middleQueue = new FetchQueue(1000);
	// 高优先级队列
	public static FetchQueue lowQueue = new FetchQueue(1000);
	// 线程池
	public static ExecutorService exe = Executors.newFixedThreadPool(100);
	//处理记录总数--〉for test
	private long recordCount=0;
	/**
	 * 抓取队列调度
	 * @Description: TODO
	 * @author xinhua
	 * @date 2014年12月2日 下午4:05:18 
	 * @update
	 */
	public void crawl() {
		int hSize = 0;
		int mSize = 0;
		int lSize = 0;
		// 当前队列
		// FetchQueue curQueue = new FetchQueue();
		while (true) {
			hSize = hightQueue.getSize();
			mSize = middleQueue.getSize();
			lSize = lowQueue.getSize();

			if (hSize > 0) {
				System.out.println("目前队列：hightQueue:" + hightQueue.getSize());
				crawlList(hightQueue);

			} else if (mSize > 0) {
				System.out.println("目前队列：middleQueue:" + middleQueue.getSize());
				crawlList(middleQueue);

			} else if (lSize > 0) {
				System.out.println("目前队列：lowQueue:" + lowQueue.getSize());
				crawlList(lowQueue);
			}
		}
	}
	/**
	 * 进如抓取流程
	 * @Description: TODO
	 * @author xinhua
	 * @date 2014年12月2日 下午4:05:40 
	 * @update 
	 * @param queueList
	 */
	private void crawlList(FetchQueue queueList) {
		// TODO Auto-generated method stub
		if (queueList != null) {
			String key = null;
			while ((key =queueList.get())!=null) {
				try {
					// 对当前抓取队列进行抓取
					recordCount++;
					System.out
					.println("..................................................................目前处理第:"
							+ recordCount);
					Crawler crawler = new Crawler(key,recordCount);
					exe.submit(crawler);
					// spiderDoc.getFetchtime());
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
			try {
				//最后结果写入数据库
			    ResultList resultList=new ResultList();
			    resultList.insert();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
