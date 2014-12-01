package com.pujun.spider.fetch;

import java.util.HashMap;
/**
 * 抓取队列
 * @Title: FetchQueue.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年11月21日 下午2:38:14
 */
public class FetchQueue {
	private HashMap<String, String> queue;
    public FetchQueue() {
    	queue=new HashMap<String, String>();
	}
    public void add(String key,String url) {
		queue.put(key, url);
	}
    public void remove(String key) {
    	if (queue.containsKey(key)) {
    		queue.remove(key);
		}
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
	public HashMap<String, String> getQueue() {
		return queue;
	}
    
}
