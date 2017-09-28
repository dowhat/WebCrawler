package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import baiduTieba.bean.TieziInfoBean;
import util.DBStatement;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月21日 下午3:30:46
* @version 版本:				 
* @description:		
*/

public class MainTest {
	
	public static final int threadCount = 20;
	static List<String> notCrawlurlList = new LinkedList<>();
	static List<String> notCrawltiebaList = new LinkedList<>();

	public static void main(String[] args) throws Exception {

		
		/* 贴吧类型爬取 */
//		HtmlParse html = new HtmlParse("http://tieba.baidu.com/f/index/forumclass");
//		TiebaTypeBean type = new TiebaTypeBean(html.parser());
		
		/* 根据贴吧类型爬取贴吧列表 */
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
	    
	    /* 中国好声音吧  爬取前200页的帖子 */
//		TieziInfoBean teizi = new TieziInfoBean("http://tieba.baidu.com/f?kw=%E4%B8%AD%E5%9B%BD%E5%A5%BD%E5%A3%B0%E9%9F%B3","中国好声音吧");
		
		/* 多线程爬取贴吧数据 
		 * 10个线程1分钟约爬100个帖子
		 * 20个线程基本还是这个速度
		 * 100个线程就无法正常爬取数据了
		 * */
		Connection conn = DBStatement.getCon();
	    String sql = "select * from tieba_info";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	notCrawlurlList.add(rs.getString(6));
	        	notCrawltiebaList.add(rs.getString(2));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    notCrawlurlList.remove(0);
	    notCrawltiebaList.remove(0);
	    int urlNum = notCrawlurlList.size();
		MainTest mt = new MainTest();
        long start= System.currentTimeMillis();
        System.out.println("开始爬虫.........................................");
        mt.begin();

        while(true){
            if(notCrawlurlList.isEmpty()&& Thread.activeCount() == 1){
                long end = System.currentTimeMillis();
                System.out.println("总共爬了"+urlNum+"个贴吧");
                System.out.println("总共耗时"+(end-start)/1000+"秒");
                System.exit(1);
                break;
            }
        }
        
		/* 知乎图片爬虫，轮带逛 */
		
		/* 测试时间匹配 */
//		String reg = "(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)";
//		String regex = "(\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2})";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher("2017-09-23 02:45");
//		if(matcher.find())System.out.println(matcher.group(0));
	}
	
	
    private void begin() {

        for(int i=0;i<threadCount;i++){
            new Thread(new Runnable(){
                public void run() {

                    while (true) {
//                      System.out.println("当前进入"+Thread.currentThread().getName());
                        String tmp = notCrawlurlList.get(0);
                        notCrawlurlList.remove(0);
                        String name = notCrawltiebaList.get(0);
                        notCrawltiebaList.remove(0);
                        if(tmp!=null){
                        	try {
								TieziInfoBean teizi = new TieziInfoBean(tmp,name);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }else
                        	break;
                    }
                }
            },"thread-"+i).start();
        }
    }

}
