package hd.crawler.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Cltxt {
	private String saveUrl; //存放路徑
	private String Localhtml; //本地端路徑
	private String SQLiteDbName ; //db存放路徑
	private String CSVFileName; //csv 檔案存放名
	private String cat ; //查詢職務
	private String area ; //查詢地區
 	public String getSaveUrl() {
		return saveUrl;
	}
	public String getSQLiteDbName(){
		return SQLiteDbName;
	}
	public String getCSVFileName() {
		return CSVFileName;
	}
	public String getCat() {
		return cat;
	}
	public String getArea() {
		return area;
	}
	public String getLocalhtml() {
		return Localhtml;
	}
	
	public Cltxt(){
		getString();
	}
	private void getString(){
		try {
			FileReader fr = new FileReader("Urlinfo.txt");
			BufferedReader br = new BufferedReader(fr);
			
			try {
				while(br.ready()){
					String str = br.readLine();
					String[] strsplit = str.split(",");
					
					switch(strsplit[0]){
					case "SaveUrl" :
						this.saveUrl =strsplit[1];
						break;
					case "Localhtml" :
						this.Localhtml = strsplit[1];
						break;
					case "SQLiteDbName" :
						this.SQLiteDbName = strsplit[1];
						break;
					case "CSVFileName" :
						this.CSVFileName = strsplit[1];
						break;						
					case "cat" :
						this.cat = strsplit[1];
						break;
					case "area" :
						this.area = strsplit[1];
						break;
					
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(this.getLocalhtml());
		File f = new File(Localhtml);
		
		if(f.exists()){
			System.out.println("找到Localhtml的文件夾不需生成");
		}else{
			boolean f_true = f.mkdirs(); 
			if(f_true){
			 System.out.println("文件夾生成OK");	
			}else{
				System.out.println("文件夾生成失敗");
				return;
			}
		
		}
	
		
	}
	
	
	
}
