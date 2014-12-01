package com.pujun.spider.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * url抓取类，封装抓取动作和内容取得
 * @Title: ResponseUtil.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年11月21日 下午1:50:16
 */
public class ResponseUtil {
	private CloseableHttpResponse response;
	private int statusCode;
	private String html;
	private String charset;
	private static final int CHUNK_SIZE = 2000;

	// NUTCH-1006 Meta equiv with single quotes not accepted
	private static Pattern metaPattern = Pattern.compile(
			"<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
			Pattern.CASE_INSENSITIVE);
	private static Pattern charsetPattern = Pattern.compile(
			"charset=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);
   
	public void getResponse(String url) {
		if (StringUtils.isBlank(url)) {
			return;
		}
		URI uri;
		try {
			URL curUrl=new URL(url);
			uri=new URI(curUrl.getProtocol(), curUrl.getHost(), curUrl.getPath(), curUrl.getQuery(), null);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// HttpPost httpPost=new HttpPost("http://www.sina.com.cn/");
		HttpGet httpPost = new HttpGet(uri);
		// Set the User Agent in the header
		httpPost.addHeader("User-Agent", "nutch2/Nutch-2.2.1");
		// prefer English
		httpPost.addHeader("Accept-Language", "en-us,en-gb,en;q=0.7,*;q=0.3");
		// prefer UTF-8
		httpPost.addHeader("Accept-Charset",
				"utf-8,GBK,ISO-8859-1;q=0.7,*;q=0.7");
		// prefer understandable formats
		httpPost.addHeader(
				"Accept",
				"text/html,application/xml;q=0.9,application/xhtml+xml,text/xml;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		// accept gzipped content
		httpPost.addHeader("Accept-Encoding", "x-gzip, gzip, deflate");
		try {
			response = httpClient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity hEntity = response.getEntity();
			ByteBuffer buffer;
			buffer = ByteBuffer.wrap(EntityUtils.toByteArray(hEntity));
			charset = sniffCharacterEncoding(buffer, "utf-8");
			html= Charset.forName(charset).decode(buffer).toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @return the response
	 */
	public CloseableHttpResponse getResponse() {
		return response;
	}
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}
	/**
	 * Given a <code>ByteBuffer</code> representing an html file of an
	 * <em>unknown</em> encoding, read out 'charset' parameter in the meta tag
	 * from the first <code>CHUNK_SIZE</code> bytes. If there's no meta tag for
	 * Content-Type or no charset is specified, <code>null</code> is returned. <br />
	 * FIXME: non-byte oriented character encodings (UTF-16, UTF-32) can't be
	 * handled with this. We need to do something similar to what's done by
	 * mozilla
	 * (http://lxr.mozilla.org/seamonkey/source/parser/htmlparser/src/nsParser
	 * .cpp#1993). See also http://www.w3.org/TR/REC-xml/#sec-guessing <br />
	 *
	 * @param content
	 *            <code>ByteBuffer</code> representation of an html file
	 */

	private static String sniffCharacterEncoding(ByteBuffer content,
			String defaultEncoding) {
		int length = Math.min(content.remaining(), CHUNK_SIZE);

		// We don't care about non-ASCII parts so that it's sufficient
		// to just inflate each byte to a 16-bit value by padding.
		// For instance, the sequence {0x41, 0x82, 0xb7} will be turned into
		// {U+0041, U+0082, U+00B7}.
		String str = "";
		try {
			str = new String(content.array(), content.arrayOffset()
					+ content.position(), length, Charset.forName("ASCII")
					.toString());
		} catch (UnsupportedEncodingException e) {
			// code should never come here, but just in case...
			return null;
		}

		Matcher metaMatcher = metaPattern.matcher(str);
		String encoding = null;
		if (metaMatcher.find()) {
			Matcher charsetMatcher = charsetPattern.matcher(metaMatcher
					.group(1));
			if (charsetMatcher.find())
				encoding = new String(charsetMatcher.group(1));
		}
		return encoding == null ? defaultEncoding : encoding;
	}
}
