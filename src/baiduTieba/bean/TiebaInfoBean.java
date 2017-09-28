package baiduTieba.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBStatement;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月21日 下午4:14:54
* @version 版本:				 
* @description:		
*/

public class TiebaInfoBean {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	long id;
	
	String name;
	
	String url;
	
	long type_id;
	
	long tiezi_num;

	long follow_num;
	
	public TiebaInfoBean(String context, long type_id) throws Exception{
		
		//链接 贴吧名 关注数 帖子数
		Pattern pattern = Pattern.compile("<div class=\"ba_info\"><a target=\"_blank\" href=\"(.*?)\" class=\"ba_href clearfix\">.*?"
				+ "<p class=\"ba_name\">(.*?)</p>.*?"
				+ "<span class=\"ba_m_num\">(.*?)</span>.*?"
				+ "<span class=\"ba_p_num\">(.*?)</span>");
		Matcher matcher = pattern.matcher(context);

		Connection con = DBStatement.getCon();
        PreparedStatement ps = null;
		String sql = "INSERT INTO tieba_info(type_id,url,name,follow_num,tiezi_num)"
 				+ " VALUES(?,?,?,?,?)";
        ps = con.prepareStatement(sql);
        
		int tiebaNum = 0;
		while(matcher.find()){
			setUrl("http://tieba.baidu.com" + matcher.group(1));
			setName(matcher.group(2));
			setTiezi_num(Long.valueOf(matcher.group(4)));
			setFollow_num(Long.valueOf(matcher.group(3)));
			ps.setLong(1, type_id);
			ps.setString(2, this.url);
			ps.setString(3, this.name);
			ps.setLong(4, this.follow_num);
			ps.setLong(5, this.tiezi_num);
			ps.executeUpdate();
			tiebaNum++;
		}
		System.out.println("we get " + tiebaNum + " tieba");
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getType_id() {
		return type_id;
	}

	public void setType_id(long type_id) {
		this.type_id = type_id;
	}

	public long getTiezi_num() {
		return tiezi_num;
	}

	public void setTiezi_num(long tiezi_num) {
		this.tiezi_num = tiezi_num;
	}

	public long getFollow_num() {
		return follow_num;
	}

	public void setFollow_num(long follow_num) {
		this.follow_num = follow_num;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
