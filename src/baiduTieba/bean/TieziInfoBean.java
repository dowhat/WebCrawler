package baiduTieba.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBStatement;
import util.HtmlParse;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月21日 下午4:15:21
* @version 版本:				 
* @description:		
*/

public class TieziInfoBean {
	
	public static final long PAGE = 100;

	public static void main(String[] args) {

	}

	long id;
	
	String tieba_name;
	
	String tiezi_url;
	
	String title;
	
	String poster;
	
	long reply_num;
	
	Date post_time;
	
	String last_reply;
	
	Date last_time;
	
	public TieziInfoBean(String url,String tieba_name) throws Exception{
		//获取前100页的帖子
		String tiebaContext = "";
		String lastPageUrl = "";
		String tieziFirstPageContext = "";
		String tieziLastPageContext = "";
		Pattern pattern = Pattern.compile("");
		Matcher matcher = null;
		
		// 时间匹配正则表达式 2009-01-01 12:30:30
		String reg = "(\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2})";
		
		//数据库连接
		Connection con = DBStatement.getCon();
        PreparedStatement ps = null;
		String sql = "INSERT INTO tiezi_info(tieba_name,title,poster,reply_num,post_time,last_reply,last_time,tiezi_url)"
 				+ " VALUES(?,?,?,?,?,?,?,?)";
        ps = con.prepareStatement(sql);
        ps.setString(1, tieba_name);
		
		//帖子链接去重
		Set<String> tieziUrlSet = new HashSet<>();
		for(int i=0;i<100;i++){
			System.out.println(url + "&ie=utf-8&pn=" + (i*50));
			HtmlParse html = new HtmlParse(url + "&ie=utf-8&pn=" + (i*50));
			tiebaContext = html.parser();
			
			//匹配帖子的链接，标题
			Pattern pattern1 = Pattern.compile("<div class=\"threadlist_title pull_left j_th_tit \">.*?"
					+ "<a href=\"(.*?)\" title=\"(.*?)\"");
			Matcher matcher1 = pattern1.matcher(tiebaContext);

			int tieziNum = 0;
			while(matcher1.find()){
				tiezi_url = "http://tieba.baidu.com" + matcher1.group(1);
//				System.out.println(tiezi_url);
				if(tieziUrlSet.add(tiezi_url)){
					tieziNum++;
					setTitle(matcher1.group(2));
//					System.out.println("帖子链接" + matcher.group(1));
					System.out.println("帖子标题:    " + matcher1.group(2));
					//获取帖子首页的内容
					HtmlParse html1 = new HtmlParse(tiezi_url);
					tieziFirstPageContext = html1.parser();
					
					//匹配帖子的首页，发帖人，发帖时间，回帖数，尾页链接
					pattern = Pattern.compile(
							"<span class=\"red\" style=\"margin-right:3px\">"
							+ "(.*?)</span>回复贴，共<span class=\"red\">"
							+ "(.*?)</span>页</li>。*?");
					matcher = pattern.matcher(tieziFirstPageContext);
					if(matcher.find()){
						setReply_num(Integer.valueOf(matcher.group(1)));
						lastPageUrl = tiezi_url + "?pn=" + matcher.group(2);
					}
					
					//匹配帖子的发帖人，发帖时间
					pattern = Pattern.compile(
							reg + ".*?"
							+ "<div class=\"louzhubiaoshi  j_louzhubiaoshi\" author=\"(.*?)\">");
					matcher = pattern.matcher(tieziFirstPageContext);
					if(matcher.find()){
						Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(matcher.group(1));
						setPost_time(dt);
						setPoster(matcher.group(2));
					}
					//获取帖子尾页的内容
					HtmlParse html2 = new HtmlParse(lastPageUrl);
					tieziLastPageContext = html2.parser();
					
					//匹配帖子的尾页，结帖人，结帖时间
					pattern = Pattern.compile(reg + ".*?<li class=\"d_name\".*?target=\"_blank\">(.*?)</a>");
					matcher = pattern.matcher(tieziLastPageContext);
					while(matcher.find()){
						Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(matcher.group(1));
						setLast_time(dt);
						setLast_reply(matcher.group(2));
					}
					poster = poster.replaceAll("<img src=.*?/>", "");
					last_reply = last_reply.replaceAll("<img src=.*?/>", "");
					//将数据存入数据库
					ps.setString(2, this.title);
					ps.setString(3, this.poster);
					ps.setLong(4, this.reply_num);
					ps.setTimestamp(5, new Timestamp(post_time.getTime()));
					ps.setString(6, this.last_reply);
					ps.setTimestamp(7, new Timestamp(last_time.getTime()));
					ps.setString(8, this.tiezi_url);
					try {
						ps.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("共爬取 " +tieziNum +  " 个帖子");
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTieba_name() {
		return tieba_name;
	}

	public void setTieba_name(String tieba_name) {
		this.tieba_name = tieba_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTiezi_url() {
		return tiezi_url;
	}

	public void setTiezi_url(String tiezi_url) {
		this.tiezi_url = tiezi_url;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public long getReply_num() {
		return reply_num;
	}

	public void setReply_num(long reply_num) {
		this.reply_num = reply_num;
	}


	public String getLast_reply() {
		return last_reply;
	}

	public void setLast_reply(String last_reply) {
		this.last_reply = last_reply;
	}

	public final Date getPost_time() {
		return post_time;
	}

	public final void setPost_time(Date post_time) {
		this.post_time = post_time;
	}

	public final Date getLast_time() {
		return last_time;
	}

	public final void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

}
