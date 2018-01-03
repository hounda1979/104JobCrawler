package hd.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
/**
 * 測試 統計技能數量
 * 主要的skill中很多業主沒有填寫,而寫在工作內容或其它內.
 * 現在做法用關鍵字來查詢,其它,工作內容及skill有就加總計
 */
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import hd.crawler.doa.SqlHelper;

public class Tdbtotal {
	static ResultSet rs;
	static SqlHelper sp = new SqlHelper();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList temre = new ArrayList<String>();
		Vector count = new Vector();
		int Count =0; //記錄總數
		FileReader fr = new FileReader("typeset.txt");
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			temre.add(br.readLine());
		}
		fr.close();
		for(int i = 0 ; i < temre.size() ; i++){
			count.add(getTypeCount(temre.get(i).toString()));
		}
		//String t = "'%Java%'";  //主要查詢的變數   '%  %' 要保留
		for(int x = 0 ; x < temre.size() ; x++){
			System.out.println(temre.get(x).toString()+" : "+count.get(x));
		}
	}
	private static int getTypeCount(String type){
		int count=0;
		String t = "'%"+type+"%'";
		String sql = "select * from jobtable where skill LIKE "+t+" OR jobDescription LIKE "+t+" OR other LIKE" +t;
		rs = sp.excuteQuery(sql, null);
		Vector temp = new Vector();
		try {
			ResultSetMetaData rsmt = rs.getMetaData();
			while(rs.next()){
				count++;
				Vector tp = new Vector();
				for(int i = 0 ; i <rsmt.getColumnCount() ; i++){
					tp.add(rs.getString(i + 1));
				}
				temp.add(tp);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int x = 0 ; x < temp.size() ; x++){
			System.out.println(temp.get(x).toString());
		}
		System.out.println(count);
		
		
		return count;
	}

}
