package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class HtmlParse {
 /**
  * 要分析的网页
  */
 String htmlUrl;
 /**
  * 分析结果
  */
 ArrayList<String> hrefList = new ArrayList<String>();
 /**
  * 网页编码方式
  */
 String charSet;
 
 public HtmlParse(String htmlUrl) {
	 this.htmlUrl = htmlUrl;
 }

/**
  * 解析网页链接
  * 
  * @return
 * @throws Exception 
 * @throws Exception 
  */
 public  String parser() throws Exception {
  URL url = new URL(htmlUrl);
  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  connection.setDoOutput(true);
  String contenttype = connection.getContentType();
  charSet = getCharset(contenttype);
  InputStreamReader isr = new InputStreamReader(
    connection.getInputStream(), charSet);
  BufferedReader br = new BufferedReader(isr);
  String str = null;
  String result ="";
  while ((str = br.readLine()) != null) {
	  result += str;
  }
  return result;
 }
 /**
  * 获取网页编码方式
  * 
  * @param str
  */
 private String getCharset(String str) {
  Pattern pattern = Pattern.compile("charset=.*");
  Matcher matcher = pattern.matcher(str);
  if (matcher.find())
   return matcher.group(0).split("charset=")[1];
  return null;
 }
 
}
