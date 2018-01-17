package hd.crawler.doa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 這sqlhelper是為了sqlite所使用
 * 因為sqlite是輕量的資料庫
 */
public class SqlHelper {
	//定義所需變量
	private static Connection ct =null;
	public static Connection getCt() {
		return ct;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static ResultSet getRs() {
		return rs;
	}

	//static Statement sm = null;
	//使用preparedstatement 可以防止sql注入問題
	private static PreparedStatement ps = null; 
	private static ResultSet rs = null;
	
	//加載驅動,只需一次
	static{
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//得到連接
	public static Connection getConnection(){
		try {
			//ct=DriverManager.getConnection("jdbc:sqlite:D:\\Testdb\\104job.db");
			ct=DriverManager.getConnection("jdbc:sqlite:104job.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ct;
	}
	public static void CreateTable(String sql){
		Statement stmt =null;
		try {
			stmt = ct.createStatement();
			stmt.executeQuery(sql);
			ct.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//先寫一個 update / delete / insert
	//sql 格式: update 表名 set 字段名=? where 字段=?
	//parameters 應該是 {"abd",123}
	public static void executeUpdate(String sql , String[] parameters){
		//1.創建一個ps
		
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			//給?賦值
			if(parameters != null){
				for(int i = 0 ; i <parameters.length;i++){
					ps.setString(i+1, parameters[i]);
				}
			}
			//執行
			int nu = ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//開發階段
			//拋出異常,抛出運行異常,可以給調用該函數的函數一個選擇
			//可以處理,也可以放棄處理
			throw new RuntimeException(e.getMessage());
		}finally{
			//關閉資源
			close(rs,ps,ct);
		}
		
	}
	//統一的select
	
	public static ResultSet excuteQuery(String sql , String []parameters){
		try{
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			if(parameters!=null && !parameters.equals("")){
				
				for(int i = 0 ; i <parameters.length ; i++){
					ps.setString(i+1,parameters[i] );
				}
			}
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			//close(rs, ps, ct);
		}
		return rs;
	}
	//如果有多個 update / delete / insert [需要考慮事務處理
	public static void executeUpdate2(String sql[] , String [][] parameters){
		
		try{
			//核心
			//1,取得連椄
			ct = getConnection();
			
			//因為這時用戶傳入的可能是多個sql語句.
			ct.setAutoCommit(false);
			//...
			for(int i =0;i<sql.length; i++){
				if(parameters[i]!=null){
					ps= ct.prepareStatement(sql[i]);
				for(int j=0;j<parameters[i].length;j++){
					ps.setString(j+1,parameters[i][j] );
				}
				ps.executeUpdate();
				}
			}
			
			
			ct.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			//回滾
			try {
				ct.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			
			close(rs,ps,ct);
		}
		
	}
	
	//關閉資源的函數
	public static void close(ResultSet rs , Statement ps , Connection ct){
		if(rs != null){
			try{
				rs.close();
			}catch(Exception e ){
				e.printStackTrace();
			}
			rs = null;
		}
		if(ps!=null){
			try{
				ps.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			ps = null;
		}
		if(ct!=null){
			try{
				ct.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			ct = null;
		}
	}
}
	
