package util;
  
import java.io.StringReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;  
  
public class IKAnalyze {  
    public static void main(String[] args) throws Exception {  
        String text="基于java语言开发的轻量级的中文分词工具包";  
        //创建分词对象  
        Analyzer anal=new IKAnalyzer(true);       
        StringReader reader=new StringReader(text);  
        //分词  
        TokenStream ts=anal.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        //遍历分词数据  
        while(ts.incrementToken()){  
            System.out.print(term.toString()+"|");  
        }  
        reader.close();  
        System.out.println(); 
        
        DBtoText dt = new DBtoText();
//        analyzeWordFrequency2(dt.getTitleString());
        analyzeWordFrequency2(dt.getTitleStringList());
    }
    
    //获取分词结果的第一种方式
    public static Map<String,Integer> analyzeWordFrequency(String text) throws Exception{
    	Map<String,Integer> result = new HashMap<String, Integer>();
    	Set<String> wordSet = new HashSet<>();
    	//创建分词对象  
        Analyzer anal=new IKAnalyzer(true);       
        StringReader reader=new StringReader(text);  
        //分词  
        TokenStream ts=anal.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        //遍历分词数据  
		while(ts.incrementToken()){
			if(wordSet.add(term.toString()))
				result.put(term.toString(), 1);
			else
				result.put(term.toString(), result.get(term.toString())+1);
		}
		
        reader.close();  
        System.out.println(wordSet);
    	
        System.out.println(sortByValue(result));
        DBtoText dt = new DBtoText();
        dt.DateToText("D:/中国好声音分词结果.txt", wordSet);
        dt.DateToText("D:/中国好声音词频分析.txt", sortByValue(result));
    	return result;
    }
    
    //获取分词结果的第二种方式
    public static Map<String,Integer> analyzeWordFrequency2(String text) throws Exception{
    	Map<String,Integer> result = new HashMap<String, Integer>();
    	Set<String> wordSet = new HashSet<>();
    	
    	System.out.println(text.length());
    	//创建分词对象  
    	Configuration configuration = new Configuration();
    	configuration.setUseSmart(true);
    	StringReader reader=new StringReader(text);
    	IKSegmenter ik = new IKSegmenter(reader, configuration);
    	Lexeme t;
    	//遍历分词数据  
    	while ((t = ik.next()) != null) {
    		if(wordSet.add(t.getLexemeText()))
    			result.put(t.getLexemeText(), 1);
    		else
    			result.put(t.getLexemeText(), result.get(t.getLexemeText())+1);
    	}
    	reader.close();  
    	System.out.println(wordSet);
    	
    	System.out.println(sortByValue(result));
    	DBtoText dt = new DBtoText();
    	dt.DateToText("D:/中国好声音分词结果1.txt", wordSet);
    	dt.DateToText("D:/中国好声音词频分析1.txt", sortByValue(result));
    	return result;
    }
    //获取分词结果的第二种方式
    public static Map<String,Integer> analyzeWordFrequency2(List<String> titleList) throws Exception{
    	Map<String,Integer> result = new HashMap<String, Integer>();
    	Set<String> wordSet = new HashSet<>();
    	for(String text:titleList){
    		System.out.println(text.length());
	    	//创建分词对象  
	    	Configuration configuration = new Configuration();
	    	configuration.setUseSmart(true);
	    	StringReader reader=new StringReader(text);
	    	IKSegmenter ik = new IKSegmenter(reader, configuration);
	    	Lexeme t;
	    	//遍历分词数据  
	    	while ((t = ik.next()) != null) {
	    		if(wordSet.add(t.getLexemeText()))
	    			result.put(t.getLexemeText(), 1);
	    		else
	    			result.put(t.getLexemeText(), result.get(t.getLexemeText())+1);
	    	}
	    	reader.close();
    	}
    	System.out.println(wordSet);
    	
    	System.out.println(sortByValue(result));
    	DBtoText dt = new DBtoText();
    	dt.DateToText("D:/中国好声音分词结果1.txt", wordSet);
    	dt.DateToText("D:/中国好声音词频分析1.txt", sortByValue(result));
    	return result;
    }
    
    //对map value 排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Entry<K, V>> st = map.entrySet().stream();
        //升序
//        st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        
        //降序
        st.sorted(Comparator.comparing(e -> ((Entry<K, V>) e).getValue()).reversed()).forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
    
    
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}  