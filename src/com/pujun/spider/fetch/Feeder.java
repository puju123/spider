package com.pujun.spider.fetch;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pujun.spider.storage.SpiderDoc;

public class Feeder implements Runnable {
	public static FetchQueue hightQueue = new FetchQueue();
	public static FetchQueue middleQueue = new FetchQueue();
	public static FetchQueue lowQueue = new FetchQueue();
	public static ExecutorService exe = Executors.newCachedThreadPool();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int hSize = 0;
		int mSize = 0;
		int lSize = 0;
		FetchQueue curQueue = null;
		while (true) {
			hSize = hightQueue.getSize();
			mSize = middleQueue.getSize();
			lSize = lowQueue.getSize();
			curQueue = null;
			if (hSize > 0) {
				curQueue = hightQueue;
				System.out.println("目前队列：hightQueue");
			} else if (mSize > 0) {
				curQueue = middleQueue;
				System.out.println("目前队列：middleQueue");
			} else if (lSize > 0) {
				curQueue = lowQueue;
				System.out.println("目前队列：lowQueue");
			}
			if (curQueue!=null) {
				Iterator<Integer> it = curQueue.getQueue().keySet().iterator();
				int key = 0;
				try {
					while (it.hasNext()) {
						key = it.next();
						HtmlFetcher htmlFetcher = new HtmlFetcher(curQueue
								.getQueue().get(key));
						SpiderDoc spiderDoc = exe.submit(htmlFetcher).get();
						System.out.println(spiderDoc.getUrl()+" : " + spiderDoc.getFetchtime());
					}
					curQueue.getQueue().clear();
					System.out.println("队列长度："+curQueue.getSize());
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
		}
	}
}
