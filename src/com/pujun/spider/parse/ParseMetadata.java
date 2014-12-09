package com.pujun.spider.parse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

public class ParseMetadata extends Parser{

	@Override
	public void parse() {
        if (spiderDoc!=null) {
			Document doc=preproccessing(spiderDoc.getHtml());
			if (doc!=null) {
				analysis(doc);
			}
		}		
	}

	private void analysis(Document doc) {
		// TODO Auto-generated method stub
		
	}

	private Document preproccessing(String html) {
		if (html==null) {
			return null;
		}
		String[] searchList={};
		String[] replacementList={};
		StringUtils.replaceEach(html, searchList, replacementList).
		Document document=Jsoup.parse(html);
		document.getAllElements().select("meta|link|script").remove();
		return null;
	}
    
}
