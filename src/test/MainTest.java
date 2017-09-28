package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import baiduTieba.bean.TieziInfoBean;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月21日 下午3:30:46
* @version 版本:				 
* @description:		
*/

public class MainTest {

	public static void main(String[] args) throws Exception {

		
		/* tieba type spider */
//		HtmlParse html = new HtmlParse("http://tieba.baidu.com/f/index/forumclass");
//		TiebaTypeBean type = new TiebaTypeBean(html.parser());
		
		/* tieba list spider */
//		Connection conn = DBStatement.getCon();
//	    String sql = "select * from tieba_type";
//	    PreparedStatement pstmt;
//	    try {
//	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
//	        ResultSet rs = pstmt.executeQuery();
//	        while (rs.next()) {
//	    		HtmlParse html1 = new HtmlParse(rs.getString(3));
//	    		TiebaInfoBean info = new TiebaInfoBean(html1.parser(),rs.getLong(1));
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
	    
	    /* 中国好声音吧 tiezi list spider */
		TieziInfoBean teizi = new TieziInfoBean("http://tieba.baidu.com/f?kw=%E4%B8%AD%E5%9B%BD%E5%A5%BD%E5%A3%B0%E9%9F%B3","中国好声音吧");
		
		/* 多线程爬取贴吧数据 */
		
		
		/* 测试时间匹配 */
//		String reg = "(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)";
//		String regex = "(\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2})";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher("2017-09-23 02:45");
//		if(matcher.find())System.out.println(matcher.group(0));
	}

}
