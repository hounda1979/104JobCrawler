package hd.crawler.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hd.crawler.doa.SqlHelper;

public class dbTocsv {

	private ResultSet rs;
	SqlHelper sp = new SqlHelper();
	Vector alldata; 
	Cltxt cl;
	
	public dbTocsv(Cltxt cl){
		this.cl = cl;
		try {
			baleAllType();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void baleAllType() throws IOException {
		Vector temp = new Vector();//
		temp = this.getAlldata();//所有資料
		Vector tm = new Vector();//存入技能
		Vector col, row;
		try {
			FileReader fr = new FileReader("typeset.txt"); //請入技能檔
			BufferedReader br = new BufferedReader(fr);
			try {
				while (br.ready()) {
					tm.add(br.readLine()); //把技能變數加入tm中
				}
				fr.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		row = new Vector(); //列
		col = new Vector(); //行
		for (int i = 0; i < temp.size(); i++) {
			String[] temp1 = new String[((Vector) temp.get(i)).size()];
			((Vector) temp.get(i)).toArray(temp1);

			row.add(gettypeda(tm, temp1));
			
		}
		
		FileWriter fr = new FileWriter(cl.getLocalhtml()+cl.getCSVFileName());
		fr.write("公司名稱,職務名稱,");
		for(int g = 0 ; g < tm.size() ; g++){
			if(g == tm.size()-1){
				fr.write(tm.get(g).toString()+"\n");
			}else{
				fr.write(tm.get(g).toString()+",");
			}
		}
		
		for(int z =0 ; z<row.size();z++){
			fr.write(row.get(z).toString()+"\n");
		}
		fr.flush();
		fr.close();
		

	}
	/**
	 * 
	 * @param tm 技能變數
	 * @param temp 所有資料
	 * @return tp 資料重整
	 */
	private Vector gettypeda(Vector tm, String[] temp) {
		Vector tp = new Vector();
		int[] countNum = new int[tm.size()];
		for (int i = 0; i < temp.length; i++) { 
				//所有資料的列數
			for (int j = 0; j < tm.size(); j++) {
				//技能列數
				if(tm.get(j).toString().equals("C")){
					Pattern p1 = Pattern.compile("C.");
					Matcher m1 = p1.matcher(temp[i]);
					while(m1.find()){
						String jop = m1.group();
						String f = jop.trim();
						if(f.equals("C")){
							countNum[j] = 1;
						}
					}
				}else if(tm.get(j).toString().equals("C++")){
					Pattern p1 = Pattern.compile("C.");
					Matcher m1 = p1.matcher(temp[i]);
					while(m1.find()){
						String jop = m1.group();
						String f = jop.trim();
						if(f.equals("C+")){
							countNum[j] = 1;
						}
					}
				}else if(tm.get(j).toString().equals("C#")){
					Pattern p1 = Pattern.compile("C.");
					Matcher m1 = p1.matcher(temp[i]);
					while(m1.find()){
						String jop = m1.group();
						String f = jop.trim();
						if(f.equals("C#")){
							countNum[j] = 1;
						}
					}
				}else{
					Pattern p1 = Pattern.compile(tm.get(j).toString());
					Matcher m1 = p1.matcher(temp[i]);
					if (m1.find()) {
						countNum[j] = 1;
						
					}
				}
				
			}
		}
		
			tp.add(temp[1]);
			tp.add(temp[2]);
			for(int z = 0 ; z< countNum.length ; z++){
				tp.add(countNum[z]);
			
		}
		//System.out.println(tp.toString());
		return tp ;
	}
	/**
	 * 取得資料庫中所有資料
	 * @return
	 */
	private Vector getAlldata() {
		// Vector temp1 = new Vector();
		alldata = new Vector();
		String sql = "select * from jobtable";
		rs = sp.excuteQuery(sql, null);
		try {
			ResultSetMetaData rsmt = rs.getMetaData();
			while (rs.next()) {
				Vector temp1 = new Vector();
				for (int i = 0; i < rsmt.getColumnCount(); i++) {
					temp1.add(rs.getString(i + 1));
				}
				alldata.add(temp1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alldata;
	}
}
