package com.pujun.spider.fetch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pujun.spider.crawl.Crawl;
import com.pujun.spider.parse.ParsePage;
import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;

public class Feeder implements Runnable {
	// 高优先级队列
	public static FetchQueue hightQueue = new FetchQueue();
	// 高优先级队列
	public static FetchQueue middleQueue = new FetchQueue();
	// 高优先级队列
	public static FetchQueue lowQueue = new FetchQueue();
	// 线程池
	public static ExecutorService exe = Executors.newFixedThreadPool(200);
	// 抓取内容列表
	public static List<SpiderDoc> resultList = new ArrayList<SpiderDoc>();

	private long urlCount = 0;

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
				crawlList(hightQueue.getQueue());

			} else if (mSize > 0) {
				System.out.println("目前队列：middleQueue:" + middleQueue.getSize());
				crawlList(middleQueue.getQueue());

			} else if (lSize > 0) {
				System.out.println("目前队列：lowQueue:" + lowQueue.getSize());
				crawlList(lowQueue.getQueue());

			}

		}
	}

	private void crawlList(HashMap<String, String> queue) {
		// TODO Auto-generated method stub
		if (queue != null) {
			HashMap<String, String> curQueue = (HashMap<String, String>) queue
					.clone();
			Iterator<String> it = curQueue.keySet().iterator();
			String key = null;
			while (it.hasNext()) {
				urlCount++;
				key = it.next();
				try {
					// 对当前抓取队列进行抓取
					HtmlFetcher htmlFetcher = new HtmlFetcher(
							curQueue.get(key), urlCount);
					SpiderDoc spiderDoc = exe.submit(htmlFetcher).get();
					if (spiderDoc != null) {
						ParsePage parsePage = new ParsePage();
						parsePage.parse(spiderDoc);
						resultList.add(spiderDoc);
					}
					// 取得结果大于200条则存入数据库
					if (resultList.size() >= 20) {
						insertList();
					}
					// spiderDoc.getFetchtime());
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}finally{
					// 删除处理完成的url
					queue.remove(key);
					System.out
							.println(".................................................................................目前队列:"
									+ queue.size());
					// System.out.println(spiderDoc.getUrl()+" : " +
				}
			}
			// 剩余结果存入数据库
			insertList();
			// System.out.println("队列长度："+curQueue.getSize());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void insertList() {
		if (resultList!=null&&resultList.size() >= 0) {
			SpiderDocDao spiderDocDao = new SpiderDocDao();
			try {
				spiderDocDao.add(resultList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultList.clear();
		}
	}

}
