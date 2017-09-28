package qidian;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class QidianCrawler {
 /**
  * 要分析的网页
  */
 String htmlUrl;
 /**
  * 分析结果
  */
 ArrayList<String> hrefList = new ArrayList<String>();
 static List<QidianBean> dataList = new ArrayList<QidianBean>();
 /**
  * 网页编码方式
  */
 String charSet;
 
 public QidianCrawler(String htmlUrl) {
	 this.htmlUrl = htmlUrl;
 }
 /**
  * 获取分析结果
 * @throws Exception 
  */
 public ArrayList<String> getHrefList() throws Exception {
	 parser();
	 return hrefList;
 }
 /**
  * 解析网页链接
  * 
  * @return
 * @throws Exception 
  */
 private void parser() throws Exception {
  URL url = new URL(htmlUrl);
  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  connection.setDoOutput(true);
  String contenttype = connection.getContentType();
  charSet = getCharset(contenttype);
  InputStreamReader isr = new InputStreamReader(
    connection.getInputStream(), charSet);
  BufferedReader br = new BufferedReader(isr);
  String str = null, rs = null;
  String result ="";
  while ((str = br.readLine()) != null) {
	  result += str;
  }
  getHref(result);
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
 /**
  * 从一行字符串中读取链接
  * 
  * @return
 * @throws Exception 
  */
 private void getHref(String str) throws Exception {
	 
	  String matchType1= "<em>「</em>(.*?)</a>";
	  String matchType2= "data-eid=\"qd_B61\">(.*?)<em>」</em>";
	  String matchBook= "data-eid=\"qd_B58\" target=\"_blank\">(.*?)</a>";
	  String matchWriter="target=\"_blank\" data-eid=\"qd_B59\">(.*?)</a>";
	  String matchWordNum="<span class=\"total\">(.*?)</span>";
	  String matchDate="<td class=\"date\">(.*?)</td>";
	  Pattern pattern1 = Pattern.compile(matchType1);
	  Matcher matcher1 = pattern1.matcher(str);
	  Pattern pattern2 = Pattern.compile(matchType2);
	  Matcher matcher2 = pattern2.matcher(str);
	  Pattern pattern3 = Pattern.compile(matchBook);
	  Matcher matcher3 = pattern3.matcher(str);
	  Pattern pattern4 = Pattern.compile(matchWriter);
	  Matcher matcher4 = pattern4.matcher(str);
	  Pattern pattern5 = Pattern.compile(matchWordNum);
	  Matcher matcher5 = pattern5.matcher(str);
	  Pattern pattern6 = Pattern.compile(matchDate);
	  Matcher matcher6 = pattern6.matcher(str);
	  
	  
	  while(matcher1.find()&& matcher2.find() && matcher3.find() && matcher4.find() && matcher5.find() && matcher6.find()){
		  //检查密码字段为空
		  QidianBean data = new QidianBean();
		  data.setType(matcher1.group(1));
		  data.setSub_type(matcher2.group(1));
		  data.setBook(matcher3.group(1));
		  data.setWriter(matcher4.group(1));
		  data.setWords_num(matcher5.group(1));
		  data.setFinish_time(matcher6.group(1));
		  dataList.add(data);
		  System.out.println(matcher1.group(1)
					  + "-" + matcher2.group(1) + "    " + matcher3.group(1) + " " + matcher4.group(1)+ " "+ matcher5.group(1) +" " + matcher6.group(1));
			  hrefList.add(matcher1.group(1)
					  + "-" + matcher2.group(1) + "    " + matcher3.group(1) + " " + matcher4.group(1)+ " " +matcher5.group(1) +" " + matcher6.group(1)); 
	  } 
 }
 
 public static void main(String[] arg) throws Exception {
	 long startTime=System.currentTimeMillis();
	 String fileName = "D:/起点中文网人气排序前50页.txt";
	 String html = "http://a.qidian.com/?action=1&orderId=&style=2&pageSize=50&siteid=1&hiddenField=0&page=";
	 /**
		 * jdbc四大配置参数：
		 * 1.driverClassName:com.mysql.jdbc.Driver
		 * 2.url:jdbc:mysql://localhost:3306/mydb
		 * 3.username:root
		 * 4.password:123
		 */
		Class.forName("com.mysql.jdbc.Driver");//加载驱动类(注册驱动类)
		String mySqlUrl = "jdbc:mysql://localhost:3306/crawler";
		String username = "admin";
		String password = "123456";
		
		//得到连接对象
		Connection con = DriverManager.getConnection(mySqlUrl, username, password);
			
		/*对数据库做增、删、改
		 * 1.通过Connection对象创建Statement
		 *   Statement语句的发送器，它的功能就是向数据库发送sql语句！
		 * 2.调用他的int executeUpdate(String sql),返回影响了几行
		 */
		//通过Connection 得到Statement;
		Statement stmt = con.createStatement();
	 int pageNum = 100;
	 int data_num = 0;
	 FileOutputStream out = new FileOutputStream(new File(fileName));
	 BufferedOutputStream bos = new BufferedOutputStream(out);
	 for(int j=1;j<=pageNum;j++){
		 QidianCrawler a = new QidianCrawler(html+j);
		 ArrayList<String> hrefList = a.getHrefList();
		 for(QidianBean x:dataList){
	    	 data_num++;
	    	 System.out.println(x.getBook());
	    	 String sql = "INSERT INTO qidian_book(type,sub_type,book,writer,words_num,finish_time)"
	 				+ " VALUES('"+x.getType()+"','"+x.getSub_type()+"','"+x.getBook()+"','"
	 				+x.getWriter()+"','"+x.getWords_num()+"','"+x.getFinish_time()+"')";
	 		//执行sql
	 		stmt.executeUpdate(sql);
	     }
		 dataList.clear();
		 for (int i = 0; i < hrefList.size(); i++){
			bos.write((hrefList.get(i)+"\r\n").getBytes());
		 }
	 	}
	 bos.flush();   
     bos.close();
     System.out.println(data_num + "条信息存入数据库成功！"); 
     System.out.println("共用时 " + (System.currentTimeMillis()-startTime) + " 毫秒");
 	}
}
