package baiduTieba.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBStatement;
import util.HtmlParse;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月21日 下午4:09:29
* @version 版本:				 
* @description:		
*/

public class TiebaTypeBean {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	long id;
	
	String type;
	
	String url;
	
	public TiebaTypeBean(String context) throws Exception{
		Pattern pattern = Pattern.compile("<ul class=\"f_class_menu\" id=\"f_class_menu\">(.*?)</ul>");
		Matcher matcher = pattern.matcher(context);
		String typeList = "";
		if(matcher.find())typeList = matcher.group(1);
		
		Connection con = DBStatement.getCon();
        PreparedStatement ps = null;
		String sql = "INSERT INTO tieba_type(type,url)"
 				+ " VALUES(?,?)";
        ps = con.prepareStatement(sql);
        
		pattern = Pattern.compile("<a href=\"(.*?)\" title=\"(.*?)\" class=");
		matcher = pattern.matcher(typeList);
		
		int typeNum = 0;
		while(matcher.find()){
			setUrl("http://tieba.baidu.com" + matcher.group(1));
			setType(matcher.group(2));
			ps.setString(1, this.type);
			ps.setString(2, this.url);
			ps.executeUpdate();
			typeNum++;
		}
		System.out.println("we have "+ typeNum + " type");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
