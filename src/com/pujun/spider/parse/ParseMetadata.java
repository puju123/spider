package com.pujun.spider.parse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseMetadata extends Parser{
    private static final String[] paraTag={"p","br","h1","h2","h3","h4","h5","h6","table","tr","hr","div","center"};
    private static final String deleteTag="script,noscript,style";
    private static final String[] blockTag={"<div","<table"};
    private static final String[] replaceTag={"$text","$text"};
    private static final String commentReg="<!--[\\s\\S]*?-->";
    private static final String scriptReg="<script[^>]*?[\\s\\S]*?</script>";
    private static final String noscriptReg="<noscript[^>]*?[\\s\\S]*?</noscript>";
    private static final String styleReg="<style[^>]*?[\\s\\S]*?</style>";
    private static final String whiteReg="[\\s\\r\\n\\t]+";
	@Override
	public void parse() {
        if (spiderDoc!=null) {
			List<String> doc=preproccessing(spiderDoc.getHtml());
			if (doc!=null) {
				analysis(doc);
			}
		}		
	}

	private void analysis(List<String> doc) {
		// TODO Auto-generated method stub
		
	}

	private List<String> preproccessing(String html) {
		if (html==null) {
			return null;
		}
		List<String> resultList=new ArrayList<String>();
//		//去除注释
//		Pattern pattern;
//		Matcher matcher;
//		//
//		pattern=Pattern.compile(commentReg);
//		matcher=pattern.matcher(html);
//		html=matcher.group();
//		//
//		pattern=Pattern.compile(scriptReg);
//		matcher=pattern.matcher(html);
//		html=matcher.group();
//		//
//		pattern=Pattern.compile(noscriptReg);
//		matcher=pattern.matcher(html);
//		html=matcher.group();
//		//
//		pattern=Pattern.compile(commentReg);
//		matcher=pattern.matcher(html);
//		html=matcher.group();
//		//
//		pattern=Pattern.compile(commentReg);
//		matcher=pattern.matcher(html);
//		html=matcher.group();
		//去除多余节点
//		Document document=Jsoup.parse(html);
//		document.body().getAllElements().select(deleteTag).remove();
//		String htmlString=document.body().html();
		//文本分段
		String htmlString=null;
		htmlString=html.replaceAll(scriptReg, "");
		htmlString=htmlString.replaceAll(noscriptReg, "");
		htmlString=htmlString.replaceAll(styleReg, "");
		htmlString=htmlString.replaceAll(commentReg, "");
		htmlString=StringUtils.replaceEach(htmlString, blockTag, replaceTag);
		String[] blockList=StringUtils.splitByWholeSeparator(htmlString,replaceTag[0] );
		int count=blockList.length;
		for (int i = 0; i <count; i++) {
			if (StringUtils.isBlank(blockList[i])) {
				continue;
			}
			resultList.add(blockList[i]);
		}
		return resultList;
	}
    
}
