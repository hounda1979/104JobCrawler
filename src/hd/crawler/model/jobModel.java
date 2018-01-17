package hd.crawler.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import hd.crawler.doa.SqlHelper;

public class jobModel extends AbstractTableModel {
	SqlHelper sp = new SqlHelper();
	private ResultSet rs;
	private Vector rows,columns;

	/**
	 * 刪除表
	 * 
	 * @param table 要刪除的表名
	 * @return
	 */
	public boolean delTable(String table) throws SQLException {
		boolean b = true;

		String sql = "DROP TABLE IF EXISTS "+table;
		try {
			sp.executeUpdate(sql, null);
		} catch (Exception se) {
			b = false;
		}
		return b;
	}
	/**
	 * 創建表
	 * @param table 創建表名
	 * @return
	 */
	public boolean CreatTable(String table) {
		String sql = "";
		boolean b = true;
				
			sql = " CREATE TABLE jobtable(ID INTEGER PRIMARY KEY AUTOINCREMENT ,coname VARCHAR (255),jobname STRING (255),jobtype STRING (255),academic STRING (20),area STRING (255),salary TRING (50),skill STRING (255),jobDescription STRING (255),other STRING (255));";
		
		
		try{
			
			sp.executeUpdate(sql, null);
		// sp.executeUpdate(sql, null);
		}catch(Exception e){
			b = false;
		}
		return b;

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return ((Vector)rows.get(arg0)).get(arg1);
	}

}
