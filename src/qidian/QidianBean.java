package qidian;

/** 
* @author 作者 :		xueyu 
* @date 创建时间:		2017年9月21日 下午11:17:27
* @version 版本:				 
* @description:		
*/

public class QidianBean {
	int id;
	
	String type;
	
	String sub_type;
	
	String book;
	
	String writer;
	
	String words_num;
	
	String finish_time;

	public int getId() {
		return id;
	}

	public String getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSub_type() {
		return sub_type;
	}

	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWords_num() {
		return words_num;
	}

	public void setWords_num(String words_num) {
		this.words_num = words_num;
	}

	
}
