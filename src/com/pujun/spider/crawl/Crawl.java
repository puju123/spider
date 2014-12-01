package com.pujun.spider.crawl;

import com.pujun.spider.fetch.Feeder;
import com.pujun.spider.parse.ParsePage;
import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;
import com.pujun.spider.tool.Const;

import java.util.List;

public class Crawl {
    public void fetch(){
        Feeder feeder=new Feeder();
        Thread thread=new Thread(feeder);
        thread.start();
        feeder.hightQueue.add(String.valueOf(1), "http://www.sina.com.cn/");
        List<SpiderDoc> list=getSpiderDocList();
        if (list.size()>0){
        	for (int j = 0; j < list.size(); j++) {
            	SpiderDoc spiderDoc=list.get(j);
            	if (!Const.FETCHED.equals(spiderDoc.getStatus())) {
                    feeder.hightQueue.add(spiderDoc.getId(), spiderDoc.getUrl());
				}

			}

        }
//        feeder.hightQueue.add(2, "http://blog.csdn.net/mars5337/article/details/6576417");
//        feeder.hightQueue.add(3, "http://os.51cto.com/art/201306/398363_6.htm");
//        feeder.hightQueue.add(4, "http://www-01.ibm.com/software/data/infosphere/biginsights/quick-start/downloads.html");
//        feeder.hightQueue.add(5, "http://blog.csdn.net/ghsau/article/details/7451464");
//        feeder.middleQueue.add(1, "http://blog.csdn.net/ghsau/article/details/7451464");

    }
    public void parse(){
    	ParsePage page=new ParsePage();
//    	page.parse();
    }
    private List<SpiderDoc> getSpiderDocList() {
        // TODO Auto-generated method stub
        SpiderDocDao spiderDocDao=new SpiderDocDao();
        return spiderDocDao.selectAll();
    }
}
