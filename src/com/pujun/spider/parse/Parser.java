package com.pujun.spider.parse;

import com.pujun.spider.storage.SpiderDoc;

public abstract class Parser {
	protected SpiderDoc spiderDoc=new SpiderDoc();
	
    /**
	 * @return the spiderDoc
	 */
	public SpiderDoc getSpiderDoc() {
		return spiderDoc;
	}

	/**
	 * @param spiderDoc the spiderDoc to set
	 */
	public void setSpiderDoc(SpiderDoc spiderDoc) {
		this.spiderDoc = spiderDoc;
	}

	public abstract void parse();
}
