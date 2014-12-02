package com.pujun.spider.fetch;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * 抓取队列
 * @Title: FetchQueue.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年11月21日 下午2:38:14
 */
public class FetchQueue {
	private Queue<String> queue;
    public FetchQueue(int size) {
    	queue=new LinkedBlockingDeque<String>(size);
	}
    public void add(String url) {
		queue.offer(url);
	}
    public String get() {
    	return queue.poll();
	}
    public int getSize() {
		// TODO Auto-generated method stub
    	if (queue==null) {
			return 0;
		}
        return queue.size();
	}
	/**
	 * @return the queue
	 */
	public Queue<String> getQueue() {
		return queue;
	}
    
}
