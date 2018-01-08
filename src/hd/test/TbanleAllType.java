package hd.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hd.crawler.doa.SqlHelper;

public class TbanleAllType {
	private ResultSet rs;
	SqlHelper sp = new SqlHelper();
	Vector alldata;

	public TbanleAllType() throws IOException {
		baleAllType();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			TbanleAllType ta = new TbanleAllType();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Vector baleAllType() throws IOException {
		Vector temp = new Vector();
		temp = this.getAlldata();
		Vector tm = new Vector();
		Vector col, row;
		col = new Vector();
		row = new Vector();
		try {
			FileReader fr = new FileReader("typeset.txt");
			BufferedReader br = new BufferedReader(fr);
			try {
				while (br.ready()) {
					tm.add(br.readLine());
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
		row = new Vector();
		col = new Vector();
		for (int i = 0; i < temp.size(); i++) {
			String[] temp1 = new String[((Vector) temp.get(i)).size()];
			((Vector) temp.get(i)).toArray(temp1);

			row.add(gettypeda(tm, temp1));
			
		}
		
		FileWriter fr = new FileWriter("D:/104dbhtml/TT.txt");
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
		return alldata;

	}

	private Vector gettypeda(Vector tm, String[] temp) {
		Vector tp = new Vector();
		boolean b = false;
		int[] countNum = new int[20];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < tm.size(); j++) {
				Pattern p1 = Pattern.compile(tm.get(j).toString());
				Matcher m1 = p1.matcher(temp[i]);
				if (m1.find()) {
					countNum[j] = 1;
					
				}
			}
		}
		
			tp.add(temp[1]);
			tp.add(temp[2]);
			for(int z = 0 ; z< countNum.length ; z++){
				tp.add(countNum[z]);
			
		}
		//System.out.println(tp.toString());
		return tp;
	}

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
