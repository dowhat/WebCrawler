package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月26日 下午7:08:22
* @version 版本:				 
* @description:		
*/

public class DBtoText {

	public static void main(String[] args) throws Exception {
		//获取帖子的标题 rs.getString(3) 存入txt文档中
		String fileName = "D:/tiezi_title.txt";
		Connection conn = DBStatement.getCon();
	    String sql = "select * from tiezi_info";
	    
	    FileOutputStream out = new FileOutputStream(new File(fileName));
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	bos.write(rs.getString(3).getBytes());
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    bos.flush();   
	    bos.close();
	}
	
	public void DateToText(String fileName, Map<String, Integer> map) throws Exception{
		FileOutputStream out = new FileOutputStream(new File(fileName));
		BufferedOutputStream bos = new BufferedOutputStream(out);
		

		for (String key : map.keySet()) {
		    int value = map.get(key);
		    bos.write((key + "     " + value+"\r\n").getBytes());
		}
		
		bos.flush();
		bos.close();
	}
	public void DateToText(String fileName, Set<String> set) throws Exception{
		FileOutputStream out = new FileOutputStream(new File(fileName));
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
		
		for (String x : set) {
			bos.write((x+"\r\n").getBytes());
		}
		
		bos.flush();
		bos.close();
	}

	public String getTitleString(){
		//获取帖子的标题 rs.getString(3) 返回之
		String title = "";
		Connection conn = DBStatement.getCon();
	    String sql = "select * from tiezi_info";
	    
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        int i = 0;
	        while (rs.next()) {
	        	title+=rs.getString(3);
	        	i++;
	        }
	        System.out.println(i);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return title;
	}
	public List<String> getTitleStringList(){
		//获取帖子的标题 rs.getString(3) 返回之
		List<String> titleList = new ArrayList<>();
		String title = "";
		Connection conn = DBStatement.getCon();
		String sql = "select * from tiezi_info";
		
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				title+=rs.getString(3);
				i++;
				if(i%200==0){
					titleList.add(title);
					title="";
				}
			}
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return titleList;
	}
}
